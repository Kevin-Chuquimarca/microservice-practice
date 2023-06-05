from flask import Flask, jsonify, make_response, request
from flask_cors import CORS
import sys
# import py_eureka_client.eureka_client as eureka_client
from repository.db_connection import DBConnection
from repository.product_repository import ProductRepository
from repository.transaction_repository import TransactionRepository
from repository.admin_repository import AdminRepository
from model.product import Product
from model.transaction import Transaction

rest_port = 8060
host_name = "0.0.0.0"
app_name = "service-company-b"

# eureka_client.init(eureka_server="http://192.168.55.221:8761/eureka",
#                    app_name=app_name,
#                    instance_port=rest_port,
#                    instance_id=app_name,
#                    instance_ip=host_name)

app = Flask(__name__)
CORS(app)

db_connection = DBConnection()
product_repository = ProductRepository(db_connection=db_connection)
admin_repository = AdminRepository(db_connection=db_connection)
transaction_repository = TransactionRepository(db_connection=db_connection)


@app.route('/cb/products', methods=['GET'])
def get_products():
    products = product_repository.get_list_products()
    json_products = []
    for product in products:
        json_products.append({'idprod': product.idprod, 'nameprod': product.nameprod,
                             'quantityprod': product.quantityprod, 'priceprod': product.priceprod, 'companyprod': product.companyprod})
    return make_response(jsonify(json_products), 200)


@app.route('/cb/product', methods=['POST'])
def post_product():
    req = request.get_json()
    product_repository.save_product(Product(
        'idprod', req['nameprod'], req['quantityprod'], req['priceprod'], req['companyprod']))
    return make_response(jsonify({'created': 'created product', }), 201)


@app.route('/cb/product/<idprod>', methods=['PUT'])
def put_product(idprod):
    req = request.get_json()
    product_repository.update_product(Product(
        idprod, req['nameprod'], req['quantityprod'], req['priceprod'], req['companyprod']))
    return make_response(jsonify({'updated': 'updated product', }), 200)


@app.route('/cb/product/<idprod>', methods=['DELETE'])
def delete_product(idprod):
    transaction_repository.delete_transactions_to_product(idprod)
    product_repository.delete_product(idprod)
    return make_response(jsonify({'deleted': 'deleted product', }), 200)


@app.route('/cb/admin', methods=['POST'])
def post_admin():
    req = request.get_json()
    res = admin_repository.validate_user(req['username'], req['password'])
    return make_response(jsonify({'access': res, }), 201)


@app.route('/cb/transactions', methods=['GET'])
def get_transactions():
    transactions = transaction_repository.get_list_transactions()
    json_transactions = []
    for transaction in transactions:
        json_transactions.append({'idtrans': transaction.id_trans, 'idprod': transaction.id_prod,
                                 'nametrans': transaction.name_trans, 'typetrans': transaction.type_trans, 'obstrans': transaction.obs_trans})
    return make_response(jsonify(json_transactions), 200)


@app.route('/cb/transaction', methods=['POST'])
def post_transaction():
    req = request.get_json()
    transaction_repository.save_transaction(Transaction(
        'idtrans', req['idprod'], req['nametrans'], req['typetrans'], req['obstrans']))
    return make_response(jsonify({'created': 'created transaction', }), 201)

@app.route('/cb/transactions/<idprod>', methods=['DELETE'])
def delete_transactions_to_product(idprod):
    transaction_repository.delete_transactions_to_product(idprod)
    return make_response(jsonify({'deleted': 'deleted transactions', }), 200)

if __name__ == '__main__':
    arguments = sys.argv[1:]
    print(arguments[0])
    if len(arguments) > 0:
        app.run(host=host_name, port=arguments[0])
