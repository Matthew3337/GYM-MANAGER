package gestioneEsercizio;

import java.io.IOException;

import dashboardSchede.Dashboard;
import graficoEsercizio.GraficoEsercizio;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modificaEsercizio.ModificaEsercizio;
import registraEsercizi.RegistraEserciziPage;
import registracarico.RegistraCarico;
import javafx.scene.control.Button;
import startWindow.Main;

public class GestioneEsercizioListener {	
	//METODI
	
	public void modifica(ActionEvent e)
	{
		//open page modifica
		try {
			ModificaEsercizio m = new ModificaEsercizio(e);
		} catch (IOException e1) {

		}
	}
	

	public void grafico(ActionEvent e)
	{
		//open page grafico
		GraficoEsercizio g = new GraficoEsercizio(e);
	}
	
	public void newCarico (ActionEvent e)
	{
		//open page registra carico
		RegistraCarico r = new RegistraCarico(e);
	}
	
	public void elimina(ActionEvent e)
	{
		
		int idEsercizio = getIdEsercizio(e);
		
		Main.getC().sendMsg("$$@#");
		System.out.println(idEsercizio);
		Main.getC().sendMsg(Integer.toString(idEsercizio));
		if( Main.getC().readResponse().equals("-1")) //operazione non andata a buon fine
			message(AlertType.ERROR, "ERRORE", "IMPOSSIBILE COMPLETARE L'OPERAZIONE", "riprova più tardi");
		else
			message(AlertType.INFORMATION, "ESERCIZIO ELIMINATO", "L'OPERAZIONE E' ANDATA A BUON FINE", "");
	}
	
	public void indietro(ActionEvent e)
	{
		Dashboard d = new Dashboard(e);
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
