package creaScheda;

import java.io.IOException;
import java.time.LocalDate;

import homePage.HomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import registraEsercizi.RegistraEserciziPage;
import javafx.scene.control.Alert.AlertType;
import startWindow.*;
import javafx.scene.control.DatePicker;

public class CreaSchedaListener {
	
	//ATTRIBUTI
	Client c = Main.getC();
	@FXML
	private TextField nomeScheda;
	@FXML 
	private DatePicker dataInizio;
	@FXML 
	private DatePicker dataFine;
	@FXML 
	private TextField nGiorni;
	
	//METODI
	
	public void indietro(ActionEvent e)
	{
		HomePage h = new HomePage(e);
	}
	
	public void continua(ActionEvent e)
	{
		//verifico se tutti i campi sono compilati e sono validi
		if(verifyField())
		{
			sendInformation();
			manageResult(e);
		}
	}
	
	private void manageResult(ActionEvent e)
	{
		String res = c.readResponse();
		
		if(res.equals("1"))
		{
			message(AlertType.INFORMATION, "SCHEDA CREATA CON SUCCESSO", "LA SUA SCHEDA E' STATA CREATA", "ORA VERRA' REINDIRIZZATO ALLE PAGINE PER LA REGISTRAZIONE DEGLI ESERCIZI");
			try {
				RegistraEserciziPage r = new RegistraEserciziPage(e);
			} catch (NumberFormatException | IOException e1) {}//!!!!apro la scheda per la registrazione giorni allenanti 
		}
		else if(res.equals("-1"))
			message(AlertType.ERROR, "RISULTA GIA' REGISTRATA UNA SCHEDA CON LO STESSO NOME", " MODIFICARE NOME O ELIMINARE LA SCHEDA ESISTENTE", "in seguito riprovare");
		else if(res.equals("-2"))	
			message(AlertType.ERROR, "PERIODO DI TEMPO NON VALIDO", "RISULTA GIA' UNA SCHEDA IN USO DURANTE IL PERIODO DI TEMPO SELEZIONATO \n la scheda gia' registrata in questione e' " + c.readResponse(), "riprovare modificando il periodo o eliminando la scheda esistente");

	}
	
	private void sendInformation()
	{
		c.sendMsg("!@#");
		c.sendMsg(nomeScheda.getText());
		c.sendMsg(dataInizio.getValue().toString());
		c.sendMsg(dataFine.getValue().toString());
		c.sendMsg(nGiorni.getText());
	}
	
	private boolean verifyField()
	{
		boolean res = true;
		//VERIFICO SE C'E' QUALCHE CAMPO VUOTO
		if(nomeScheda.getText().isEmpty() || dataInizio.getValue().toString().isEmpty() || dataFine.getValue().toString().isEmpty() || nGiorni.getText().isEmpty())
		{
			message(AlertType.ERROR, "EERROE", "RISULTANO CAMPI NON COMPILATI", " per continuare la registrazione della scheda bisogna compilare tutti i campi");
			res = false;
		}
		else if(dataInizio.getValue().isAfter(dataFine.getValue()))
		{
			message(AlertType.ERROR, "EERROE", "LA DATA DI FINE E' PRECEDENTE ALLA DATA DI INIZIO", " per continuare la registrazione della scheda la data di inizio deve essere precedente a quella di fine");
			res = false;
		}
		else if(dataInizio.getValue().isBefore(LocalDate.now()) || dataFine.getValue().isBefore(LocalDate.now()))
		{
			message(AlertType.ERROR, "EERROE", "NON E' POSSIBILE REGISTRARE UNA SCHEDA IN UN PERIODO PRECEDENTE ALLA DATA DI OGGI", " per continuare la registrazione della scheda la data di inizio e di fine devono essere post la data attuale");
			res = false;
		}
		else if(!verifyOnlyNumber(nGiorni.getText())) //se tutti i campi sono pieni, verifico la correttezza del campo numerico
		{
			message(AlertType.ERROR, "EERROE", "IL CAMPO NUMERO GIORNI ALLENANTI NON E' VALIDO", "il campo numero giorni allenanti puo contenere solo numeri");
			res = false;
		}
		else if( Integer.parseInt(nGiorni.getText()) <= 0 )
		{
			message(AlertType.ERROR, "EERROE", "IL CAMPO NUMERO GIORNI ALLENANTI NON E' VALIDO", "il campo numero giorni allenanti puo contenere solo valori maggiori di 0");
			res = false;
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

}
