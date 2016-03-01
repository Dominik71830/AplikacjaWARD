
package pakiet;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import static javafx.scene.input.KeyCode.P;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Functions {
    
    Connection myConn= null;
    
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
    new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

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

public void getConnection() throws IOException, SQLException{
                Properties props = new Properties();
		props.load(new FileInputStream("properties.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String url = props.getProperty("url");
                
                myConn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Połączono z " + url + "\n" + "Użytkownik: " + user);
}


public User getUser(String _login) throws SQLException{
    User user = null;
    
    PreparedStatement myStmt = null;
    ResultSet myRs = null;
    
    try{
		myStmt = myConn.prepareStatement("select * from users where login = ?");
                myStmt.setString(1, _login);
		myRs = myStmt.executeQuery();
	
		while(myRs.next()){
			user = converRowToUser(myRs);
		}
	return user;
	}
	finally{
		//myRs.close();
                //myStmt.close();
	}
}

private User converRowToUser(ResultSet _myRs) throws SQLException {
        int id = _myRs.getInt("id");
        String login = _myRs.getString("login");
        String password = _myRs.getString("password");
        User temp = new User(id,login,password);
        return temp;
    }
}
