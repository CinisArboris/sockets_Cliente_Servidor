package CLIENTE;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClienteBD {
    private String HOST;
    private String PORT;
    private String BD;
    private String TBL;
    private String USR;
    private String PWD;
    private Properties NAV;
    private Connection CNX;

    public ClienteBD() {
        this.HOST = "localhost";
        this.PORT = "5432";
        this.BD = "db_agenda";
        this.TBL = "amigo";
        this.USR = "default";
        this.PWD = "default";
        this.NAV = new Properties();
        this.CNX = null;
    }
    
    

    public String getHOST() {
        return HOST;
    }
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    public String getPORT() {
        return PORT;
    }
    public void setPORT(String PORT) {
        this.PORT = PORT;
    }
    public String getBD() {
        return BD;
    }
    public void setBD(String BD) {
        this.BD = BD;
    }
    public String getTBL() {
        return TBL;
    }
    public void setTBL(String TBL) {
        this.TBL = TBL;
    }
    public String getUSR() {
        return USR;
    }
    public void setUSR(String USR) {
        this.USR = USR;
    }
    public String getPWD() {
        return PWD;
    }
    public void setPWD(String PWD) {
        this.PWD = PWD;
    }
    public Properties getNAV() {
        return NAV;
    }
    public void setNAV(String option, String value) {
        this.NAV.setProperty(option, value);
    }
    public Connection getCNX() {
        return CNX;
    }
    public void setCNX(String url, Properties nav) {
        try {
            this.CNX = DriverManager.getConnection(url, nav);
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - setCNX");
        }
    }
    
    private void conectar() {
        Scanner input = new Scanner(System.in);
        
        System.out.print("[USR] ");
        this.setUSR(input.nextLine());
            
        System.out.print("[PWD] ");
        this.setPWD(input.nextLine());
        
        System.out.println(this.getUSR() + this.getPWD());
        this.setNAV("user", this.getUSR());
        this.setNAV("password", this.getPWD());
        this.setNAV("ssl", "false");
        
        String urlBD = "jdbc:postgresql://"+
                            this.getHOST()+":"+
                            this.getPORT()+"/"+
                            this.getBD();
        this.setCNX(urlBD, this.getNAV());
        System.out.println("200 - CONECTADO");
    }
    
    private void desconectar() {
        try {
            this.CNX.close();
            System.out.println("200 - DESCONECTADO");
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - desconectar");
        }
    }
    public static void main(String[] args) {
        ClienteBD cli = new ClienteBD();
        cli.conectar();
        cli.desconectar();
        
    }
}
