import HeaderComponent from "./components/common/HeaderComponent";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import CreateAccount from "./components/auth/CreateAccount";
import LoginComponent from "./components/auth/LoginComponent";
import TasksComponent from "./components/tasks/TasksComponent";
import AddTaskComponent from "./components/tasks/AddTaskComponent";
import TaskHistory from "./components/tasks/TaskHistory";
import DetailPage from "./components/pages/DetailPage";
import { getLoggedInUserId, isUserLoggedIn } from "./api/AuthApiService";
import HomePage from "./components/pages/Home";
import "./App.css";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  const activeUserId = getLoggedInUserId();

  function AuthenticatedRoute({ children }) {
    const isAuthenticated = isUserLoggedIn();

    if (isAuthenticated) {
      return children;
    }
  return <Navigate to="/" />;
  }

  //取得當前登入使用者的 ID，傳遞給其他頁面使用
  return (
    //提供前端路由功能
    <BrowserRouter>
      {/* 顯示通知 */}
      <ToastContainer />
      {/* 顯示導覽列 (Navbar) */}
      <HeaderComponent />
      <div className="container mt-5">
        <Routes>
          {/* 定義不同的 Route */}
          <Route path="/" element={<HomePage />} />
          <Route
            path="/tasks"
            element={
              <AuthenticatedRoute>
                {/* 若使用者已登入，顯示 TasksComponent */}
                <TasksComponent userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />
          <Route
            path="/add-task"
            element={
              <AuthenticatedRoute>
                <AddTaskComponent userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />
          <Route
            // :id 代表 URL 參數，例如 /task-details/123，顯示 ID 為 123 的任務
           path="/task-details/:id" 
           element={
              <AuthenticatedRoute>
              <DetailPage userId={activeUserId}/> 
                </AuthenticatedRoute>
           }
          />

          <Route
            path="/history"
            element={
              <AuthenticatedRoute>
                <TaskHistory userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />
          <Route
            path="/update-task/:id"
            element={
              <AuthenticatedRoute>
                <AddTaskComponent userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />

          {/* 公開頁面 (無需登入) */}
          <Route path="/create-account" element={<CreateAccount />} />
          <Route path="/login" element={<LoginComponent />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
// 將 App 作為預設匯出，供 index.js 使用
export default App;


//最初期
// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'
// function App() {
//   const [count, setCount] = useState(0)

//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.jsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }

// export default App


//之後繼續研究
// function App() {
//   return (
//     <BrowserRouter>
//       <div className="container">
//         <HeaderComponent />
//         <div className="main flex">
//           <aside className="sidebar w-64 bg-gray-200 p-4">📌 Sidebar</aside>
//           <section className="content flex-1 p-4">
//             <Routes>
//               {/* 創建兩個會返還文字的按鍵 */}
//               <Route path="/home" element={<HomePage />} />
//               <Route path="/login" element={<LoginPage />} />
//             </Routes>
//           </section>
//         </div>
//       </div>
//     </BrowserRouter>
//   );
// }
// export default App;
