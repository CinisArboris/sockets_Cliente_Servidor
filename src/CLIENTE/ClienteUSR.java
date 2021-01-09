package CLIENTE;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author eyver-dev
 */
public class ClienteUSR {
    private ClienteBD cliBD;
    private ClientePOP cliPOP;
    private ClienteSMTP cliSMTP;
    private Scanner input;
    private String color;
    public static final String STY_RESET    = "\u001B[0m";
    public static final String STY_BLACK    = "\u001B[30m";
    public static final String STY_RED      = "\u001B[31m";
    public static final String STY_GREEN    = "\u001B[32m";
    public static final String STY_YELLOW   = "\u001B[33m";
    public static final String STY_BLUE     = "\u001B[34m";
    public static final String STY_PURPLE   = "\u001B[35m";
    public static final String STY_CYAN     = "\u001B[36m";
    public static final String STY_WHITE    = "\u001B[37m";
    
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
            System.out.println(STY_CYAN+"0. Salir.");
            System.out.println(STY_CYAN+"h. Ayuda.");
            System.out.println("=============================================");
            System.out.println(STY_CYAN+"1. [USR] - Solicitar información.");
            System.out.println(STY_RED+"2. [SYSTEM] - Leer mensaje.");
            System.out.println(STY_RED+"3. [SYSTEM] - Procesar mensaje.");
            System.out.println(STY_RED+"4. [SYSTEM] - Responder mensaje.");
            System.out.println(STY_CYAN+"5. [USR] - Lectura de la respuesta.");
            System.out.println("=============================================");
            comando = cli.getInput().nextLine();
            
            if ("0".equals(comando)) bandera = false;
            
            if ("1".equals(comando)){
                cli.solicitar_informacion();
            }
            if ("2".equals(comando)){
                cli.leer_mensaje();
            }
        }
    }

    /**
     * Espacio en el cliente.
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
        
//        System.out.print(this.getColor()+"Correo Destino: ");
//        comando = this.getInput().nextLine();
//        this.cliSMTP.setTO(comando);
        
        /* A P A R T A D O   D E L   M E N S A J E   Y   V A L I D A C I O N*/
        System.out.print(this.getColor()+"Asunto: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setSUBJECT(comando);
        
        bandera = this.cliSMTP.smtp_validar_SUBJECT();
        if (!bandera) return bandera;
        
        System.out.print(this.getColor()+"Cuerpo del mensaje: ");
        comando = this.getInput().nextLine();
        this.cliSMTP.setDATA(comando);
        
        /* A P A R T A D O   D E L   S I S T E M A */
        this.cliSMTP.setHOST("mail.tecnoweb.org.bo");
        this.cliSMTP.setPORT(25);
        this.cliSMTP.setTO("grupo01sa@tecnoweb.org.bo");
        
        /* P R O C E S O   E N V I A R   M A I L */
        // Si hay problemas, terminar el proceso.
        bandera = this.cliSMTP.conectar();
        if (!bandera) return bandera;
        
        // Si hay problemas, terminar el proceso.
        bandera = this.cliSMTP.smtp_enviar_mensaje();
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
        
        bandera = this.cliPOP.validar_sign_IN();
        if (!bandera) return bandera;
        
        // Si hay problemas, terminar el proceso.
        bandera = this.cliPOP.pop_ultimo_mensaje();
        if (!bandera) return bandera;
        
        // G00D B0Y !!!
        bandera = this.cliPOP.desconectar();
        return bandera;
    }
}
