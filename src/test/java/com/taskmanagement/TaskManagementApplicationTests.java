package com.taskmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//用途是進行單元或整合測試
@SpringBootTest
class TaskManagementApplicationTests {
	//contextLoads()表示是在測試上下文是否能正確加載，內部沒有邏輯，又稱煙霧測試（Smoke Test）
	@Test
	void contextLoads() {
	}

}