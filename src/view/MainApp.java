package view;

import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	public static AgentController CENTRAL,PHONE,FOOD;

	@Override
	public void start(Stage Stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root);
			Stage.setTitle("Expert System");
			Stage.setScene(scene);
			Stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		// Create and start agents.
		try {
			
			/* phone agent */
			jade.core.Runtime rt2 = jade.core.Runtime.instance();
			ProfileImpl p2= new ProfileImpl("localhost",1097,"RSHP");
			ContainerController mc2= rt2.createMainContainer(p2);
			AgentController ag2 = null;
			ag2 = mc2.createNewAgent("foodAgent", "view.FoodAgent",new Object[0]);
			ag2.start();
			FOOD = ag2;
			
			/* food agent */
			jade.core.Runtime rt3 = jade.core.Runtime.instance();
			ProfileImpl p3= new ProfileImpl("localhost",1098,"RSHP");
			ContainerController mc3= rt3.createMainContainer(p3);
			AgentController ag3 = null;
			ag3 = mc3.createNewAgent("phoneAgent", "view.PhoneAgent",new Object[0]);
			ag3.start();
			PHONE = ag3;
			
			
			/* central agent */
			jade.core.Runtime rt = jade.core.Runtime.instance();
			ProfileImpl p= new ProfileImpl("localhost",1099,"RSHP");
			ContainerController mc= rt.createMainContainer(p);
			AgentController ag1 = null;
			ag1 = mc.createNewAgent("centralAgent", "view.CentralAgent",new Object[0]);
			ag1.start();
			CENTRAL = ag1;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}
}
