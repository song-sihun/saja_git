import { redirect } from "next/navigation";
import { cookies } from "next/headers";

export async function GET() {
  const store = await cookies();
  store.delete("accessToken");
  store.delete("refreshToken");
  redirect("/");
}
