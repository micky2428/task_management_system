import axios from "axios";

//之後所有 API 請求都會使用這個 authApiClient，不需要重複寫 baseURL
const authApiClient = axios.create({
  baseURL: 'http://localhost:5173/api/auth',
  // baseURL: 'http://localhost:8080/api/auth',
});

//用 POST /register 註冊使用者，user 物件會包含 使用者名稱、密碼、Email 等資訊
// Combine username and password into a single object for loginApi
const loginCredentials = (username, password) => ({ username, password });

export const registerApi = (user) => authApiClient.post('/register', user);

//組合 username 和 password 成 loginCredentials 物件，並 POST /login 發送登入請求。
export const loginApi = (username, password) =>
  authApiClient.post('/login', loginCredentials(username, password));

// 記錄已登入使用者
//存入 sessionStorage，讓登入資訊在 瀏覽器關閉時清除
export const saveLoggedUser = (userId, username, role) => {
  sessionStorage.setItem('activeUserId', userId);
  sessionStorage.setItem('authenticatedUser', username);
  sessionStorage.setItem('role', role);
};
//將 Basic Auth 存入 localStorage (登入狀態可跨瀏覽器重新整理保持)
export const storeBasicAuth = (basicAuth) => localStorage.setItem('auth', basicAuth);
export const getBasicAuth = () => localStorage.getItem('auth');

//判斷使用者是否登入
export const isUserLoggedIn = () => !!sessionStorage.getItem('authenticatedUser'); // Leverage double negation for concise check

///取得當前登入的使用者
// export const getLoggedInUserId = () => sessionStorage.getItem('activeUserId');
export const getLoggedInUserId = () => sessionStorage.getItem('activeUserId') || "Guest";
export const getLoggedInUser = () => sessionStorage.getItem('authenticatedUser');

//清除所有儲存的登入資訊 (localStorage & sessionStorage)，使用者完全登出
export const logout = () => {
  localStorage.clear();
  sessionStorage.clear();
};

//檢查是否為管理員
export const isAdminUser = () => sessionStorage.getItem('role') === 'ROLE_ADMIN'; // Strict comparison for role check

