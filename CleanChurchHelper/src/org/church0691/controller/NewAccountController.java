package org.church0691.controller;

	import javax.swing.JOptionPane;

import org.church0691.dao.AccountDAO;
import org.church0691.vo.Account;

import common.MyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewAccountController {

    @FXML
    private TextField txtAccountBank;

    @FXML
    private Button btnRegisteAccount;

    @FXML
    private TextField txtAccountNo;

    @FXML
    private TextField txtAccountNickname;

    @FXML
    private TextArea txtaAccountInfo;

    @FXML
    void actClickRegistBtn(ActionEvent event) {
		if(txtAccountNo.getText().equals("")){
			JOptionPane.showMessageDialog(null, "계좌번호를 입력해 주세요.");
		}else if(txtAccountBank.getText().equals("")){
			JOptionPane.showMessageDialog(null, "은행명을 입력해 주세요.");
		}else if(txtAccountNickname.getText().equals("")){
			JOptionPane.showMessageDialog(null, "계좌별명을 입력해 주세요.");
		}else {
			AccountDAO dao = new AccountDAO();
			Account vo = new Account();
			vo.setNo(txtAccountNo.getText());
			vo.setBank(txtAccountBank.getText());
			vo.setName(txtAccountNickname.getText());
			vo.setInfo(txtaAccountInfo.getText());
			
			try {
				boolean res = dao.insert(vo, MyUtils.getConn());
				if(res) {
					JOptionPane.showMessageDialog(null, "계좌가 등록 되었습니다.");
					txtAccountNo.setText("");
					txtAccountBank.setText("");
					txtAccountNickname.setText("");
					txtaAccountInfo.setText("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}