import Link from "next/link";

export default function Home() {
  return (
    <ul>
      <li>
        <Link href="/login-company-a">Login company A</Link>
      </li>
      <li>
        <Link href="/login-company-b">Login company B</Link>
      </li>
      <li>
        <Link href="/products-stock">Stock de Productos</Link>
      </li>
    </ul>
  );
}
