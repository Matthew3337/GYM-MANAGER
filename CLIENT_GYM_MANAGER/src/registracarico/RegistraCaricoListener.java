package registracarico;

import dashboardSchede.Dashboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import startWindow.Main;

public class RegistraCaricoListener {
	
	@FXML
	private DatePicker data;
	@FXML
	private TextField carico;
	
	//METODI 
	
	public void continua(ActionEvent e )
	{
		if(data.getValue() == null)
		{
			message(AlertType.ERROR, "IMPOSSIBILE CONFERMARE", "NON E' STATA SELEZIONATA ALCUNA DATA", "selezionare suna data e riprovare");
		}
		else if(carico.getText().isEmpty() || !verifyOnlyNumber(carico.getText()) || Integer.parseInt(carico.getText()) <= 0)
		{
			message(AlertType.ERROR, "IMPOSSIBILE CONFERMARE", "IL CARICO INSERITO NON E' VALIDOs", "modificare il valore inserito e riprovare");
		}
		else
		{
			int idEs = getIdEsercizio(e);
			sendCarico(idEs);
			manageResult();
		}
	}
	
	public void indietro(ActionEvent e)
	{
		Dashboard d = new Dashboard(e);
	}
	
	private void sendCarico(int idEs)
	{
		Main.getC().sendMsg("^^@#"); //codice op
		Main.getC().sendMsg(Integer.toString(idEs)); //ides
		Main.getC().sendMsg(carico.getText());//carico
		Main.getC().sendMsg(data.getValue().toString());//data
		
	}
	
	private void manageResult()
	{
		String r = Main.getC().readResponse();
		
		if(r.equals("1"))
		{
			message(AlertType.INFORMATION, "OPERAZIONE TERMINATA", "CARICO CORRETTAMENTE REGISTRATO", "premere indietro per tornare alla scheda");
		}
		else
		{
			message(AlertType.ERROR, "OPERAZIONE FALLITA", "CARICO NON REGISTRATO", "premere indietro per tornare alla scheda o riprovare");
		}
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
	
	private int getIdEsercizio(ActionEvent e)
	{
		Parent r = ( (Parent) e.getSource() );
		Stage w =(Stage) r.getScene().getWindow();
		return (int) w.getUserData();
	}

}
