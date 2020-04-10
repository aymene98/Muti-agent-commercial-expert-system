package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import engine.EqualsClause;
import engine.Rule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminFoodController implements Initializable{
	@FXML CheckBox avalability; 
	@FXML CheckBox available;
	
	@FXML ComboBox rules;
	ArrayList<Rule> rulesList = new ArrayList<Rule>();
	
	@FXML TextField ruleName;
	@FXML TextField meat;
	@FXML TextField packaging;
	@FXML TextField drink;
	@FXML TextField price;
	@FXML TextField type;
	
	@FXML Button addRule;
	@FXML Button deleteRule;
	@FXML Button goBack;
	
	@FXML TextArea currentRules;
	
	@FXML Label outputLabel;
	
	public void goBackButtonClicked(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("Home.fxml"));
		Scene s = new Scene(p);
		Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
		window.setScene(s);
		window.show();
	}
	
	public void deleteRuleButtonPressed(ActionEvent event) throws Exception{
		int indexRemove = -1;
		Rule r;
		for (int i = 0; i < this.rulesList.size(); i++) {
			r = this.rulesList.get(i);
			if(this.rules.getValue().equals(r.getName())){
				indexRemove = i;
			}
		}
		this.rulesList.remove(indexRemove);
		
		this.currentRules.setText(this.rulesList.toString());
		this.rules.getItems().clear();;
		for (Rule ru : this.rulesList) {
			this.rules.getItems().add(ru.getName()); // adding to the combobox
		}
		
		FileOutputStream f = new FileOutputStream(new File("foodRules.txt"),false);
		ObjectOutputStream o = new ObjectOutputStream(f);
		
		o.writeObject(this.rulesList);
		
		o.close();
		f.close();	
	}
	
	public void addRuleButtonClicked(ActionEvent event) throws Exception{
		for (Rule rule : rulesList) {
			if(rule.getName().equals(this.ruleName.getText())){
				this.outputLabel.setText("Rule name taken !");
				return;
			}
		}
		
		Rule r = new Rule(this.ruleName.getText());
		
		if (this.avalability.isSelected()) {
			if (!this.type.getText().equals("")) {
				r.addAntecedent(new EqualsClause("type", this.type.getText()));
			}
			if (this.available.isSelected()) {
				r.setConsequent(new EqualsClause("available", "yes"));	
			} else {
				r.setConsequent(new EqualsClause("available", "no"));
			}
		}else{
			
			if (!this.meat.getText().equals("")) {
				r.addAntecedent(new EqualsClause("meat", this.meat.getText()));				
			}
			if (!this.packaging.getText().equals("")) {
				r.addAntecedent(new EqualsClause("packaging", this.packaging.getText()));			
			}
			if (!this.drink.getText().equals("")) {
				r.addAntecedent(new EqualsClause("drink", this.drink.getText()));				
			}
			if (!this.price.getText().equals("")) {
				r.addAntecedent(new EqualsClause("price", this.price.getText()));				
			}
			if (!this.type.getText().equals("")) {
				r.setConsequent(new EqualsClause("type", this.type.getText()));				
			}
			
		}
		
		if (this.type.getText().equals("") || this.ruleName.getText().equals("")) {
			this.outputLabel.setText("mandentory fields empty !");				
		}
		else{
			this.rulesList.add(r);
			this.currentRules.setText(this.rulesList.toString());
			
			for (Rule ru : this.rulesList) {
				this.rules.getItems().add(ru.getName()); // adding to the combobox
			}
			FileOutputStream f = new FileOutputStream(new File("foodRules.txt"),false);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(this.rulesList);
			
			o.close();
			f.close();			
		}
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.outputLabel.setText("");
			
			File f = new File("foodRules.txt");
			f.createNewFile();
			
			FileInputStream fi = new FileInputStream(f);
			if (fi.available() != 0) {
				ObjectInputStream oi = new ObjectInputStream(fi);
				this.rulesList = (ArrayList<Rule>) oi.readObject();
				oi.close();
			} else {
				this.rulesList = new ArrayList<Rule>();
			}
			fi.close();
			
			for (Rule r : this.rulesList) {
				this.rules.getItems().add(r.getName()); // adding to the combobox
			}
			
			this.currentRules.setText(this.rulesList.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
