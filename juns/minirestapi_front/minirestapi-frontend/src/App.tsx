import { BrowserRouter, Link, Route, Routes, useNavigate  } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";
import OAuthCallbackPage from "./pages/OAuthCallbackPage";
import OAuthErrorPage from "./pages/OAuthErrorPage";
import { getAccessToken, getMyInfo, logout } from "./api/authApi";
import "./style.css";
import { useEffect, useState } from "react";


type LoginUser = {
  id?: number;
  username?: string;
  email?: string;
  name?: string;
  avatarUrl?: string;
  roles?: string[];
};

function HomePage() {
  const navigate = useNavigate();
  const [loginUser, setLoginUser] = useState<LoginUser | null>(null);

  useEffect(() => {
  const checkLogin = async () => {
    const token = getAccessToken();

    if (!token) {
      setLoginUser(null);
      return;
    }

    try {
      const user = await getMyInfo();
      setLoginUser(user);
    } catch (error: any) {
      console.error("getMyInfo 실패 status =", error.response?.status);
      console.error("getMyInfo 실패 data =", error.response?.data);
      console.error("getMyInfo 실패 error =", error);

      // logout();
      setLoginUser(null);
    }
  };

    checkLogin();
  }, []);

  const handleLogout = () => {
    logout();
    setLoginUser(null);
    navigate("/login");
  };

  return (
    <div className="home-page">
      <nav className="nav">
        <Link to="/">TradeHub</Link>

        <div className="nav-menu">
          {loginUser ? (
            <>
              <span className="user-info">
                {loginUser.name || loginUser.username || loginUser.email} 님
              </span>

              {loginUser.email && (
                <span className="user-email">
                  {loginUser.email}
                </span>
              )}

              <button type="button" className="logout-button" onClick={handleLogout}>
                로그아웃
              </button>
            </>
          ) : (
            <>
              <Link to="/login">로그인</Link>
              <Link to="/signup">회원가입</Link>
            </>
          )}
        </div>
      </nav>

      <main className="home-content">
        <h1>TradeHub</h1>

        {loginUser ? (
          <p>{loginUser.username || loginUser.email} 계정으로 로그인 중입니다.</p>
        ) : (
          <p>로그인 후 중고거래 서비스를 이용해보세요.</p>
        )}
      </main>
    </div>
  );
}


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/oauth/callback" element={<OAuthCallbackPage />} />
        <Route path="/oauth/error" element={<OAuthErrorPage />} />
      </Routes>
    </BrowserRouter>
  );
}