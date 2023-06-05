import Link from "next/link";
import "../app/globals.css";
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";

export default function AdminCompanyB() {
  const [products, setProducts] = useState([]);
  const [onUpdate, setOnUpdate] = useState(false);
  const [onCreate, setOnCreate] = useState(false);
  const [id, setId] = useState("");
  const [idProd, setIdProd] = useState("");
  const [crudOperation, setCrudOperation] = useState("");
  const { register, handleSubmit } = useForm();

  useEffect(() => {
    getProducts();
  }, []);

  const getProducts = async () => {
    const res = await fetch("http://localhost:8060/cb/products");
    const data = await res.json();
    setProducts(data);
  };

  const postProduct = async (data) => {
    const res = await fetch("http://localhost:8060/cb/product", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nameprod: data.nameprod,
        quantityprod: data.quantityprod,
        priceprod: data.priceprod,
        companyprod: "B",
      }),
    });
  };

  const postTransaction = async (data) => {
    const res = await fetch("http://localhost:8060/cb/transaction", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        idprod: idProd,
        nametrans: data.nametrans,
        typetrans: data.typetrans,
        obstrans: data.obstrans,
      }),
    });
  };

  const putProduct = async (data) => {
    const res = await fetch("http://localhost:8060/cb/product/" + idProd, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nameprod: data.nameprod,
        quantityprod: data.quantityprod,
        priceprod: data.priceprod,
        companyprod: "B",
      }),
    });
  };

  const deleteProduct = async () => {
    const res = await fetch("http://localhost:8060/cb/product/" + idProd, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });
  };

  const onSubmit = (data, e) => {
    e.target.reset();
    if (crudOperation == "update") {
      putProduct(data);
      postTransaction(data);
    }
    if (crudOperation == "create") {
      postProduct(data);
    }
    if (crudOperation == "delete") {
      deleteProduct();
    }
    setCrudOperation("");
    setTimeout(() => {
      location.reload();
    }, 1000);
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <h2 className="text-center">GESTIÓN DE PRODUCTOS COMPAÑIA A</h2>
        {onUpdate && (
          <div className="m-4">
            <div className="my-3">
              <label>Código: </label>
              <input
                className="mx-3"
                type="text"
                {...register("nametrans", { required: true })}
                placeholder="Ingrese el código"
              />
            </div>
            <div className="my-3">
              <label>Transacción: </label>
              <select className="mx-3" {...register("typetrans")}>
                <option value="venta">Venta</option>
                <option value="devolucion">Devolución</option>
              </select>
            </div>
            <div className="my-3">
              <label>Observación: </label>
              <input
                className="mx-3"
                type="text"
                {...register("obstrans", { required: true })}
                placeholder="Ingrese las observaciones"
              />
            </div>
          </div>
        )}
        <div className="d-flex">
          <table className="table table-dark table-striped m-4">
            <thead>
              <tr>
                <th>Nombre Producto</th>
                <th>Cantidad</th>
                <th>Precio</th>
                <th>Operaciones</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product) =>
                onUpdate && id == product.idprod ? (
                  <tr key={product.idprod}>
                    <td>
                      <input
                        className="mx-3"
                        type="text"
                        defaultValue={product.nameprod}
                        {...register("nameprod", { required: true })}
                      />
                    </td>
                    <td>
                      <input
                        className="mx-3"
                        type="text"
                        defaultValue={product.quantityprod}
                        {...register("quantityprod", { required: true })}
                      />
                    </td>
                    <td>
                      <input
                        className="mx-3"
                        type="text"
                        defaultValue={product.priceprod}
                        {...register("priceprod", { required: true })}
                      />
                    </td>
                    <td>
                      <button
                        className="btn btn-warning me-2"
                        type="submit"
                        onClick={() => {
                          setCrudOperation("update");
                          setOnUpdate(!onUpdate);
                          setId("");
                        }}
                      >
                        Guardar Cambios
                      </button>
                      <button
                        className="btn btn-danger ms-2"
                        onClick={(e) => {
                          setOnUpdate(!onUpdate);
                          setId("");
                          e.preventDefault();
                        }}
                      >
                        Cancelar
                      </button>
                    </td>
                  </tr>
                ) : (
                  <tr key={product.idprod}>
                    <td>{product.nameprod}</td>
                    <td>{product.quantityprod}</td>
                    <td>$ {product.priceprod}</td>
                    <td>
                      {(!onUpdate || idProd == product.idprod) && (
                        <>
                          <button
                            className="btn btn-warning me-2"
                            onClick={(e) => {
                              setOnUpdate(!onUpdate);
                              setId(product.idprod);
                              setIdProd(product.idprod);
                              e.preventDefault();
                            }}
                          >
                            Editar
                          </button>
                          <button
                            className="btn btn-danger ms-2"
                            onClick={(e) => {
                              setIdProd(product.idprod);
                              setCrudOperation("delete");
                            }}
                          >
                            Eliminar
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                )
              )}
              {onCreate && (
                <tr>
                  <td>
                    <input
                      className="mx-3"
                      type="text"
                      {...register("nameprod", { required: true })}
                    />
                  </td>
                  <td>
                    <input
                      className="mx-3"
                      type="text"
                      {...register("quantityprod", { required: true })}
                    />
                  </td>
                  <td>
                    <input
                      className="mx-3"
                      type="text"
                      {...register("priceprod", { required: true })}
                    />
                  </td>
                  <td>
                    <button
                      className="btn btn-warning me-2"
                      type="submit"
                      onClick={() => {
                        setCrudOperation("create");
                        setId("");
                      }}
                    >
                      Guardar
                    </button>
                    <button
                      className="btn btn-danger ms-2"
                      onClick={(e) => {
                        setOnCreate(!onCreate);
                        setId("");
                        e.preventDefault();
                      }}
                    >
                      Cancelar
                    </button>
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
        {onCreate || onUpdate || (
          <button
            className="btn btn-primary ms-4"
            onClick={(e) => {
              setOnCreate(!onCreate);
              setIdProd("");
              e.preventDefault();
            }}
          >
            Agregar
          </button>
        )}
      </form>
      <button className="btn btn-secondary m-4">
        <Link href="/">Regresar</Link>
      </button>
    </>
  );
}
