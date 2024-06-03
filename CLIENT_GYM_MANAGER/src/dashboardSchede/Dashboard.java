package dashboardSchede;

import java.io.IOException;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Main;

public class Dashboard {
	
	//ATTRIBUTI
	private Stage w;
	private Scene s;
	private Parent radice;

	
	//METODI
	
	//COSTRUTTORE
	public Dashboard(ActionEvent e)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("DashboardXml.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		
		s = new Scene(radice, Color.TRANSPARENT);
		s.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		
		Button tmp = (Button) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		w.show();
	}
	

	

}
