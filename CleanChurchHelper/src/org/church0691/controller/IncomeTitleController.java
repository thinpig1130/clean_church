package org.church0691.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.IncomeTitleDAO;
import org.church0691.vo.IncomeTitle;

import common.MyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class IncomeTitleController implements Initializable{
	
	ObservableList<IncomeTitle> incomeTitleArr;
	ObservableList btnChild;
	
    @FXML
    private TableView<IncomeTitle> tableIncomeTitle;
    
    @FXML
    private HBox hboxAllBtn;

    @FXML
    private TextField txtIncomeTitleNum;
    @FXML
    private TextField txtIncomeTitleName;
    
    @FXML
    private Button btnIncomeTitleUpdate;
    @FXML
    private Button btnIncomeTitleInsert;
    @FXML
    private Button btnIncomeTitleDelete;

    @FXML
    private TableColumn<IncomeTitle, String> colIncomeTitleName;

    @FXML
    private TableColumn<IncomeTitle, Integer> colIncomeTitleNo;
    
    private String insertMsg = "계정번호(자동생성)";

    @FXML
    void actClickUpdateBtn(ActionEvent event) {
    	System.out.println("actClickUpdateBtn");
    	if(txtIncomeTitleName.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "변경 할 계정명을 입력하세요.");
    	}else {
	    	int res = JOptionPane.showConfirmDialog(null, "계정이름을 변경하시겠습니까? \n"
	    			+ "(경고) 지금까지 이 계정을 사용한 모든 데이터가 변경됩니다.","변경 확인",JOptionPane.YES_OPTION);
	    	if(res== JOptionPane.YES_OPTION) {
	    		IncomeTitleDAO dao = new IncomeTitleDAO();
				IncomeTitle vo = new IncomeTitle();
				vo.setId(Integer.parseInt(txtIncomeTitleNum.getText()));
				vo.setTitle(txtIncomeTitleName.getText());
				try {
					boolean result = dao.update(vo, MyUtils.getConn());
					if(result) {
						JOptionPane.showMessageDialog(null, "수정완료!");
						setIncomeTitleArr();
						actClickInsertBtn(null);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	}
	}
    
    @FXML
    void actClickDeleteBtn(ActionEvent event) {
    	int selectedIncomeTitleIndex = tableIncomeTitle.getSelectionModel().getSelectedIndex();
		IncomeTitle selectedIncomeTitle = (IncomeTitle) tableIncomeTitle.getItems().get(selectedIncomeTitleIndex);
    	int res = JOptionPane.showConfirmDialog(null, selectedIncomeTitle + " 계정을 삭제하시겠습니까?  \n"
    			,"삭제 확인",JOptionPane.YES_OPTION);
    	if(res == JOptionPane.YES_OPTION) {
    		IncomeTitleDAO dao = new IncomeTitleDAO();
    		boolean result;
    		if(false){
    			//income계정에 참조하는 데이터가 있으면 (추후 코딩)
    			selectedIncomeTitle.setDel("삭제");
    			try {
					result = dao.update(selectedIncomeTitle, MyUtils.getConn());
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}else {
    			//income에 참조하는 계정이 없으면 정말로 삭제
				try {
					result = dao.delete(selectedIncomeTitle.getId(), MyUtils.getConn());
					if(result) {
						JOptionPane.showMessageDialog(null, "삭제완료");
						setIncomeTitleArr();
						actClickInsertBtn(null);
					}else {
						JOptionPane.showMessageDialog(null, "dao.delete : 삭제실패");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    }

    @FXML
    void actClickInsertBtn(ActionEvent event) {
    	if(txtIncomeTitleNum.getText().equals(insertMsg)) {
    		if(txtIncomeTitleName.getText().equals("")){
    			JOptionPane.showMessageDialog(null, "계정이름을 입력해 주세요.");
    		}else {
    			IncomeTitleDAO dao = new IncomeTitleDAO();
    			IncomeTitle vo = new IncomeTitle();
    			vo.setTitle(txtIncomeTitleName.getText());
    			try {
					boolean res = dao.insert(vo, MyUtils.getConn());
					if(res) {
						JOptionPane.showMessageDialog(null, "계정과목이 등록되었습니다.");
						setIncomeTitleArr();
						txtIncomeTitleName.setText("");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}else {
    		txtIncomeTitleNum.setText(insertMsg);
    		txtIncomeTitleNum.setDisable(true);
    		txtIncomeTitleName.setText("");
    		btnChild.removeAll(btnChild);
        	btnChild.add(btnIncomeTitleInsert);
    	}
    }
    
    @FXML
    void actClickIncome(MouseEvent event) {
    	int selectedIncomeTitleIndex = tableIncomeTitle.getSelectionModel().getSelectedIndex();
    	if(selectedIncomeTitleIndex!=-1) {
	    	IncomeTitle selectedIncomeTitle = (IncomeTitle) tableIncomeTitle.getItems().get(selectedIncomeTitleIndex);
	    	txtIncomeTitleNum.setText(selectedIncomeTitle.getId()+"");
	    	txtIncomeTitleName.setText(selectedIncomeTitle.getTitle());
	    	btnChild.removeAll(btnChild);
	    	btnChild.add(btnIncomeTitleInsert);
	    	btnChild.add(btnIncomeTitleUpdate);
	    	btnChild.add(btnIncomeTitleDelete);
    	}
    	System.out.println("actClickIncome");
    }
    
    void setIncomeTitleArr() {
    	incomeTitleArr = FXCollections.observableArrayList();
    	IncomeTitleDAO dao = new IncomeTitleDAO();
    	List<IncomeTitle> data = new ArrayList<>();
//    	String attTitle = "MEM_ID";

 		try {
			data = dao.selectAll(MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		if(!data.isEmpty()){ 
			for (IncomeTitle x : data) {
				incomeTitleArr.add(x);
			}
		}
		tableIncomeTitle.setItems(incomeTitleArr);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnChild = hboxAllBtn.getChildren();
		setIncomeTitleArr();
		actClickInsertBtn(null);

		colIncomeTitleNo.setCellValueFactory(new PropertyValueFactory<IncomeTitle, Integer>("id"));
		colIncomeTitleName.setCellValueFactory(new PropertyValueFactory<IncomeTitle, String>("title"));
		
	}
}
