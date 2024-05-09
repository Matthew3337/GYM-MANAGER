package homePage;

import java.io.IOException;

import creaScheda.CreaSchedaPage;
import dashboardSchede.Dashboard;
import javafx.event.ActionEvent;

public class HomePageListener {
	
	//METODI
	
	public void creaScheda(ActionEvent e)
	{
		CreaSchedaPage c = new CreaSchedaPage(e);
	}
	
	public void mostraSchede(ActionEvent e)
	{
		Dashboard d = new Dashboard(e);
	}
	
	public void mostraClassificheGen(ActionEvent e)
	{
		// new classificheGeneraliPage()
	}

}
