package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Vector;

import engine.Clause;
import engine.EqualsClause;
import engine.KieRuleInferenceEngine;
import engine.Rule;
import engine.RuleInferenceEngine;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class PhoneAgent extends Agent{
	
	RuleInferenceEngine rie = new KieRuleInferenceEngine();
	
	@Override
	protected void setup() {
		setEnabledO2ACommunication(true, 0);
		
		addBehaviour(new CyclicBehaviour(this){
			public void action(){
				String info = (String) myAgent.getO2AObject();
				if (info != null){
					System.out.println(info);
					try {
						getInferenceEngine();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(info);
					String T[] = info.split(" ");
					String screenSize = T[0], battery = T[2], price = T[4], chain = T[6];
					System.out.println("phone agent : ");
					System.out.print(screenSize + " ");
					System.out.print(battery + " ");
					System.out.print(price + " ");
					System.out.println(chain);

					
					if (!screenSize.equals("")) {
						rie.addFact(new EqualsClause("screen_size", screenSize));
					}
					
					if (!battery.equals("")) {
						rie.addFact(new EqualsClause("battery", battery));
					}
					
					if (!price.equals("")) {
						rie.addFact(new EqualsClause("price", price));
					}
					
					if (chain.equals("forwardChain")) {
						Clause res1,res2 ;
						
						rie.infer(); //forward chain
						System.out.println("rule base size : "+rie.getFacts().size());
						PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
						if (rie.getFacts().size() == 3) {
							PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							PhoneController.available = null;
						}
						if (rie.getFacts().size() == 4) {
							res1 = rie.getFacts().get(3);
							PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							PhoneController.available = null;
						}
						
						if (rie.getFacts().size() == 5) {
							res1 = rie.getFacts().get(3);
							res2 = rie.getFacts().get(4);
							PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							if (res2.getValue().equals("yes")) {
								PhoneController.available = new Boolean(true);
							}else{
								PhoneController.available = new Boolean(false);
							}
						}
					}
					
					if (chain.equals("backwardChain")) {
						Vector<Clause> unproved_conditions= new Vector<>();
					    Clause conclusion = rie.infer("available", unproved_conditions);
					    
					    if (conclusion == null) {
					    	PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
					    	PhoneController.available = null;
						}else{
							PhoneController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							if (conclusion.getValue().equals("yes")) {
								PhoneController.available = new Boolean(true);
							}else{
								PhoneController.available = new Boolean(false);
							}
						}
					}
					
				}
			
			}
		});
		
	}
	
	public void getInferenceEngine() throws Exception{
		ArrayList<Rule> rulesList = new ArrayList<Rule>();
	    Rule rule;
	    rie = new KieRuleInferenceEngine();
	    
	    rule=new Rule("dispo1");
	    rule.addAntecedent(new EqualsClause("type", "SmallP"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo2");
	    rule.addAntecedent(new EqualsClause("type", "BigP"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo3");
	    rule.addAntecedent(new EqualsClause("type", "Tablet"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("nondispo");
	    rule.addAntecedent(new EqualsClause("type", "PC"));
	    rule.setConsequent(new EqualsClause("available", "no"));
	    rie.addRule(rule);
	
	    rule = new Rule("SmallP");
	    rule.addAntecedent(new EqualsClause("screen_size", "5"));
	    rule.addAntecedent(new EqualsClause("battery", "4000"));
	    rule.addAntecedent(new EqualsClause("price", "20000"));
	    rule.setConsequent(new EqualsClause("type", "SmallP"));
	    rie.addRule(rule);
	
	    rule=new Rule("BigP");
	    rule.addAntecedent(new EqualsClause("screen_size", "6"));
	    rule.addAntecedent(new EqualsClause("battery", "4000"));
	    rule.addAntecedent(new EqualsClause("price", "100000"));
	    rule.setConsequent(new EqualsClause("type", "BigP"));
	    rie.addRule(rule);
	
	    rule=new Rule("Tablet");
	    rule.addAntecedent(new EqualsClause("screen_size", "10"));
	    rule.addAntecedent(new EqualsClause("battery", "10000"));
	    rule.addAntecedent(new EqualsClause("price", "50000"));
	    rule.setConsequent(new EqualsClause("type", "Tablet"));
	    rie.addRule(rule);
	
	    rule=new Rule("PC");
	    rule.addAntecedent(new EqualsClause("screen_size", "27"));
	    rule.addAntecedent(new EqualsClause("battery", "100000"));
	    rule.addAntecedent(new EqualsClause("price", "150000"));
	    rule.setConsequent(new EqualsClause("type", "PC"));
	    rie.addRule(rule);
	
	    File f = new File("phoneRules.txt");
		
	    FileInputStream fi = new FileInputStream(f);
		if (fi.available() != 0) {
			ObjectInputStream oi = new ObjectInputStream(fi);
			rulesList = (ArrayList<Rule>) oi.readObject();
			oi.close();
		} 
		
		fi.close();
		
		for (Rule rule2 : rulesList) {
			this.rie.addRule(rule2);
		}
	    
	}

}
