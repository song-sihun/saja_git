import { cookies } from "next/headers";
import { redirect } from "next/navigation";

type Comment = {
  id: number;
  comment: string;
  childrenDTO?: Comment[];
};

type PostDetail = {
  id: number;
  title: string;
  userName?: string;
  content?: string;
  comments?: Comment[];
};

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

async function getPost(id: string) {
  const accessToken = (await cookies()).get("accessToken")?.value;
  if (!accessToken) return { post: null, message: "로그인하면 글 상세를 볼 수 있습니다." };

  try {
    const res = await fetch(`${API_BASE_URL}/posts/${id}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
      cache: "no-store",
    });
    if (!res.ok) throw new Error("post failed");
    return { post: (await res.json()) as PostDetail, message: "" };
  } catch {
    return { post: null, message: "백엔드에서 글 상세를 가져오지 못했습니다." };
  }
}

async function createComment(formData: FormData) {
  "use server";

  const accessToken = (await cookies()).get("accessToken")?.value;
  if (!accessToken) redirect("/login");

  const postId = String(formData.get("postId") ?? "");
  const res = await fetch(`${API_BASE_URL}/comments`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify({
      postId: Number(postId),
      comment: formData.get("comment"),
      parentID: null,
    }),
  });

  redirect(res.ok ? `/posts/${postId}` : `/posts/${postId}?commentError=1`);
}

function CommentList({ comments }: { comments: Comment[] }) {
  if (!comments.length) return <p className="emptyMessage">댓글이 없습니다.</p>;

  return (
    <ul className="commentList">
      {comments.map((item) => (
        <li key={item.id}>
          <p>{item.comment}</p>
          {item.childrenDTO?.length ? <CommentList comments={item.childrenDTO} /> : null}
        </li>
      ))}
    </ul>
  );
}

export default async function PostDetailPage({
  params,
  searchParams,
}: {
  params: Promise<{ id: string }>;
  searchParams?: Promise<{ commentError?: string }>;
}) {
  const { id } = await params;
  const commentError = (await searchParams)?.commentError;
  const { post, message } = await getPost(id);

  return (
    <main className="subPage">
      <div className="subHeader">
        <a className="brand" href="/">BoardHub</a>
        <div>
          <a className="ghostButton" href="/posts">전체글</a>
          <a className="primaryButton" href="/posts/new">글쓰기</a>
        </div>
      </div>

      <article className="boardCard detailCard">
        {message && <p className="emptyMessage">{message}</p>}
        {post && (
          <>
            <header>
              <h1>{post.title}</h1>
              <span>{post.userName ?? "익명"}</span>
            </header>
            <p className="postContent">{post.content}</p>
          </>
        )}
      </article>

      {post && (
        <section className="boardCard">
          <div className="sectionTitle">
            <h2>댓글</h2>
            <span>{post.comments?.length ?? 0}개</span>
          </div>
          <form action={createComment} className="commentForm">
            <input type="hidden" name="postId" value={post.id} />
            <textarea name="comment" required rows={3} placeholder="댓글을 입력하세요" />
            {commentError && <p className="formMessage">댓글 저장에 실패했습니다.</p>}
            <button className="primaryButton">댓글 등록</button>
          </form>
          <CommentList comments={post.comments ?? []} />
        </section>
      )}
    </main>
  );
}
