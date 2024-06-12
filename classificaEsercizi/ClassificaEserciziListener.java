package classificaEsercizi;

import java.net.URL;
import java.util.ResourceBundle;

import dashboardSchede.Scheda;
import homePage.HomePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import startWindow.Main;

public class ClassificaEserciziListener {
	
	@FXML
	private TableView<RisultatoClassifica> classifica;
	@FXML
	private TableColumn<RisultatoClassifica,String> esercizio;
	@FXML
	private TableColumn<RisultatoClassifica,String> aumentoPerc;
	@FXML
	private ComboBox<String> muscolo;
	@FXML
	private DatePicker da;
	@FXML
	private DatePicker a;
	
	private String s_muscolo;
	private String s_da, s_a;

	
	
	public void fillTable()
	{
		linkColumnToData();
		if(veirfyField())
		{
			Main.getC().sendMsg("^^%$&");
			Main.getC().sendMsg(s_muscolo);
			Main.getC().sendMsg(s_da);
			Main.getC().sendMsg(s_a);
			fetchData();
		}
		
	}
	
	private boolean veirfyField()
	{
		
		if( (da.getValue() == null && a.getValue() != null) || (da.getValue() != null && a.getValue() == null) ) //il periodo deve avere per forza un inizio ed una fine o nessuno dei due
		{
			message(AlertType.ERROR, "ERRORE", "CAMPI DI RICERCA NON CORRETTTAMENTE COMPILATI", "il periodo deve avere per forza un inizio ed una fine o nessuno dei due");
			return false;
		}
		else
		{
			if(muscolo.getValue() == null)
			{
				s_muscolo = "vuoto";
			}
			else
				s_muscolo = muscolo.getValue();
			
			if(da.getValue() == null || a.getValue() == null)
			{
				s_da = "vuoto";
				s_a = "vuoto";
			}
			else
			{
				s_da = da.getValue().toString();
				s_a = a.getValue().toString();
			}
			
			return true;
		}
		
	}
	
	private void linkColumnToData()
	{
		//associo ad ogni colonna il rispettivo attributo della classe che conterra' il record
		esercizio.setCellValueFactory(new PropertyValueFactory<RisultatoClassifica, String>("esercizio")); //alla colonna esercizio faccio corrispondere il valore dell attributto di nome esericzio
		aumentoPerc.setCellValueFactory(new PropertyValueFactory<RisultatoClassifica, String>("aumentoPerc"));
		
	}
	
	private void fetchData()
	{
		ObservableList<RisultatoClassifica> data = FXCollections.observableArrayList(); //creo la lista che conterra gli oggetti rappresentanti i record della tabella
		String msg;
		while( !(msg = Main.getC().readResponse()).equals("!") )
		{
			RisultatoClassifica r = new RisultatoClassifica();
			r.setEsercizio(msg);
			
			msg = Main.getC().readResponse();//aumento perc
			r.setAumentoPerc(msg);
			
			data.add(r); //aggiungo l oggetto nella lista e passo al prossimo record
		}
		
		classifica.setItems(data); //passo la lista completa alla tabella che smistera ogni record 
		
	}
	
	public void indietro(ActionEvent e)
	{
		HomePage h = new HomePage(e);
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
