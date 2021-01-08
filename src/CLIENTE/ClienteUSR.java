package CLIENTE;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author eyver-dev
 */
public class ClienteUSR {
    ClienteBD cliBD;
    ClientePOP cliPOP;
    ClienteSMTP cliSMTP;
    Scanner input;
    String color;

    public ClienteUSR() {
        this.cliBD = new ClienteBD();
        this.cliPOP = new ClientePOP();
        this.cliSMTP = new ClienteSMTP();
        this.input = new Scanner(System.in);
        this.color = "\u001B[33m";
    }
    
    public ClienteBD getCliBD() {
        return cliBD;
    }
    public void setCliBD(ClienteBD cliBD) {
        this.cliBD = cliBD;
    }
    public ClientePOP getCliPOP() {
        return cliPOP;
    }
    public void setCliPOP(ClientePOP cliPOP) {
        this.cliPOP = cliPOP;
    }
    public ClienteSMTP getCliSMTP() {
        return cliSMTP;
    }
    public void setCliSMTP(ClienteSMTP cliSMTP) {
        this.cliSMTP = cliSMTP;
    }
    public Scanner getInput() {
        return input;
    }
    public void setInput(Scanner input) {
        this.input = input;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
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
    private void validarSIN(String cad) {
        String numerosValidos = "0123456789";
        byte[] vect = numerosValidos.getBytes();

        System.out.println(Arrays.toString(vect));
        
        // Cadena vacia.
        if (cad.length() == 0) return;
        
        // Cadena mayor a 1 digito.
        if (cad.length() > 1) return;
    }

    private void validarOP(String cad) {
        System.out.println("");
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
    public static void main(String[] args) throws IOException {
        ClienteUSR cli = new ClienteUSR();
        String comando;             // Variable de control.
        boolean bandera = true;     // Vari able de control.
        
        while (bandera){
            cli.espacio();
            
            System.out.println("=============================================");
            System.out.println("0. Salir.");
            System.out.println("1. Iniciar proceso.");
            comando = cli.getInput().nextLine();
            if ("0".equals(comando)) bandera = false;
            if (!"1".equals(comando)) return;
            System.out.println("=============================================");
            System.out.println("1. [cliente] - Solicitar información.");
            cli.solicitar_informacion();
            System.out.println("2. [sistema] - Leer mensaje.");
            cli.leer_mensaje();
            System.out.println("3. [sistema] - Procesar mensaje.");
            System.out.println("4. [sistema] - Responder mensaje.");
            System.out.println("5. [cliente] - Lectura de la respuesta.");
            System.out.println("=============================================");
            
        }
    }

    /**
     * 
     */
    private void espacio() {
        for (int i = 0; i < 10; i++){
            System.out.println("");
        }
    }

    /**
     * 
     * @return 
     */
    private boolean solicitar_informacion() {
        String comando;
        boolean bandera;
        
        /* A P A R T A D O   D E L   U S U A R I O */
        System.out.print(this.getColor()+"Correo Origen: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setFROM(comando);
        
        System.out.print(this.getColor()+"Correo Destino: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setTO(comando);
        
        /* A P A R T A D O   D E L   M E N S A J E */
        System.out.print(this.getColor()+"Asunto: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setSUBJECT(comando);
        
        System.out.print(this.getColor()+"Cuerpo del mensaje: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setDATA(comando);
        
        /* A P A R T A D O   D E L   S I S T E M A */
        this.cliSMTP.setHOST("www.tecnoweb.org.bo");
        this.cliSMTP.setPORT(25);
        
        // Si hay problemas, terminar el proceso.
        bandera = this.cliSMTP.conectar();
        if (!bandera) return bandera;
        
        // Si hay problemas, terminar el proceso.
        bandera = this.cliSMTP.enviar_mensaje(this.cliSMTP.getSUBJECT(), this.cliSMTP.getDATA());
        if (!bandera) return bandera;
        
        // G00D B0Y !!!
        bandera = this.cliSMTP.desconectar();
        return bandera;
    }

    /**
     * 
     * @return 
     */
    private boolean leer_mensaje() {
        boolean bandera;
        
        /* A P A R T A D O   D E L   S I S T E M A */
        this.cliPOP.setHOST("www.tecnoweb.org.bo");
        this.cliPOP.setPORT(110);
        
        /* A P A R T A D O   D E L   U S U A R I O */
        bandera = this.cliPOP.signIN();
        if (!bandera) return bandera;
        
        bandera = this.cliPOP.conectar();
        if (!bandera) return bandera;
        
        bandera = this.cliPOP.validando_LOGIN_POP();
        if (!bandera) return bandera;
        
        // Si hay problemas, terminar el proceso.
        bandera = this.cliPOP.pop_ultimo_mensaje();
        if (!bandera) return bandera;
        
        // G00D B0Y !!!
        bandera = this.cliPOP.desconectar();
        return bandera;
    }
}
