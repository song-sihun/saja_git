import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signup } from "../api/authApi";

export default function SignupPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    username: "",
    name: "",
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
      await signup(form);
      alert("회원가입이 완료되었습니다.");
      navigate("/login");
    } catch (error) {
      setErrorMessage("회원가입에 실패했습니다. 입력 정보를 확인해주세요.");
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>회원가입</h1>
        <p className="auth-description">서비스 이용을 위한 계정을 생성하세요.</p>

        <form onSubmit={handleSubmit} className="auth-form">
          <label>
            아이디
            <input
              type="text"
              name="username"
              value={form.username}
              onChange={handleChange}
              placeholder="username"
              required
            />
          </label>

          <label>
            이름
            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleChange}
              placeholder="홍길동"
              required
            />
          </label>

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
            회원가입
          </button>
        </form>

        <p className="auth-link">
          이미 계정이 있나요? <Link to="/login">로그인</Link>
        </p>
      </div>
    </div>
  );
}