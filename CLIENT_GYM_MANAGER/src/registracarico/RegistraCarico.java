package registracarico;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistraCarico {
	
	//ATTRIBUTI
	private Stage stage;
	private Scene scena;
	private Parent radice;

	
	//METODI
	
	//costruttore
	public RegistraCarico(ActionEvent e)  //si prende l'evento per risalire alla finestra(stage) e cambiare pannello(scene)
	{
		try {
			radice =  FXMLLoader.load(getClass().getResource("registraCarico.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node tmp = (Node) e.getSource();
		stage = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice); //passo alla scena l elemento radice
		scena.getStylesheets().add(getClass().getResource("/startWindow/Main.css").toExternalForm());
		stage.setScene(scena); //aggiungo la nuova scena alla finestra
		stage.show(); //aggiorno finestra
		

	}
	

}
