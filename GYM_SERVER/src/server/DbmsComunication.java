package server;
import java.sql.*;
public class DbmsComunication {
	
	//ATTRIBUTI
	private Connection c; //crea la connessione con il dbms
	private Statement s; //ogetto tuilizzato per inviare  query al ddbms
	private boolean connesso; //indica se la connessione con il dbms e' andata a buon fine
	//METODI

	//costruttore
	DbmsComunication()
	{
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_manager", "root", "");
			s = c.createStatement();
			connesso = true;
		} catch (SQLException e) {
			connesso = false;
		}
	}
	
	public boolean isConnesso() {
		return connesso;
	}

	public ResultSet select(String query)  //SELECT
	{
		try {
			return s.executeQuery(query);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public int modifieData(String query) //INSERT UPDATE DELETE
	{
		try {
			return s.executeUpdate(query); //restituisce il numero di righe affette dal ccomando 
		} catch (SQLException e) { //primary key duplicata
			return -1;
			
		}
		
	}

}
