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
            {/* 首頁內容 (Col 區塊) ，lg={8}在 大螢幕 (lg, ≥992px) 時，寬度佔 8 格 (共 12 格)*/}
            <Col lg={8}>
              <h1 className="display-4">歡迎使用 <span className="text-primary">任務管理系統 📅</span></h1>
              <p className="lead mb-4">
                此系統提供新增、更改、刪除任務的功能，方便您管理任務、控管時程、提升工作效能。
              </p>
            </Col>
          </Row>
        </Container>
      </div>
    );
  };

export default HomePage;
