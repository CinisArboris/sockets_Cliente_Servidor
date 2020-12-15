package CLIENTE;

import java.io.*;
import java.net.*;

/**
 *
 * @author eyver-dev
 */
public class ClientePOP {
    private String HOST;
    private int PUERTO;
    private String USR;
    private String PWD;
    private String cmd;

    public ClientePOP() {
        this.HOST = "www.tecnoweb.org.bo";
        this.PUERTO = 110;
    }
    public ClientePOP(String HOST, int PUERTO) {
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
    public String getCmd() {
        return cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    
    
    private void test_cliente_01() {
        System.err.println("NOT YET");
    }
    
    
    public static void main(String[] args) {
        // #Inicializacion de los [CLIENTES].
        /* A */ //ClientePOP cli = new ClientePOP("127.0.0.1", 25);
        /* B */ ClientePOP cli = new ClientePOP();//B
        
        // #Conexion simple.
        cli.test_cliente_01();
    }
}
