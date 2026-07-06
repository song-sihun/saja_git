"use client";

import { FormEvent, useState } from "react";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

export default function SignupPage() {
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setLoading(true);
    setMessage("");

    const form = new FormData(event.currentTarget);
    const res = await fetch(`${API_BASE_URL}/users`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: form.get("username"),
        password: form.get("password"),
        email: form.get("email"),
        name: form.get("name"),
      }),
    }).catch(() => null);

    setLoading(false);
    setMessage(res?.ok ? "회원가입이 완료되었습니다. 로그인해 주세요." : "회원가입에 실패했습니다. 백엔드 실행 상태와 입력값을 확인하세요.");
  }

  return (
    <main className="authPage">
      <a className="brand" href="/">BoardHub</a>
      <section className="authCard">
        <h1>회원가입</h1>
        <p>아이디, 이메일, 이름을 입력해 새 계정을 만드세요.</p>
        <form onSubmit={onSubmit}>
          <label>
            아이디
            <input name="username" required autoComplete="username" />
          </label>
          <label>
            비밀번호
            <input name="password" type="password" required autoComplete="new-password" />
          </label>
          <label>
            이메일
            <input name="email" type="email" required autoComplete="email" />
          </label>
          <label>
            이름
            <input name="name" required autoComplete="name" />
          </label>
          <button className="primaryButton" disabled={loading}>{loading ? "가입 중..." : "회원가입"}</button>
        </form>
        {message && <strong className="formMessage">{message}</strong>}
        <span className="authSwitch">이미 계정이 있나요? <a href="/login">로그인</a></span>
      </section>
    </main>
  );
}
