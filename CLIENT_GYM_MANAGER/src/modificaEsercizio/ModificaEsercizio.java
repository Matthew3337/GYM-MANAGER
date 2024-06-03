package modificaEsercizio;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Client;
import startWindow.Main;

public class ModificaEsercizio {
	
	//ATTRIBUTI
	private Stage stage;
	private Scene scena;
	private Parent radice;
	Client c;
	
	
	ComboBox<String> giorni;
	TextField nome;
	TextField serie;
	TextField ripetizioni;
	TextField recupero;
	TextArea note;
	ComboBox<String> muscolo;

	
	//METODI
	
	//costruttore
	public ModificaEsercizio(ActionEvent e) throws IOException //si prende l'evento per risalire alla finestra(stage) e cambiare pannello(scene)
	{
		c = Main.getC();
		radice =  FXMLLoader.load(getClass().getResource("modificaEsercizio.fxml"));
		Node tmp = (Node) e.getSource();
		stage = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice, Color.TRANSPARENT); //passo alla scena l elemento radice
		
		initializeComponent();
		fillField();
		
		stage.setScene(scena); //aggiungo la nuova scena alla finestra
		stage.show(); //aggiorno finestra

	}
	
	private void initializeComponent()
	{
		giorni = (ComboBox<String>) scena.lookup("#giorni");
		nome = (TextField) scena.lookup("#nome");
		serie = (TextField) scena.lookup("#serie");
		ripetizioni = (TextField) scena.lookup("#ripetizioni");
		recupero = (TextField) scena.lookup("#recupero");
		note = (TextArea) scena.lookup("#note");
		muscolo = (ComboBox<String>) scena.lookup("#muscolo");
	}
	
	private void fillField()
	{
		//invio richiesta caratteristiche esercizio al server
		c.sendMsg("!^%%");
		int idEsercizio = (int) stage.getUserData();
		c.sendMsg(Integer.toString(idEsercizio)); //invio al server l id dell esercizio di cui inviare le info
		//fetcho i dati nei vari componenti
		giorni.setValue(c.readResponse());
		nome.setText(c.readResponse());
		serie.setText(c.readResponse());
		ripetizioni.setText(c.readResponse());
		recupero.setText(c.readResponse());
		note.setText(c.readResponse());
		muscolo.setValue(c.readResponse());
		
	}
	

}
