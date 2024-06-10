package graficoScheda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import startWindow.Client;
import startWindow.Main;

public class GraficoScheda {
	
	//ATTRIBUTI
	private Stage w;
	private Scene s;
	private Parent radice;
	private LineChart<String, Double> grafico;
	Client c;

	
	//METODI
	
	//COSTRUTTORE
	public GraficoScheda(ActionEvent e)
	{
		c = Main.getC();
		try {
			radice = FXMLLoader.load(getClass().getResource("graficoScheda.fxml"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		
		s = new Scene(radice, Color.TRANSPARENT);

		
		Button tmp = (Button) e.getSource();
		w = (Stage) tmp.getScene().getWindow();
		w.setScene(s);
		grafico = (LineChart<String, Double>) s.lookup("#grafico");
		fillChart();
		w.show();
	}
	
	private void fillChart()
	{
		XYChart.Series<String, Double> insiemeDati = new XYChart.Series<>(); //oggetto che contiene le coordinate di tutti i punti da inserire nel grafico
		Double peso = 0.0;
		String data;
		
		//invio richiesta al server dei dati 
		c.sendMsg("!))(*");
		//ricevo dati 
		while(!(data = c.readResponse() ).equals("!"))
		{
			System.out.println(data);
			peso = Double.parseDouble(c.readResponse());
			//li memorizzo negli oggetti xyData
			insiemeDati.getData().add(new XYChart.Data<String, Double>(data, peso)); //inserisco un nella x la data e nella y il peso
		}
		
		//passo i dati al grafico
		grafico.getData().add(insiemeDati);
	}
	
	
}
