import React from 'react';
import { useLocation, useParams, Link } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import '../pages/tasks.css';

const DetailPage = () => {
    //const { state } = useLocation();: 這行代碼從路由中提取 state，通常是從其他頁面導航時傳遞的資料。在這個組件中，state 預期是一個任務的資料對象
    const { state } = useLocation();
    //const { id } = useParams();: 從 URL 中提取 id 參數，這通常是任務的 ID。
    const { id } = useParams();
    //const task = state || { id };: 如果 state 存在（從路由傳遞過來的任務資料），那麼使用 state
    const task = state || { id };

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-lg-8">
                    <div className="card shadow-lg">
                        <div className="card-body detail-card">
                            <div className="row mb-4">
                                <div className="col-auto">
                                    <Link to="/tasks" className="btn btn-outline-secondary btn-sm">
                                        {/* FaArrowLeft: 顯示一個向左的箭頭圖標，表示返回的操作 */}
                                        <FaArrowLeft className="me-2" />
                                        Back to Tasks
                                    </Link>
                                </div>
                            </div>

                            <div className="text-center mb-4">
                                <h3>Task Details</h3>
                            </div>

                            <div className="mb-3">
                                <h5 className="m-0">Task:</h5>
                                {/* 顯示任務名稱: 顯示任務名稱，從 task.task 提取 */}
                                <p className="m-0">{task.task}</p>
                            </div>
                            <div className="mb-3">
                                <h5 className="m-0">Status:</h5>
                                {/* 顯示任務狀態: 根據 task.completed 顯示不同的狀態 */}
                                <p className={`m-0 ${task.completed ? 'text-success' : 'text-warning'}`}>
                                    {task.completed ? 'Completed' : 'Pending'}
                                </p>
                            </div>
                            <div>
                                {/* 顯示任務創建時間: 顯示創建時間，從 task.taskCreatedAt 提取 */}
                                <h5 className="m-0">Created At:</h5>
                                <p className="m-0">{task.taskCreatedAt}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DetailPage;
