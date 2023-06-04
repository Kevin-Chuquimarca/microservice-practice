from flask import Flask, jsonify, make_response, request
from flask_cors import CORS
import py_eureka_client.eureka_client as eureka_client
from repository.product_repository import ProductRepository
from model.product import Product
from model.transaction import Transaction
from repository.admin_repository import AdminRepository
from repository.db_connection import DBConnection

rest_port = 8060
host_name = "0.0.0.0"
app_name = "service-company-b"

eureka_client.init(eureka_server="http://192.168.55.221:8761/eureka",
                   app_name=app_name,
                   instance_port=rest_port,
                   instance_id=app_name,
                   instance_ip=host_name)

app = Flask(__name__)
CORS(app)

db_connection = DBConnection()
product_repository = ProductRepository(db_connection=db_connection)
admin_repository = AdminRepository(db_connection=db_connection)

@app.route('/cb/products', methods=['GET'])
def get_products():
    products = product_repository.get_list_products()
    json_products = []
    for product in products:
        json_products.append({'idprod':product.idprod, 'nameprod':product.nameprod, 'quantityprod':product.quantityprod, 'priceprod':product.priceprod, 'companyprod':product.companyprod})
    return make_response(jsonify(json_products), 200)

@app.route('/cb/product', methods=['POST'])
def post_product():
    req = request.get_json()
    product_repository.save_product(Product('idprod',req['nameprod'], req['quantityprod'], req['priceprod'], req['companyprod']))
    return make_response(jsonify({'created':'created product', }), 201)

@app.route('/cb/product/<idprod>', methods=['PUT'])
def put_product(idprod):
    req = request.get_json()
    product_repository.update_product(Product(idprod,req['nameprod'], req['quantityprod'], req['priceprod'], req['companyprod']))
    return make_response(jsonify({'updated':'updated product', }), 200)

@app.route('/cb/product/<idprod>', methods=['DELETE'])
def delete_product(idprod):
    product_repository.delete_product(idprod)
    return make_response(jsonify({'deleted':'deleted product', }), 200)


@app.route('/cb/admin', methods=['POST'])
def post_admin():
    req = request.get_json()
    res = admin_repository.validate_user(req['username'], req['password'])
    return make_response(jsonify({'access':res, }), 201)


@app.route('/cb/transactions', methods=['GET'])
def get_transactions():
    transactions = admin_repository.get_list_transactions()
    json_transactions = []
    for transaction in transactions:
        json_transactions.append({'id':transaction.id, 'name':transaction.name, 'type':transaction.type, 'obs':transaction.obs, 'id_prod':transaction.id_prod})
    return make_response(jsonify(json_transactions), 200)

@app.route('/cb/transaction', methods=['POST'])
def post_transaction():
    req = request.get_json()
    admin_repository.save_transaction(Transaction('id',req['id_prod'], req['name'], req['type'], req['obs']))
    return make_response(jsonify({'created':'created transaction', }), 201)
    

if __name__ == '__main__':
    app.run(host=host_name, port=rest_port)