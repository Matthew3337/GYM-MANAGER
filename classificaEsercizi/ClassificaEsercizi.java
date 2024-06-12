package classificaEsercizi;

import java.io.IOException;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Main;

public class ClassificaEsercizi {
	

	//ATTRIBUTI
	private Stage stage;
	private Scene scena;
	private Parent radice;

	
	//METODI
	
	//costruttore
	public ClassificaEsercizi(ActionEvent e)  //si prende l'evento per risalire alla finestra(stage) e cambiare pannello(scene)
	{
		try {
			radice =  FXMLLoader.load(getClass().getResource("ClassificaEsercizi.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Node tmp = (Node) e.getSource();
		stage = (Stage) tmp.getScene().getWindow(); //la finestra in cui apparira la nuova scena sara' la stessa
		scena = new Scene(radice, Color.TRANSPARENT); //passo alla scena l elemento radice
		setMuscoli();
		stage.setScene(scena); //aggiungo la nuova scena alla finestra
		stage.show(); //aggiorno finestra
		

	}
	
	private void setMuscoli()
	{
		Main.getC().sendMsg("))");
		
		int nMuscoli = Integer.parseInt(Main.getC().readResponse());
		Vector<String> opzioni = new Vector<String>();
		for(int cont=0 ; cont < nMuscoli ; cont++)
		{
			opzioni.add(Main.getC().readResponse());
		}
		
		( (ComboBox)scena.lookup("#muscolo") ).setItems(FXCollections.observableArrayList(opzioni)); //registro le opzioni nella combo box
		
	}
	

}
