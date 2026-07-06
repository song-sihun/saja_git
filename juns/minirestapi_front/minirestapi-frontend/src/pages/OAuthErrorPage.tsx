import { Link, useSearchParams } from "react-router-dom";

export default function OAuthErrorPage() {
  const [searchParams] = useSearchParams();

  const error = searchParams.get("error");
  const message = searchParams.get("message");

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>소셜 로그인 실패</h1>

        <p className="auth-description">
          소셜 로그인 처리 중 문제가 발생했습니다.
        </p>

        {error && (
          <p className="error-message">
            에러 코드: {error}
          </p>
        )}

        {message && (
          <p className="error-message">
            사유: {decodeURIComponent(message)}
          </p>
        )}

        <Link to="/login">
          <button className="primary-button">
            로그인 페이지로 돌아가기
          </button>
        </Link>
      </div>
    </div>
  );
}