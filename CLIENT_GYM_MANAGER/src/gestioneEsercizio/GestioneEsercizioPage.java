package gestioneEsercizio;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestioneEsercizioPage {
	
	//ATTRIBUTI
	private Stage w;
	private Scene s;
	private Parent radice;
	
	//METODI
	
	//COSTRUTTORE
	public GestioneEsercizioPage(Event e, int es)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("GestioneEsercizio.fxml"));
		} catch (IOException e1) {}
		
		s = new Scene(radice);
		s.getStylesheets().add(getClass().getResource("/startWindow/Main.css").toExternalForm());

			
	
		TableView  tmp = (TableView) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		w.setUserData(es);	
		w.show();
		
		
	}

}
