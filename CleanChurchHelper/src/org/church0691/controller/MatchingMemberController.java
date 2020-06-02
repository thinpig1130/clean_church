package org.church0691.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.MemberDAO;
import org.church0691.dao.WatingMemberDAO;
import org.church0691.vo.Member;
import org.church0691.vo.WatingMember;

import common.MyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MatchingMemberController implements Initializable {

	private ObservableList<Member> tableArrMem;
	private ObservableList<WatingMember> tableArrWatingMem;
	
	@FXML
	private TableView<WatingMember> tableRequestMem;
	@FXML
	private TableColumn<WatingMember, String> colRequestName;
	@FXML
	private TableColumn<WatingMember, String> colRequestAdd;
	@FXML
	private TableColumn<WatingMember, String> colRequestId;
	@FXML
	private TableColumn<WatingMember, String> colRequestPhone;
	
	@FXML
	private TableView<Member> tableMem;
	@FXML
	private TableColumn<Member, String> colMemNo;
	@FXML
	private TableColumn<Member, String> colMemPhone;
	@FXML
	private TableColumn<Member, String> colMemAdd;
	@FXML
	private TableColumn<Member, String> colMemName;

    @FXML
    private Button btnMachSubmit;
    @FXML
    private CheckBox checkMachAdd;
    @FXML
    private CheckBox checkMachPhone;
    @FXML
    private CheckBox checkMachName;

    @FXML
    private TextField txtMachName;
    @FXML
    private TextField txtMachMemNo;
    @FXML
    private TextField txtMachId;
    @FXML
    private TextField txtMachPhone;
    @FXML
    private TextArea txtaMachAdd;

    @FXML
    void actSelectRequestMem(MouseEvent event) {
    	int selectedWatingMemIndex = tableRequestMem.getSelectionModel().getSelectedIndex();
    	if(selectedWatingMemIndex != -1) {
	    	WatingMember actWatingMember = (WatingMember) tableRequestMem.getItems().get(selectedWatingMemIndex);
	    	System.out.println(actWatingMember);
	        checkMachPhone.setSelected(true);
	        checkMachName.setSelected(true);
	    	if(actWatingMember.getAddress() == null) {
	    		checkMachAdd.setSelected(false);
	    	}else {
	    		checkMachAdd.setSelected(true);
	    	}
    	}    	
    	updateMachingInfo();
    }
    
    @FXML
    void actSelectMachingMem(MouseEvent event) {
    	updateMachingInfo();
    }
    
    @FXML
    void actClickNameCheckbox(ActionEvent event) {
    	updateMachingInfo();
    }

    @FXML
    void actClickPhoneCheckbox(ActionEvent event) {
    	updateMachingInfo();
    }

    @FXML
    void actClickAddCheckbox(ActionEvent event) {
    	updateMachingInfo();
    }
    
    @FXML
    void actMaching(ActionEvent event) {
    	if(txtMachId.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "가입 요청 정보를 선택해 주세요.");
    	}else if(txtMachMemNo.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "매칭 시킬 멤버를 선택해 주세요.");
    	}else {
    		MemberDAO dao = new MemberDAO();
    		WatingMemberDAO dao2 = new WatingMemberDAO();
    		int selectedMemIndex = tableMem.getSelectionModel().getSelectedIndex();
    		int selectedWatingMemIndex = tableRequestMem.getSelectionModel().getSelectedIndex();
    		Member vo = (Member) tableMem.getItems().get(selectedMemIndex);
			WatingMember wVo = (WatingMember) tableRequestMem.getItems().get(selectedWatingMemIndex);
    		vo.setMemNo(txtMachMemNo.getText()); //MEM_NO
			vo.setMemId(txtMachId.getText());//MEM_ID
			vo.setMemName(txtMachName.getText()); //MEM_NAME
			vo.setMemPhone(txtMachPhone.getText()); //MEM_PHONE
			vo.setMemAddress(txtaMachAdd.getText()); //MEM_ADDRESS
			vo.setMemPassword(wVo.getPassword());
			System.out.println(vo);
			try {
				if(dao.update(vo, MyUtils.getConn())) {
					if(dao2.delete(wVo.getId(), MyUtils.getConn())) {
						JOptionPane.showMessageDialog(null, "매칭이 완료 되었습니다.");
						setTableMemData();
						setTableRequestMemData();
					}else {
						System.out.println("Wating 멤버 삭제 실패");
					}
				};
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	}
    }
    
    void updateMachingInfo() {
    	int selectedWatingMemIndex = tableRequestMem.getSelectionModel().getSelectedIndex();
    	int selectedMemIndex = tableMem.getSelectionModel().getSelectedIndex();
    	Member actMember = new Member();
    	WatingMember actWatingMember = new WatingMember();
    	if(selectedMemIndex !=-1) {
    		actMember = (Member) tableMem.getItems().get(selectedMemIndex);
    		txtMachMemNo.setText(actMember.getMemNo());
    	}
    	if(selectedWatingMemIndex !=-1) {
    		actWatingMember = (WatingMember) tableRequestMem.getItems().get(selectedWatingMemIndex);
    		txtMachId.setText(actWatingMember.getId());
    	}
    	
    	if(checkMachName.isSelected()) {
    		txtMachName.setText(actWatingMember.getName()==null?"":actWatingMember.getName());
    	}else {
    		txtMachName.setText(actMember.getMemName()==null?"":actMember.getMemName());
    	}
    	
    	if(checkMachPhone.isSelected()) {
    		txtMachPhone.setText(actWatingMember.getPhone()==null?"":actWatingMember.getPhone());
    	}else {
    		txtMachPhone.setText(actMember.getMemPhone()==null?"":actMember.getMemPhone());
    	}
    	
    	if(checkMachAdd.isSelected()) {
    		txtaMachAdd.setText(actWatingMember.getAddress()==null?"":actWatingMember.getAddress());
    	}else {
    		txtaMachAdd.setText(actMember.getMemAddress()==null?"":actMember.getMemAddress());
    	}
    	
    }
    
    void setTableMemData(){
    	
    	MemberDAO dao = new MemberDAO();
    	List<Member> data = new ArrayList<>();
    	String attTitle = "MEM_ID";

 		try {
 			//아이디가 없는 멤버만 불러옴
			data = dao.selectNull(attTitle, MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		tableArrMem = FXCollections.observableArrayList();
		if(!data.isEmpty()){ 
			for (Member x : data) {
				tableArrMem.add(x);
			}
		}
		tableMem.setItems(tableArrMem);
    }
    
    void setTableRequestMemData(){
    	WatingMemberDAO dao = new WatingMemberDAO();
    	List<WatingMember> data = new ArrayList<>();
 		try {
			data = dao.selectAll(MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
 		tableArrWatingMem = FXCollections.observableArrayList();
		if(!data.isEmpty()){ 
			for (WatingMember x : data) {
				tableArrWatingMem.add(x);
			}
		}
		tableRequestMem.setItems(tableArrWatingMem);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtMachId.setDisable(true);
		txtMachMemNo.setDisable(true);
		txtMachPhone.setDisable(true);
		txtMachName.setDisable(true);
		txtaMachAdd.setDisable(true);
		// TODO Auto-generated method stub
		setTableMemData();
		setTableRequestMemData();
		colMemNo.setCellValueFactory(new PropertyValueFactory<Member, String>("memNo"));
		colMemName.setCellValueFactory(new PropertyValueFactory<Member, String>("memName"));
		colMemPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("memPhone"));
		colMemAdd.setCellValueFactory(new PropertyValueFactory<Member, String>("memAddress"));
		colRequestName.setCellValueFactory(new PropertyValueFactory<WatingMember, String>("name"));
		colRequestAdd.setCellValueFactory(new PropertyValueFactory<WatingMember, String>("address"));
		colRequestId.setCellValueFactory(new PropertyValueFactory<WatingMember, String>("id"));
		colRequestPhone.setCellValueFactory(new PropertyValueFactory<WatingMember, String>("phone"));
	}

}
