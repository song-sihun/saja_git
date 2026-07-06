import { cookies } from "next/headers";

type SpringPage<T> = { content?: T[] };

type Post = {
  id: number;
  title: string;
  userName?: string;
  createdAt?: string;
};

type Me = {
  email?: string;
  username?: string;
};

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

const categories = [
  ["💬", "자유게시판", "자유로운 주제의 이야기를 나눠요"],
  ["❔", "질문/답변", "궁금한 내용을 질문하고 답변을 찾아요"],
  ["</>", "개발", "개발 지식과 경험을 공유해요"],
  ["💼", "취업", "취업 정보와 합격 후기를 나눠요"],
  ["📖", "스터디", "스터디 모집과 학습 자료를 공유해요"],
  ["📣", "공지사항", "서비스 공지 및 안내사항을 확인하세요"],
];

async function getHomeData() {
  const accessToken = (await cookies()).get("accessToken")?.value;
  if (!accessToken) {
    return { me: null, posts: [], message: "로그인하면 백엔드 게시글을 불러옵니다." };
  }

  const headers = { Authorization: `Bearer ${accessToken}` };

  try {
    const [meRes, postsRes] = await Promise.all([
      fetch(`${API_BASE_URL}/users/me`, { headers, cache: "no-store" }),
      fetch(`${API_BASE_URL}/posts?size=5`, { headers, cache: "no-store" }),
    ]);

    const me = meRes.ok ? ((await meRes.json()) as Me) : null;
    if (!postsRes.ok) throw new Error("posts failed");

    const data = (await postsRes.json()) as SpringPage<Post>;
    const posts = data.content ?? [];
    return { me, posts, message: posts.length ? "" : "백엔드 게시글이 없습니다." };
  } catch {
    return { me: null, posts: [], message: "백엔드에서 게시글을 가져오지 못했습니다. 서버와 토큰을 확인하세요." };
  }
}

export default async function Home() {
  const { me, posts, message } = await getHomeData();

  return (
    <>
      <header className="topbar">
        <a className="brand" href="/">BoardHub</a>
        <nav>
          <a className="active" href="/">홈</a>
          <a href="#">카테고리</a>
          <a href="/posts">전체글</a>
          <a href="/posts">최신글</a>
          <a href="#">공지사항</a>
        </nav>
        <div className="actions">
          <button className="iconButton" aria-label="검색">⌕</button>
          {me ? (
            <>
              <span className="userEmail">{me.email ?? me.username}</span>
              <a className="ghostButton" href="/logout">로그아웃</a>
            </>
          ) : (
            <>
              <a className="ghostButton" href="/login">로그인</a>
              <a className="primaryButton" href="/signup">회원가입</a>
            </>
          )}
        </div>
      </header>

      <main>
        <section className="hero">
          <div className="heroCopy">
            <h1>관심사를 나누는<br /><span>커뮤니티 게시판</span></h1>
            <p>다양한 카테고리의 게시글을 탐색하고, 생각을 공유하며 더 나은 인사이트를 함께 만들어가세요.</p>
            <form className="searchBox">
              <span>⌕</span>
              <input placeholder="게시글을 검색해 보세요" suppressHydrationWarning />
              <button>검색</button>
            </form>
            <div className="tags">
              <b>인기 검색어:</b>
              {["# 개발", "# 취업", "# 면접후기", "# 스터디", "# 이직"].map((tag) => <span key={tag}>{tag}</span>)}
            </div>
          </div>
          <div className="heroArt" aria-hidden="true">
            <div className="bubble code">&lt;/&gt;</div>
            <div className="bubble chat">💬</div>
            <div className="panel">
              <i />
              <i />
              <i />
              <strong />
              <strong />
            </div>
            <div className="people">
              <span />
              <span />
              <span />
            </div>
          </div>
        </section>

        <section className="categoryGrid" aria-label="카테고리">
          {categories.map(([icon, title, desc]) => (
            <a className="categoryCard" href="#" key={title}>
              <span className="categoryIcon">{icon}</span>
              <strong>{title}</strong>
              <small>{desc}</small>
              <em>›</em>
            </a>
          ))}
        </section>

        <section className="contentGrid">
          <div className="boardCard latest">
            <div className="sectionTitle">
              <h2>최신 게시글</h2>
              <a href="/posts">더보기 ›</a>
            </div>
            {message && <p className="emptyMessage">{message}</p>}
            <ul className="postList">
              {posts.map((post, index) => (
                <li key={post.id}>
                  <span className={`label label${index % 5}`}>{["개발", "질문/답변", "취업", "자유게시판", "스터디"][index % 5]}</span>
                  <a href={`/posts/${post.id}`}>{post.title}</a>
                  <strong>{post.userName ?? "익명"}</strong>
                  <time>{post.createdAt ? new Date(post.createdAt).toLocaleDateString("ko-KR") : "-"}</time>
                  <span className="metric">◎ -</span>
                  <span className="metric">♡ -</span>
                </li>
              ))}
            </ul>
          </div>

          <aside className="sideStack">
            <div className="boardCard">
              <div className="sectionTitle">
                <h2>인기 게시글</h2>
                <a href="/posts">더보기 ›</a>
              </div>
              {posts.length ? (
                <ol className="rankList">
                  {posts.slice(0, 5).map((post, index) => (
                    <li key={post.id}><span>{index + 1}</span><a href={`/posts/${post.id}`}>{post.title}</a><small>-</small></li>
                  ))}
                </ol>
              ) : (
                <p className="emptyMessage">백엔드 게시글을 불러오면 표시됩니다.</p>
              )}
            </div>
            <div className="boardCard">
              <div className="sectionTitle">
                <h2>공지사항</h2>
              </div>
              <p className="emptyMessage">백엔드 공지 API가 아직 없습니다.</p>
            </div>
          </aside>
        </section>
      </main>

      <footer>
        <div>
          <strong>BoardHub</strong>
          <span>© 2024 BoardHub. All rights reserved.</span>
        </div>
        <nav>
          <a href="#">이용약관</a>
          <a href="#">개인정보처리방침</a>
          <a href="#">운영정책</a>
          <a href="#">문의하기</a>
        </nav>
      </footer>
    </>
  );
}
