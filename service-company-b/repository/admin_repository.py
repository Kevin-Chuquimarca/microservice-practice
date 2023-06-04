from model.admin import Admin

class AdminRepository:
    def __init__(self, db_connection):
        self.db_connection = db_connection
    
    def validate_user(self, username, password):
        self.db_connection.cursor.execute("SELECT * FROM USERADMIN WHERE USERACCOUNT=:1 AND PASSACCOUNT=:2", (username, password))
        if self.db_connection.cursor.fetchone():
            return True
        else:
            return False
        