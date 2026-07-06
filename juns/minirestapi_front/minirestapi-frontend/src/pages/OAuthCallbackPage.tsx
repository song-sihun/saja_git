import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { saveTokens } from "../api/authApi";

export default function OAuthCallbackPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const accessToken = searchParams.get("accessToken");
    const refreshToken = searchParams.get("refreshToken");

    // console.log("callback accessToken =", accessToken);
    // console.log("callback refreshToken =", refreshToken);

    if (!accessToken) {
      alert("소셜 로그인에 실패했습니다.");
      navigate("/login");
      return;
    }

    saveTokens({
      accessToken,
      refreshToken,
    });

    console.log("저장 직후 accessToken =", localStorage.getItem("accessToken"));

    navigate("/");
  }, [navigate, searchParams]);

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>로그인 처리 중...</h1>
        <p className="auth-description">잠시만 기다려주세요.</p>
      </div>
    </div>
  );
}