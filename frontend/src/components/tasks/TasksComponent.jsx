import React, { useEffect, useState } from "react";
import { deleteTask, markDone, markPending, retrieveAllTasks } from "../../api/TaskApiService";
import { Link, useNavigate } from "react-router-dom";
//載入 FontAwesome 圖示
// import { FaPlus, FaTrash, FaPen, FaEye } from "react-icons/fa";
//顯示通知訊息 (如任務刪除成功)
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
// import "../css/tasks.css";
//任務清單 (Task List)，讓使用者可以查看、標記完成/未完成、刪除、編輯任務，取得 Tasks 並顯示清單

const TasksComponent = ({ userId }) => {
  //setTasks：用來更新 tasks
  const [tasks, setTasks] = useState([]);
  const navigate = useNavigate();
  //當 userId 變更時，重新取得任務清單 (fetchTasks())
  useEffect(() => {
    fetchTasks();
  }, [userId]);

  //向 API 取得該使用者的所有任務 (retrieveAllTasks(userId))
  const fetchTasks = () => {
    retrieveAllTasks(userId)
      .then((response) => setTasks(response.data))
      .catch((error) => console.log(error));
  };

  const updateTask = (id) => {
    navigate(`/update-task/${id}`);
  };

  //呼叫 deleteTask(id) API 刪除任務
  const deleteTaskFun = (id) => {
    deleteTask(id)
      .then(() => {
        //成功後重新取得任務清單 (fetchTasks()) 並顯示成功通知 (toast.success())
        fetchTasks();
        toast.success("Task deleted successfully");
      })
      .catch((error) => {
        console.log(error);
        toast.error("Failed to delete task");
      });
  };

  //標記任務為完成/未完成
  const markTask = (id, isChecked) => {
    if (isChecked) {
      markDone(id)
        .then(() => {
          fetchTasks();
          toast.success("Task marked as completed");
        })
        .catch((error) => {
          console.error(error);
          toast.error("Failed to mark task as completed");
        });
    } else {
      markPending(id)
        .then(() => {
          fetchTasks();
          toast.success("Task marked as pending");
        })
        .catch((error) => {
          console.error(error);
          toast.error("Failed to mark task as pending");
        });
    }
  };

  //檢視任務詳情
  const viewTaskDetails = (task) => {
    navigate(`/task-details/${task.id}`, { state: task });
  };
  //使用 Bootstrap 來美化 UI
  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-body">
              {/* 任務列表 */}
              <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="m-0">Task List</h2>
                <Link to="/add-task" className="btn btn-primary btn-sm">
                  <FaPlus className="me-2" /> Add Task
                </Link>
              </div>
              {/* 過濾 & 顯示未完成任務 */}
              {tasks.map(
                (task) =>
                  !task.completed && (
                    <div key={task.id} className="mb-4">
                      <div className="card">
                        <div className="card-body">
                          <div className="d-flex justify-content-end gap-2 mb-2">
                            {/* 👁️ (檢視) FaEye → 點擊後導向 詳細頁 */}
                            <button
                              className="btn btn-sm btn-outline-primary"
                              onClick={() => viewTaskDetails(task)}
                            >
                              <FaEye />
                            {/* ✏️ (編輯) FaPen → 點擊後導向 更新頁 */}
                            </button>
                            <button
                              className="btn btn-sm btn-outline-secondary"
                              onClick={() => updateTask(task.id)}
                            >
                              <FaPen />
                            </button>
                            {/* 🗑️ (刪除) FaTrash → 點擊後刪除任務 */}
                            <button
                              className="btn btn-sm btn-outline-danger"
                              onClick={() => deleteTaskFun(task.id)}
                            >
                              <FaTrash />
                            </button>
                          </div>
                          {/* 任務標題 & 完成狀態 */}
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
                              {task.updatedAt
                                ? `Updated: ${new Date(
                                    task.updatedAt
                                  ).toLocaleString()}`
                                : `Created: ${new Date(
                                    task.taskCreatedAt
                                  ).toLocaleString()}`}
                            </small>
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

export default TasksComponent;
