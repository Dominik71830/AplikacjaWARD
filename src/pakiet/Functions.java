
package pakiet;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import static javafx.scene.input.KeyCode.P;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Functions {
    
    Connection myConn= null;
    
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
    new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

    public Functions() throws FileNotFoundException, IOException, SQLException {
        
        Properties props = new Properties();
		props.load(new FileInputStream("properties.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String url = props.getProperty("url");
                
                myConn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Połączono z " + url + "\n" + "Użytkownik: " + user);
    }

    
    
    
    
 public String encrypt(String valueToEnc) throws Exception {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGORITHM);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encValue = c.doFinal(valueToEnc.getBytes());
    String encryptedValue = new BASE64Encoder().encode(encValue);
    return encryptedValue;
}

public String decrypt(String encryptedValue) throws Exception {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGORITHM);
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
    byte[] decValue = c.doFinal(decordedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
}

private static Key generateKey() throws Exception {
    Key key = new SecretKeySpec(keyValue, ALGORITHM);
    return key;
}
public boolean compare(String s1, String s2){
    return(s1.equals(s2) ? true : false);
}

/*public void getConnection() throws IOException, SQLException{
                Properties props = new Properties();
		props.load(new FileInputStream("properties.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String url = props.getProperty("url");
                
                myConn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Połączono z " + url + "\n" + "Użytkownik: " + user);
}*/


public User getUser(String _login) throws SQLException{
    User user = null;
    
    PreparedStatement myStmt = null;
    ResultSet myRs = null;
    
    try{
		myStmt = myConn.prepareStatement("select * from users where login = ?");
                myStmt.setString(1, _login);
		myRs = myStmt.executeQuery();
	
		while(myRs.next()){
			user = convertRowToUser(myRs);
		}
	return user;
	}
	finally{
		//myRs.close();
                //myStmt.close();
	}
}

private User convertRowToUser(ResultSet _myRs) throws SQLException {
        int id = _myRs.getInt("id");
        String login = _myRs.getString("login");
        String password = _myRs.getString("password");
        String imie = _myRs.getString("imie");
        String nazwisko = _myRs.getString("nazwisko");
        String email = _myRs.getString("email");
        Date data_rejestracji = _myRs.getDate("data_rejestracji");
        User temp = new User(id, login, password, imie, nazwisko, email,data_rejestracji);
        return temp;
    }


public void addUser(User _user){
    PreparedStatement myStmt = null;
    try{
        myStmt = myConn.prepareStatement("insert into users"
				+ " (login,password,imie,nazwisko,email)"
				+ " values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        
        myStmt.setString(1, _user.getLogin());
        myStmt.setString(2, _user.getPassword());
        myStmt.setString(3, _user.getImie());
        myStmt.setString(4, _user.getNazwisko());
        myStmt.setString(5, _user.getEmail());
        //myStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        //JOptionPane.showMessageDialog(null, new Timestamp(System.currentTimeMillis()));
        myStmt.executeUpdate();
        
        //Logi
        
    }
    catch(Exception e){
        JOptionPane.showMessageDialog(null,"Błąd przy dodawaniu użytkownika do bazy. Sprawdź czy wszystkie pola są poprawnie uzupełnione");
    }
}

/*public List<User> getAllUsers(){
    List<User> lista = new ArrayList<User>();
    
    
    return lista;
}*/

public List<User> getAllUsers() throws SQLException {
	List<User> lista = new ArrayList<User>();
	
	Statement myStmt = null;
	ResultSet myRs = null;
	
	try{
		myStmt = myConn.createStatement();
		myRs = myStmt.executeQuery("select * from users");
		
		while(myRs.next()){
			User temp = convertRowToUser(myRs);
			lista.add(temp);
		}
	return lista;
	}
	finally{
		myStmt.close();
	}
}

public boolean userExist(String _login) throws SQLException{
    boolean value = false;
    
    List<User> lista = getAllUsers();
    for(User u : lista){
        if(u.getLogin().equals(_login)){
            value = true;
            break;
        }
    }
    return value;
}
}
