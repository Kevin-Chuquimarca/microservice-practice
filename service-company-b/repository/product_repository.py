from model.product import Product

class ProductRepository:
    def __init__(self, db_connection):
        self.db_connection = db_connection
    
    def get_list_products(self):
        self.db_connection.cursor.execute("SELECT * FROM PRODUCT")
        products = []
        for row in self.db_connection.cursor:
            product = Product(row[0], row[1], row[2], row[3], row[4])
            products.append(product)
        return products
    
    def save_product(self, product):
        self.db_connection.cursor.execute("INSERT INTO PRODUCT(NAMEPROD, QUANTITYPROD, PRICEPROD, COMPANYPROD) VALUES (:1, :2, :3, :4)", (product.nameprod, product.quantityprod, product.priceprod, product.companyprod, ))
        print("Product saved successfully")

    def update_product(self, product):
        self.db_connection.cursor.execute("UPDATE PRODUCT SET NAMEPROD=:1, QUANTITYPROD=:2, PRICEPROD=:3, COMPANYPROD=:4 WHERE IDPROD=:5", (product.nameprod, product.quantityprod, product.priceprod, product.companyprod, product.idprod))
        print("Product updated successfully")

    def delete_product(self, idprod):
        self.db_connection.cursor.execute("DELETE FROM PRODUCT WHERE IDPROD=:1", (idprod,))
        print("Product deleted successfully")