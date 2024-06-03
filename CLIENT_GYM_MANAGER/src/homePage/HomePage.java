package homePage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomePage {
	
	//ATTRIBUTI
	Stage w;
	Scene s;
	Parent radice;
	
	//METODI
	
	//COSTRUTTORE
	public HomePage(ActionEvent e)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("HomePageXml.fxml"));
		} catch (IOException e1) {}
		
		s = new Scene(radice, Color.TRANSPARENT);
		s.getStylesheets().add(getClass().getResource("/startWindow/Main.css").toExternalForm());

		
		Button tmp = (Button) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		w.show();
	}

}
