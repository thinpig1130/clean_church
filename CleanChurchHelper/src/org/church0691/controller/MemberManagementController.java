package org.church0691.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.AuthorityDAO;
import org.church0691.dao.MemberDAO;
import org.church0691.dao.WatingMemberDAO;
import org.church0691.vo.Authority;
import org.church0691.vo.Member;

import common.MyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MemberManagementController implements Initializable {	
	private ObservableList<Member> tableArrMem;
	private ObservableList<String> comboAuthArr;

	@FXML
	private TableView<Member> tableMember;
    @FXML
    private TableColumn<Member, String> colMemName;
    @FXML
    private TableColumn<Member, String> colMemId;
    @FXML
    private TableColumn<Member, String> colMemNo;
    @FXML
    private TableColumn<Member, String> colMemPhone;
    @FXML
    private TableColumn<Member, String> colMemAddress;
    @FXML
    private TableColumn<Member, String> colAuthName;
    @FXML
    private TableColumn<Member, String> colMemRemark;
    
    @FXML
    private TextArea txtaMemAddress;
    @FXML
    private ComboBox<String> comboSearchAuth;
    @FXML
    private ComboBox<String> comboAuthName;
    @FXML
    private TextField txtMemName;
    @FXML
    private TextField txtMemPhone;
    @FXML
    private TextField txtMemNo;
    @FXML
    private TextField txtMemRemark;
    @FXML
    private TextField txtSearchName;
    
    @FXML
    private Label labFormTitle;
    @FXML
    private Label labRequestJoin;

    @FXML
    private Button btnAuthPlus;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnViewAll;
    @FXML
    private Button btnFormSubmit;
    @FXML
    private Button btnPulsMem;
    @FXML
    private Button btnMinusMem;
    
    @FXML
    void actViewRequestJoin(MouseEvent event) {
    	// 조인화면 구현 로드 하기 !! 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MatchingMemberScreen.fxml"));
		try {
			Stage stage = new Stage();
			BorderPane mainScreen = loader.load();
			Scene scene = new Scene(mainScreen);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.out.println("actViewRequestJoin err");
			e.printStackTrace();
		}
    	
    	System.out.println("actViewRequestJoin");
    }

    @FXML
    void actViewAllMem(ActionEvent event) {
    	setTableAllMem();
    }

    @FXML
    void actMinusMem(ActionEvent event) {
    	int selectedIndex = tableMember.getSelectionModel().getSelectedIndex();
    	if(selectedIndex==-1) {
    		JOptionPane.showMessageDialog(null, "삭제 할 멤버를 선택하세요.");
    	}else{
	    	Member actMember = (Member) tableMember.getItems().get(selectedIndex);
	    	String msg = actMember.getMemName() +"(" + actMember.getMemNo() + ") 정말로 삭제 하시겠습니까?" ; 
	    	int res = JOptionPane.showConfirmDialog(null, msg, "삭제 확인", JOptionPane.YES_NO_OPTION);
	    	if(res== JOptionPane.YES_OPTION) {
	    		//삭제
	    		MemberDAO dao = new MemberDAO();
	    		try {
					if(dao.delete(actMember.getMemNo(), MyUtils.getConn())) {
						JOptionPane.showMessageDialog(null, "삭제 되었습니다.");
						setTableAllMem();
    					actPulsMem(null);
					};
				} catch (Exception e) {
					System.out.println("actMinusMem error");
					e.printStackTrace();
				}	    		
	    	}
    	}
    }
    
    @FXML
    void actPulsMem(ActionEvent event) {
		//Member actMember = (Member) tableMember.getItems().get(selectedIndex);
		txtMemNo.setText("");
		txtMemNo.setDisable(false);
		txtMemName.setText("");
		txtMemPhone.setText("");
		txtaMemAddress.setText("");
		txtMemRemark.setText("");
		comboAuthName.setValue("GUEST");
		btnFormSubmit.setText("등록");
		labFormTitle.setText("새로운 Member 등록");
    }
    
    void registerMember() {
    	String memNo = txtMemNo.getText();
    	String name = txtMemName.getText();
    	String phone = txtMemPhone.getText();
    	String add = txtaMemAddress.getText();
    	String remark = txtMemRemark.getText();
    	String authname= comboAuthName.getValue();
    	if(memNo.equals("")) {
    		JOptionPane.showMessageDialog(null, "식별코드를 입력 해 주세요.");
    	} else if(name.equals("")) {
    		JOptionPane.showMessageDialog(null, "이름을 입력 해 주세요.");
    	}else if(phone.equals("")){
    		JOptionPane.showMessageDialog(null, "전화번호를 입력 해 주세요.");
    	}else if(authname.equals("")) {
    		JOptionPane.showMessageDialog(null, "권한을 선택 해 주세요.");
    	}else{		
    			Member vo = new Member();
    			vo.setMemNo(memNo);
    			vo.setMemName(name);
    			vo.setMemPhone(phone);
    			vo.setMemAddress(add);
    			vo.setMemRemark(remark);
    			vo.setAuthName(authname);
    			MemberDAO dao = new MemberDAO();
    			try {
    				if(dao.insert(vo, MyUtils.getConn())) {
    					JOptionPane.showMessageDialog(null, "정상적으로 등록되었습니다.");
    					setTableAllMem();
    					actPulsMem(null);
    				};
    			} catch (Exception e) {
    				System.out.println("actionJoinRequest Sql error");
    				e.printStackTrace();
    			}
    	}
    }
    
    void updateMember() {
    	
    	String memNo = txtMemNo.getText();
    	String name = txtMemName.getText();
    	String phone = txtMemPhone.getText();
    	String add = txtaMemAddress.getText();
    	String remark = txtMemRemark.getText();
    	String authname= comboAuthName.getValue();
    	
    	if(name.equals("")) {
    		JOptionPane.showMessageDialog(null, "이름을 입력 해 주세요.");
    	}else if(phone.equals("")){
    		JOptionPane.showMessageDialog(null, "전화번호를 입력 해 주세요.");
    	}else if(authname.equals("")) {
    		JOptionPane.showMessageDialog(null, "권한을 선택 해 주세요.");
    	}else{
			Member vo = new Member();
			vo.setMemNo(memNo);
			vo.setMemName(name);
			vo.setMemPhone(phone);
			vo.setMemAddress(add);
			vo.setMemRemark(remark);
			vo.setAuthName(authname);
			MemberDAO dao = new MemberDAO();
			try {
				if(dao.update(vo, MyUtils.getConn())) {
					JOptionPane.showMessageDialog(null, "해당 내용 수정 완료");
					setTableAllMem();
					actPulsMem(null);
				};
			} catch (Exception e) {
				System.out.println("actionJoinRequest Sql error");
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void actSubmitForm(ActionEvent event) {
    	System.out.println("actSubmitForm");
    	if(btnFormSubmit.getText().equals("등록")) {
    		registerMember();
    	}else {
    		updateMember();
    	}

    }
    
    @FXML
    void actSelectMem(MouseEvent event) {
    	//System.out.println("actSelectMem");
    	int selectedIndex = tableMember.getSelectionModel().getSelectedIndex();
    	if(selectedIndex != -1) {
    		Member actMember = (Member) tableMember.getItems().get(selectedIndex);
    		txtMemNo.setText(actMember.getMemNo());
    		txtMemNo.setDisable(true);
    		txtMemName.setText(actMember.getMemName());
    		txtMemPhone.setText(actMember.getMemPhone());
    		txtaMemAddress.setText(actMember.getMemAddress());
    		txtMemRemark.setText(actMember.getMemRemark());
    		comboAuthName.setValue(actMember.getAuthName());
    		btnFormSubmit.setText("수정");
    		labFormTitle.setText("Member 정보 수정");
    	}
    }

    @FXML
    void actClickAuthPlus(ActionEvent event) {
    	System.out.println("actClickAuthPlus");
    	// 권한명과 관리 화면 넘어가는 버튼.
    }
    
    void setAuthCombo() {
    	comboAuthArr = FXCollections.observableArrayList();
    	AuthorityDAO dao = new AuthorityDAO();
    	List<Authority> list = new ArrayList<>();
    	try {
			list = dao.selectAll(MyUtils.getConn());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(Authority x : list) {
    		comboAuthArr.add(x.getAuthName());
    	}
    	comboAuthName.setItems(comboAuthArr);
    	comboSearchAuth.setItems(comboAuthArr);
    	comboAuthName.setValue("GUEST");
    }
    
    void setTableAllMem(){
    	MemberDAO dao = new MemberDAO();
    	List<Member> data = new ArrayList<>();
 		try {
			data = dao.selectAll(MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		tableArrMem = FXCollections.observableArrayList();
		if(!data.isEmpty()){ 
			for (Member x : data) {
				tableArrMem.add(x);
			}
		}
		tableMember.setItems(tableArrMem);
    }
    
    void setRequestJoinCount() {
    	WatingMemberDAO dao = new WatingMemberDAO();
    	try {
			int cnt = dao.countAll(MyUtils.getConn());
			labRequestJoin.setText(cnt + "개의 가입 승인 요청이 있습니다.");
		} catch (Exception e) {
			System.out.println("setRequestJoinCount err");
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setTableAllMem(); //Member 데이터 셋팅
		setAuthCombo(); // 권한 콤보박스 셋팅
		setRequestJoinCount();
		colMemNo.setCellValueFactory(new PropertyValueFactory<Member, String>("memNo"));
		colMemName.setCellValueFactory(new PropertyValueFactory<Member, String>("memName"));
		colMemPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("memPhone"));
		colMemAddress.setCellValueFactory(new PropertyValueFactory<Member, String>("memAddress"));
		colMemRemark.setCellValueFactory(new PropertyValueFactory<Member, String>("memRemark"));
		colAuthName.setCellValueFactory(new PropertyValueFactory<Member, String>("authName"));
		colMemId.setCellValueFactory(new PropertyValueFactory<Member, String>("memId"));
		
		
	}

}
