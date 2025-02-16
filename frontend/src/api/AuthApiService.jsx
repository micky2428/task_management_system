import axios from "axios";

//之後所有 API 請求都會使用這個 authApiClient，不需要重複寫 baseURL，以串聯後端
const authApiClient = axios.create({
  baseURL: 'http://localhost:8080/api/auth',
  withCredentials: true,  // 確保跨域請求可以攜帶 cookies 或 token
});



//用 POST 向 /register 註冊使用者(user)，包含使用者名稱、密碼、Email 等資訊
export const registerApi = (user) => authApiClient.post('/register', user);

//組合 username 和 password 成 loginCredentials 物件，並用POST向 /login 發送登入請求。
const loginCredentials = (username, password) => ({ username, password });

export const loginApi = (username, password) =>
  authApiClient.post('/login', loginCredentials(username, password));

// 記錄已登入的使用者
//存入 sessionStorage，登入資訊會在瀏覽器關閉時清除
export const saveLoggedUser = (userId, username, role) => {
  sessionStorage.setItem('activeUserId', userId);
  sessionStorage.setItem('authenticatedUser', username);
  sessionStorage.setItem('role', role);
};
//將 Basic Auth 存入 localStorage (登入狀態可跨瀏覽器重新整理)
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
export const isAdminUser = () => sessionStorage.getItem('role') === 'ROLE_ADMIN'; 

