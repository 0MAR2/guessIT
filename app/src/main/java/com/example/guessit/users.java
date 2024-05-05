package com.example.guessit;

public class users {
    String username, mail, password;
    int winstreak;
    public users(){

    }
    public users(String username, String mail, String password, int winstreak) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.winstreak = winstreak;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWinstreak() {
        return winstreak;
    }

    public void setWinstreak(int winstreak) {
        this.winstreak = winstreak;
    }


}
