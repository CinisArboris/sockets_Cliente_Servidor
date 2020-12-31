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
    // Variables de Servidor.
    private String HOST;
    private int PORT;
    // Variables de Sesión.
    private String USR;
    private String PWD;
    // Variables de Comunicación.
    private String CMD;
    private String SMS;
    // Variables del System.
    private Socket SOK;
    private BufferedReader ENTRADA;
    private DataOutputStream SALIDA;

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
    public String getSMS() {
        return SMS;
    }
    public void setSMS(String SMS) {
        this.SMS = SMS;
    }

    public Socket getSOK() {
        return SOK;
    }
    public void setSOK(String host, int port) {
        try {
            this.SOK = new Socket(host, port);
        } catch (IOException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[setSOK] - Error creando el SOCKET.");
        }
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
     * Ingresar las credenciales para la conexión : POP.
     */
    private void signIN() {
        System.err.println("POP : Conectando : "+this.getHOST()+":"+this.getPORT());
        
        Scanner input = new Scanner(System.in);
        System.err.print("[POP :: USR] ");
        this.setUSR(input.nextLine());
        System.err.print("[POP :: PWD] ");
        this.setPWD(input.nextLine());
    }
    
    /**
     * Guarda el ultimo mensaje de la bandeja.
     * @param entrada 
     */
    private void getLastSMS(String entrada) {
        String[] tmp = entrada.split(" ");
        String tmp2 = tmp[tmp.length-2];
        tmp = tmp2.split("\n");
        tmp2 = tmp[1];
        this.setSMS(tmp2);
    }
    
    /**
     * Conexión al servidior - Listar buzon - Guardar el código último mensaje.
     */
    private void test_cliente_01() {
        try {
            Socket sok = new Socket(this.getHOST(), this.getPORT());
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            DataOutputStream salida = new DataOutputStream (sok.getOutputStream());
            
            // #Primer contacto.
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
            String lineas = this.getMultiLine(entrada);
//            System.out.println("[S]"+lineas);
            this.getLastSMS(lineas);
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
            
            // #get mensaje del correo.
            System.out.println("RETR "+this.getSMS());
            this.setCMD("RETR"+" "+this.getSMS()+"\r\n");
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
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR 404");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSMTP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[System]ERROR SLEEP");
        }
    }

    public static void main(String[] args) {
        // #Inicializacion de los [CLIENTES].
        /* local  */
        //ClientePOP cli = new ClientePOP("127.0.0.1", 110);
        
        /* freyja */
        //ClientePOP cli = new ClientePOP("192.168.1.2", 110);
        
        /* tecno  */
        ClientePOP cli = new ClientePOP();//B
        
        // #Cargar credenciales.
        cli.signIN();
        
        // #Listar mensajes y obtener el ultimo.
//        cli.test_cliente_01();
        
        // #Obtener mensaje.
//        cli.test_cliente_02();
        
        // #Conexión servidor POP modular.
        cli.test_cliente_03();
    }

    /**
     * 
     */
    private void test_cliente_03() {
        try {
            this.conectar();
            
            // #Primer contacto.
            System.out.print("[S]"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            
//            // #set usuario al servidor.
//            System.out.println("USER ******");
//            this.setCMD("USER"+" "+this.getUSR()+"\r\n");
//            salida.writeBytes(this.getCMD());
//            System.out.println("[S]"+ENTRADA.readLine());
//            TimeUnit.SECONDS.sleep(1);
            
//            // #set password al servidor.
//            System.out.println("PASS ******");
//            this.setCMD("PASS"+" "+this.getPWD()+"\r\n");
//            salida.writeBytes(this.getCMD());
//            System.out.println("[S]"+ENTRADA.readLine());
//            TimeUnit.SECONDS.sleep(1);
//            
//            // #get estadisticas del correo.
//            System.out.println("STAT");
//            this.setCMD("STAT"+"\r\n");
//            salida.writeBytes(this.getCMD());
//            System.out.println("[S]"+ENTRADA.readLine());
//            TimeUnit.SECONDS.sleep(1);
//            
//            // #get estadisticas del correo.
//            System.out.println("LIST");
//            this.setCMD("LIST"+"\r\n");
//            salida.writeBytes(this.getCMD());
//            String lineas = this.getMultiLine(ENTRADA);
////            System.out.println("[S]"+lineas);
//            this.getLastSMS(lineas);
//            TimeUnit.SECONDS.sleep(1);
//            
//            // #Fin del mensaje.
//            this.setCMD("quit"+"\r\n");
//            salida.writeBytes(this.getCMD());
//            System.out.println("[S]"+ENTRADA.readLine());
//            TimeUnit.SECONDS.sleep(1);
            
            this.desconectar();
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
     */
    private void conectar() {
        try {
            this.setSOK(this.getHOST(), this.getPORT());
            this.setENTRADA(new BufferedReader(new InputStreamReader(this.getSOK().getInputStream())));
            this.setSALIDA(new DataOutputStream (this.getSOK().getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * 
     */
    private void desconectar() {
        try {
            System.err.println("[C]Cerrando sesión.");
            this.getSOK().close();
            this.getENTRADA().close();
            this.getSALIDA().close();
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    
}
