"use client";

import { useEffect, useState } from "react";

export default function OAuthErrorPage() {
  const [message, setMessage] = useState("GitHub 로그인에 실패했습니다.");

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    setMessage(params.get("message") || "GitHub 로그인에 실패했습니다.");
  }, []);

  return (
    <main className="authPage">
      <a className="brand" href="/">BoardHub</a>
      <section className="authCard">
        <h1>로그인 실패</h1>
        <p>{message}</p>
        <a className="primaryButton" href="/login">로그인으로 돌아가기</a>
      </section>
    </main>
  );
}
