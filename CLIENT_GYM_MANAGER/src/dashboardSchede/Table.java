package dashboardSchede;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import startWindow.Main;

public class Table {
	
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
	
	//appena creo la finestra devo riempire la tabella della scheda in uso 
	public void fillTable()
	{
		linkColumnToData();
		Main.getC().sendMsg("##@@");
		fetchData();
	}
	
	private void linkColumnToData()
	{
		//associo ad ogni colonna il rispettivo attributo della classe che conterra' il record
		esercizio.setCellValueFactory(new PropertyValueFactory<Scheda, String>("esercizio")); //alla colonna esercizio faccio corrispondere il valore dell attributto di nome esericzio
		serie.setCellValueFactory(new PropertyValueFactory<Scheda, String>("serie"));
		ripetizioni.setCellValueFactory(new PropertyValueFactory<Scheda, String>("ripetizioni"));
		recupero.setCellValueFactory(new PropertyValueFactory<Scheda, String>("recupero"));
		note.setCellValueFactory(new PropertyValueFactory<Scheda, String>("note"));
		carico.setCellValueFactory(new PropertyValueFactory<Scheda, String>("carico"));
		muscolo.setCellValueFactory(new PropertyValueFactory<Scheda, String>("muscolo"));
		giorno.setCellValueFactory(new PropertyValueFactory<Scheda, String>("giorno"));
	}
	
	private void fetchData() //memorizzo i record nella lista che passero alla tabella 
	{
		String msg;
		
		ObservableList<Scheda> data = FXCollections.observableArrayList(); //creo la lista che conterra gli oggetti rappresentanti i record della tabella
		
		while( !(msg = Main.getC().readResponse()).equals("!") )
		{
			Scheda s = new Scheda();
			s.setEsercizio(msg);
			
			msg = Main.getC().readResponse();//serie
			s.setSerie(msg);
			
			msg = Main.getC().readResponse(); //ripetizioni
			s.setRipetizioni(msg);
			
			msg = Main.getC().readResponse();//recupero
			s.setRecupero(msg);
			
			msg = Main.getC().readResponse();//note
			s.setNote(msg);
			
			msg = Main.getC().readResponse();//carico
			s.setCarico(msg);
			
			msg = Main.getC().readResponse();//muscolo
			s.setMuscolo(msg);
			
			msg = Main.getC().readResponse();//giorno
			s.setGiorno(msg);
			
			data.add(s); //aggiungo l oggetto nella lista e passo al prossimo record
		}
		
		scheda.setItems(data); //passo la lista completa alla tabella che smistera ogni record 
		
	}

}
