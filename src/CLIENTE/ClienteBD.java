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
        this.HOST   = "localhost";
        this.PORT   = "5432";
        this.BD     = "db_agenda";
        this.TBL    = "amigo";
        this.NAV    = new Properties();
        this.CNX    = null;
    }
    public ClienteBD(String HOST, String PORT, String BD, String TBL) {
        this.HOST   = HOST;
        this.PORT   = PORT;
        this.BD     = BD;
        this.TBL    = TBL;
        this.NAV    = new Properties();
        this.CNX    = null;
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
            System.exit(404);
        }
    }
    
     /**
     * Ingresar las credenciales de la cuenta BD.
     */
    private void signIN() {
        System.err.println("BD : Conectando : "+this.getHOST()+":"+this.getPORT());
        
        Scanner input = new Scanner(System.in);
        System.err.print("[BD :: USR] ");
        this.setUSR(input.nextLine());
        System.err.print("[BD :: PWD] ");
        this.setPWD(input.nextLine());
    }
    
    /**
     * Iniciar la conexión a la BD.
     */
    private void conectar() {
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
    
    /**
     * Cerrar la conexión a la BD.
     */
    private void desconectar() {
        try {
            this.CNX.close();
            System.out.println("200 - DESCONECTADO");
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - desconectar");
        }
    }
    
    /**
     * Ejecutar una consulta a la BD.
     * @param consulta 
     */
    private void consultaSQL(String consulta) {
        try {
            Statement shell = this.getCNX().createStatement();
            ResultSet res = shell.executeQuery(consulta);
            ResultSetMetaData meta = res.getMetaData();
            
            int column = meta.getColumnCount();
            while (res.next()){
                for (int i = 1; i <= column; i++){
                    System.out.print(res.getString(i));
                    System.out.print("|");
                }
                System.out.println("");
                System.out.println("*****************************************");
            }
            shell.close();
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - consultaSQL");
        }
    }
    
    public static void main(String[] args) {
        /* Servidor local  */
//        ClienteBD cli = new ClienteBD();
        
        /* Servidor freyja */
//        ClienteBD cli = new ClienteBD("192.168.1.9", "5432", 
//                "db_agenda", "amigo");
        
        /* Servidor windows */
        ClienteBD cli = new ClienteBD("localhost", "5432", 
                "db_agenda", "amigo");
        
        /* Servidor tecno  */
//        ClienteBD cli = new ClienteBD("www.tecnoweb.org.bo", "5432",
//                "db_agenda", "amigo");
        
        
        
        cli.signIN();
        cli.conectar();
        cli.consultaSQL("select * from amigo");
        cli.desconectar();
    }
}
