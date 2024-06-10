package modificaDurataScheda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Main;

public class ModificaDurata {

	//ATTRIBUTI
	private Stage stage;
	private Scene scena;
	private Parent radice;

	
	//METODI
	
	//costruttore
	public ModificaDurata(ActionEvent e)  //si prende l'evento per risalire alla finestra(stage) e cambiare pannello(scene)
	{
		try {
			radice =  FXMLLoader.load(getClass().getResource("ModificaDurata.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node tmp = (Node) e.getSource();
		stage = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice, Color.TRANSPARENT); //passo alla scena l elemento radice

		
		getDateAttuali();
		
		stage.setScene(scena); //aggiungo la nuova scena alla finestra
		stage.show(); //aggiorno finestra
		

	}
	
	private void getDateAttuali()
	{
		//richiedo la durata attuale della scheda e la mostro 
		Main.getC().sendMsg("(*&^%");
		((Label) scena.lookup("#inizioAttuale")).setText(Main.getC().readResponse());
		((Label) scena.lookup("#fineAttuale")).setText(Main.getC().readResponse());
	}

}
