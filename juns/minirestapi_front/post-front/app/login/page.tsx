"use client";

import { FormEvent, useState } from "react";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

export default function LoginPage() {
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const githubLoginUrl = `${API_BASE_URL}/oauth2/authorization/github`;

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    const form = new FormData(event.currentTarget);
    const res = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: form.get("username"),
        password: form.get("password"),
      }),
    }).catch(() => null);

    setLoading(false);
    if (!res?.ok) {
      setMessage("로그인에 실패했습니다. 백엔드 실행 상태와 계정을 확인하세요.");
      return;
    }

    const token = await res.json();
    localStorage.setItem("accessToken", token.accessToken);
    localStorage.setItem("refreshToken", token.refreshToken);
    document.cookie = `accessToken=${token.accessToken}; path=/; SameSite=Lax`;
    document.cookie = `refreshToken=${token.refreshToken}; path=/; SameSite=Lax`;
    window.location.assign("/");
  }

  return (
    <main className="authPage">
      <a className="brand" href="/">BoardHub</a>
      <section className="authCard">
        <h1>로그인</h1>
        <p>BoardHub 계정으로 게시글과 댓글을 관리하세요.</p>
        <form onSubmit={onSubmit}>
          <label>
            아이디
            <input name="username" required autoComplete="username" />
          </label>
          <label>
            비밀번호
            <input name="password" type="password" required autoComplete="current-password" />
          </label>
          <button className="primaryButton" disabled={loading}>{loading ? "로그인 중..." : "로그인"}</button>
        </form>
        <div className="authDivider"><span>또는</span></div>
        <a className="githubButton" href={githubLoginUrl}>
          <span>GitHub</span>
          GitHub로 로그인
        </a>
        {message && <strong className="formMessage">{message}</strong>}
        <span className="authSwitch">계정이 없나요? <a href="/signup">회원가입</a></span>
      </section>
    </main>
  );
}
