package org.church0691.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.church0691.dao.ExpenseTitleDAO;
import org.church0691.dao.IncomeTitleDAO;
import org.church0691.vo.ExpenseTitle;
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

public class ExpenseTitleController implements Initializable{

	ObservableList<ExpenseTitle> expenseTitleArr;
	ObservableList btnChild;
	
    @FXML
    private TableView<ExpenseTitle> tableExpenseTitle;
    @FXML
    private TableColumn<ExpenseTitle, Integer> colExpenseTitleNo;
    @FXML
    private TableColumn<ExpenseTitle, String> colExpenseSubTitle;
    @FXML
    private TableColumn<ExpenseTitle, String> colExpenseTitle;


    @FXML
    private TextField txtExpenseTitle;
    @FXML
    private TextField txtExpenseTitleNum;
    @FXML
    private TextField txtExpenseSubTitle;

    @FXML
    private HBox hboxAllBtn;
    @FXML
    private Button btnExpenseTitleUpdate;
    @FXML
    private Button btnExpenseTitleInsert;
    @FXML
    private Button btnExpenseTitleDelete;

    private String insertMsg = "계정번호(자동생성)";
    
    @FXML
    void actClickInsertBtn(ActionEvent event) {
    	if(txtExpenseTitleNum.getText().equals(insertMsg)) {
    		if(txtExpenseTitle.getText().equals("")){
    			JOptionPane.showMessageDialog(null, "계정분류를 입력해 주세요.");
    		}else if(txtExpenseSubTitle.getText().equals("")){
    			JOptionPane.showMessageDialog(null, "계정이름을 입력해 주세요.");
    		}else {
    			ExpenseTitleDAO dao = new ExpenseTitleDAO();
    			ExpenseTitle vo = new ExpenseTitle();
    			vo.setTitle(txtExpenseTitle.getText());
    			vo.setSubTitle(txtExpenseSubTitle.getText());
    			try {
					boolean res = dao.insert(vo, MyUtils.getConn());
					if(res) {
						JOptionPane.showMessageDialog(null, "계정과목이 등록되었습니다.");
						setExpenseTitleArr();
						txtExpenseTitle.setText("");
						txtExpenseSubTitle.setText("");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}else {
    		txtExpenseTitleNum.setText(insertMsg);
    		txtExpenseTitleNum.setDisable(true);
    		txtExpenseTitle.setText("");
    		txtExpenseSubTitle.setText("");
    		btnChild.removeAll(btnChild);
        	btnChild.add(btnExpenseTitleInsert);
    	}

    }

    @FXML
    void actClickUpdateBtn(ActionEvent event) {
    	System.out.println("actClickUpdateBtn");
    	if(txtExpenseTitle.getText().equals("")) {
    		JOptionPane.showMessageDialog(null, "변경 할 계정명을 입력하세요.");
    	}else if(txtExpenseSubTitle.getText().equals("")){
			JOptionPane.showMessageDialog(null, "변경 할 계정이름을 입력해 주세요.");
    	}else {
	    	int res = JOptionPane.showConfirmDialog(null, "계정이름을 변경하시겠습니까? \n"
	    			+ "(경고) 지금까지 이 계정을 사용한 모든 데이터가 변경됩니다.","변경 확인",JOptionPane.YES_OPTION);
	    	if(res== JOptionPane.YES_OPTION) {
	    		ExpenseTitleDAO dao = new ExpenseTitleDAO();
	    		ExpenseTitle vo = new ExpenseTitle();
				vo.setId(Integer.parseInt(txtExpenseTitleNum.getText()));
				vo.setTitle(txtExpenseTitle.getText());
				vo.setSubTitle(txtExpenseSubTitle.getText());
				try {
					boolean result = dao.update(vo, MyUtils.getConn());
					if(result) {
						JOptionPane.showMessageDialog(null, "수정완료!");
						setExpenseTitleArr();
						actClickInsertBtn(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
    	}

    }

    @FXML
    void actClickDeleteBtn(ActionEvent event) {
    	int selectedExpenseTitleIndex = tableExpenseTitle.getSelectionModel().getSelectedIndex();
    	ExpenseTitle selectedExpenseTitle = (ExpenseTitle) tableExpenseTitle.getItems().get(selectedExpenseTitleIndex);
    	int res = JOptionPane.showConfirmDialog(null, selectedExpenseTitle + " 계정을 삭제하시겠습니까?  \n"
    			,"삭제 확인",JOptionPane.YES_OPTION);
    	if(res == JOptionPane.YES_OPTION) {
    		ExpenseTitleDAO dao = new ExpenseTitleDAO();
    		boolean result;
    		if(false){
    			//income계정에 참조하는 데이터가 있으면 (추후 코딩)
    			selectedExpenseTitle.setDel("삭제");
    			try {
					result = dao.update(selectedExpenseTitle, MyUtils.getConn());
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}else {
    			//income에 참조하는 계정이 없으면 정말로 삭제
				try {
					result = dao.delete(selectedExpenseTitle.getId(), MyUtils.getConn());
					if(result) {
						JOptionPane.showMessageDialog(null, "삭제완료");
						setExpenseTitleArr();
						actClickInsertBtn(null);
					}else {
						JOptionPane.showMessageDialog(null, "expense dao.delete : 삭제실패");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }

    @FXML
    void actClickExpense(MouseEvent event) {
    	int selectedIncomeTitleIndex = tableExpenseTitle.getSelectionModel().getSelectedIndex();
    	if(selectedIncomeTitleIndex!=-1) {
    		ExpenseTitle selectedIncomeTitle = (ExpenseTitle) tableExpenseTitle.getItems().get(selectedIncomeTitleIndex);
	    	txtExpenseTitleNum.setText(selectedIncomeTitle.getId()+"");
	    	txtExpenseTitle.setText(selectedIncomeTitle.getTitle());
	    	txtExpenseSubTitle.setText(selectedIncomeTitle.getSubTitle());
	    	btnChild.removeAll(btnChild);
	    	btnChild.add(btnExpenseTitleInsert);
	    	btnChild.add(btnExpenseTitleUpdate);
	    	btnChild.add(btnExpenseTitleDelete);
    	}
    }
    
    void setExpenseTitleArr() {
    	expenseTitleArr = FXCollections.observableArrayList();
    	ExpenseTitleDAO dao = new ExpenseTitleDAO();
    	
    	List<ExpenseTitle> data = new ArrayList<>();
 		try {
			data = dao.selectAll(MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		if(!data.isEmpty()){ 
			for (ExpenseTitle x : data) {
				expenseTitleArr.add(x);
			}
		}
		tableExpenseTitle.setItems(expenseTitleArr);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnChild = hboxAllBtn.getChildren();
		setExpenseTitleArr();
		actClickInsertBtn(null);

		colExpenseTitleNo.setCellValueFactory(new PropertyValueFactory<ExpenseTitle, Integer>("id"));
		colExpenseTitle.setCellValueFactory(new PropertyValueFactory<ExpenseTitle, String>("title"));
		colExpenseSubTitle.setCellValueFactory(new PropertyValueFactory<ExpenseTitle, String>("subTitle"));
		
	}

}


