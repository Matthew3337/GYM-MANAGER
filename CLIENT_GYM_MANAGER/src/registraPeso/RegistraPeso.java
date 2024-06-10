package registraPeso;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistraPeso {

	//ATTRIBUTI
	private Stage stage;
	private Scene scena;
	private Parent radice;

	
	//METODI
	
	//costruttore
	public RegistraPeso(ActionEvent e, String idScheda)  //si prende l'evento per risalire alla finestra(stage) e cambiare pannello(scene)
	{
		try {
			radice =  FXMLLoader.load(getClass().getResource("RegistraPeso.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node tmp = (Node) e.getSource();
		stage = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice, Color.TRANSPARENT); //passo alla scena l elemento radice
		//per consentire al listener di ricavare l id delkla scheda utilizzo un meccanismo di campi nascosti tramite una label invisibile
		((Label) scena.lookup("#idScheda")).setText(idScheda);
		stage.setScene(scena); //aggiungo la nuova scena alla finestra
		stage.show(); //aggiorno finestra
		

	}
}
