package CLIENTE;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyver-dev
 */
public class ClienteSMTP {
    private String HOST;
    private int PUERTO;

    public ClienteSMTP() {
        this.HOST = "127.0.0.1";
        this.PUERTO = 25;
    }
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
    
    /**
     * Conexión simple al servidor.
     */
    void test_cliente_01() {
        try {
            Socket socCli = new Socket(
                    this.getHOST(), this.getPUERTO()
            );
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socCli.getInputStream()));
            System.out.println("[C]"+"Conectado a:"+this.getHOST());
            System.out.println("[S]"+entrada.readLine());
            socCli.close();
        } catch (IOException ex) {
            //Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        }
    }
    
    public static void main(String[] args) throws IOException {
        // Conexion simple
        ClienteSMTP cli = new ClienteSMTP("127.0.0.1", 25);
        cli.test_cliente_01();
        
        // Conexion para 3
        
    }
}
