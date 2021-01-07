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
    private String HOST;                // Variables de Servidor.
    private int PORT;                   // Variables de Servidor.
    private String USR;                 // Variables de Sesión.
    private String PWD;                 // Variables de Sesión.
    private String CMD;                 // Variables de Comunicación.
    private String SMS;                 // Variables de Comunicación.
    private Socket SOK;                 // Variables del System.
    private BufferedReader ENTRADA;     // Variables del System.
    private DataOutputStream SALIDA;    // Variables del System.

    public ClientePOP() {
    }
    public ClientePOP(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
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
    public String getSMS() {
        return SMS;
    }
    /**
     * Establecer el número del [mensaje] guardado que esta en el servidor.
     * @param SMS 
     */
    public void setSMS(String SMS) {
        this.SMS = SMS;
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
    private boolean signIN() {
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
    private boolean conectar() {
        boolean bandera;
        try {
            bandera = this.setSOK(this.getHOST(), this.getPORT());
            if (!bandera) return false;
            
            InputStream var01 = this.getSOK().getInputStream();
            InputStreamReader var02 = new InputStreamReader(var01);
            BufferedReader var03 = new BufferedReader(var02);
            this.setENTRADA(var03);
            TimeUnit.SECONDS.sleep(1);
            
            OutputStream var04 = this.getSOK().getOutputStream();
            DataOutputStream var05 = new DataOutputStream (var04);
            this.setSALIDA(var05);
            TimeUnit.SECONDS.sleep(1);
            
            // #Primer contacto.
            System.out.println("[S]"+"\u001B[33m"+this.getENTRADA().readLine());
            
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
    private boolean validando_LOGIN_POP() {
        boolean bandera;
        String tmp;
        try {
            // #set usuario al servidor.
            System.out.println("USER *****");
            this.setCMD("USER"+" "+this.getUSR()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getENTRADA().readLine();
            System.out.println("[S]"+"\u001B[33m"+tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #set password al servidor.
            System.out.println("PASS *****");
            this.setCMD("PASS"+" "+this.getPWD()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getENTRADA().readLine();
            System.out.println("[S]"+"\u001B[33m"+tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // La validación, segun la respuesta del error.
            if (tmp.contains("Authentication failed")){
                System.err.println("Credenciales invalidas.");
                bandera = false;
                return bandera;
            }
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
     */
    private void desconectar() {
        try {
            // #Fin del mensaje.
            this.setCMD("quit"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            System.out.println("[S]"+"\u001B[33m"+this.getENTRADA().readLine());
            TimeUnit.SECONDS.sleep(1);
            
            System.err.println("[C]Cerrando sesión.");
            this.getSOK().close();
            this.getENTRADA().close();
            this.getSALIDA().close();
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - desconectar");
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
            System.out.println("RETR "+this.getSMS());
            this.setCMD("RETR"+" "+this.getSMS()+"\r\n");
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
     * Obtener y mostrar el último mensaje de la bandeja.
     * @return 
     */
    private boolean pop_ultimo_mensaje() {
        boolean bandera;
        String tmp;
        try {
            // #get estadisticas del correo.
            System.out.println("STAT");
            this.setCMD("STAT"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getENTRADA().readLine();
            System.out.println("[S]"+"\u001B[33m"+tmp);
            TimeUnit.SECONDS.sleep(1);
            
            // #get estadisticas del correo.
            System.out.println("LIST");
            this.setCMD("LIST"+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            String lineas = this.getMultiLine(this.getENTRADA());
            this.getLastSMS(lineas);
            TimeUnit.SECONDS.sleep(1);

            // #get mensaje del correo.
            System.out.println("RETR "+this.getSMS());
            this.setCMD("RETR"+" "+this.getSMS()+"\r\n");
            this.getSALIDA().writeBytes(this.getCMD());
            tmp = this.getMultiLine(this.getENTRADA());
            System.out.println("[S]"+"\u001B[33m"+tmp);
            TimeUnit.SECONDS.sleep(1);
            
            bandera = true;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(ClientePOP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR - test_cliente_03");
            bandera = false;
        }
        return bandera;
    }
    
    public static void main(String[] args) {
        int codigo;
        ClientePOP cli;
        boolean bandera;
        
        codigo = 2;
        cli = null;
        switch (codigo){
            case 0 :
                cli = new ClientePOP("127.0.0.1", 110);
            case 1 :
                cli = new ClientePOP("192.168.1.2", 110);
            case 2 :
                cli = new ClientePOP("www.tecnoweb.org.bo", 110);
        }
        if (cli == null) return;
        
        cli.signIN();
        
        bandera = cli.conectar();
        if (!bandera) return;
        
        bandera = cli.validando_LOGIN_POP();
        if (!bandera) return;
        
        bandera = cli.pop_ultimo_mensaje();
        
        if (bandera) cli.desconectar();
    }
}
