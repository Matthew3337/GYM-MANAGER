package registrati;

import java.io.IOException;

import accedi.AccediPage;
import homePage.HomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import startWindow.Client;
import startWindow.Main;
import javafx.scene.control.Alert.AlertType;

public class RegistratiListener {
	
	//ATTRIBUTI
	private Client c;
	@FXML
	private TextField nome, cognome, peso, altezza, nomeUtente, password;
	private ActionEvent e;
	
	//METODI
	//COSTRUTTORE
	public RegistratiListener()
	{
		c = Main.getC();
	}
	
	
	
	public void continua(ActionEvent e)
	{
		this.e = e;
		//verifico se qualche campo non e' stato compilato 
		if(nome.getText().isEmpty() || cognome.getText().isEmpty() || peso.getText().isEmpty() 
		|| altezza.getText().isEmpty() || nomeUtente.getText().isEmpty() || password.getText().isEmpty() )
			//MOSTRO UN ALERT PER COMUNICARE LA MANCANZA DI INFORMAZI0NI
			message(AlertType.ERROR, "ERRORE", "ALCUNI CAMPI NON SONO STATI COMPILATI", "inserire i dati nei campi mancanti e cliccare continua");
		else
		{
			//verifico la correttezza dei campi numerici
			if(verrifyNumericField()) //se sono corretti allora 
			{
				c.sendMsg("!@");//invio al server il codice dell operazione registrazione
				sendInformation(); //invio le informazioni necessarie
				manageResponse();//ricevo e gestisco la risposta del server
			}
		}
			
	}
	
	public void accedi(ActionEvent e) throws IOException
	{
		AccediPage a = new AccediPage(e); //gli passo questa finestra
	}
	

	private boolean verrifyNumericField()
	{
		boolean res = true;
		if(!verifyOnlyNumber(peso.getText())) //se non contiene solo numeri
		{
			res = false;
			message(AlertType.ERROR, "ERRORE", "IL CAMPO PESO PUO CONTENERE SOLO NUMERI", "riprova");
		}
		else if(Integer.parseInt(peso.getText()) <= 0) //se contiene solo numeri verifico se sono negativi(erroe)
		{
			res = false;
			message(AlertType.ERROR, "ERRORE", "IL CAMPO PESO PUO CONTENERE SOLO NUMERI MAGGIORI DI 0", "riprova");
		}

		if(!verifyOnlyNumber(altezza.getText()))
		{
			res = false;
			message(AlertType.ERROR, "ERRORE", "IL CAMPO ALTEZZA PUO CONTENERE SOLO NUMERI", "riprova");
		}
		else if(Integer.parseInt(altezza.getText()) <= 0) //se contiene solo numeri verifico se sono negativi(erroe)
		{
			res = false;
			message(AlertType.ERROR, "ERRORE", "IL CAMPO ALTEZZA PUO CONTENERE SOLO NUMERI MAGGIORI DI 0", "riprova");
		}
		
		return res;
	}
	
	private boolean verifyOnlyNumber(String s)//true se contiene solo numeri altrimenti false
	{
		for(int cont = 0; cont < s.length(); cont++)
		{
			if(!Character.isDigit(s.charAt(cont))) //se un carattere non e' una cifra 
				return false;
		}
		
		return true;
	}
	
	private void message(AlertType tipo, String title, String header, String text)
	{
		Alert error = new Alert(tipo);
		error.setTitle(title);
		error.setHeaderText(header);
		error.setContentText(text);
		error.show();
	}
	
	private void sendInformation()
	{
		c.sendMsg(nome.getText());
		c.sendMsg(cognome.getText());
		c.sendMsg(nomeUtente.getText());
		c.sendMsg(password.getText());
		c.sendMsg(peso.getText());
		c.sendMsg(altezza.getText());
		
	}
	
	private void manageResponse()
	{
		String res = c.readResponse();
		if(res.equals("1")) //registrazione andata a buon fine
		{
			message(AlertType.INFORMATION, "REGISTRAZIONE ANDATA A BUON FINE", "IL SUO ACCOUNT E' STATO CREATO", "ora accedera' alla sua homePage"); //lo comunico
			//passo alla home page
			HomePage home = new HomePage(e);
		}
		else if(res.equals("-1")) //registrazione non andata a buon fine 
		{
			message(AlertType.ERROR, "ERROE", "RISULTA GIA' UN ACCOUNT CON QUEL NOME UTENTE", "SE E' IL PROPRIETARIO DELL'ACCOUNT COLLEGATO A QUEL NOME UTENTE EFFETTUI L ACCESSO ALTRIMENTI PROVI A CAMBIARE IL NOME UTENTE"); //lo comunico
		}
		else if(res.equals("-2"))
			message(AlertType.ERROR, "ERROE", "PROBLEMI LATO CONNESSIONE DATABASE", "RIPROVARE AD EFFETTUARE LA REGISTRAZIONE ALTRIMENTI ATTENDERE LA MANUTENZIONE DEL SERVER"); //lo comunico
		

	}

}
