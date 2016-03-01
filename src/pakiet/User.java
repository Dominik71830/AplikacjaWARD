/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakiet;

import java.sql.Date;

/**
 *
 * @author Dominik
 */
public class User {
    private int id;
    private String login;
    private String password;
    private String imie;
    private String nazwisko;
    private String email;
    private Date data_rejestracji;

    public User(int id, String login, String password, String imie, String nazwisko, String email, Date data_przydzialu) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.data_rejestracji = data_przydzialu;
    }

    public User(String login, String password, String imie, String nazwisko, String email){//, Date data_przydzialu) {
        this.login = login;
        this.password = password;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        //this.data_rejestracji = data_przydzialu;
    }

    
    public Date getData_rejestracji() {
        return data_rejestracji;
    }

    public void setData_rejestracji(Date data_rejestracji) {
        this.data_rejestracji = data_rejestracji;
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

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getEmail() {
        return email;
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

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", password=" + password + ", imie=" + imie + ", nazwisko=" + nazwisko + ", email=" + email + ", data_rejestracji=" + data_rejestracji + '}';
    }

    
}
