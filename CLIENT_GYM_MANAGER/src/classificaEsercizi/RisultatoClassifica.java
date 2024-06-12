package classificaEsercizi;

import javafx.beans.property.SimpleStringProperty;

public class RisultatoClassifica {
	
	//e' buon uso creare una classe che rappresenti il record della tabella per riempire una tabella in java fx
	
		//ATTRIBUTI
		
		private SimpleStringProperty  esercizio;
		private SimpleStringProperty  aumentoPerc;
		
		

		//COSTRUTTORE
		RisultatoClassifica(String esercizio, String aumentoPerc)
		{
			this.esercizio = new SimpleStringProperty(esercizio);
			this.aumentoPerc = new SimpleStringProperty(aumentoPerc);
			
		}
		
		RisultatoClassifica(){};
		
		public String getEsercizio() {
			return esercizio.getValue();
		}

		public void setEsercizio(String esercizio) {
			this.esercizio =  new SimpleStringProperty(esercizio);
		}

		public String getAumentoPerc() {
			return aumentoPerc.getValue();
		}

		public void setAumentoPerc(String aumentoPerc) {
			this.aumentoPerc =  new SimpleStringProperty(aumentoPerc);
		}
}
