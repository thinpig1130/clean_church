package org.church0691.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.church0691.dao.AccountDAO;
import org.church0691.vo.Account;
import common.MyUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class AccountNicknameController implements Initializable {

	// ObservableList<Account> AccountArr;

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
	@FXML
	private Button btnSaveAccount;
	@FXML
	private Label labSaveMsg;

	String msgBasic = "수정된 내용이 없습니다.";
	String msgWarning;

	@FXML
	void actClickSaveBtn(ActionEvent event) {
		if (labSaveMsg.getText().equals(msgWarning)) {
			AccountDAO dao = new AccountDAO();
			int res = 0;
			for (Account vo : tableAccount.getItems()) {
				try {
					res += dao.update(vo, MyUtils.getConn()) ? 1 : 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (tableAccount.getItems().size() == res)
					JOptionPane.showMessageDialog(null, "저장완료");
			}
			labSaveMsg.setText(msgBasic);
			labSaveMsg.setTextFill(Color.SEAGREEN);
		}
	}

	void setAccountArr() {
		AccountDAO dao = new AccountDAO();
		List<Account> data = null;// new ArrayList<>();

		try {
			data = dao.selectAll(MyUtils.getConn());
			tableAccount.getItems().addAll(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// btnChild = hboxAllBtn.getChildren();
		msgWarning = "수정 된 내용을 반영하려면 반드시 저장해야 합니다.";
		setAccountArr();
		labSaveMsg.setText(msgBasic);
		labSaveMsg.setTextFill(Color.SEAGREEN);

		Callback<TableColumn<Account, String>, TableCell<Account, String>> editableCellFactory = new Callback<TableColumn<Account, String>, TableCell<Account, String>>() {
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};

		colAccountNo.setCellValueFactory(new PropertyValueFactory<Account, String>("no"));
		colAccountBank.setCellValueFactory(new PropertyValueFactory<Account, String>("bank"));
		colAccountNickname.setCellValueFactory(new PropertyValueFactory<Account, String>("name"));
		colAccountInfo.setCellValueFactory(new PropertyValueFactory<Account, String>("info"));

		colAccountNickname.setCellFactory(editableCellFactory);

		colAccountNickname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			}
		});

		colAccountInfo.setCellFactory(editableCellFactory);

		colAccountInfo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setInfo(t.getNewValue());

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
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
						labSaveMsg.setText(msgWarning);
						labSaveMsg.setTextFill(Color.CRIMSON);
						for (Account x : tableAccount.getItems())
							System.out.println(x);

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
