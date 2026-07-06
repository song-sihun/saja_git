import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

const authApi = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
});

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  username: string;
  name: string;
  email: string;
  password: string;
}

export interface TokenResponse {
  accessToken?: string;
  refreshToken?: string;
}

export interface LoginUser {
  username: string;
  email: string;
  name: string;
  roles: string[];
}



export const login = async (data: LoginRequest): Promise<TokenResponse> => {
  const response = await authApi.post("/auth/login", data);
  return response.data;
};

export const signup = async (data: SignupRequest): Promise<unknown> => {
  const response = await authApi.post("/users", data);
  return response.data;
};

export const saveTokens = ({ accessToken, refreshToken }: TokenResponse): void => {
  if (accessToken) {
    localStorage.setItem("accessToken", accessToken);
  }

  if (refreshToken) {
    localStorage.setItem("refreshToken", refreshToken);
  }
};

export const getAccessToken = (): string | null => {
  return localStorage.getItem("accessToken");
};

export const logout = (): void => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
};

export const parseJwt = (token: string): any | null => {
  try {
    const base64Payload = token.split(".")[1];
    const payload = atob(base64Payload);
    return JSON.parse(payload);
  } catch {
    return null;
  }
};

export const getLoginUser = (): LoginUser | null => {
  const token = getAccessToken();

  if (!token) {
    return null;
  }

  const payload = parseJwt(token);

  if (!payload) {
    return null;
  }

  return {
    username: payload.username || payload.sub || "",
    email: payload.email || "",
    name: payload.name || "",
    roles: payload.roles || [],
  };
};


export const getMyInfo = async (): Promise<LoginUser> => {
  const token = getAccessToken();

  if (!token) {
    throw new Error("토큰이 없습니다.");
  }

  const response = await authApi.get("/users/me");
  return response.data;
};


authApi.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      logout();
    }

    return Promise.reject(error);
  }
);

authApi.interceptors.request.use((config) => {
  const token = getAccessToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});