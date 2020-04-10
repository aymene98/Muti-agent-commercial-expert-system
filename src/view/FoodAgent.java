package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import jade.lang.acl.ACLMessage;

public class FoodAgent extends Agent{
	RuleInferenceEngine rie=new KieRuleInferenceEngine();
	
	@Override
	protected void setup() {
		setEnabledO2ACommunication(true, 0);
		
		addBehaviour(new CyclicBehaviour(this){
			public void action(){
				
				String info = (String) myAgent.getO2AObject();
				
				if (info != null){
					System.out.println(info);
					System.out.println("food agent");
					try {
						getInferenceEngine();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String T[] = info.split(" ");
					String meat = T[0], packaging = T[2], drink = T[4], price = T[6], chain = T[8];
					System.out.println("food agent : ");
					System.out.print(meat + " ");
					System.out.print(packaging + " ");
					System.out.print(drink + " ");
					System.out.print(price + " ");
					System.out.println(chain);


					if (!meat.equals("")) {
						rie.addFact(new EqualsClause("meat", meat));
					}
					
					if (!packaging.equals("")) {
						rie.addFact(new EqualsClause("packaging", packaging));
					}
					
					if (!drink.equals("")) {
						rie.addFact(new EqualsClause("drink", drink));
					}
					
					if (!price.equals("")) {
						rie.addFact(new EqualsClause("price", price));
					}
					
					if (chain.equals("forwardChain")) {
						Clause res1,res2 ;
						
						rie.infer(); //forward chain
						System.out.println("rule base size : "+rie.getFacts().size());
						
						FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
						
						if (rie.getFacts().size() == 4) {
							FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							FoodController.available = null;
						}
						if (rie.getFacts().size() == 5) {
							res1 = rie.getFacts().get(4);
							FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							FoodController.available = null;
						}
						
						if (rie.getFacts().size() == 6) {
							//res1 = rie.getFacts().get(4);
							res2 = rie.getFacts().get(5);
							FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							
							if (res2.getValue().equals("yes")) {
								FoodController.available = new Boolean(true);
							}else{
								FoodController.available = new Boolean(false);
							}
						}
						//System.out.println(rie.getFacts().toString());
					}
					
					if (chain.equals("backwardChain")) {
						Vector<Clause> unproved_conditions= new Vector<>();
					    Clause conclusion = rie.infer("available", unproved_conditions);
					    
					    if (conclusion == null) {
					    	FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
					    	FoodController.available = null;
						}else{
							FoodController.rulebase = rie.getRules().toString() + "\n"+ rie.getFacts().toString();
							if (conclusion.getValue().equals("yes")) {
								FoodController.available = new Boolean(true);
							}else{
								FoodController.available = new Boolean(false);
							}
						}
					}
					
				}
			}
		});
		
	}
	
	public void getInferenceEngine() throws Exception{
	    Rule rule;
	    ArrayList<Rule> rulesList = new ArrayList<Rule>();
	    rie = new KieRuleInferenceEngine();;
	    
	    rule=new Rule("dispo1");
	    rule.addAntecedent(new EqualsClause("type", "etudiant1"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo2");
	    rule.addAntecedent(new EqualsClause("type", "etudiant2"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo3");
	    rule.addAntecedent(new EqualsClause("type", "etudiant3"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo4");
	    rule.addAntecedent(new EqualsClause("type", "fancyetudiant"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("dispo5");
	    rule.addAntecedent(new EqualsClause("type", "prof"));
	    rule.setConsequent(new EqualsClause("available", "yes"));
	    rie.addRule(rule);
	    
	    rule=new Rule("nondispo");
	    rule.addAntecedent(new EqualsClause("type", "config1"));
	    rule.setConsequent(new EqualsClause("available", "no"));
	    rie.addRule(rule);
	    
	    rule=new Rule("nondispo");
	    rule.addAntecedent(new EqualsClause("type", "config2"));
	    rule.setConsequent(new EqualsClause("available", "no"));
	    rie.addRule(rule);
	
	    rule = new Rule("etudiant1");
	    rule.addAntecedent(new EqualsClause("meat", "chicken"));
	    rule.addAntecedent(new EqualsClause("packaging", "sandwish"));
	    rule.addAntecedent(new EqualsClause("drink", "coca"));
	    rule.addAntecedent(new EqualsClause("price", "300"));
	    rule.setConsequent(new EqualsClause("type", "etudiant1"));
	    rie.addRule(rule);
	    
	    rule = new Rule("etudiant2");
	    rule.addAntecedent(new EqualsClause("meat", "meat"));
	    rule.addAntecedent(new EqualsClause("packaging", "sandwish"));
	    rule.addAntecedent(new EqualsClause("drink", "coca"));
	    rule.addAntecedent(new EqualsClause("price", "300"));
	    rule.setConsequent(new EqualsClause("type", "etudiant2"));
	    rie.addRule(rule);
	    
	    rule = new Rule("etudiant3");
	    rule.addAntecedent(new EqualsClause("meat", "meat"));
	    rule.addAntecedent(new EqualsClause("packaging", "ham"));
	    rule.addAntecedent(new EqualsClause("drink", "coca"));
	    rule.addAntecedent(new EqualsClause("price", "200"));
	    rule.setConsequent(new EqualsClause("type", "etudiant3"));
	    rie.addRule(rule);
	    
	    rule = new Rule("fancyetudiant");
	    rule.addAntecedent(new EqualsClause("meat", "chicken"));
	    rule.addAntecedent(new EqualsClause("packaging", "taco"));
	    rule.addAntecedent(new EqualsClause("drink", "juice"));
	    rule.addAntecedent(new EqualsClause("price", "800"));
	    rule.setConsequent(new EqualsClause("type", "fancyetudiant"));
	    rie.addRule(rule);
	    
	    rule = new Rule("prof");
	    rule.addAntecedent(new EqualsClause("meat", "fish"));
	    rule.addAntecedent(new EqualsClause("packaging", "meal"));
	    rule.addAntecedent(new EqualsClause("drink", "juice"));
	    rule.addAntecedent(new EqualsClause("price", "1000"));
	    rule.setConsequent(new EqualsClause("type", "prof"));
	    rie.addRule(rule);
	    
	    rule = new Rule("config1");
	    rule.addAntecedent(new EqualsClause("meat", "fish"));
	    rule.addAntecedent(new EqualsClause("packaging", "taco"));
	    rule.addAntecedent(new EqualsClause("drink", "juice"));
	    rule.addAntecedent(new EqualsClause("price", "800"));
	    rule.setConsequent(new EqualsClause("type", "config1"));
	    rie.addRule(rule);
	    
	    rule = new Rule("config2");
	    rule.addAntecedent(new EqualsClause("meat", "fish"));
	    rule.addAntecedent(new EqualsClause("packaging", "ham"));
	    rule.addAntecedent(new EqualsClause("drink", "pepsi"));
	    rule.addAntecedent(new EqualsClause("price", "300"));
	    rule.setConsequent(new EqualsClause("type", "config2"));
	    rie.addRule(rule);
	
	    File f = new File("foodRules.txt");
		
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
