import oracledb

class DBConnection:
    def __init__(self):
        self.connection = oracledb.connect(user="system",password="root",dsn="localhost/XE", port=1522)
        print("Successfully connected to Oracle Database")
        self.cursor = self.connection.cursor()