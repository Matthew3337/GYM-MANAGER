package dashboardSchede;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Scheda {
	
	//e' buon uso creare una classe che rappresenti il record della tabella per riempire una tabella in java fx
	
	//ATTRIBUTI
	
	private SimpleStringProperty  esercizio;
	private SimpleStringProperty  serie;
	private SimpleStringProperty  ripetizioni;
	private SimpleStringProperty  recupero;
	private SimpleStringProperty  note;
	private SimpleStringProperty  carico;
	private SimpleStringProperty  muscolo;
	private SimpleStringProperty  giorno;
	
	//METODI
	
	//COSTRUTTORE
	Scheda(String esercizio, String serie, String ripetizioni, String recupero, String note, String carico, String giorno)
	{
		this.esercizio = new SimpleStringProperty(esercizio);
		this.serie = new SimpleStringProperty(serie);
		this.ripetizioni = new SimpleStringProperty(ripetizioni);
		this.recupero = new SimpleStringProperty(recupero);
		this.note = new SimpleStringProperty(note);
		this.carico = new SimpleStringProperty(carico);
		this.giorno = new SimpleStringProperty(giorno);
	}
	
	Scheda(){}

	public String getEsercizio() {
		return esercizio.toString();
	}

	public void setEsercizio(String esercizio) {
		this.esercizio = new SimpleStringProperty(esercizio);
	}

	public String getSerie() {
		return serie.toString();
	}

	public void setSerie(String serie) {
		this.serie = new SimpleStringProperty(serie);
	}

	public String getRipetizioni() {
		return ripetizioni.toString();
	}

	public void setRipetizioni(String ripetizioni) {
		this.ripetizioni = new SimpleStringProperty(ripetizioni);
	}

	public String getRecupero() {
		return recupero.toString();
	}

	public void setRecupero(String recupero) {
		this.recupero = new SimpleStringProperty(recupero);
	}

	public String getNote() {
		return note.toString();
	}

	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}

	public String getCarico() {
		return carico.toString();
	}

	public void setCarico(String carico) {
		this.carico = new SimpleStringProperty(carico);
	}

	public String getGiorno() {
		return giorno.toString();
	}

	public void setGiorno(String giorno) {
		this.giorno = new SimpleStringProperty(giorno);
	}
	
	public String getMuscolo() {
		return serie.toString();
	}

	public void setMuscolo(String muscolo) {
		this.muscolo = new SimpleStringProperty(muscolo);
	}

}
