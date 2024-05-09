package dashboardSchede;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startWindow.Main;

public class Dashboard {
	
	//ATTRIBUTI
	private Stage w;
	private Scene s;
	private Parent radice;
	@FXML
	TableView<Scheda> scheda;
	@FXML
	TableColumn<Scheda,String> esercizio;
	@FXML
	TableColumn<Scheda,String> serie;
	@FXML
	TableColumn<Scheda,String> ripetizioni;
	@FXML
	TableColumn<Scheda,String> recupero;
	@FXML
	TableColumn<Scheda,String> note;
	@FXML
    TableColumn<Scheda,String> carico;
	@FXML
	TableColumn<Scheda,String> muscolo;
	@FXML
	TableColumn<Scheda,String> giorno;
	
	//METODI
	
	//COSTRUTTORE
	public Dashboard(ActionEvent e)
	{
		try {
			radice = FXMLLoader.load(getClass().getResource("DashboardXml.fxml"));
		} catch (IOException e1) {}
		
		s = new Scene(radice);
		s.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		
		Button tmp = (Button) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		Table t = new Table();
		t.fillTable();
		w.show();
	}
	
	

}
