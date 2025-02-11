import React from 'react'
// import logo from '../assets/react.svg'
import { NavLink, useNavigate } from "react-router-dom"
import { isUserLoggedIn, logout } from '../../api/AuthApiService'
//
import 'bootstrap/dist/css/bootstrap.min.css';
//



const HeaderComponent = () => {
  // 判斷使用者是否已登入
  const isAuth = isUserLoggedIn();
  //1140211-test:(true顯示登出和歷史任務;false:創建帳號和登入)
  //const isAuth = false;
  // 用來程式化跳轉頁面
  const navigate = useNavigate();

  function handleLogout() {
    // 登出使用者
    logout();
    navigate("/login");
  }

  function isUrlHistory() {
    //取得當前網址
    let url = window.location.href;
    // 判斷網址是否以 "history" 結尾，回傳 true，表示使用者在 Task History 頁面，回傳 false，表示使用者在 Tasks 頁面
    return url.endsWith("history");
  }

  return (
    // 建立導航列 (Navbar)，fixed-top 讓它固定在頁面頂端
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div className="container">
        <NavLink className="navbar-brand" to="/">
          {/* <img src={logo} alt="logo" width={30} height={30} /> */}
          🔥 任務管理系統
        </NavLink>
        <button
          // 在 小螢幕 (手機) 上，這顆按鈕可 展開 / 收合 選單
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse justify-content-end" id="navbarNav">
          <ul className="navbar-nav">
            {isAuth && (
              // 使用者登入 (isAuth === true)
              <li className="nav-item">
                {isUrlHistory() ? (
                  <NavLink className="nav-link" to="/tasks">
                    📋 任務
                  </NavLink>
                ) : (
                  <NavLink className="nav-link" to="/history">
                    📜 歷史任務
                  </NavLink>
                )}
              </li>
            )}
            {/* 使用者未登入 (isAuth === false) */}
            {!isAuth && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/create-account">
                  🆕 創建帳號
                </NavLink>
              </li>
            )}
            {!isAuth && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/login">
                  🔑 登入
                </NavLink>
              </li>
            )}
            {isAuth && (
              <li className="nav-item">
                <button className="btn btn-outline-light ms-2" onClick={handleLogout}>
                  🚪 登出
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default HeaderComponent;

