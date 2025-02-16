import React, { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { loginApi, saveLoggedUser, storeBasicAuth } from "../../api/AuthApiService";
import { useNavigate } from "react-router-dom";
import "../pages/tasks.css";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  //errors：用於儲存表單驗證的錯誤訊息
  const [errors, setErrors] = useState({
    username: "",
    password: "",
  });

  async function handleLoginForm(event) {
    //event.preventDefault()：防止表單的預設提交行為
    event.preventDefault();

    if (validateForm()) {
      await loginApi(username, password)
        .then((response) => {
          console.log(response.data);
          //btoa(username + ":" + password)：將帳號和密碼組合後轉換為 Base64 編碼的字串，用於基本認證
          const basicAuth = "Basic " + btoa(username + ":" + password);
          const role = response.data.role;
          storeBasicAuth(basicAuth);
          saveLoggedUser(response.data.id, username, role);
          //navigate("/tasks")：登入成功後，導向 /tasks 頁面
          navigate(`/tasks`);
        })
        .catch((error) => console.error(error));
    }
  }
  //檢查 username 和 password 是否為空，並設定相應的錯誤訊息
  function validateForm() {
    let valid = true;

    const errorsCopy = { ...errors };

    if (!username.trim()) {
      errorsCopy.username = "Username required";
      valid = false;
    } else {
      errorsCopy.username = "";
    }

    if (!password.trim()) {
      errorsCopy.password = "Password required";
      valid = false;
    } else {
      errorsCopy.password = "";
    }
    setErrors(errorsCopy);

    return valid;
  }

  return (
    <div className="login-page">
      <Container>
        <Row className="align-items-center">
          <Col md={6} className="text-center">
            <img src="/src/assets/loginPage.png" alt="Login Page" className="img-fluid" style={{ maxWidth: '70%' }}/>
          </Col>
          <Col md={6}>
            <div className="login-form bg-light shadow-lg p-4">
              <h2 className="mb-4 text-center">帳號登入</h2>
              <form onSubmit={handleLoginForm}>
                <div className="mb-3">
                  <input
                    type="text"
                    name="username"
                    className={`form-control ${
                      errors.username ? "is-invalid" : ""
                    }`}
                    placeholder="使用者帳號"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                  />
                  {errors.username && (
                    <div className="invalid-feedback">{errors.username}</div>
                  )}
                </div>
                <div className="mb-3">
                  <input
                    type="password"
                    name="password"
                    className={`form-control ${
                      errors.password ? "is-invalid" : ""
                    }`}
                    placeholder="密碼"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                  />
                  {errors.password && (
                    <div className="invalid-feedback">{errors.password}</div>
                  )}
                </div>
                <div className="text-center">
                  <button type="submit" className="btn btn-dark btn-block">
                    登入
                  </button>
                </div>
              </form>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default LoginComponent;
