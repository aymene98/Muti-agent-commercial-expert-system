package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;


public class Main {
	
	public static RuleInferenceEngine getInferenceEngine(){
	    RuleInferenceEngine rie=new KieRuleInferenceEngine();
	
	    Rule rule=new Rule("Bicycle");
	    rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
	    rule.addAntecedent(new EqualsClause("num_wheels", "2"));
	    rule.addAntecedent(new EqualsClause("motor", "no"));
	    rule.setConsequent(new EqualsClause("vehicle", "Bicycle"));
	    rie.addRule(rule);
	
	    rule=new Rule("Tricycle");
	    rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
	    rule.addAntecedent(new EqualsClause("num_wheels", "3"));
	    rule.addAntecedent(new EqualsClause("motor", "no"));
	    rule.setConsequent(new EqualsClause("vehicle", "Tricycle"));
	    rie.addRule(rule);
	
	    rule=new Rule("Motorcycle");
	    rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
	    rule.addAntecedent(new EqualsClause("num_wheels", "2"));
	    rule.addAntecedent(new EqualsClause("motor", "yes"));
	    rule.setConsequent(new EqualsClause("vehicle", "Motorcycle"));
	    rie.addRule(rule);
	
	    rule=new Rule("SportsCar");
	    rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
	    rule.addAntecedent(new EqualsClause("size", "medium"));
	    rule.addAntecedent(new EqualsClause("num_doors", "2"));
	    rule.setConsequent(new EqualsClause("vehicle", "Sports_Car"));
	    rie.addRule(rule);
	
	    rule=new Rule("Sedan");
	    rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
	    rule.addAntecedent(new EqualsClause("size", "medium"));
	    rule.addAntecedent(new EqualsClause("num_doors", "4"));
	    rule.setConsequent(new EqualsClause("vehicle", "Sedan"));
	    rie.addRule(rule);
	
	    rule=new Rule("MiniVan");
	    rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
	    rule.addAntecedent(new EqualsClause("size", "medium"));
	    rule.addAntecedent(new EqualsClause("num_doors", "3"));
	    rule.setConsequent(new EqualsClause("vehicle", "MiniVan"));
	    rie.addRule(rule);
	
	    rule=new Rule("SUV");
	    rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
	    rule.addAntecedent(new EqualsClause("size", "large"));
	    rule.addAntecedent(new EqualsClause("num_doors", "4"));
	    rule.setConsequent(new EqualsClause("vehicle", "SUV"));
	    rie.addRule(rule);
	
	    rule=new Rule("Cycle");
	    rule.addAntecedent(new LessClause("num_wheels", "4"));
	    rule.setConsequent(new EqualsClause("vehicleType", "cycle"));
	    rie.addRule(rule);
	
	    rule=new Rule("Automobile");
	    rule.addAntecedent(new EqualsClause("num_wheels", "4"));
	    rule.addAntecedent(new EqualsClause("motor", "yes"));
	    rule.setConsequent(new EqualsClause("vehicleType", "automobile"));
	    rie.addRule(rule);
	
	    return rie;
	}
	
	public static List<Clause> testForwardChain()
	{
	    RuleInferenceEngine rie=getInferenceEngine();
	    /*rie.addFact(new EqualsClause("num_wheels", "4"));
	    rie.addFact(new EqualsClause("motor", "yes"));
	    rie.addFact(new EqualsClause("num_doors", "3"));
	    rie.addFact(new EqualsClause("size", "medium"));*/
	    
	    rie.addFact(new EqualsClause("num_wheels", "4"));
	    rie.addFact(new EqualsClause("motor", "yes"));
	    rie.addFact(new EqualsClause("num_doors", "2"));
	    rie.addFact(new EqualsClause("size", "small"));

	    System.out.println("before inference");
	    System.out.println(rie.getFacts());
	    System.out.println();

	    rie.infer(); //forward chain

	    System.out.println("after inference");
	    System.out.println(rie.getFacts());
	    System.out.println();
	    return rie.getFacts();
	}
	
	public static void testBackwardChain()
	{
	    RuleInferenceEngine rie=getInferenceEngine();
	    rie.addFact(new EqualsClause("num_wheels", "4"));
	    rie.addFact(new EqualsClause("motor", "yes"));
	    rie.addFact(new EqualsClause("num_doors", "3"));
	    rie.addFact(new EqualsClause("size", "medium"));

	    System.out.println("Infer: vehicle");

	    Vector<Clause> unproved_conditions= new Vector<>();

	    Clause conclusion=rie.infer("vehicle", unproved_conditions);

	    System.out.println("Conclusion: "+conclusion);
	}

	
	public static void main(String[] args) {
		/*Clause res;
		try {
			res = testForwardChain().get(5);
			System.out.println(res);
		} catch (Exception e) {
			System.out.println("goal not reached");
		}
		*/
		//testBackwardChain();
		Object[] options = {"Yes, please",
                "No, thanks",
                "No eggs, no ham!"};
		int n = JOptionPane.showOptionDialog(new JFrame(),
		"Would you like some green eggs to go with that ham?",
		"A Silly Question",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,
		options[2]);

		System.out.println(n);
		
	}
}
