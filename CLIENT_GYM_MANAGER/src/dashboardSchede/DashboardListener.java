package dashboardSchede;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import gestioneEsercizio.GestioneEsercizioPage;
import homePage.HomePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import startWindow.Main;

public class DashboardListener implements Initializable{
	
	@FXML
	private TableView<Scheda> scheda;
	@FXML
	private TableColumn<Scheda,String> esercizio;
	@FXML
	private TableColumn<Scheda,String> serie;
	@FXML
	private TableColumn<Scheda,String> ripetizioni;
	@FXML
	private TableColumn<Scheda,String> recupero;
	@FXML
	private TableColumn<Scheda,String> note;
	@FXML
	private TableColumn<Scheda,String> carico;
	@FXML
	private TableColumn<Scheda,String> muscolo;
	@FXML
	private TableColumn<Scheda,String> giorno;
	@FXML
	private ComboBox<String> other;
	
	private Vector<Integer> idEsercizi = new  Vector<Integer>();
	
	
	//appena creo la finestra devo riempire la tabella della scheda in uso 
	private void fillTableInUso()
	{
		linkColumnToData();
		Main.getC().sendMsg("##@@");
		fetchData();
	}
	
	public void fillSpecificTable()
	{
		if(other.getValue() != null)
		{
			Main.getC().sendMsg("@$!@");
			System.out.println( other.getValue());
			Main.getC().sendMsg(other.getValue());
			fetchData();
		}
		
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
		idEsercizi.removeAllElements(); //memorizzo una nuova scheda quindi elimino gli id degli esercizi della scheda precedente
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
			
			msg = Main.getC().readResponse();//la nona informazione rappresenta l id dell esercizio, quindi non va memorizzata nella tabella ma nel vettore degli id
			idEsercizi.add(Integer.parseInt(msg));
			
			data.add(s); //aggiungo l oggetto nella lista e passo al prossimo record
		}
		
		scheda.setItems(data); //passo la lista completa alla tabella che smistera ogni record 
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
			fillTableInUso();
	}
	
	public void fillOptionSchede()
	{
		String msg;
		Vector<String> opzioni = new Vector<String>(); //opzioni della comboi box
		Main.getC().sendMsg("!!$$");
		
		while( !(msg = Main.getC().readResponse()).equals("!") ) //il server comunica di aver inviato tutte le schede tramite il !
		{
			opzioni.add(msg);
		}
		other.setItems(FXCollections.observableArrayList(opzioni));
	}
	
	public void gestioneClick(Event e)
	{
		TableViewSelectionModel<Scheda> selectionModel = scheda.getSelectionModel(); //oggetto che gestisce le selezione degli elementi della tabella
		selectionModel.setSelectionMode(SelectionMode.SINGLE); //si puo selezionare massimo un elemento alla volta
		int sel = selectionModel.getSelectedIndex();
		if(sel >= 0) //sel minore di 0 vuol dire che e' stata cliccata la tabella generica e non una riga specifica
		{
			//new page menu esercizio
			GestioneEsercizioPage g = new GestioneEsercizioPage(e, idEsercizi.get(sel));
		}
		
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
