import LoginForm from "@/components/login-form";
import Link from "next/link";

export default function LoginCompanyB() {
  return (
    <div>
      <h2 className="text-center">COMPAÑÍA B</h2>
      <LoginForm
        url={"http://127.0.0.1:8060/cb/admin"}
        urlAdm={"admin-company-b"}
      />
      <button className="btn btn-secondary ms-4">
        <Link href={"/"}>Regresar</Link>
      </button>
    </div>
  );
}
