export default function OAuthCallbackPage() {
  const script = `
    const params = new URLSearchParams(window.location.search);
    const accessToken = params.get("accessToken");
    const refreshToken = params.get("refreshToken");
    if (accessToken && refreshToken) {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      document.cookie = "accessToken=" + accessToken + "; path=/; SameSite=Lax";
      document.cookie = "refreshToken=" + refreshToken + "; path=/; SameSite=Lax";
      window.location.replace("/");
    }
  `;

  return (
    <main className="authPage">
      <script dangerouslySetInnerHTML={{ __html: script }} />
      <a className="brand" href="/">BoardHub</a>
      <section className="authCard">
        <h1>GitHub 로그인</h1>
        <p>GitHub 로그인을 처리하고 있습니다.</p>
        <a className="primaryButton" href="/">홈으로 이동</a>
      </section>
    </main>
  );
}
