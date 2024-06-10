package gestioneScheda;

import java.io.IOException;

import dashboardSchede.Dashboard;
import graficoScheda.GraficoScheda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import modificaDurataScheda.ModificaDurata;
import registraEsercizi.RegistraEserciziPage;
import registraPeso.RegistraPeso;
import javafx.scene.control.Alert.AlertType;
import startWindow.Main;

public class GestioneSchedaListener {
	
	
	@FXML
	private Label idScheda;
	
	
	public void registraPeso(ActionEvent e)
	{
		RegistraPeso r = new RegistraPeso(e, idScheda.getText());
	}
	
	public void aggiungiEsercizio(ActionEvent e)
	{
		try {
			RegistraEserciziPage r = new RegistraEserciziPage(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void modificaDurata(ActionEvent e)
	{
		ModificaDurata md = new ModificaDurata(e);
		
	}
	
	public void grafico(ActionEvent e)
	{
		GraficoScheda gs = new GraficoScheda(e);
	}
	
	public void eliminaScheda(ActionEvent e)
	{
		Main.getC().sendMsg("####");
		Main.getC().sendMsg(idScheda.getText());
		
		if(Main.getC().readResponse().equals("1"))
			message(AlertType.INFORMATION, "SCHEDA ELIMINATA CORRETTAMENTE", "GRAZIE", "");
		else
			message(AlertType.ERROR, "SCHEDA NON ELIMINATA ", "IMPOSSIBILE COMPLETARE L'OPERAZIONE", "RIPROVA PIU' TARDI");
	}
	
	public void indietro(ActionEvent e )
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

}
