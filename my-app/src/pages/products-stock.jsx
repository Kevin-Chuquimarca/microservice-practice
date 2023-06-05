import "../app/globals.css";
import { useState, useEffect } from "react";

export default function ProductsStock() {
  const [products, setProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    getUsers();
  }, []);

  const getUsers = async () => {
    const res = await fetch("http://localhost:8080/product-company");
    const data = await res.json();
    setProducts(data);
  };

  const handleSearch = () => {
    const filteredProducts = products.filter((product) =>
      product.nameprod.includes(searchTerm)
    );
    setProducts(filteredProducts);
  };

  return (
    <>
      <h2 className="text-center">STOCK DE PRODUCTOS</h2>
      <div className="m-4">
        <label>PRODUCTO A BUSCAR: </label>
        <input
          className="mx-3"
          placeholder="Ingrese el nombre del producto"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button className="btn btn-primary" onClick={handleSearch}>
          Buscar
        </button>
      </div>
      <div className="d-flex">
        <table className="table table-dark table-striped m-4">
          <thead>
            <tr>
              <th>Nombre Producto</th>
              <th>Cantidad</th>
              <th>Precio</th>
              <th>Compañía</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr key={product.idprod}>
                <td>{product.nameprod}</td>
                <td>{product.quantityprod}</td>
                <td>{product.priceprod}</td>
                <td>{product.companyprod}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
