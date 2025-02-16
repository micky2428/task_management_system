import { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { registerApi } from "../../api/AuthApiService";
import { useNavigate } from "react-router-dom";
import "../pages/tasks.css";
import 'bootstrap/dist/css/bootstrap.min.css';

//主要功能
// 使用者輸入：包含 username、email 和 password 
const CreateAccount = () => {
  console.log("CreateAccount Component Loaded!");

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  //註冊成功後導向 /login 頁面
  const navigate = useNavigate();
  //儲存驗證相關錯誤訊息
  const [errors, setErrors] = useState({
    username: "",
    email: "",
    password: "",
  });

  
  function handleRegistrationForm(event) {
    // 阻止預設行為 (event.preventDefault()) 避免頁面重新整理
    event.preventDefault();
    // 紀錄
    console.log("Submit button clicked");

    if (validateForm()) {
      console.log("Validation passed");
      //若通過驗證，則組成 register 物件 { username, email, password }
      const register = { username, email, password };
      //呼叫 registerApi(register) 發送註冊請求
      registerApi(register)
        .then((response) => {
          console.log("Register API response:", response.data);
          //註冊成功後，導向 /login 頁面
          navigate("/login");
        })
        .catch((error) => console.error("API error:", error));
    } else {
      console.log("Validation failed:", errors);
    }
  }
  //表單驗證
  function validateForm() {
    let valid = true;

    const errorsCopy = { ...errors };

    if (!username.trim()) {
      errorsCopy.username = "Username required";
      valid = false;
    } else {
      errorsCopy.username = "";
    }

    if (!email.trim()) {
      errorsCopy.email = "Email required";
      valid = false;
    } else if (!isValidEmail(email)) {
      errorsCopy.email = "Invalid email address";
      valid = false;
    } else {
      errorsCopy.email = "";
    }

    if (!password.trim()) {
      errorsCopy.password = "Password required";
      valid = false;
    } else if (!isValidPassword(password)) {
      errorsCopy.password = "Password must be at least 6 characters long";
      valid = false;
    } else {
      errorsCopy.password = "";
    }

    setErrors(errorsCopy);

    return valid;
  }

  function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  function isValidPassword(password) {
    return password.length >= 6;
  }

  return (
    //UI
    <div className="signup-page">
      <Container>
        <Row className="align-items-center">
          <Col md={6} className="text-center">
           <img
              src="/src/assets/createAccountPage.png"
              alt="createAccountPage"
              className="img-fluid"
            />
          </Col>
          <Col md={6}>
            <div className="signup-form shadow-lg p-5 rounded-3">
              <h2 className="mb-4 text-center">建立新帳號</h2>
              <form onSubmit={handleRegistrationForm}>
                {/* 使用者名稱 */}
                <div className="mb-3">
                  <input
                    type="text"
                    name="username"
                    className={`form-control ${
                      errors.username ? "is-invalid" : ""
                    }`}
                    placeholder="使用者名稱"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                  />
                  {errors.username && (
                    <div className="invalid-feedback">{errors.username}</div>
                  )}
                </div>
                {/* email */}
                <div className="mb-3">
                  <input
                    type="text"
                    name="email"
                    className={`form-control ${
                      errors.email ? "is-invalid" : ""
                    }`}
                    placeholder="Email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                  />
                  {errors.email && (
                    <div className="invalid-feedback">{errors.email}</div>
                  )}
                </div>
                {/* 密碼 */}
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
                  <button
                    type="submit"
                    className="btn btn-dark btn-block"
                  >
                    建立
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

export default CreateAccount;
