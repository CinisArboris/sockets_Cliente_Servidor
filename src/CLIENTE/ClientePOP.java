package CLIENTE;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClientePOP {
    private String HOST;                // Variables de Servidor.
    private int PORT;                   // Variables de Servidor.
    private String USR;                 // Variables de Sesión.
    private String PWD;                 // Variables de Sesión.
    private String CMD;                 // Variables de Comunicación.
    private String N_MAIL;                 // Variables de Comunicación.
    private String color;
    private Socket SOK;                 // Variables del Sistema.
    private BufferedReader ENTRADA;     // Variables del Sistema.
    private DataOutputStream SALIDA;    // Variables del Sistema.
    
    private String SMTP_SUBJECT;
    private String SMTP_FROMTO;
    private String SMTP_MAIL;

    public ClientePOP() {
        this.color = "\u001B[33m";
    }
    public ClientePOP(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.color = "\u001B[33m";
    }

    /**
     * Obtener el [dominio/ip] del servidor.
     * @return 
     */
    public String getHOST() {
        return HOST;
    }
    /**
     * Establecer el [dominio/ip] del servidor.
     * @param HOST 
     */
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    /**
     * Obtener el [puerto] del servidor.
     * @return 
     */
    public int getPORT() {
        return PORT;
    }
    /**
     * Establecer el [puerto] del servidor.
     * @param PORT 
     */
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
    /**
     * Obtener el [usuario] de la cuenta en el servidor.
     * @return 
     */
    public String getUSR() {
        return USR;
    }
    /**
     * * Establecer el [usuario] de la cuenta en el servidor.
     * @param USR 
     */
    public void setUSR(String USR) {
        this.USR = USR;
    }
    /**
     * Obtener el [password] de la cuenta en el servidor.
     * @return 
     */
    public String getPWD() {
        return PWD;
    }
    /**
     * Establecer el [password] de la cuenta en el servidor.
     * @param PWD 
     */
    public void setPWD(String PWD) {
        this.PWD = PWD;
    }
    /**
     * Obtener el [comando] que ejecutara el servidor.
     * @return 
     */
    public String getCMD() {
        return CMD;
    }
    /**
     * Establecer el [comando] que ejecutara el servidor.
     * @param CMD 
     */
    public void setCMD(String CMD) {
        this.CMD = CMD;
    }
    /**
     * Obtener el número del [mensaje] guardado que esta en el servidor.
     * @return 
     */
    public String getN_MAIL() {
        return N_MAIL;
    }
    /**
     * Establecer el número del [mensaje] guardado que esta en el servidor.
     * @param N_MAIL 
     */
    public void setN_MAIL(String N_MAIL) {
        this.N_MAIL = N_MAIL;
    }
    /**
     * Obtener el objeto [socket] para crear las conexiones con el servidor.
     * @return 
     */
    public Socket getSOK() {
        return SOK;
    }
    /**
     * Establecer el socket con el servidor POP.
     * @param host
     * @param port
     * @return 
     */
    public boolean setSOK(String host, int port) {
        boolean bandera;
        try {
            this.SOK = new Socket(host, port);
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - setSOK");
            bandera = false;
        }
        return bandera;
    }
    public BufferedReader getENTRADA() {
        return ENTRADA;
    }
    public void setENTRADA(BufferedReader ENTRADA) {
        this.ENTRADA = ENTRADA;
    }
    public DataOutputStream getSALIDA() {
        return SALIDA;
    }
    public void setSALIDA(DataOutputStream SALIDA) {
        this.SALIDA = SALIDA;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getSMTP_SUBJECT() {
        return SMTP_SUBJECT;
    }
    public void setSMTP_SUBJECT(String SMTP_SUBJECT) {
        this.SMTP_SUBJECT = SMTP_SUBJECT;
    }
    public String getSMTP_FROMTO() {
        return SMTP_FROMTO;
    }
    public void setSMTP_FROMTO(String SMTP_FROMTO) {
        this.SMTP_FROMTO = SMTP_FROMTO;
    }
    public String getSMTP_MAIL() {
        return SMTP_MAIL;
    }
    public void setSMTP_MAIL(String SMTP_MAIL) {
        this.SMTP_MAIL = SMTP_MAIL;
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
     * Ingresar las credenciales para la conexión : POP.
     * @return 
     */
    public boolean signIN() {
        boolean bandera;
        try {
            System.err.println("POP : Conectando : "+this.getHOST()+":"+this.getPORT());
            Scanner input = new Scanner(System.in);
            
            System.err.print("[POP :: USR] ");
            this.setUSR(input.nextLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.print("[POP :: PWD] ");
            this.setPWD(input.nextLine());
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - signIN");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Iniciar : [socket] y [entrada/salida] con el servidor POP.
     * @return 
     */
    public boolean conectar() {
        boolean bandera;
        try {
            /* I N I C I A R   S O C K E T */
            bandera = this.setSOK(this.getHOST(), this.getPORT());
            if (!bandera) return false;
            
            /* I N I C I A R   E N T R A D A */
            InputStream var01 = this.getSOK().getInputStream();
            InputStreamReader var02 = new InputStreamReader(var01);
            BufferedReader var03 = new BufferedReader(var02);
            this.setENTRADA(var03);
            TimeUnit.SECONDS.sleep(1);
            
            /* I N I C I A R   S A L I D A */
            OutputStream var04 = this.getSOK().getOutputStream();
            DataOutputStream var05 = new DataOutputStream (var04);
            this.setSALIDA(var05);
            TimeUnit.SECONDS.sleep(1);
            
            // #Primer contacto.
            System.out.println("[S]"+this.getColor()+this.getENTRADA().readLine());
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - conectar");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Validar credenciales de la cuenta.
     * @return 
     */
    public boolean signIn_server() {
        boolean bandera;
        String tmp;
        try {
            // #set usuario al servidor.
            System.out.println("USER *****");
            tmp = "USER"+" "+this.getUSR()+"\r\n";
            this.setCMD(tmp);
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            bandera = this.validarRESPONSE(tmp);
            if (!bandera) return bandera;
            
            // #set password al servidor.
            System.out.println("PASS *****");
            tmp = "PASS"+" "+this.getPWD()+"\r\n";
            this.setCMD(tmp);
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            bandera = this.validarRESPONSE(tmp);
            if (!bandera) return bandera;
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - validando_LOGIN_POP");
            bandera = false;
        }
        return bandera;
    }

    /**
     * Finalizar : [socket] y [entrada/salida] con el servidor POP.
     * @return 
     */
    public boolean desconectar() {
        boolean bandera;
        String tmp;
        try {
            /* D E S C O N E C T A N D O   P R O T O C O L O */
            this.setCMD("quit"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            /* D E S C O N E C T A N D O   C O M U N I C A C I O N */
            System.err.println("[C]Cerrando sesión.");
            this.getSOK().close();
            this.getENTRADA().close();
            this.getSALIDA().close();
            TimeUnit.SECONDS.sleep(2);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - desconectar");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Recopila multiples lineas de la respuesta del servidor.
     * @param entrada
     * @return 
     */
    private String getMultiLine(BufferedReader entrada) {
        String lines = "";
        while (true){
            try {
                String line = entrada.readLine();
                if (line == null){
                    System.out.println("");
                    break;
                }
                if (".".equals(line)){
                    System.out.println("");
                    break;
                }
                if ((line.length() > 0) && (line.charAt(0) == '.')){
                    // The line starts with a "." - strip it off.
                    line = line.substring(1);
                }
                lines=lines+"\n"+line;
            } catch (IOException ex) {
                Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return lines;
    }
    
    /**
     * 
     * @param tmp
     * @return 
     */
    private boolean validarRESPONSE(String tmp) {
        boolean bandera = true;
        if (
                tmp.contains("Rejecting open proxy")
                ||
                tmp.contains("Authentication failed")
            )
            bandera = false;
        return bandera;
    }

    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    boolean pop_estadisticas_mail() {
        String tmp;
        boolean bandera;
        try {
            // #get estadisticas del correo.
            System.out.println("STAT");
            this.setCMD("STAT"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            bandera = false;
            System.out.println("pop_estadisticas_mail");
        }
        return bandera;
    }
    
    private boolean guardar_cod_ultimo_mail() {
        boolean bandera;
        String tmp;
        try {
            this.setCMD("LIST"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getMultiLine(this.getENTRADA());
            
            System.out.println(tmp);
            if (tmp.contains("+OK 0 messages:")){
                System.out.println("BANDEJA VACIA.");
                this.setN_MAIL("0");
                bandera = true;
                return bandera;
            }
            
            String[] vect = tmp.split(" ");
            
            String sub_cadena = vect[vect.length-2];
            vect = sub_cadena.split("\n");
            sub_cadena = vect[1];
            this.setN_MAIL(sub_cadena);
            
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("guardar_ultimo_mail");
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
     * Conexión al servidior - Listar buzon - Guardar el código último mensaje.
     */
    private void test_cliente_01() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set usuario al servidor.
            System.out.println("USER ******");
            this.setCMD("USER"+" "+this.getUSR()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set password al servidor.
            System.out.println("PASS ******");
            this.setCMD("PASS"+" "+this.getPWD()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #get estadisticas del correo.
            System.out.println("STAT");
            this.setCMD("STAT"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #get estadisticas del correo.
            System.out.println("LIST");
            this.setCMD("LIST"+"\r\n");
            salida.writeBytes(this.getCMD());
            String lineas = this.getMultiLine(entrada);
//            System.out.println("[S]"+"\u001B[33m"+lineas);
            this.getLastSMS(lineas);
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("quit"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.println("[C]Cerrando sesión.");
            sok.close();
            entrada.close();
            salida.close();
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR SLEEP");
        }
    }
    
    /**
     * Conexion al servidor - extraer el mensaje X.
     * @param nro_mensaje 
     */
    private void test_cliente_02() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set usuario al servidor.
            System.out.println("USER ******");
            this.setCMD("USER"+" "+this.getUSR()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set password al servidor.
            System.out.println("PASS ******");
            this.setCMD("PASS"+" "+this.getPWD()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #get mensaje del correo.
            System.out.println("RETR "+this.getN_MAIL());
            this.setCMD("RETR"+" "+this.getN_MAIL()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+this.getMultiLine(entrada));
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("quit"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.println("[C]Cerrando sesión.");
            sok.close();
            entrada.close();
            salida.close();
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR SLEEP");
        }
    }
    
    /**
     * 
     * @return 
     */
    private boolean pop_listar_mails() {
        String tmp;
        boolean bandera;
        try {
            // #get estadisticas del correo.
            System.out.println("LIST");
            this.setCMD("LIST"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getMultiLine(this.getENTRADA());
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            this.setN_MAIL(tmp);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("pop_listar_mails");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Obtener y mostrar el último mensaje de la bandeja.
     * @return 
     */
    public boolean pop_mostrar_mensaje_ultimo() {
        boolean bandera;
        
        bandera = this.pop_mostrar_mensaje_X(this.getN_MAIL());
        if (!bandera) return bandera;
        
        return bandera;
    }
    
    /**
     * 
     * @param cod_mail
     * @return 
     */
    public boolean pop_mostrar_mensaje_X(String cod_mail) {
        boolean bandera;
        String tmp;
        
        if (Integer.parseInt(cod_mail) > Integer.parseInt(this.getN_MAIL())){
            System.out.println("No existe ese codigo de mail.");
            bandera = false;
            return bandera;
        }
        
        try {
            // #show mensaje nro X de la bandeja.
            System.out.println("RETR "+cod_mail);
            this.setCMD("RETR"+" "+cod_mail+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getMultiLine(this.getENTRADA());
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - pop_mostrar_mensaje_X");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * 
     */
    private boolean execute() {
        boolean bandera;
        
        
        
        System.out.println("*************************************************");
        /* Establecer credenciales */
        //bandera = this.signIN();
        //if (!bandera) return bandera;
        this.setUSR("grupo01sa");
        this.setPWD("grup001grup001");
        /* socket */
        bandera = this.conectar();
        if (!bandera) return bandera;
        /* login server */
        bandera = this.signIn_server();
        if (!bandera) return bandera;
        /*  */
        bandera = this.guardar_cod_ultimo_mail();
        if (!bandera){
            bandera = this.desconectar();
            return bandera;
        }
        System.out.println("*************************************************");
        bandera = this.pop_mostrar_mensaje_X("1");
        if (!bandera){
            bandera = this.desconectar();
            return bandera;
        }
        bandera = this.pop_extraer_datos_mensaje_X("1");
        if (!bandera){
            bandera = this.desconectar();
            return bandera;
        }
        System.out.println("*************************************************");
        bandera = this.desconectar();
        if (!bandera) return bandera;
        
        return bandera;
    }
    
    public static void main(String[] args) {
        ClientePOP cli = new ClientePOP();
        cli.setHOST("www.tecnoweb.org.bo");
        cli.setPORT(110);
        cli.execute();
    }
    
    public boolean pop_guardar_mensaje_X(String cod_mail) {
        boolean bandera;
        String tmp;
        
        if (Integer.parseInt(cod_mail) > Integer.parseInt(this.getN_MAIL())){
            System.out.println("No existe ese codigo de mail.");
            bandera = false;
            return bandera;
        }
        
        try {
            // #show mensaje nro X de la bandeja.
            this.setCMD("RETR"+" "+cod_mail+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getColor()+"[S]"+this.getMultiLine(this.getENTRADA());
            TimeUnit.SECONDS.sleep(1);
            
            // #guardar el mensaje
            this.setSMTP_MAIL(tmp);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - pop_guardar_mensaje_X");
            bandera = false;
        }
        return bandera;
    }
    private boolean pop_extraer_datos_mensaje_X(String cod_mail) {
        boolean bandera;
        String tmp;
        
        tmp =
            "+OK 405 octets\n" +
            "Return-Path: <free.bitcoin@gmail.com>\n" +
            "Received: from mail.tecnoweb.org.bo ([181.115.163.244])\n" +
            "	by www.tecnoweb.org.bo (8.15.2/8.15.2) with SMTP id 10A16Ftf1270074\n" +
            "	for grupo01sa@tecnoweb.org.bo; Sat, 9 Jan 2021 21:06:19 -0400\n" +
            "Date: Sat, 9 Jan 2021 21:06:15 -0400\n" +
            "From: free.bitcoin@gmail.com\n" +
            "Message-Id: <202101100106.10A16Ftf1270074@www.tecnoweb.org.bo>\n" +
            "subject: GIFT BITCOIN NOW\n" +
            "\n" +
            "BITCOIN LIFE";
        this.setSMTP_MAIL(tmp);
        
        String[] vect = tmp.split("\n");
        int tope = vect.length;
        for (int i = 0; i < tope; i++){
            String linea = vect[i];
            if (linea.contains("From: ")){
                String[] subLinea = linea.split(" ");
                this.setSMTP_FROMTO(subLinea[1]);
            }
            if (linea.contains("subject: ")){
                String[] subLinea = linea.split(" ");
                this.setSMTP_SUBJECT(subLinea[1]);
            }
        }
        if (!(this.getSMTP_FROMTO() != null)){
            bandera = false;
            return bandera;
        }
        if (!(this.getSMTP_FROMTO().contains("@"))){
            bandera = false;
            return bandera;
        }
        if (!(this.getSMTP_FROMTO() != null)){
            bandera = false;
            return bandera;
        }   
        System.out.println("FROM:" + this.getSMTP_FROMTO());
        System.out.println("SBJC:" + this.getSMTP_SUBJECT());
        bandera = true;
        return bandera;
    }

    private boolean pop_guardar_mensaje_N() {
        boolean bandera;
        bandera = this.pop_guardar_mensaje_X(this.N_MAIL);
        return bandera;
    }
    private boolean pop_extraer_datos_mensaje_N() {
        boolean bandera;
        bandera = this.pop_extraer_datos_mensaje_X(this.N_MAIL);
        return bandera;
    }
    

    

}
