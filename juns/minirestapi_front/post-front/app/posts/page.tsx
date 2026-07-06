import { cookies } from "next/headers";

type SpringPage<T> = { content?: T[] };

type Post = {
  id: number;
  title: string;
  userName?: string;
  createdAt?: string;
};

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

async function getPosts() {
  const accessToken = (await cookies()).get("accessToken")?.value;
  if (!accessToken) return { posts: [], message: "로그인하면 전체글을 볼 수 있습니다." };

  try {
    const res = await fetch(`${API_BASE_URL}/posts?size=20`, {
      headers: { Authorization: `Bearer ${accessToken}` },
      cache: "no-store",
    });
    if (!res.ok) throw new Error("posts failed");
    const data = (await res.json()) as SpringPage<Post>;
    return { posts: data.content ?? [], message: "" };
  } catch {
    return { posts: [], message: "백엔드에서 글 목록을 가져오지 못했습니다." };
  }
}

export default async function PostsPage() {
  const { posts, message } = await getPosts();

  return (
    <main className="subPage">
      <div className="subHeader">
        <a className="brand" href="/">BoardHub</a>
        <div>
          <a className="ghostButton" href="/">홈</a>
          <a className="primaryButton" href="/posts/new">글쓰기</a>
        </div>
      </div>

      <section className="boardCard">
        <div className="sectionTitle">
          <h1>전체글</h1>
          <span>{posts.length}개</span>
        </div>
        {message && <p className="emptyMessage">{message}</p>}
        <ul className="fullPostList">
          {posts.map((post) => (
            <li key={post.id}>
              <a href={`/posts/${post.id}`}>{post.title}</a>
              <span>{post.userName ?? "익명"}</span>
              <time>{post.createdAt ? new Date(post.createdAt).toLocaleDateString("ko-KR") : "-"}</time>
            </li>
          ))}
        </ul>
      </section>
    </main>
  );
}
