import { useEffect, useState } from "react";
import { createTask, retrieveTaskById, updateTask } from "../../api/TaskApiService";
import { Container, Row, Col, Form, Button, Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { FaTasks } from "react-icons/fa";

const AddTaskComponent = ({ userId }) => {
    //task 和 completed: 管理任務描述和任務的完成狀態
    const [task, setTask] = useState("");
    //taskCreatedAt: 記錄任務創建時間，格式化為 ISO 字符串
    const [completed, setCompleted] = useState(false);
    const taskCreatedAt = new Date().toISOString(); // Convert to ISO string
    //id: 從 URL 中獲取任務的 id（當有 id 時，表示是更新現有任務）
    const { id } = useParams();
    const navigate = useNavigate();
    const [errors, setErrors] = useState({ task: "" });

    //若 URL 中有 id，則會調用 retrieveTaskById 函數，獲取並顯示該任務的詳細信息。這樣就能夠進行任務更新
    //如果獲取成功，則設置 task 和 completed 狀態，以便在表單中顯示
    useEffect(() => {
      if (id) {
        retrieveTaskById(id)
          .then((response) => {
            console.log(response.data.object);
            setTask(response.data.object.task);
            setCompleted(response.data.object.completed);
          })
          .catch((error) => console.log(error));
      }
    }, [id]);
    //當表單提交時，首先會驗證表單。若表單有效，則創建一個任務對象（taskObj），並根據 id 判斷是創建新任務還是更新現有任務
    //若 id 存在，調用 updateTask 更新任務
    function saveTask(event) {
      event.preventDefault();
      if (validateForm()) {
        const taskObj = {
          task,
          completed,
          taskCreatedAt,
          updatedAt: new Date().toISOString(), // Add updatedAt field
        };
        if (id) {
          updateTask(taskObj, id)
            .then(navigate("/tasks"))
            .catch((error) => console.error(error));
        } else {
          createTask(taskObj, userId)
            .then(navigate("/tasks"))
            .catch((error) => console.error(error));
        }
      }
    }
  
  //validateForm: 用來檢查 task 欄位是否填寫。若未填寫，設置錯誤信息並返回 false
  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };
    if (task.trim()) {
      errorsCopy.task = "";
    } else {
      errorsCopy.task = "Task field is required";
      valid = false;
    }
    setErrors(errorsCopy);
    return valid;
  }
  //AddUpdateText: 根據是否有 id 來決定顯示“Add”還是“Update”，從而讓用戶知道他們是在創建新任務還是更新現有任務。
  function AddUpdateText() {
    if (id) {
      return "Update";
    } else {
      return "Add";
    }
  }
  //UI 結構
  //使用了 react-bootstrap 組件來實現響應式布局和美觀的表單界面
  //Card 用於顯示表單。 Form 用於收集任務描述，並檢查是否有錯誤信息（如果 task 為空則顯示錯誤）。 Button 用來提交表單。 FaTasks 顯示一個任務圖標，提升 UI 視覺效果。
  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <Container>
        <Row className="justify-content-center">
          <Col md={8} lg={6} xl={5}>
            <Card className="shadow rounded-lg">
              <Card.Body>
                <div className="d-flex align-items-center mb-4">
                  <FaTasks className="mr-3 text-primary" size={32} />
                  <h2 className="m-0">{AddUpdateText()} Task</h2>
                </div>
                <Form onSubmit={saveTask}>
                  <Form.Group controlId="formTask">
                    <Form.Label>Task Description</Form.Label>
                    <Form.Control
                      as="textarea"
                      rows={3}
                      placeholder="Enter task description"
                      value={task}
                      onChange={(event) => setTask(event.target.value)}
                      isInvalid={!!errors.task}
                      className="rounded-lg"
                    />
                    <Form.Control.Feedback type="invalid" className="d-block">
                      {errors.task}
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Button
                    variant="primary"
                    type="submit"
                    className="mt-3 w-100 rounded-pill"
                  >
                    {AddUpdateText()} Task
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default AddTaskComponent;