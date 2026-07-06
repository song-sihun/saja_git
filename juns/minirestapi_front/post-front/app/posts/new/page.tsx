import { cookies } from "next/headers";
import { redirect } from "next/navigation";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

async function createPost(formData: FormData) {
  "use server";

  const accessToken = (await cookies()).get("accessToken")?.value;
  if (!accessToken) redirect("/login");

  const res = await fetch(`${API_BASE_URL}/posts`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      title: formData.get("title"),
      content: formData.get("content"),
    }),
  });

  if (!res.ok) redirect("/posts/new?error=1");

  const post = (await res.json()) as { id: number };
  redirect(`/posts/${post.id}`);
}

export default async function NewPostPage({ searchParams }: { searchParams?: Promise<{ error?: string }> }) {
  const error = (await searchParams)?.error;

  return (
    <main className="subPage">
      <div className="subHeader">
        <a className="brand" href="/">BoardHub</a>
        <div>
          <a className="ghostButton" href="/posts">전체글</a>
          <a className="ghostButton" href="/">홈</a>
        </div>
      </div>

      <section className="boardCard editorCard">
        <h1>글쓰기</h1>
        <form action={createPost} className="postForm">
          <label>
            제목
            <input name="title" required maxLength={120} placeholder="제목을 입력하세요" />
          </label>
          <label>
            내용
            <textarea name="content" required rows={12} placeholder="내용을 입력하세요" />
          </label>
          {error && <p className="formMessage">글 저장에 실패했습니다. 로그인 상태와 백엔드를 확인하세요.</p>}
          <button className="primaryButton">등록</button>
        </form>
      </section>
    </main>
  );
}
