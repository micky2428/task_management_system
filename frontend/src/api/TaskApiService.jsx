import axios from "axios";
import { getBasicAuth } from "./AuthApiService";

const API_BASE_URL = 'http://localhost:5173/api/v1/tasks';

//這段程式碼定義了一個 response interceptor，用來捕獲 API 請求的回應
axios.interceptors.response.use(
  response => response,
  error => {

    console.error('API request error:', error);
    return Promise.reject(error);
  }
  
);
//取得所有任務
export const retrieveAllTasks = (userId) =>
  axios.get(`${API_BASE_URL}/user/${userId}`, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });

// 創建新任務 
export const createTask = (task, userId) =>
  axios.post(`${API_BASE_URL}/user/${userId}`, task, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
// 根據 ID 取得任務
export const retrieveTaskById = (taskId) =>
  axios.get(`${API_BASE_URL}/${taskId}`, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
//更新任務
export const updateTask = (task, id) =>
  axios.put(`${API_BASE_URL}/${id}`, task, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
//刪除任務
export const deleteTask = (id) =>
  axios.delete(`${API_BASE_URL}/${id}`, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
//標註任務為完成
export const markDone = (id) =>
  axios.patch(`${API_BASE_URL}/${id}/task-done`, null, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
//標註任務為待處理
export const markPending = (id) =>
  axios.patch(`${API_BASE_URL}/${id}/task-pending`, null, {
    headers: {
      'Authorization': getBasicAuth()
    }
  });
