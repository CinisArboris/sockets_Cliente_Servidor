package CLIENTE;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClienteSMTP {
    private String HOST;
    private int PORT;
    private String CMD;
    private String FROM;
    private String TO;

    public ClienteSMTP() {
        this.HOST   = "www.tecnoweb.org.bo";
        this.PORT   = 25;
        this.FROM   = "minpres@presidencia.gob.bo";
        this.TO     = "grupo01sa@tecnoweb.org.bo";
    }
    public ClienteSMTP(String HOST, int PUERTO) {
        this.HOST = HOST;
        this.PORT = PUERTO;
    }
    public ClienteSMTP(String HOST, int PUERTO, String FROM, String TO) {
        this.HOST = HOST;
        this.PORT = PUERTO;
        this.FROM = FROM;
        this.TO = TO;
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
    
    /**
     * Conexión simple al servidor.
     */
    private void test_cliente_01() {
        try {
            Socket cli = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            
            System.out.println("[S]"+entrada.readLine());
            
            cli.close();
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        }
    }
    
    /**
     * Conexion simple al servidor - enviar mensaje.
     * Requisitos del servidor:
     * [Dovecot] or [Sendmail]
     */
    private void test_cliente_02() {
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
            this.setCMD("subject: Descolonización imperialista.\r\n");
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
     * Conexión simple - enviar mensaje - subject custom.
     * @param subject 
     */
    private void test_cliente_03(String subject) {
        this.validarSUBJECT(subject);
        System.exit(0);
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
            this.setCMD("subject: "+subject+".\r\n");
            System.out.println(this.getCMD());
            salida.writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "AUFWIEDERSEHEN\r\n"+
                    "AUFWIEDERSEHEN\r\n"+
                    "AUFWIEDERSEHEN\r\n"
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
    
    public static void main(String[] args) throws IOException {
        // #Inicializacion de los [CLIENTES].
        /* local */
//        ClienteSMTP cli = new ClienteSMTP("127.0.0.1", 25);
        
        /* Tecno */
        ClienteSMTP cli = new ClienteSMTP("www.tecnoweb.org.bo", 25,
                "minpres@presidencia.gob.bo",
                "grupo01sa@tecnoweb.org.bo");
        
        /* fedora Server */
//        ClienteSMTP cli = new ClienteSMTP("192.168.1.2", 25,
//                "minpres@presidencia.gob.bo",
//                "freyja@freyja.wiki.bo");


        // #Saludo.
        System.out.println("SMTP : Conectando : "+cli.getHOST()+":"+cli.getPORT());
        
        // #Conexion simple.
        //cli.test_cliente_01();
        
        // #Conexion simple enviar mensaje.
        //cli.test_cliente_02();
        
        // #Conexion simple, enviar mensaje personalizado.
        cli.test_cliente_03("asdf1234 {}");
    }

    /**
     * Verificar que el [subject] es una cadena/comando válido.
     * Posee métodos intermedios.
     * @param subject 
     */
    private void validarSUBJECT(String subject) {
        boolean flag;
        
        flag = this.verificar_MIN_MAY(subject);
        if (flag)
            System.out.println("ERROR : != letras");
        
        flag = this.verificar_NUMERO(subject);
        if (flag)
            System.out.println("ERROR : != numero");
    }

    /**
     * Verificar que los elementos de la cadena son [letras] MAY/MIN.
     * Letras : a...z - A...Z
     * @param subject
     * @return
     */
    private boolean verificar_MIN_MAY(String subject) {
        byte minMIN = (byte)((char)'a');//097
        byte minMAX = (byte)((char)'z');//122
        byte mayMIN = (byte)((char)'A');//065
        byte mayMAX = (byte)((char)'Z');//090
        for (int i = 0; i < subject.length(); i++){
            if (((subject.charAt(i) < minMIN) && (subject.charAt(i) > minMAX))
                ||
                ((subject.charAt(i) < mayMIN) && (subject.charAt(i) > mayMAX))
            )
                return false;
        }
//        System.out.println(minMIN);
//        System.out.println(minMAX);
//        System.out.println(mayMIN);
//        System.out.println(mayMAX);
        return true;
    }

    /**
     * Verificar que los elementos de la cadena son [números].
     * Números : 0...9
     * @param subject
     * @return 
     */
    private boolean verificar_NUMERO(String subject) {
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
}
