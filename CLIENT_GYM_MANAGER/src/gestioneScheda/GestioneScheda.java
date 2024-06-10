package gestioneScheda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Main;

public class GestioneScheda {
	
	//ATTRIBUTI
	private Stage w;
	private Scene s;
	private Parent radice;
	
	//METODI
	
	//COSTRUTTORE
	public GestioneScheda(Event e, int idScheda)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("GestioneScheda.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		
		s = new Scene(radice, Color.TRANSPARENT);
		//per consentire al listener di ricavare l id delkla scheda utilizzo un meccanismo di campi nascosti tramite una label invisibile
		((Label) s.lookup("#idScheda")).setText(Integer.toString(idScheda));
		
		//lato server aggiorno la scheda in uso
		Main.getC().sendMsg("!@#$%");
		Main.getC().sendMsg(Integer.toString(idScheda));
			
	
		TableView  tmp = (TableView) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		w.show();
		
		
	}

}
