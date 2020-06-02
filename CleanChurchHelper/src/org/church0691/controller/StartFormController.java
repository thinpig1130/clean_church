package org.church0691.controller;

import javax.swing.JOptionPane;

import org.church0691.dao.MemberDAO;
import org.church0691.vo.Member;

import common.MyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartFormController {

    @FXML
    private TextField txtStMemNo;

    @FXML
    private Button btnStart;

    @FXML
    private TextField txtStMemPhone;

    @FXML
    private TextField txtStMemAdd;

    @FXML
    private TextField txtStMemId;

    @FXML
    private TextField txtStMemPwCheck;

    @FXML
    private TextField txtStMemPw;

    @FXML
    private TextField txtStMemName;

    @FXML
    void btnStartRegiste(ActionEvent event) {
    	String memNo = txtStMemNo.getText();
    	String name = txtStMemName.getText();
    	String id = txtStMemId.getText();
    	String pw = txtStMemPw.getText();
    	String pwCheck = txtStMemPwCheck.getText();
    	String phone = txtStMemPhone.getText();
    	String add = txtStMemAdd.getText();
    	
    	if(memNo.equals("")) {
    		JOptionPane.showMessageDialog(null, "식별코드를 입력 해 주세요.");
    	} else if(name.equals("")) {
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
	    		if(pw.equals(pwCheck)) {  		
	    			Member vo = new Member();
	    			
	    			vo.setMemNo(memNo);
	    			vo.setMemPassword(pw);
	    			vo.setMemId(id);
	    			vo.setMemName(name);
	    			vo.setMemPhone(phone);
	    			vo.setMemAddress(add);
	    			vo.setMemRemark("최초 Member / 모든권한 부여");
	    			vo.setAuthName("담임목사");
	    			
	    			MemberDAO dao = new MemberDAO();
	    			try {
	    				if(dao.insert(vo, MyUtils.getConn())) {
	    					Stage stage = (Stage) btnStart.getScene().getWindow();
	    					JOptionPane.showMessageDialog(null, "관리자 등록 완료 ! 로그인 페이지로 이동합니다.");
	    					BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
	    					Scene scene = new Scene(root);
	    					stage.setScene(scene);
	    					stage.show();
	    				};
	    			} catch (Exception e) {
	    				System.out.println("actionJoinRequest Sql error");
	    				e.printStackTrace();
	    			}
	    		}else {
	    			JOptionPane.showMessageDialog(null, "입력한 비밀번호가 서로 다릅니다.");
	    		}
    	}
    }

}
