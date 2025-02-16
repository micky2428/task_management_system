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
    //提供前端路徑功能(其他方式:HashRouter)
    <BrowserRouter>
      {/* 顯示通知 */}
      <ToastContainer />
      {/* 顯示導覽列 */}
      <HeaderComponent />
      <div className="container mt-5">
        <Routes>
          {/* 定義不同的路徑導向的頁面 */}
          {/* 首頁 */}
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
          {/* 新增任務頁面 */}
          <Route
            path="/add-task"
            element={
              <AuthenticatedRoute>
                <AddTaskComponent userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />
          {/* 任務資訊頁面 */}
          <Route
            // :id 代表 URL 參數，例如 /task-details/123，顯示 ID 為 123 的任務
           path="/task-details/:id" 
           element={
              <AuthenticatedRoute>
              <DetailPage userId={activeUserId}/> 
                </AuthenticatedRoute>
           }
          />
          {/* 歷史任務頁面 */}
          <Route
            path="/history"
            element={
              <AuthenticatedRoute>
                <TaskHistory userId={activeUserId} />
              </AuthenticatedRoute>
            }
          />
          {/* 更新任務頁面 */}
          <Route
            path="/update-task/:id"
            element={
              <AuthenticatedRoute>
                <AddTaskComponent userId={activeUserId} />
              </AuthenticatedRoute>
            } 
          />
          {/* 公開頁面 */}
          {/* 建立帳號頁面 */}
          <Route path="/create-account" element={<CreateAccount />} />
          {/* 登入頁面 */}
          <Route path="/login" element={<LoginComponent />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
// 將 App 作為預設匯出，供 index.js 使用
export default App;

