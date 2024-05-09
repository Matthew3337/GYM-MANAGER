package registraEsercizi;

import java.util.Vector;

import homePage.HomePage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import startWindow.Client;
import startWindow.Main;

public class RegistraEserciziListener {
	
	//ATTRIBUTI 
	Client c = Main.getC();
	@FXML
	ComboBox<String> giorni;
	@FXML
	TextField nome;
	@FXML
	TextField serie;
	@FXML
	TextField ripetizioni;
	@FXML
	TextField recupero;
	@FXML
	TextArea note;
	@FXML
	ComboBox<String> muscolo;
	
	
	//METODI

	public void setGiorni()
	{
		c.sendMsg("((");
		int nGiorni = Integer.parseInt(c.readResponse());
		int option = 65;
		Vector<String> opzioni = new Vector<String>();
		for(int cont = 0; cont < nGiorni ; cont++)
		{
			opzioni.add(Character.toString((char) option));
			option++;
		}
		
		giorni.setItems(FXCollections.observableArrayList(opzioni));
	}
	
	public void setMuscoli()
	{
		c.sendMsg("))");
		
		int nMuscoli = Integer.parseInt(c.readResponse());
		Vector<String> opzioni = new Vector<String>();
		for(int cont=0 ; cont < nMuscoli ; cont++)
		{
			opzioni.add(c.readResponse());
		}
		muscolo.setItems(FXCollections.observableArrayList(opzioni));
		
	}
	
	public void registraEsercizio(ActionEvent e)
	{
		if(verifyField()) //se tutti i campi sono corretti
		{
			//invio al server
			c.sendMsg("!@#$");
			c.sendMsg(giorni.getValue()); //giorno selected
			c.sendMsg(muscolo.getValue());
			c.sendMsg(nome.getText());
			c.sendMsg(serie.getText());
			c.sendMsg(ripetizioni.getText());
			c.sendMsg(recupero.getText());
			if(note.getText().isEmpty())
				c.sendMsg(" ");
			else
				c.sendMsg(note.getText());//TODO sistemare quando nelle note aggiuntive non viene scritto niente va in errore
			//manage della risposta
			if(c.readResponse().equals("-1"))
				message(AlertType.ERROR, "ERRORE", "ERRORE LATO SERVER", "riprova");
			else
				message(AlertType.INFORMATION, "REGISTRAAZIONE AVVENUTA CON SUCCESSO", "INSERIRE LE INFORMAZIONI DEL PROSSIMO ESERCIZIO ALTRIMENTI CLICCARE SU HOME PAGE", "");
			
		}

	}
	
	public void fine(ActionEvent e)
	{
		HomePage h = new HomePage(e);
	}
	
	private void message(AlertType tipo, String title, String header, String text)
	{
		Alert error = new Alert(tipo);
		error.setTitle(title);
		error.setHeaderText(header);
		error.setContentText(text);
		error.show();
	}
	
	private boolean verifyField()
	{
		boolean res = false;
		if(giorni.getValue().isEmpty())
			message(AlertType.ERROR, "ERRORE", "SELEZIONARE UN GIORNO ALLENANTE", "riprova");
		else if(muscolo.getValue().isEmpty())
		message(AlertType.ERROR, "ERRORE", "SELEZIONARE UN GIORNO ALLENANTE", "riprova");
		else if(nome.getText().isEmpty() || serie.getText().isEmpty() || ripetizioni.getText().isEmpty())
			message(AlertType.ERROR, "ERRORE", "RISULTANO CAMPI NON COMPILATI", "Compila tutti i campi obbligatori (*) e riprova");
		else if(!verifyInt(serie.getText()))
			message(AlertType.ERROR, "ERRORE", "IL CAMPO SERIE NON E' VALIDO", "Il campo serie puo contenere solo numeri interi, positivi");
		else if(!verifyInt(ripetizioni.getText()))
			message(AlertType.ERROR, "ERRORE", "IL CAMPO RIPETIZIONI NON E' VALIDO", "Il campo serie puo contenere solo numeri interi, positivi");
		else if(!verifyDouble(recupero.getText()))
			message(AlertType.ERROR, "ERRORE", "IL CAMPO RECUPERO NON E' VALIDO", "Il campo serie puo contenere solo numeri, positivi");
		else
			res = true;
		
		return res;
	}
	
	private boolean verifyDouble(String s)//true se contiene solo numeri altrimenti false
	{
		double d;
		boolean res = true;
		try
		{
			 d = Double.parseDouble(s);
			 if(d <= 0)
				 res = false;
			 
		} catch(NumberFormatException e) 
		{
			res = false;
		}
		
		return res;
	}
	
	private boolean verifyInt(String s)//true se contiene solo numeri altrimenti false
	{
		int d;
		boolean res = true;
		try
		{
			 d = Integer.parseInt(s);
			 if(d <= 0)
				 res = false;
			 
		} catch(NumberFormatException e) 
		{
			res = false;
		}
		
		return res;
	}
	
	
}
