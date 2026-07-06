import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login, saveTokens } from "../api/authApi";

export default function LoginPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage("");

    try {
      const data = await login(form);

      saveTokens({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
      });

      navigate("/");
    } catch (error) {
      setErrorMessage("로그인에 실패했습니다. 이메일과 비밀번호를 확인해주세요.");
    }
  };

  const handleSocialLogin = (provider) => {
    window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>로그인</h1>
        <p className="auth-description">계정으로 로그인하세요.</p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label>
            이메일
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              placeholder="test@example.com"
              required
            />
          </label>

          <label>
            비밀번호
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              placeholder="비밀번호"
              required
            />
          </label>

          {errorMessage && <p className="error-message">{errorMessage}</p>}

          <button type="submit" className="primary-button">
            로그인
          </button>
        </form>

        <div className="divider">
          <span>또는</span>
        </div>

        <div className="social-buttons">
          <button type="button" onClick={() => handleSocialLogin("google")}>
            Google로 로그인
          </button>

          <button type="button" onClick={() => handleSocialLogin("github")}>
            Github로 로그인
          </button>

          <button type="button" onClick={() => handleSocialLogin("naver")}>
            Naver로 로그인
          </button>
        </div>

        <p className="auth-link">
          아직 계정이 없나요? <Link to="/signup">회원가입</Link>
        </p>
      </div>
    </div>
  );
}