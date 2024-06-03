package creaScheda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreaSchedaPage {
	
	//ATTRIBUTI
	Stage w;
	Scene s;
	Parent radice;
	
	//METODI
	
	//costruttore
	
	public CreaSchedaPage(ActionEvent e)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("CreaSchedaXml.fxml"));
		} catch (IOException a) {}
		
		s = new Scene(radice, Color.TRANSPARENT);
		s.getStylesheets().add(getClass().getResource("/startWindow/Main.css").toExternalForm());

		
		Button tmp = (Button) e.getSource();
		w = (Stage)tmp.getScene().getWindow();
		w.setScene(s);
		w.show();
	}

}
