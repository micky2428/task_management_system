import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css';

//無狀態 (Stateless) 函式元件，只負責渲染畫面，不包含任何邏輯
const HomePage = () => {
    return (
      <div className="center-in-page">
        {/* Bootstrap 容器 */}
        <Container>
          <Row className="justify-content-center align-items-center text-center">
            {/* 內容 (Col 區塊) ，lg={8}在 大螢幕 (lg, ≥992px) 時，寬度佔 8 格 (共 12 格)*/}
            <Col lg={8}>
              <h1 className="display-4">Welcome to <span className="text-primary">Task Management Web Application</span></h1>
              <p className="lead mb-4">
                Experience a smarter way to manage tasks. Boost productivity, collaborate seamlessly, and accomplish more in less time.
              </p>
            </Col>
          </Row>
        </Container>
      </div>
    );
  };

export default HomePage;
