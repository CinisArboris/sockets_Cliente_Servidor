package CLIENTE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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
    private String USR;
    private String PWD;
    private String TBL;
    

    public ClienteBD() {
        this.HOST   = "www.#.org.bo";
        this.PORT   = "#";
        this.USR    = "#";
        this.PWD    = "#";
        this.BD     = "#";
        this.TBL    = "#";
    }
    public ClienteBD(String HOST, String PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
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
    public String getTBL() {
        return TBL;
    }
    public void setTBL(String TBL) {
        this.TBL = TBL;
    }
    
    
    public static void main(String[] args) {
        ClienteBD cli = new ClienteBD();
        cli.conectar();
    }

    private void conectar() {
        try {
            Properties nav = new Properties();
            nav.setProperty("user", this.getUSR());
            nav.setProperty("password", this.getPWD());
            nav.setProperty("ssl", "True");
            
            String urlBD = "jdbc:postgresql://"+this.getHOST()+":"+this.getPORT()+"/"+this.getBD();
            
            Connection con = DriverManager.getConnection(urlBD, nav);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
