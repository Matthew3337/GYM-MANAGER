package server;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class ServerThread extends Thread{
	
	//ATTRIBUTI
	private  DbmsComunication dbCom = new  DbmsComunication(); //oggetto comune a tutti i thread che consente la comunicazione con il dbms
	private Socket client;
	private DataOutputStream toClient;
	private BufferedReader byClient;
	private Vector<String> codiciOp; //contiene in ordine le codifiche dei codici univocamente corrispondenti alle operazioni
	// posizione codice nel vettore + 1 = posizione operazione in elenco "doc/specificheComunicazione"
	
	// variabili da memorizzare per tenere traccia dell utente con cui si parla, della scheda su cui si agisce eccetera
	String utente;
	int schedaSelezionata;
	
	//METODI
	
	//costruttore
	ServerThread(Socket c)
	{
		
		client = c;
		try {
			toClient = new DataOutputStream(client.getOutputStream());
			byClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {}
		
		codiciOp = new Vector<String>();
		saveOp();
		
	}
	
	private void saveOp() //memorizza le operazioni nel vettore
	{
		File codici = new File("src/codiciOperazioni.txt"); //file che contiene i codici delle operazioni
		Scanner inputFile = null;//oggetto per leggere dati da un file
		try {
			inputFile = new Scanner(codici);
		} catch (FileNotFoundException e) {} 
		
		while(inputFile.hasNextLine())
		{
			codiciOp.add(inputFile.nextLine());
		}
	
	}
	
	@Override
	public void run()
	{
		String codiceOp = null; //CONTIENE UNO ALLA VOLTA I CODICI CHE IDENTIFICANO OGNI OPERAZIONE CHE IL CLIENT RICHIEDERA
		//si rimanda al file "doc/specificheComunicazione" per conoscere la codifica dei codici identificativi
		
		
			while( !(codiceOp = readResponse()).equals(codiciOp.get(codiciOp.size()-1) ) ) //il thread muore quando il client invia il codice di chiusura connessione
			{
				int operationPosition = codiciOp.indexOf(codiceOp); //contiene la posizione del codice nel vettore ( che determina che operazione bisogna compiere)
				callOperattion(operationPosition);
			}
	}
	
	private void callOperattion(int op) 
	{
		switch(op)
		{
		case 0: // accedi
			accesso();
			break;
		case 1: //registrati
			registrazione();
			break;
		case 2 :  //crea scheda
			creaScheda();
			break;
		case 3 :  // invia nGiorni della scheda
			sendNgiorni();//per consentire all utente di aggiungere gli esercizi il client d=necessita di sapere quanti giorni allenanti ci sono nella scheda
			break;
		case 4 : //invia gruppi muscolari
			sendMuscoli();
			break;
		case 5 : //registra esercizio
			registraEsercizio();
			break;
		case 6 : //invia scheda in uso
			sendScchedaInUso();
			break;
		case 7 : // invia elenco schede 
			sendEleencoSchede();
			break;
		case 8: //invia una specifica scheda  
			sendSelectedScheda();
			break;
		case 9: //elimina esercizio
			eliminaEsercizio();
			break;
		case 10 : //registra carico
			registraCarico();
			break;
		case 11:
			break;
		case 12:
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		}
	}
	
	
	private void registrazione()
	{
		int res;
		if(dbCom.isConnesso())//se la connessione con il dbms e' andata a buon fine
			res = dbCom.modifieData(createRegistrationQuery()); // 1 andata a buon fine
		else
			res = -2; //impossibile connettersi al database
		
		try {
			toClient.writeBytes(Integer.toString(res) + "\n"); //comunico risultato
		} catch (IOException e) {}
			
	}
	

	private void accesso()
	{
		int res;
		
		if(dbCom.isConnesso())
		{
			String utente;
			//  -1 = nome utente non valido
			//  -2 = password non valido
			//1 = credenziali valide
			//VERIFICO 1 CREDENZIALE ALLA VOLTA
			if( verifyAccess("nomeUtente", (utente= readResponse()) ) ) //se il nome utente e' valido
			{
				//allora verifico anche la password
				if(verifyAccess("password", readResponse()))
				{
					//credenziali corrette, accesso consentito, memorizzo l account con cui sto dialogando
					this.utente = utente;
					//lo comunico all utente
					sendMsg("1");
				}
				else
					sendMsg("-2\n"); //password errata
			}
			else
				sendMsg("-1"); //nomeUtente errata
		}
		else
			sendMsg("-3"); //impossibile connettersi al database
			 
	}
	
	private void creaScheda()
	{
		int res; // -1: nome scheda duplicato | -2 : periodo scheda gia' occupato | 1 : scheda registrata 
		String verificaPeriodo = "";
		//memorizzo le informazioni inviate dal client
		String nome = readResponse();
		String dataInizio = readResponse();
		String dataFine = readResponse();
		int nGiorni = Integer.parseInt(readResponse());
		//verifico se risulta gia' una scheda collegata a quell utente con lo stesso nome
		if(!existScheda(nome))
		{
			verificaPeriodo = isValidPeriod(dataInizio, dataFine);
			if(verificaPeriodo.equals("free")) //verifico se risulta gia' una scheda registrata in quel periodo di tempo
			{
				res = insertSchedaDb(nome, dataInizio, dataFine, nGiorni); //eseguo la query per registrare memorizzo il risultato
				this.schedaSelezionata = findIdScheda(nome, utente);;
			}
			else
			{
				res = -2;
			}
		}
		else
			res = -1;
		
		sendMsg(Integer.toString(res)); //invio risultato al client
		if(res == -2) //se il periodo di tempo e' occupato allora comunico anche l inizio e la fine del periodo di tempo occupato
			sendMsg(verificaPeriodo);
	}
	
	private void sendNgiorni()
	{
		int nGiorni = 0;
		System.out.println("select nGiorniAllenanti from scheda where atleta='" + utente + "' && id='" + schedaSelezionata +"'");
		ResultSet res = dbCom.select("select nGiorniAllenanti from scheda where atleta='" + utente + "' && id='" + schedaSelezionata +"'");
		try {
			res.next();
			nGiorni = res.getInt(1);
		} catch (SQLException e) {}
		
		sendMsg(Integer.toString(nGiorni));
	}
	
	private void sendMuscoli()
	{
		ResultSet res = dbCom.select("select count(g.id) from gruppo_muscolare g");
		
		try {
			res.next();
			sendMsg(Integer.toString(res.getInt(1))); // invio numero gruppi 
		} catch (SQLException e) {}
		
		res = dbCom.select("select g.nome from gruppo_muscolare g");
		
		try {
			while(res.next())
			{
				sendMsg(res.getString(1));
			}
		} catch (SQLException e) {
		}
	}
	
	private void registraEsercizio()
	{
		String giorno = readResponse();
		int muscolo = findIdMuscolo(readResponse()) ;//trovo id del gruppo scelto
		String nome = readResponse();
		int serie = Integer.parseInt(readResponse());
		int rep = Integer.parseInt(readResponse());
		double recupero = Double.parseDouble(readResponse());
		String note = readResponse();
		
		String qr = "insert into esercizio(nome, nSerie, nRep, recupero, note, muscolo, giornoAllenante, scheda) values('" + nome + "'," + serie + "," + rep + "," + recupero + ",'" + note + "','" + muscolo + "','" + giorno + "','" + schedaSelezionata +"')";
		//TODO sistemare quando nelle note aggiuntive non viene scritto niente va in errore
		System.out.println(qr);
		sendMsg(Integer.toString(dbCom.modifieData(qr))); //eseguo la query ed invio il risultato
	}
	
	private void sendScchedaInUso()
	{
		
		ResultSet res = getSchedaInUso();
		boolean pieno;
		try {
			pieno = res.next();
		} catch (Exception e) {
			pieno = false;
		}
		while(pieno)//fin quando non scorre tutto il result set continua ad inviare i dati
		{
			try {
				sendMsg(res.getString(1));//esercizio
				sendMsg(Integer.toString(res.getInt(2))); //n serie
				sendMsg(Integer.toString(res.getInt(3))); //n rep
				sendMsg(Double.toString(res.getDouble(4))); //recupero
				sendMsg(res.getString(5)); //note
				sendMsg(Integer.toString(res.getInt(6))); // peso
				sendMsg(res.getString(7)); //muscolo
				sendMsg(res.getString(8)); //giorno
				sendMsg(Integer.toString(res.getInt(9))); //id
				
				pieno = res.next(); //prossima riga
			} catch (SQLException e) {
				sendMsg("!");
			} 
		}
		sendMsg("!");//per comunicare la fine dei dati da inviare il server invia il carattere !
		
	}
	
	private ResultSet getSchedaInUso()
	{
		//il server effettua la query 
		String qr = "SELECT e.nome, e.nSerie, e.nRep, e.recupero, e.note, rc.peso, m.nome, e.giornoAllenante, e.id "
				+ "FROM esercizio e "
				+ "left join registrazione_carico rc on rc.idEsercizio=e.id "
				+ "join gruppo_muscolare m on m.id=e.muscolo "
				+ "join scheda s on s.id=e.scheda "
				+ "where s.atleta='"+utente+"' && s.inUso=1 "
				+ "order by e.giornoAllenante asc";
		
		return dbCom.select(qr); //registra il result set 
	}
	
	private void sendSelectedScheda()
	{
		String name = readResponse();
		ResultSet res = getSchedaSpecifica(name);
		boolean pieno;
		try {
			pieno = res.next();
		} catch (SQLException e) {
			pieno = false;
		}
		while(pieno)//fin quando non scorre tutto il result set continua ad inviare i dati
		{
			try {
				sendMsg(res.getString(1));//esercizio
				sendMsg(Integer.toString(res.getInt(2))); //n serie
				sendMsg(Integer.toString(res.getInt(3))); //n rep
				sendMsg(Double.toString(res.getDouble(4))); //recupero
				sendMsg(res.getString(5)); //note
				sendMsg(Integer.toString(res.getInt(6))); // peso
				sendMsg(res.getString(7)); //muscolo
				sendMsg(res.getString(8)); //giorno
				sendMsg(Integer.toString(res.getInt(9))); //id
				
				pieno = res.next(); //prossima riga
			} catch (SQLException e) {
				sendMsg("!");
			} 
		}
		sendMsg("!");//per comunicare la fine dei dati da inviare il server invia il carattere !
		
	}
	
	private ResultSet getSchedaSpecifica(String name)
	{
		//il server effettua la query 
		String qr = "SELECT e.nome, e.nSerie, e.nRep, e.recupero, e.note, temp2.peso, m.nome, e.giornoAllenante, e.id "
				+ "FROM esercizio e "
				+"LEFT JOIN ( SELECT rc.idEsercizio, rc.peso FROM registrazione_carico rc "
				+ "JOIN ( SELECT MAX(rc1.data) AS data, rc1.idEsercizio FROM registrazione_carico rc1 GROUP BY rc1.idEsercizio "
				+ ") temprc ON temprc.idEsercizio = rc.idEsercizio AND temprc.data = rc.data ) temp2 "
				+ "ON temp2.idEsercizio = e.id "
				+ "join gruppo_muscolare m on m.id=e.muscolo "
				+ "join scheda s on s.id=e.scheda "
				+ "where s.atleta='"+utente+"' && s.nome='"+name+"' "
				+ "order by e.giornoAllenante asc";
		System.out.println(qr);
		return dbCom.select(qr); //registra il result set 
	}
	
	private void eliminaEsercizio()
	{
		String idEsercizio = readResponse();
		String qr = "delete from esercizio where esercizio.id=" + idEsercizio;
		System.out.println(qr);
		sendMsg(Integer.toString(dbCom.modifieData(qr)));
	}
	
	private void registraCarico()
	{
		String idEs = readResponse();
		String carico = readResponse();
		String data = readResponse();
		String qr = "insert into registrazione_carico(data, peso, idEsercizio) values('" + data + "'," + carico + ", " + idEs + " )";
		System.out.println(qr);
		
		if(dbCom.modifieData(qr) == -1)
			sendMsg("-1");
		else
			sendMsg("1");
	}
	
	private int insertSchedaDb(String nome, String dataInizio, String dataFine, int nGiorni)
	{
		String inUso = isInUso(dataInizio, dataFine);
		String query = "insert into scheda(nome, dataInizio, dataFine, inUso, atleta, nGiorniAllenanti) values(" + "'" + nome + "', '" + dataInizio + "', '" + dataFine + "', " + inUso + ", '" + utente + "', " + nGiorni + ")";
		dbCom.modifieData(query);//registro la scheda nel db
		this.schedaSelezionata = findIdScheda(nome, utente);
		return 1;
	}
	
	private String isInUso(String dataInizio, String dataFine) // verifico se il periodo ddella scheda comprende anche oggi ( inUso = true else inUso = false)
	{
		LocalDate d = LocalDate.now();
		LocalDate inizio = LocalDate.parse(dataInizio);
		LocalDate fine = LocalDate.parse(dataFine);
		
		if( (d.isAfter(inizio) || d.isEqual(inizio)) && ( ( d.isBefore(fine) || d.isEqual(fine) ) ) )
			return "1";
		else
			return "0";
	}
	private String isValidPeriod(String dataInizio, String dataFine) //verifica se il periodo di tempo occupato dalla scheda che si vuole registrare e' libero o e' gia' occupato
	{
		String query = "select * from scheda as s where atleta='" + utente + "' && ( (s.dataInizio<='" + dataInizio + "' && s.dataFine>='" + dataInizio + "') || (s.dataInizio<='" + dataFine + "' && s.dataFine>='" + dataFine + "') )";
		ResultSet res = dbCom.select(query);
		try {
			if(!res.isBeforeFirst()) //resultSet punta inizialmente alla riga prima della prima riga. per cio se il set e' vuoto isBeforeFirst ritornera false perche non c'e una prima riga
				return "free"; //set vuoto, periodo valido
			else //set contenente una riga, periodo gia' occupato.
			{
				res.next();//mi sposto sul primo record
				return res.getString(2) + "INIZIA IL " + res.getString(3)+ " E FINISCE IL " +res.getString(4); // restituisco il nome l inizio e la fine del periodo occupato
			}
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private boolean existScheda(String nome)
	{
		ResultSet res = dbCom.select("Select * from scheda as s where s.atleta='" + this.utente + "' && s.nome='" + nome + "'");
		try {
			if(!res.isBeforeFirst()) //resultSet punta inizialmente alla riga prima della prima riga. per cio se il set e' vuoto isBeforeFirst ritornera false perche non c'e una prima riga
				return false; //set vuoto, non esiste la scheda
			else 
				return true; //set contenente una riga, esiste la scheda
		} catch (SQLException e) {
			return true;
		}
	}
	
	private String createRegistrationQuery() //legge le infoormazioni inviate dal client e crea la query
	{
		String query = "INSERT INTO utente(nome, cognome, nomeUtente, password, peso, altezza) VALUES (";
		
		for(int cont = 0; cont < 6 ;cont++) //in totale sono 6 informazioni da ricevere
		{
			try {
				if(cont < 4) //sono tutti campi stringa che necessitano gli apici prima e dopo
				{
					String msg =byClient.readLine();
					query += "'" + msg + "'";
					this.utente = msg; //memorizzo il nome dell utente che sta usando l applicazione
				}
				else
					query += byClient.readLine() ;
				if(cont <5)
					query += ", ";
			} catch (IOException e) {}
		}
		
		query += ")";		
		return query;
	}
	
	private boolean verifyAccess(String nomeCampo, String valore)  //verifica se la credenziale inserita e' presente nmel database
	{
		String query = "SELECT " + nomeCampo + " FROM utente WHERE " + nomeCampo + "=" + "'" + valore + "'";
		ResultSet res = dbCom.select(query);
		
		try {
			if(!res.isBeforeFirst()) //resultSet punta inizialmente alla riga prima della prima riga. per cio se il set e' vuoto isBeforeFirst ritornera false perche non c'e una prima riga
				return false;//credenziale non valida
			else
				return true; //credenziale valida
		} catch (SQLException e) {
			return false;
		} 
	}
	
	private int findIdScheda(String nome, String utente)
	{
		ResultSet res = dbCom.select("select s.id from scheda s where s.nome='" + nome + "' && s.atleta='"+ utente + "'");
		try {
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			return -1;
		}
		
	}
	
	private int findIdMuscolo(String s)
	{
		ResultSet res = dbCom.select("select g.id from gruppo_muscolare g where g.nome='" + s + "'");
		try {
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			return -1;
		}
		
	}
	
	private int sendMsg(String msg)
	{
		if(msg.length() == 0 || msg.charAt(msg.length() - 1) != '\n')
		{
			msg += '\n'; //la aggiungo
		}
		
		try
		{
			toClient.writeBytes(msg);
			return 1;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	
	private String readResponse()
	{
		try 
		{
			return byClient.readLine();
		}
		catch(Exception e) 
		{
			return null;
		}
	}
	
	private void sendEleencoSchede()
	{
		ResultSet res = dbCom.select("select s.nome from scheda s where s.atleta='" + utente + "'");
		try {
			while(res.next()) //invio i nomi di tutte le schede
			{
				try {
					sendMsg(res.getString(1));
				} catch (SQLException e) {
					sendMsg("!");
				}
			}
		} catch (SQLException e) {
			sendMsg("!");
		}
		
		sendMsg("!");
	}
	
}

	