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
    private int PUERTO;
    private String CMD;
    private String from;
    private String to;

    /**
     * Valores de conexión por defecto.
     */
    public ClienteSMTP() {
        this.HOST = "www.tecnoweb.org.bo";
        this.PUERTO = 25;
    }
    
    /**
     * Establecer valores de conexión.
     * @param HOST
     * @param PUERTO 
     */
    public ClienteSMTP(String HOST, int PUERTO) {
        this.HOST = HOST;
        this.PUERTO = PUERTO;
    }

    public String getHOST() {
        return HOST;
    }
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    public int getPUERTO() {
        return PUERTO;
    }
    public void setPUERTO(int PUERTO) {
        this.PUERTO = PUERTO;
    }
    public String getCMD() {
        return CMD;
    }
    public void setCMD(String CMD) {
        this.CMD = CMD;
    }
    
    /**
     * Conexión simple al servidor.
     */
    private void test_cliente_01() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPUERTO());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            
            System.out.println("[C]"+"Conectado a:"+this.getHOST());
            System.out.println("[S]"+entrada.readLine());
            
            sok.close();
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        }
    }
    
    /**
     * Conexion simple al servidor y enviar mensaje.
     * Requisitos:
     * [Dovecot] or [Sendmail]
     */
    private void test_cliente_02() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPUERTO());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
            System.err.println("[C]Conectando ..."+this.getHOST()+":"+this.getPUERTO());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo "+this.getHOST()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            //this.setCMD("mail from: "+"eyver.evm@gmail.com"+"\r\n");
            this.setCMD("mail from: "+"minpres@presidencia.gob.bo"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to: "+"grupo03sa@tecnoweb.org.bo"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: er \r\n");
            System.out.println(this.getCMD());
            salida.writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "AHORA SI, WERRA SHEVEL"+"\r\n"+
                    "1, 2, 3, 4, 5, 6, 7, 8."+"\r\n"
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
            sok.close();
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
        /* A */ //ClienteSMTP cli = new ClienteSMTP("127.0.0.1", 25);
        /* A */ ClienteSMTP cli = new ClienteSMTP("192.168.1.2", 25);
        /* B */ //ClienteSMTP cli = new ClienteSMTP();//B
        
        // #Conexion simple.
        //cli.test_cliente_01();
        
        // #Conexion simple enviar mensaje.
        //cli.test_cliente_02();
        cli.test_cliente_03();
        //cli.test_cliente_04("er");
    }

    private void test_cliente_04(String er) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Conexion simple al servidor y enviar mensaje.
     * Requisitos:
     * [Dovecot] or [Sendmail]
     */
    private void test_cliente_03() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPUERTO());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
            System.err.println("[C]Conectando ..."+this.getHOST()+":"+this.getPUERTO());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Saludo al servidor.
            this.setCMD("helo "+this.getHOST()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo emisor del mensaje.
            //this.setCMD("mail from: "+"eyver.evm@gmail.com"+"\r\n");
            this.setCMD("mail from: "+"minpres@presidencia.gob.bo"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el correo destino del mensaje.
            this.setCMD("rcpt to: "+"freyja@freyja.wiki.bo"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar los datos del mensaje.
            this.setCMD("data"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el asunto del mensaje.
            this.setCMD("subject: er \r\n");
            System.out.println(this.getCMD());
            salida.writeBytes(this.getCMD());
            TimeUnit.SECONDS.sleep(1);
            
            // #Asignar el mensaje del mensaje.
            this.setCMD(
                    "AHORA SI, WERRA SHEVEL"+"\r\n"+
                    "1, 2, 3, 4, 5, 6, 7, 8."+"\r\n"
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
            sok.close();
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
}
