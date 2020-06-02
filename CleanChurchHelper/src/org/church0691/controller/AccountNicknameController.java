package org.church0691.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.church0691.dao.AccountDAO;
import org.church0691.vo.Account;

import common.MyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class AccountNicknameController implements Initializable{

	ObservableList<Account> AccountArr;
	
	@FXML
	private TableView<Account> tableAccount;
	@FXML
	private TableColumn<Account, String> colAccountNo;
	@FXML
	private TableColumn<Account, String> colAccountBank;
	@FXML
	private TableColumn<Account, String> colAccountNickname;
    @FXML
    private TableColumn<Account, String> colAccountInfo;

    void setAccountArr(){
    	AccountArr = FXCollections.observableArrayList();
    	AccountDAO dao = new AccountDAO();
    	List<Account> data = new ArrayList<>();

 		try {
			data = dao.selectAll(MyUtils.getConn());
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		if(!data.isEmpty()){ 
			for (Account x : data) {
				AccountArr.add(x);
			}
		}
		tableAccount.setItems(AccountArr);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//btnChild = hboxAllBtn.getChildren();
		setAccountArr();
		//tableAccount.setEditable(true);
	     Callback<TableColumn<Account, String>, TableCell<Account, String>> cellFactory =
	              new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
	                  public TableCell call(TableColumn p) {
	                      return new EditingCell();
	                  }
	              };
		
		

		colAccountNo.setCellValueFactory(new PropertyValueFactory<Account, String>("no"));
		colAccountBank.setCellValueFactory(new PropertyValueFactory<Account, String>("bank"));
		colAccountNickname.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
		colAccountInfo.setCellValueFactory(new PropertyValueFactory<Account, String>("info"));
		
		colAccountNickname.setCellFactory(cellFactory);
		colAccountNickname.setOnEditCommit(
	              new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
	                  @Override public void handle(TableColumn.CellEditEvent<Account, String> t) {
	                      ((Account)t.getTableView().getItems().get(
	                              t.getTablePosition().getRow())).setName(t.getNewValue());
	                  }
	              });
		
		colAccountInfo.setCellFactory(cellFactory);
		colAccountInfo.setOnEditCommit(
	              new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
	                  @Override public void handle(TableColumn.CellEditEvent<Account, String> t) {
	                      ((Account)t.getTableView().getItems().get(
	                              t.getTablePosition().getRow())).setName(t.getNewValue());
	                  }
	              });
	}

	class EditingCell extends TableCell<Account, String> {
		
		private TextField textField;
		
		@Override
		public void startEdit() {
			super.startEdit();
			
			if (textField == null) {
				createTextField();
			}
			
			setGraphic(textField);
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			textField.selectAll();
		}
		
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(String.valueOf(getItem()));
			setContentDisplay(ContentDisplay.TEXT_ONLY);
		}
		
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setGraphic(textField);
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				} else {
					setText(getString());
					setContentDisplay(ContentDisplay.TEXT_ONLY);
				}
			}
		}
		
		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
			textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}
			});
		}
		
		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}

