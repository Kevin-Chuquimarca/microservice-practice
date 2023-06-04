class Product:
    def __init__(self, idprod, nameprod, quantityprod, priceprod, companyprod):
        self.idprod = idprod
        self.nameprod = nameprod
        self.quantityprod = quantityprod
        self.priceprod = priceprod
        self.companyprod = companyprod

    def __str__(self) -> str:
        return f"Product(id={self.idprod}, name={self.nameprod}, quantity={self.quantityprod}, price={self.priceprod}, company={self.companyprod})"