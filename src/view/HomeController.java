package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	
	@FXML private Button phone;
	@FXML private Button food;
	@FXML private Button adminPhone;
	@FXML private Button adminFood;
	
	public void phoneButtonPressed(ActionEvent event) throws IOException{
		System.out.println("Going to phones View Rule base scene");
		FXMLLoader loader = new FXMLLoader();
		
		FileInputStream fxmlStream = new FileInputStream("src/view/PhoneView.fxml");
		Parent p = loader.load(fxmlStream);
		
		Scene s = new Scene(p);
		
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		window.setScene(s);
		
		window.show();
	}
	
	public void foodButtonPressed(ActionEvent event) throws IOException{
		System.out.println("Going to food View Rule base scene");
		FXMLLoader loader = new FXMLLoader();
		
		FileInputStream fxmlStream = new FileInputStream("src/view/FoodView.fxml");
		Parent p = loader.load(fxmlStream);
		
		Scene s = new Scene(p);
		
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		window.setScene(s);
		
		window.show();
	}
	
	public void adminFoodButtonPressed(ActionEvent event) throws IOException{
		System.out.println("Going to food admin View Rule base scene");
		FXMLLoader loader = new FXMLLoader();
		
		FileInputStream fxmlStream = new FileInputStream("src/view/AdminFood.fxml");
		Parent p = loader.load(fxmlStream);
		
		Scene s = new Scene(p);
		
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		window.setScene(s);
		
		window.show();
	}
	
	public void adminPhoneButtonPressed(ActionEvent event) throws IOException{
		System.out.println("Going to food View Rule base scene");
		FXMLLoader loader = new FXMLLoader();
		
		FileInputStream fxmlStream = new FileInputStream("src/view/AdminPhone.fxml");
		Parent p = loader.load(fxmlStream);
		
		Scene s = new Scene(p);
		
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		window.setScene(s);
		
		window.show();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
