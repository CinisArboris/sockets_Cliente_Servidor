package CLIENTE;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author eyver-dev
 */
public class ClienteBD {
    private String HOST;    // Variables del Servidor.
    private String PORT;    // Variables del Servidor.
    private String BD;      // Variables de la BD.
    private String TBL;     // Variables de la BD.
    private String USR;     // Variables de la Sesión.
    private String PWD;     // Variables de la Sesión.
    private Properties NAV; // Variables del Sistema.
    private Connection CNX; // Variables del Sistema.

    public ClienteBD() {
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
    public boolean setCNX(String url, Properties nav) {
        boolean bandera;
        try {
            this.CNX = DriverManager.getConnection(url, nav);
            bandera = true;
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - setCNX");
            bandera = false;
        }
        return bandera;
    }
//       :
//      t#,
//     ;##W.
//    :#L:WE
//   .KG  ,#D
//   EE    ;#f
//  f#.     t#i
//  :#G     GK
//   ;#L   LW.
//    t#f f#:
//     f#D#;
//      G#t
//       t
     /**
     * Ingresar las credenciales para la conexión : BD.
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
     * @return 
     */
    private boolean conectar() {
        boolean bandera;
        this.setNAV("user", this.getUSR());
        this.setNAV("password", this.getPWD());
        this.setNAV("ssl", "false");
        
        String urlBD = "jdbc:postgresql://"+
                            this.getHOST()+":"+
                            this.getPORT()+"/"+
                            this.getBD();
        
        bandera = this.setCNX(urlBD, this.getNAV());
        if (!bandera) return false;
        System.out.println("200 - CONECTADO");
        bandera = true;
        return bandera;
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
//       :
//      t#,
//     ;##W.
//    :#L:WE
//   .KG  ,#D
//   EE    ;#f
//  f#.     t#i
//  :#G     GK
//   ;#L   LW.
//    t#f f#:
//     f#D#;
//      G#t
//       t
    /**
     * Ejecutar una consulta a la BD.
     * @param consulta
     * @return 
     */
    private String consultaSQL(String consulta) {
        String resultado = "";
        String tmp =    "SELECT * " +
                        "FROM "+this.getTBL()+" p " +
                        "WHERE "
                            + "p.per_nom LIKE '%"+consulta+"%' or "
                            + "p.per_appm LIKE '%"+consulta+"%' or "
                            + "p.per_email LIKE '%"+consulta+"%'";
        try {
            Statement shell = this.getCNX().createStatement();
            ResultSet res = shell.executeQuery(tmp);
            ResultSetMetaData meta = res.getMetaData();
            int column = meta.getColumnCount();
            while (res.next()){
                for (int i = 1; i <= column; i++){
                    //System.out.println("..."+meta.getColumnTypeName(i));
                    resultado = resultado + res.getString(i);
                    //resultado = resultado + "|\n";
                    resultado = resultado + "|";
                }
                System.out.println("");
                System.out.println("*****************************************");
            }
            shell.close();
            res.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ClienteBD.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - consultaSQL");
        }
        return resultado;
    }
//       :
//      t#,
//     ;##W.
//    :#L:WE
//   .KG  ,#D
//   EE    ;#f
//  f#.     t#i
//  :#G     GK
//   ;#L   LW.
//    t#f f#:
//     f#D#;
//      G#t
//       t
//    public static void main(String[] args) {
//        int codigo = -1;        // variables de control
//        String resultado = "";  // variables de control
//        ClienteBD cli = null;   // variables de control
//        boolean bandera;        // variables de control
//        
//        codigo = 3;
//        switch (codigo){
//            case 0 :
//                cli = new ClienteBD();
//            case 1 :
//                // FREYJA SERVER
//                cli = new ClienteBD("192.168.1.9", "5432",
//                        "db_agenda", "persona");
//            case 2 :
//                // WINDOWS SERVER
//                cli = new ClienteBD("localhost", "5432",
//                        "db_agenda", "persona");
//            case 3 :
//                // TECNOWEB SERVER
//                cli = new ClienteBD("www.tecnoweb.org.bo", "5432",
//                        "db_agenda", "persona");
//        }
//        if (cli == null) return;
//        
//        cli.signIN();
//        bandera = cli.conectar();
//        if (!bandera) return;
//        
//        resultado = cli.consultaSQL("er");
//        resultado = resultado.replace("  ", "");
//        System.out.println(resultado);
//        
//        cli.desconectar();
//    }
}
