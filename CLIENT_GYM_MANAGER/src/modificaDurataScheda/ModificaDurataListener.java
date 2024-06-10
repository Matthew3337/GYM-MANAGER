package modificaDurataScheda;

import java.time.LocalDate;

import dashboardSchede.Dashboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import startWindow.Main;

public class ModificaDurataListener {
	@FXML 
	private Label idScheda;
	@FXML
	private DatePicker dataInizio;
	@FXML
	private DatePicker dataFine;
	
	
	//METODI 
	
	public void continua(ActionEvent e )
	{
		if(verifyField())
		{
			sendData();
			manageResult();
		}

	}
	
	public void indietro(ActionEvent e)
	{
		Dashboard d = new Dashboard(e);
	}
	
	private void sendData()
	{
		Main.getC().sendMsg("$#^@)"); //codice op
		Main.getC().sendMsg(dataInizio.getValue().toString());//data inizio
		Main.getC().sendMsg(dataFine.getValue().toString());//data fine
		
	}
	
	private void manageResult()
	{
		String r = Main.getC().readResponse();
		
		if(r.equals("1"))
		{
			message(AlertType.INFORMATION, "OPERAZIONE TERMINATA", "DURATA  CORRETTAMENTE AGGIORNATA", "premere indietro per tornare alla scheda");
		}
		else if(r.equals("-1"))
		{
			message(AlertType.ERROR, "OPERAZIONE FALLITA", "DURATA NON REGISTRATA", "premere indietro per tornare alla scheda o riprovare");
		}
		else
			message(AlertType.ERROR, "PERIODO DI TEMPO NON VALIDO", "RISULTA GIA' UNA SCHEDA IN USO DURANTE IL PERIODO DI TEMPO SELEZIONATO \n la scheda gia' registrata in questione e' " + r , " riprovare modificando il periodo o eliminando la scheda esistente");

	}
	
	private boolean verifyField()
	{
		boolean res = true;
		//VERIFICO SE C'E' QUALCHE CAMPO VUOTO
		if( dataInizio.getValue() == null || dataFine.getValue() == null )
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
		
		return res;
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
