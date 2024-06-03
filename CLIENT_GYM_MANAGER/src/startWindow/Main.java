package startWindow;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;


public class Main extends Application {
	//ATTRIBUTI
	private static Client c;
	
	public static Client getC() {
		return c;
	}
	
	@Override
	public void start(Stage primaryStage) {
		//MI CONNETTO AL SERVER
		c = new Client();
		
		if(c.isConnected()) //se il client si e' connesso allora starto l applicazione
		{
			try {
				Parent root = FXMLLoader.load(getClass().getResource("MainXml.fxml"));
				primaryStage.setResizable(false);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				Scene scene = new Scene(root, Color.TRANSPARENT );
				scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else //IL CLIENT NON E' RIUSCITO A CONNETTERSI
		{
			Alert errore = new Alert(AlertType.ERROR);
			errore.setTitle("ERRORE");
			errore.setHeaderText("IMPOSSIBILE CONNETTERSI AL SERVER");
			errore.setContentText("provare a riavviare l'applicaizone, altrimenti riprovarre piu' tardi");
			errore.showAndWait();
		 }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
