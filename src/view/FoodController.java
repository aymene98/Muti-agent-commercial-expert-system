package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import jade.imtp.leap.sms.Boot;
import jade.wrapper.AgentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class FoodController implements Initializable{
	
	public static Boolean available = null;
	public static String rulebase = "";
	
	@FXML Label resultLabel;
	@FXML Button goBackButton;
	@FXML Button runButton;
	
	@FXML private RadioButton meal;
	@FXML private RadioButton sandwish;
	@FXML private RadioButton ham;
	@FXML private RadioButton taco;
	
	@FXML private RadioButton fish;
	@FXML private RadioButton meat;
	@FXML private RadioButton chicken;
	
	@FXML private RadioButton price200;
	@FXML private RadioButton price300;
	@FXML private RadioButton price800;
	@FXML private RadioButton price1000;
	
	@FXML private RadioButton coca;
	@FXML private RadioButton pepsi;
	@FXML private RadioButton juice;
	
	@FXML private RadioButton forwardChain;
	@FXML private RadioButton backwardChain;
	
	@FXML private TextArea textArea;
	
	public void goBackButtonClicked(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("Home.fxml"));
		Scene s = new Scene(p);
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		window.setScene(s);
		window.show();
	}
	
	public void runButtonClicked(){
		String meat = "",packaging = "",drink = "",price = "",chain = "";
		/** Adding the facts **/

		// packaging
		if (this.meal.isSelected()) {
			packaging = "meal";
		}
		if (this.sandwish.isSelected()) {
			packaging = "sandwish";
		}
		if (this.ham.isSelected()) {
			packaging = "ham";
		}
		if (this.taco.isSelected()) {
			packaging = "taco";
		}
		
		// meat
		if (this.meat.isSelected()) {
			meat = "meat";
		}
		if (this.fish.isSelected()) {
			meat = "fish";
		}
		if (this.chicken.isSelected()) {
			meat = "chicken";
		}
		
		// drink
		if (this.coca.isSelected()) {
			drink = "coca";
		}
		if (this.pepsi.isSelected()) {
			drink = "pepsi";
		}
		if (this.juice.isSelected()) {
			drink = "juice";
		}
		
		// price
		if (this.price200.isSelected()) {
			price = "200";
		}
		if (this.price300.isSelected()) {
			price = "300";
		}
		if (this.price800.isSelected()) {
			price = "800";
		}
		if (this.price1000.isSelected()) {
			price = "1000";
		}
		
		if (this.forwardChain.isSelected()) {
			chain = "forwardChain";
		}
		else{
			chain = "backwardChain";
		}
		
		try {
			String info = "food" +" " + meat +" " + packaging + " " + drink + " " + price + " " + chain;
			MainApp.CENTRAL.putO2AObject(info, AgentController.ASYNC);
			
			TimeUnit.SECONDS.sleep(2);
			
			System.out.println("controller : " + FoodController.rulebase);
			if (FoodController.available == null) {
				this.resultLabel.setText("We do not sell this meal.");
			} else {
				this.resultLabel.setText("Available : " + FoodController.available.toString());
			}
			this.textArea.setText("Rule base : \n" + FoodController.rulebase);
			//this.textArea.setText("Rule base : \n" );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resultLabel.setText("");
	}

}
