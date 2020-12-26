package CLIENTE;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClientePOP {
    private String HOST;
    private int PORT;
    private String USR;
    private String PWD;
    private String CMD;

    public ClientePOP() {
        this.HOST = "www.tecnoweb.org.bo";
        this.PORT = 110;
    }
    public ClientePOP(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
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
    public String getCMD() {
        return CMD;
    }
    public void setCMD(String CMD) {
        this.CMD = CMD;
    }

    /**
     * Conexión al servidior y listar bozones.
     */
    private void test_cliente_01() {
        Scanner input = new Scanner(System.in);
        System.out.print("[POP :: USR] ");
        this.setUSR(input.nextLine());
        System.out.print("[POP :: PWD] ");
        this.setPWD(input.nextLine());
        
        try {
            Socket sok = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
            System.err.println("[C]Conectando ..."+this.getHOST()+":"+this.getPORT());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set usuario al servidor.
            System.out.println("USER ******");
            this.setCMD("USER"+" "+this.getUSR()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #set password al servidor.
            System.out.println("PASS ******");
            this.setCMD("PASS"+" "+this.getPWD()+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #get estadisticas del correo.
            System.out.println("STAT");
            this.setCMD("STAT"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            // #get estadisticas del correo.
            System.out.println("LIST");
            this.setCMD("LIST"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+this.getMultiLine(entrada));
            TimeUnit.SECONDS.sleep(1);
            
            // #Fin del mensaje.
            this.setCMD("quit"+"\r\n");
            salida.writeBytes(this.getCMD());
            System.out.println("[S]"+entrada.readLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.println("[C]Cerrando sesión.");
            sok.close();
            entrada.close();
            salida.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR SLEEP");
        }
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
    
    public static void main(String[] args) {
        // #Inicializacion de los [CLIENTES].
        /* A */ //ClientePOP cli = new ClientePOP("127.0.0.1", 110);
        /* A */ ClientePOP cli = new ClientePOP("192.168.1.2", 110);
        /* B */ //ClientePOP cli = new ClientePOP();//B
        
        // #Extracción de mensajes.
        cli.test_cliente_01();
    }
}
