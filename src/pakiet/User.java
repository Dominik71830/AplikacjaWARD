/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakiet;

/**
 *
 * @author Dominik
 */
public class User {
    private int id;
    private String login;
    private String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.login = name;
        this.password = password;
    }
    
    
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
