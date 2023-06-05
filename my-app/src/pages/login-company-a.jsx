import LoginForm from "@/components/login-form";
import Link from "next/link";

export default function LoginCompanyA() {
  return (
    <div>
      <h2 className="text-center">COMPAÑÍA A</h2>
      <LoginForm
        url={"http://127.0.0.1:8080/ca/admin"}
        urlAdm={"admin-company-a"}
      />
      <button className="btn btn-secondary ms-4">
        <Link href={"/"}>Regresar</Link>
      </button>
    </div>
  );
}
