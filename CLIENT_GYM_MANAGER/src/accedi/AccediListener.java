package accedi;

import java.io.IOException;

import homePage.HomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import registrati.RegistratiPage;
import startWindow.Client;
import startWindow.Main;

public class AccediListener {
	
	//ATTRIBUTI
	private Client c;
	@FXML
	private TextField nomeUtente;
	@FXML
	private PasswordField password;
	private ActionEvent e;
	
	//METODI
	
	//costruttor
	public AccediListener()
	{
		c = Main.getC();
	}
	
	public void accedi(ActionEvent e)
	{
		this.e = e;
		if(nomeUtente.getText().isEmpty() || password.getText().isEmpty())
		{
			message(AlertType.ERROR, "ERROE" , "CI SONO CAMPI NON COMPILATI", "compila tutti i campi e riprova");
		}
		else
		{
			c.sendMsg("!");
			sendInformation();
			manageResult();
		}
	}
	
	public void registrati(ActionEvent e)
	{
		try {
			RegistratiPage r = new RegistratiPage(e);
		} catch (IOException e1) {}
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
		c.sendMsg(nomeUtente.getText());
		c.sendMsg(password.getText());
	}
	
	private void manageResult()
	{
		String res = c.readResponse();
		
		if(res.equals("1")) //accesso effettuato
		{
			message(AlertType.INFORMATION, "ACCESSO AVVENUTO CON SUCCESSO", "VERRA' RENDERIZZATO ALLA SUA HOME PAGE", "buon allenamento !");
			//passo alla home page
			HomePage home = new HomePage(e);
		}
		else if(res.equals("-1")) //nomeUtente errato
			message(AlertType.ERROR, "ERRORE", "NOME UTENTE ERRATO", "provare a reinserire il nome utente");
		else if(res.equals("-2")) //password errata
			message(AlertType.ERROR, "ERRORE", "PASSWORD ERRATA", "provare a reinserire la password");
		else //database off
			message(AlertType.ERROR, "ERRORE", "ERRORE LATO SERVER", "riprovare, se il problema persiste attendere qualche minuto e riavviare l'applicazione");

			
	}
}

