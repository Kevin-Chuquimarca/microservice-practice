import "../app/globals.css";
import { useForm } from "react-hook-form";
import { useState } from "react";
import Link from "next/link";

export default function LoginForm({url, urlAdm}) {
  const { register, handleSubmit } = useForm();
  const [access, setAccess] = useState(false);

  const postUsers = async (admin) => {
    console.log(admin);
    const res = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(admin),
    });
    return await res.json();
  };

  const onSubmit = (admin, e) => {
    e.target.reset();
    postUsers(admin).then((res) => {
      setAccess(res["access"]);
    });
  };

  return (
    <>
      <form className="m-4" onSubmit={handleSubmit(onSubmit)}>
        <h2>Inicio de Sesión Administrador</h2>
        <div className="my-3">
          <label htmlFor="username">Usuario</label>
          <input
            className="ms-3"
            type="text"
            {...register("username", { required: true })}
            placeholder="Ingrese su usuario"
          />
        </div>
        <div className="my-3">
          <label htmlFor="password">Contraseña</label>
          <input
            className="ms-3"
            type="password"
            {...register("password", { required: true })}
            placeholder="Ingrese su contraseña"
          />
        </div>
        <div className="my-3">
          <button className="btn btn-primary me-3" type="submit">
            Validar
          </button>
          {access && (
            <button
              className="btn btn-success ms-3"
              onClick={(e) => {
                e.preventDefault();
              }}
            >
              <Link href={urlAdm}>Ingresar</Link>
            </button>
          )}
        </div>
      </form>
    </>
  );
}
