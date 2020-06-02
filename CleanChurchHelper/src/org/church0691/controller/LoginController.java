package org.church0691.controller;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.MemberDAO;
import org.church0691.dao.WatingMemberDAO;
import org.church0691.vo.Member;
import org.church0691.vo.WatingMember;

import common.MyUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Label btnJoin;   
    @FXML
    private VBox vboxMain;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtLoginId;
    @FXML
    private PasswordField txtLoginPwd;
    @FXML
    private TextArea testT;
    
    VBox VBoxJoinForm= null;

    @FXML
    void ActFormPrint(MouseEvent event) {
    	//vboxMain.getChildren().remove(testT);
    	
		try {
			VBox joinForm = (VBox) FXMLLoader.load(getClass().getResource("../view/JoinForm.fxml"));
			ObservableList delChildren = vboxMain.getChildren();
			vboxMain.getChildren().removeAll(delChildren);
			vboxMain.getChildren().add(joinForm);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void ActLogin(ActionEvent event) {
    	MemberDAO dao = new MemberDAO();
    	
    	HashMap<String, String> conditions = new HashMap<>();
    	conditions.put("MEM_ID", "'"+ txtLoginId.getText() +"'");
		conditions.put("MEM_PASSWORD", "'"+ txtLoginPwd.getText() +"'");
    	try {
			List<Member> list = dao.selectByCondition(conditions, MyUtils.getConn());
			if(list.size()==0) {
				WatingMemberDAO dao2 = new WatingMemberDAO();
				HashMap<String, String> conditions2 = new HashMap<>();
		    	conditions2.put("ID", "'"+ txtLoginId.getText() +"'");
				conditions2.put("PASSWORD", "'"+ txtLoginPwd.getText() +"'");
				List<WatingMember> list2 = dao2.selectByCondition(conditions2, MyUtils.getConn());
				if(list2.size()==0) {
					JOptionPane.showMessageDialog(null, " 아이디/비밀번호를 확인하세요. ");
				}else {
					JOptionPane.showMessageDialog(null, "가입 승인을 기다리는 중입니다.");
				}
			}else {
				//JOptionPane.showMessageDialog(null, " 로그인 되었습니다. ");
				Stage stage = (Stage) btnLogin.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainScreen.fxml"));
				BorderPane mainScreen = loader.load();
				MainScreenController controller = loader.getController();
				
				Member mem = list.get(0);
				controller.setCurrentUser(mem.getMemNo(), mem.getMemId(), mem.getMemName(), mem.getAuthName());
				
				// 컨트롤러에 로그인 정보 지정.controller.

				Scene scene = new Scene(mainScreen);
				stage.setScene(scene);
				stage.show();
			}
		} catch (Exception e) {
			System.out.println("actLogin 실행 중 err");
			e.printStackTrace();
		}
    }
     
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
