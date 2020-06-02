package org.church0691.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.WatingMemberDAO;
import org.church0691.vo.WatingMember;

import common.MyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class JoinFormController implements Initializable{

    @FXML
    private Button btnJoinRequest;
    @FXML
    private TextField txtJoinId;
    @FXML
    private TextField txtJoinPhone;
    @FXML
    private PasswordField txtJoinPassword;
    @FXML
    private PasswordField txtJoinPasswordCheck;
    @FXML
    private TextField txtJoinAddress;
    @FXML
    private VBox VboxJoinForm;
    @FXML
    private Button btnCheckJoinId;
    @FXML
    private TextField txtJoinName;
    @FXML
    private Label labCheckMessage;
    
    String startMsg  = null;
    String checkedMsg = "사용가능 한 아이디";
    
    private WatingMemberDAO dao = null;

    @FXML
    void actionCheckJoinId(ActionEvent event) {
    	//System.out.println("실행  actionCheckJoinId");
    	if(txtJoinId.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "아이디를 입력하세요!");
    	} else {
	    	try {
	    		if(dao.isExist(txtJoinId.getText(), MyUtils.getConn())) {
	    			JOptionPane.showMessageDialog(null, " 다른 아이디를 입력하세요! ");
	    		}else{
	    			labCheckMessage.setText(checkedMsg);
	    			labCheckMessage.setTextFill(Color.DARKGREEN);
	    		};
			} catch (Exception e) {
				System.out.println("actionCheckJoinId 메소드 sql error");
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void ActionChangedId(KeyEvent event){
    	labCheckMessage.setText(startMsg);
		labCheckMessage.setTextFill(Color.CRIMSON);
    }

    @FXML
    void actionJoinRequest(ActionEvent event) {
    	//필수 입력 사항이 입력되었는지 확인함.
    	String name = txtJoinName.getText();
    	String id = txtJoinId.getText();
    	String pw = txtJoinPassword.getText();
    	String pwCheck = txtJoinPasswordCheck.getText();
    	String phone = txtJoinPhone.getText();
    	String add = txtJoinAddress.getText();
    	
    	if(name.equals("")) {
    		JOptionPane.showMessageDialog(null, "이름을 입력 해 주세요.");
    	}else if(id.equals("")){
    		JOptionPane.showMessageDialog(null, "아이디를 입력 해 주세요.");
    	}else if(pw.equals("")) {
    		JOptionPane.showMessageDialog(null, "Password를 입력 해 주세요.");
    	}else if(pwCheck.equals("")) {
    		JOptionPane.showMessageDialog(null, "Password 확인을 입력 해 주세요.");
    	}else if(phone.equals("")){
    		JOptionPane.showMessageDialog(null, "전화번호를 입력해 주세요.");
    	}else{
    		//중복확인을 했는지 Check 함.
	    	if(labCheckMessage.getText().equals(checkedMsg)){
	    		if(pw.equals(pwCheck)) {  		
	    			WatingMember vo = new WatingMember();
	    			vo.setId(id);
	    			vo.setName(name);
	    			vo.setPassword(pw);
	    			vo.setPhone(phone);
	    			vo.setAddress(add);
	    			try {
	    				if(dao.insert(vo, MyUtils.getConn())) {
	    					JOptionPane.showMessageDialog(null, "가입 요청이 완료 되었습니다.");
	    					//이동하고 옆 화면 가는 거는 나중에 짜 넣읍시다.
	    				};
	    			} catch (Exception e) {
	    				System.out.println("actionJoinRequest Sql error");
	    				e.printStackTrace();
	    			}
	    		}else {
	    			JOptionPane.showMessageDialog(null, "입력한 비밀번호가 서로 다릅니다.");
	    		}
	    	}else{
	    		JOptionPane.showMessageDialog(null, "ID 중복을 확인해 주세요.");
	    		//System.out.println("ID 중복을 확인해 주세요.");
	    	}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//System.out.println("조인 컨드롤러 초기화 실행 잘되었네요.");
		dao = new WatingMemberDAO();
		startMsg = labCheckMessage.getText();
		
	}
	
}
