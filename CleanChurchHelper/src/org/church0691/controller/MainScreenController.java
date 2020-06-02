package org.church0691.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import common.*;

public class MainScreenController implements Initializable{

	public CurrentUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}
	
	public void setCurrentUser(String memNo, String id, String name, String authName) {
		this.currentUser.setMemNo(memNo);
		this.currentUser.setId(id);
		this.currentUser.setName(name);
		this.currentUser.setAuthName(authName);
		labStatus.setText(name+"("+ memNo +") 접속 중...");
	}

		@FXML
		private Label labStatus;

		@FXML
	    private AnchorPane ancFinancialManagement;

	    @FXML
	    private AnchorPane ancReporting;

	    @FXML
	    private AnchorPane ancMyInfo;

	    @FXML
	    private AnchorPane ancMemberManagement;
	    @FXML
	    private AnchorPane ancSettingScreen;
	    @FXML
	    private AnchorPane ancExpensesRequest;
	    
	    private CurrentUser currentUser;
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 로그인 된 정보 저장
		// 권한에 따라 화면 기능 출력 여부 결정
		// 각 tabPane에 기능 추가
		currentUser = new CurrentUser();
		try {
			BorderPane memberManagement = (BorderPane)FXMLLoader.load(getClass().getResource("../view/MemberManagement.fxml"));
			ancMemberManagement.getChildren().add(memberManagement);
			BorderPane SettingScreen = (BorderPane)FXMLLoader.load(getClass().getResource("../view/SettingScreen.fxml"));
			ancSettingScreen.getChildren().add(SettingScreen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
