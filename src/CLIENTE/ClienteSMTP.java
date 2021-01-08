package CLIENTE;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClienteSMTP {
    private String HOST;            //Variable de servidor.
    private int PORT;               //Variable de servidor.
    private String CMD;             //Variable de comunicación.
    private BufferedReader ENTRADA; //Variable de comunicación.
    private DataOutputStream SALIDA;//Variable de comunicación.
    private String FROM;            //Variable de mail.
    private String TO;              //Variable de mail.
    private String SUBJECT;         //Variable de mail.
    private String DATA;            //Variable de mail.
    private LinkedList COD;         // Variables de System.
    private Socket SOK;             // Variables de System.
    

    public ClienteSMTP() {
        this.COD = new LinkedList();
        this.cargarCOD();
    }
    public ClienteSMTP(String HOST, int PUERTO) {
        this.HOST = HOST;
        this.PORT = PUERTO;
        this.COD = new LinkedList();
        this.cargarCOD();
    }
    public ClienteSMTP(String HOST, int PUERTO, String FROM, String TO) {
        this.HOST = HOST;
        this.PORT = PUERTO;
        this.FROM = FROM;
        this.TO = TO;
        this.COD = new LinkedList();
        this.cargarCOD();
    }

    public String getHOST() {
        return HOST;
    }
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    public int getPORT() {
        return PORT;
    }
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
    public String getCMD() {
        return CMD;
    }
    public void setCMD(String CMD) {
        this.CMD = CMD;
    }
    public String getFROM() {
        return FROM;
    }
    public void setFROM(String FROM) {
        this.FROM = FROM;
    }
    public String getTO() {
        return TO;
    }
    public void setTO(String TO) {
        this.TO = TO;
    }
    public LinkedList getCOD() {
        return COD;
    }
    public void setCOD(String COD) {
        this.COD.add(COD);
    }
    public Socket getSOK() {
        return SOK;
    }
    public boolean setSOK(String host, int port) {
        boolean bandera;
        try {
            this.SOK = new Socket(host, port);
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[ERROR] > setSOK");
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
    public String getSUBJECT() {
        return SUBJECT;
    }
    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }
    public String getDATA() {
        return DATA;
    }
    public void setDATA(String DATA) {
        this.DATA = DATA;
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
     * Cargar los codigos validos en el sistema [default].
     */
    public void cargarCOD() {
        this.setCOD("listar");
        this.setCOD("crear");
        this.setCOD("mostrar");
        this.setCOD("actualizar");
        this.setCOD("borrar");
    }
    
    /**
     * Estableciendo la conexión con el servidor.
     * @return 
     */
    public boolean conectar() {
        boolean bandera;
        
        bandera = this.setSOK(this.getHOST(), this.getPORT());
        if (!bandera) return bandera;
        
        bandera = this.establecer_FLUJO_COMUNICACION();
        if (!bandera) return bandera;
        
        return bandera;
    }
    
    /**
     * Iniciar [entrada/salida] para comunicar con el servidor SMTP.
     * @return 
     */
    private boolean establecer_FLUJO_COMUNICACION() {
        boolean bandera;
        try {
            InputStream vara = this.getSOK().getInputStream();
            InputStreamReader varb = new InputStreamReader(vara);
            BufferedReader varc = new BufferedReader(varb);
            this.setENTRADA(varc);
            
            OutputStream vard = this.getSOK().getOutputStream();
            DataOutputStream vare = new DataOutputStream(vard);
            this.setSALIDA(vare);
            
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[error] > establecer_FLUJO_COMUNICACION");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Cerrar la comunicación con el servidor SMTP.
     * @return 
     */
    public boolean desconectar() {
        boolean bandera;
        try {
            System.err.println("[C]Cerrando sesión.");
            this.getSOK().close();
            this.getENTRADA().close();
            this.getSALIDA().close();
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[ERROR] > desconectar");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Verificar que el [subject] es una cadena/comando válido.
     * Posee métodos intermedios.
     * @param subject 
     */
    private boolean validarSUBJECT(String subject) {
        boolean bandera;
        String sepComPar, sepPal, cadComando, cadParamet = "";
        
        sepComPar = "{}";
        sepPal = " ";
        
        /* I N I C I A N D O   V A L I D A C I O N E S */
        
//        bandera = this.verificar_rango_MIN_MAY(subject);
//        if (!bandera){
//            this.desconectar();
//            System.out.println("ERROR : != letras");
//            return bandera;
//        }
        
//        bandera = this.verificar_rango_NUMERO(subject);
//        if (!bandera){
//            this.desconectar();
//            System.out.println("ERROR : != numero");
//            return false;
//        }
        
        bandera = this.verificar_rango_ALFANUMERICO(subject);
        if (!bandera){
            this.desconectar();
            System.out.println("ERROR : != numero");
            return false;
        }
        
        return bandera;   
    }

    /**
     * Verificar que los elementos de la cadena son [letras] MAY/MIN.
     * Letras : a...z - A...Z
     * @param subject
     * @return
     */
    private boolean verificar_rango_MIN_MAY(String subject) {
        byte dato;
        boolean bandera = true;
        byte minMIN = (byte)((char)'a');//097
        byte minMAX = (byte)((char)'z');//122
        byte mayMIN = (byte)((char)'A');//065
        byte mayMAX = (byte)((char)'Z');//090
        byte espacio = (byte)((char)' ');//032
        for (int i = 0; i < subject.length(); i++){
            dato = (byte)subject.charAt(i);
            //System.out.println(dato);
            if (((dato < minMIN) || (dato > minMAX))
                &&
                ((dato < mayMIN) || (dato > mayMAX))
                &&
                (dato != espacio)
            )
                bandera = false;
        }
//        System.err.println(minMIN+" "+minMAX+" "+mayMIN+" "+mayMAX);
        return bandera;
    }

    /**
     * Verificar que los elementos de la cadena son [números].
     * Números : 0...9
     * @param subject
     * @return 
     */
    private boolean verificar_rango_NUMERO(String subject) {
        byte numMIN = (byte)((char)'0');//048
        byte numMAX = (byte)((char)'9');//057
        for (int i = 0; i < subject.length(); i++){
            if ((subject.charAt(i) < numMIN) && (subject.charAt(i) > numMAX))
                return false;
        }
//        System.out.println(numMIN);
//        System.out.println(numMAX);
        return true;
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
     * Conexión servidor SMTP.
     * Código: Secuencial.
     */
    public void test_cliente_01_e() {
        String tmp;
        boolean bandera;
        try {
            Socket cli = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            
            tmp = "\u001B[36m" + "[S]"+entrada.readLine();
            System.out.println(tmp);
            
            cli.close();
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System] - test_cliente_01_e");
        }
    }
    
    /**
     * Conexión servidor SMTP.
     * Código: Modular.
     * @return 
     */
    public boolean test_cliente_01_m() {
        String tmp;
        boolean bandera;
        try {
            bandera = this.conectar();
            if (!bandera) return false;
            
            bandera = this.establecer_FLUJO_COMUNICACION();
            if (!bandera) return false;
            
            tmp = "\u001B[36m" + "[S]"+ENTRADA.readLine();
            System.out.println(tmp);
            
            this.desconectar();
            bandera = true;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System] - test_cliente_01_m");
            bandera = false;
        }
        return bandera;
    }
    
    /**
     * Conexión servidor SMTP - enviar mensaje.
     * Código: Secuencial.
     * Requisitos del servidor:
     * [Dovecot] or [Sendmail]
     */
    public void test_cliente_02_e() {
        try {
            Socket cli = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            DataOutputStream salida = new DataOutputStream (cli.getOutputStream());
            
            // #Primer contacto.
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo "+this.getHOST()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            this.setCMD("mail from:"+" "+this.getFROM()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to:"+" "+this.getTO()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: select * from persona\r\n");
            System.out.println(this.getCMD());
            salida.writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "Este servidor imperialista, ahora esta descolonizado\r\n"+
                    "Cuerran PITITAS, cuerran.\r\n"+
                    "Propiedad de los AYLLUS Y MAMANIS - INC.\r\n"
                );
            System.out.println(this.getCMD());
            salida.writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("."+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.println("[C]Cerrando sesión.");
            cli.close();
            entrada.close();
            salida.close();
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR SLEEP");
        }
    }
    
    /**
     * Conexión servidor SMTP - enviar mensaje.
     * Código: Modular.
     * Requisitos del servidor:
     * [Dovecot] or [Sendmail]
     */
    public boolean test_cliente_02_m() {
        String tmp;
        boolean bandera;
        try {
            bandera = this.conectar();
            if (!bandera) return bandera;
            
            // #Primer contacto.
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo"+" "+this.getHOST()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            this.setCMD("mail from:"+" "+this.getFROM()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to:"+" "+this.getTO()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            if (tmp.contains("User unknown")) {
                bandera = this.desconectar();
                return bandera;
            }
            if (tmp.contains("IP name lookup failed")){
                bandera = this.desconectar();
                return bandera;
            }
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: INDEX OF\r\n");
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "ls -al\r\n"+
                    "ls -al\r\n"+
                    "ls -al\r\n"
                );
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("."+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = "\u001B[36m" + "[S]"+this.getENTRADA().readLine();
            System.out.println(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Cerrando sesión.
            bandera = this.desconectar();
            if (!bandera) return bandera;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System] - test_cliente_02_m");
            bandera = false;
        }
        return bandera;
    }

    /**
     * Conexión simple - enviar mensaje - subject custom.
     * @param subject 
     */
    public boolean enviar_mensaje(String subject) {
        boolean bandera;
        
        bandera = this.validarSUBJECT(subject);
        if (!bandera) return bandera;
        
        try {
            // #Primer contacto.
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo "+this.getHOST()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            this.setCMD("mail from:"+" "+this.getFROM()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to:"+" "+this.getTO()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: "+subject+".\r\n");
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "AUFWIEDERSEHEN\r\n"+
                    "AUFWIEDERSEHEN\r\n"+
                    "AUFWIEDERSEHEN\r\n"
                );
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("."+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[ERROR] > enviar_msj_subject");
            bandera = false;
        }
        return bandera;
    }

    /**
     * Conexión simple - enviar mensaje - subject custom - data custom.
     * @param subject
     * @param data
     * @return 
     */
    public boolean enviar_mensaje(String subject, String data) {
        boolean bandera;
        String tmp;
        bandera = this.validarSUBJECT(subject);
        if (!bandera) return bandera;
        
        try {
            // #Primer contacto.
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo "+this.getHOST()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            this.setCMD("mail from:"+" "+this.getFROM()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to:"+" "+this.getTO()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getENTRADA().readLine();
            System.out.println("[S]"+tmp);
            this.validar_rcpt_to(tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: "+subject+".\r\n");
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(data+"\r\n");
            System.out.println(this.getCMD());
            this.getSALIDA().writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("."+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[ERROR] > enviar_msj_subject");
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
//    public static void main(String[] args) throws IOException {
//        int codigo;         // Variables de control.
//        ClienteSMTP cli;    // Variables de control.
//        boolean bandera;    // Variables de control.
//        
//        codigo = 4;
//        cli = null;
//        switch (codigo){
//            case 0 :
//                cli = new ClienteSMTP();
//            case 1 :
//                cli = new ClienteSMTP("127.0.0.1", 25);
//            case 2 :
//                cli = new ClienteSMTP("192.168.1.2", 25,
//                    "minpres@presidencia.gob.bo",
//                    "freyja@freyja.wiki.bo");
//            case 3 :
//                cli = new ClienteSMTP("www.tecnoweb.org.bo", 25,
//                    "grupo01sa@tecnoweb.org.bo",
//                    "grupo01sa@tecnoweb.org.bo");
//            case 4 :
//                cli = new ClienteSMTP("www.tecnoweb.org.bo", 25,
///*  FROM    */      "grupo01sa@tecnoweb.org.bo",
///*  TO      */      "grupo01sa@tecnoweb.org.bo");
//        }
//        if (cli == null) return;
//        
//        bandera = cli.conectar();
//        if (!bandera) return;
//        
//        // #Conexion simple.
//        //cli.test_cliente_01_e();
//        //bandera = cli.test_cliente_01_m();
//        
//        // #Conexion simple - Enviar mensaje.
//        //cli.test_cliente_02_m();
//        
//        // #Conexion simple - enviar mensaje[subject].
//        //cli.enviar_mensaje("er");
//        
//        // #Conexion simple - enviar mensaje[subject-data]
//        cli.enviar_mensaje("er", "hola hola que tal.");
//        
//        cli.desconectar();
//    }

    /**
     * 
     * @param tmp
     * @return 
     */
    private boolean validar_rcpt_to(String tmp) {
        boolean bandera;
        if (
                tmp.contains("User unknown")
                ||
                tmp.contains("IP name lookup failed")
                ||
                tmp.contains("Domain name required for sender")
            ) {
            this.desconectar();
            bandera = false;
        }else{
            bandera = true;
        }
        return bandera;
    }

    /**
     * 
     * @param subject
     * @return 
     */
    private boolean verificar_rango_ALFANUMERICO(String subject) {
        String dato;
        boolean bandera = true;
        
        for (int i = 0; i < subject.length(); i++){
            dato = String.valueOf(subject.charAt(i));
            //System.out.println(dato);
            if (
                    !this.verificar_rango_MIN_MAY(dato)
                    &&
                    !this.verificar_rango_NUMERO(dato))
                bandera = false;
        }
//        System.err.println(minMIN+" "+minMAX+" "+mayMIN+" "+mayMAX);
        return bandera;
    }
}
