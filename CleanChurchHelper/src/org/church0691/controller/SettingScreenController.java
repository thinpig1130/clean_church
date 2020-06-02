package org.church0691.controller;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SettingScreenController {
    @FXML
    private Label labExpense;
    @FXML
    private AnchorPane ancMainPane;
    @FXML
    private Label labIncome;
    @FXML
    private Label labNewAccount;
    @FXML
    private Label labAccountInfo;
    @FXML
    private Label labAccountBalance;

    @FXML
    void actNewAccount(MouseEvent event) {
    	System.out.println("actNewAccount");
    }

    @FXML
    void actAccountInfo(MouseEvent event) {
    	System.out.println("actAccountInfo");
    }

    @FXML
    void actAccountBalance(MouseEvent event) {
    	System.out.println("actAccountBalance");
    }

    @FXML
    void actIncomeClick(MouseEvent event) {
    	BorderPane incomeScreen;
		try {
			incomeScreen = (BorderPane) FXMLLoader.load(getClass().getResource("../view/SettingSubScreenIncome.fxml"));
			ObservableList nodes = ancMainPane.getChildren();
			ancMainPane.getChildren().removeAll(nodes);
			ancMainPane.getChildren().add(incomeScreen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void actExpenseClick(MouseEvent event) {
    	BorderPane incomeScreen;
		try {
			incomeScreen = (BorderPane) FXMLLoader.load(getClass().getResource("../view/SettingSubScreenExpense.fxml"));
			ObservableList nodes = ancMainPane.getChildren();
			ancMainPane.getChildren().removeAll(nodes);
			ancMainPane.getChildren().add(incomeScreen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
