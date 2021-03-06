package SERVIDOR;

import java.io.*;
import java.net.*;

/**
 *
 * @author eyver-dev
 */
public class ServidorSMTP {
    private String HOST;
    private int PUERTO;

    public ServidorSMTP() {
        this.HOST = "127.0.0.1";
        this.PUERTO = 25;
    }
    public ServidorSMTP(String HOST, int PUERTO) {
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
     * Servidor simple.
     * Espera a [1 cliente] y solo da [1 respuesta].
     */
    void test_servidor_01() {
        try {
            ServerSocket socSer = new ServerSocket(this.getPUERTO());
            System.out.println("[S] Esperando clientes...");
            
            Socket socCli = socSer.accept();
            DataOutputStream salida = new DataOutputStream (socCli.getOutputStream());
            
            salida.writeBytes("HELO\n");
            
            socCli.close();
        } catch (IOException ex) {
            //Logger.getLogger(ServidorSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR :: 404");
        }
    }
    
    /**
     * Servidor simple con limite de conexiones.
     */
    private void test_servidor_02() {
        try {
            ServerSocket socSer = new ServerSocket(this.getPUERTO());
            System.out.println("[S] Esperando clientes...");
            
            int nroClientes = 1;
            Socket socCli;
            while (nroClientes <= 3){
                System.out.println("[System] Welcome " + nroClientes);
                socCli = socSer.accept();
                DataOutputStream salida = new DataOutputStream (socCli.getOutputStream());
                salida.writeBytes("HELO :: " + nroClientes);
                nroClientes++;
                socCli.close();
            }
            System.err.println("[System] Limite de clientes por hoy.");
            //socCli.close();
        } catch (IOException ex) {
            //Logger.getLogger(ServidorSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR :: 404");
        }
    }
    
    public static void main(String[] args) {
        ServidorSMTP serv = new ServidorSMTP();
        serv.test_servidor_01();
        //serv.test_servidor_02();
    }
}
