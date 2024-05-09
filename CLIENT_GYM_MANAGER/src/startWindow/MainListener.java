package startWindow;

import java.io.IOException;

import accedi.AccediPage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import registrati.RegistratiPage;

public class MainListener {
	public void registrati(ActionEvent e) throws IOException
	{
		RegistratiPage registrati = new RegistratiPage(e);
	}
	
	public void accedi(ActionEvent e) throws IOException
	{
		Button sorgente = (Button) e.getSource();
		
		AccediPage a = new AccediPage(e); //gli passo questa finestra
	}

}
