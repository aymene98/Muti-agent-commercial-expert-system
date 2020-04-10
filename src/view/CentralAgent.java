package view;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;

public class CentralAgent extends Agent{

	String s;
	@Override
	protected void setup() {
		
		setEnabledO2ACommunication(true, 0);

	    addBehaviour(new CyclicBehaviour(this){
	        public void action(){
	            String info = (String) myAgent.getO2AObject();
	            if (info != null)
	            {
	            	System.out.println(info);
	            	String T[] = info.split(" ");
	        		
	        		String agent = (String)T[0];
	        		//System.out.println(agent);
	        		
	        		if (agent.equals("phone")) {
	        			
	        			System.out.println("sending to the phone agent.");
	        			String screenSize = (String)T[1];
	        			String battery = (String)T[2];
	        			String price = (String)T[3];
	        			String chain = (String)T[4];
	        			
	        			String inf = screenSize + "  " + battery + "  " + price + "  " + chain;
	        			try {
	        				System.out.println(inf);
	        				MainApp.PHONE.putO2AObject(inf, AgentController.ASYNC);
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        				System.out.println("sending to phone agent failed");
	        			}
	        		}
	        		
	        		if (agent.equals("food")) {
	        			String meat = (String)T[1], packaging = (String)T[2], drink = (String)T[3], price = (String)T[4], chain = (String)T[5];
	        			
	        			String i = meat + "  " + packaging + "  " + drink + "  " + price + "  " + chain;
	        			try {
	        				MainApp.FOOD.putO2AObject(i, AgentController.ASYNC);
	        				
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        				System.out.println("sending to food agent failed");
	        			}
	        		}
	        		
	            }
	            else{
	            	block();
	            }
	        }
	    });
	    
		
	}
}
