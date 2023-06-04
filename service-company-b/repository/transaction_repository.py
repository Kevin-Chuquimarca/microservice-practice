from model.transaction import Transaction

class TransactionRepository:
    def __init__(self, db_connection):
        self.db_connection = db_connection

    def get_list_transactions(self):
        self.db_connection.cursor.execute("SELECT * FROM TRANSACTION")
        transaction = []
        for row in self.db_connection.cursor:
            product = Transaction(row[0], row[1], row[2], row[3])
            transaction.append(product)
        return transaction

    def save_transaction(self, transaction: Transaction):
        self.db_connection.cursor.execute("INSERT INTO TRANSACTION(NAMETRANS, TYPETRANS, OBSTRANS, IDPROD) VALUES (:1, :2, :3, :4)", (transaction.name, transaction.type, transaction.obs, transaction.id_prod))
        print("Transaction saved successfully")
    