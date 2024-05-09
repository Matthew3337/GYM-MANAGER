package accedi;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccediPage {
	
	//ATTRIBUTI
	Stage window;
	private Scene scena;
	private Parent radice;
	
	//METODI
	
	//Costruttore
	public AccediPage(ActionEvent e) throws IOException
	{
		radice =  FXMLLoader.load(getClass().getResource("AccediXml.fxml"));	
		Node tmp = (Node) e.getSource();
		window = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice);
		scena.getStylesheets().add(getClass().getResource("/startWindow/Main.css").toExternalForm());
		window.setScene(scena);
		window.show();
	}

}
