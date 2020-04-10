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

public class PhoneController implements Initializable{
	
	public static Boolean available = null;
	public static String rulebase = "";
	
	@FXML private Button goBackButton;
	@FXML private Button runButton;
	
	@FXML private Label resultLabel;
	
	@FXML private RadioButton inches5;
	@FXML private RadioButton inches6;
	@FXML private RadioButton inches10;
	@FXML private RadioButton inches27;
	
	@FXML private RadioButton battery4K;
	@FXML private RadioButton battery10K;
	@FXML private RadioButton battery100K;
	
	@FXML private RadioButton price20K;
	@FXML private RadioButton price50K;
	@FXML private RadioButton price100K;
	@FXML private RadioButton price150K;
	
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
		String screenSize = "",battery = "",price = "",chain = "";
		/** Adding the facts **/
		// screen size
		if (this.inches5.isSelected()) {
			screenSize = "5";
		}
		if (this.inches6.isSelected()) {
			screenSize = "6";
		}
		if (this.inches10.isSelected()) {
			screenSize = "10";
		}
		if (this.inches27.isSelected()) {
			screenSize = "27";
		}
		
		// battery
		if (this.battery4K.isSelected()) {
			battery = "4000";
		}
		if (this.battery10K.isSelected()) {
			battery = "10000";
		}
		if (this.battery100K.isSelected()) {
			battery = "100000";
		}
		
		// price
		if (this.price20K.isSelected()) {
			price = "20000";
		}
		if (this.price50K.isSelected()) {
			price = "50000";
		}
		if (this.price100K.isSelected()) {
			price = "100000";
		}
		if (this.price150K.isSelected()) {
			price = "150000";
		}
		
		if (this.forwardChain.isSelected()) {
			chain = "forwardChain";
		}
		else{
			chain = "backwardChain";
		}
		
		try {
			
			String info = "phone" +" " + screenSize +" " + battery + " " + price + " " + chain;
			MainApp.CENTRAL.putO2AObject(info, AgentController.ASYNC);
			
			TimeUnit.SECONDS.sleep(2);
			
			System.out.println(PhoneController.rulebase);
			if (PhoneController.available == null) {
				this.resultLabel.setText("We do not sell this model.");
			} else {
				this.resultLabel.setText("Available : " + PhoneController.available.toString());
			}
			this.textArea.setText("Rule base : \n" + PhoneController.rulebase);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.resultLabel.setText("");
	}
	
}
