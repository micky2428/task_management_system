import { useEffect, useState } from "react";
import {
  deleteTask,
  markDone,
  markPending,
  retrieveAllTasks,
} from "../../api/TaskApiService";
import { Link, useNavigate } from "react-router-dom";
import { FaTrash, FaPen, FaEye, FaCheck, FaTimes } from "react-icons/fa";
import "../pages/tasks.css";

const TaskHistory = ({ userId }) => {
  //tasks: 使用 useState 設置的狀態變數，存儲從後端獲取的任務列表
  const [tasks, setTasks] = useState([]);
  //navigate: 使用 useNavigate 來實現路由跳轉
  const navigate = useNavigate();

  useEffect(() => {
    allTasks(userId);
  }, [userId]);

  //retrieveAllTasks: 調用 API 獲取所有任務，並將返回的數據設置到 tasks 狀態中。如果發生錯誤，則打印錯誤
  function allTasks(userId) {
    retrieveAllTasks(userId)
      .then((response) => setTasks(response.data))
      .catch((error) => console.log(error));
  }
  //viewTaskDetails: 當用戶點擊查看詳情按鈕時，導航到 /task-details/:id 頁面並傳遞當前任務的 task 對象作為 state，用於顯示詳細信息。
  const viewTaskDetails = (task) => {
    navigate(`/task-details/${task.id}`, { state: task });
  };

  //updateTask: 當用戶點擊更新按鈕時，導航到 /update-task/:id 頁面以便編輯該任務
  function updateTask(id) {
    navigate(`/update-task/${id}`);
  }
  //deleteTaskFun: 調用 deleteTask API 來刪除指定的任務，並在刪除後重新獲取最新的任務列表
  function deleteTaskFun(id) {
    deleteTask(id)
      .then(() => allTasks(userId))
      .catch((error) => console.log(error));
  }
  //markTask: 根據勾選框的狀態（isChecked），調用相應的 API 更新任務狀態。如果勾選框被選中，將任務標記為“已完成” (markDone)，否則標記為“待辦” (markPending)
  function markTask(id, isChecked) {
    if (isChecked) {
      markDone(id)
        .then((response) => {
          console.log(response.data.id);
          allTasks(userId);
        })
        .catch((error) => console.error(error));
    } else {
      markPending(id)
        .then((response) => {
          console.log(response.data);
          allTasks(userId);
        })
        .catch((error) => console.error(error));
    }
  }
  //UI 結構
  //卡片布局: 頁面使用卡片來顯示每個任務，並為每個任務提供操作按鈕（查看詳情、編輯、刪除）
  //任務狀態顯示: 使用勾選框來標記任務為已完成或待辦，並根據任務狀態更新顯示文本
  //任務過濾: 只顯示已完成的任務，這是通過 task.completed 來進行條件渲染的
  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="m-0">Task History</h2>
                <Link to="/add-task" className="btn btn-primary btn-sm">
                  <i className="fas fa-plus me-2"></i> Add Task
                </Link>
              </div>
              {tasks.map(
                (task) =>
                  task.completed && (
                    <div key={task.id} className="card mb-4">
                      <div className="card-body">
                        <div className="d-flex justify-content-end gap-2 mb-2">
                          <button
                            className="btn btn-sm btn-outline-primary"
                            onClick={() => viewTaskDetails(task)}
                          >
                            <FaEye />
                          </button>
                          <button
                            className="btn btn-sm btn-outline-secondary"
                            onClick={() => updateTask(task.id)}
                          >
                            <FaPen />
                          </button>
                          <button
                            className="btn btn-sm btn-outline-danger"
                            onClick={() => deleteTaskFun(task.id)}
                          >
                            <FaTrash />
                          </button>
                        </div>
                        <div className="d-flex align-items-center gap-3">
                          <div className="form-check">
                            <input
                              className="form-check-input"
                              checked={task.completed}
                              onChange={(e) =>
                                markTask(task.id, e.target.checked)
                              }
                              type="checkbox"
                            />
                          </div>
                          <div
                            className={`${
                              task.completed
                                ? "text-decoration-line-through text-muted"
                                : ""
                            }`}
                          >
                            <strong>{task.task}</strong>
                          </div>
                        </div>
                        <div className="mt-2">
                          <small className="text-muted">
                            Created: {task.taskCreatedAt}
                          </small>
                          <div>
                            {task.completed ? (
                              <FaCheck className="text-success" />
                            ) : (
                              <FaTimes className="text-danger" />
                            )}
                          </div>
                        </div>
                      </div>
                    </div>
                  )
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TaskHistory;
