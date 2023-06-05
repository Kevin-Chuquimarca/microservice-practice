from model.transaction import Transaction

class TransactionRepository:
    def __init__(self, db_connection):
        self.db_connection = db_connection

    def get_list_transactions(self):
        self.db_connection.cursor.execute("SELECT * FROM TRANSACTION")
        transaction = []
        for row in self.db_connection.cursor:
            product = Transaction(row[0], row[1], row[2], row[3], row[4])
            transaction.append(product)
        return transaction

    def save_transaction(self, transaction: Transaction):
        self.db_connection.cursor.execute("INSERT INTO TRANSACTION(IDPROD, NAMETRANS, TYPETRANS, OBSTRANS) VALUES (:1, :2, :3, :4)", (transaction.id_prod, transaction.name_trans, transaction.type_trans, transaction.obs_trans))
        print("Transaction saved successfully")
    
    def delete_transactions_to_product(self, idprod):
        self.db_connection.cursor.execute("DELETE FROM TRANSACTION WHERE IDPROD = :1", (idprod,))
        print("Transactions deleted successfully")