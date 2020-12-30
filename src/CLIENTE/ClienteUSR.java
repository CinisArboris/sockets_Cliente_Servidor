package CLIENTE;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author eyver-dev
 */
public class ClienteUSR {
    ClienteBD clibd = new ClienteBD();
    ClientePOP clipop = new ClientePOP();
    ClienteSMTP cliSMTP = new ClienteSMTP();

    public ClienteUSR() {
    }
    public ClienteBD getClibd() {
        return clibd;
    }
    public void setClibd(ClienteBD clibd) {
        this.clibd = clibd;
    }
    public ClientePOP getClipop() {
        return clipop;
    }
    public void setClipop(ClientePOP clipop) {
        this.clipop = clipop;
    }
    public ClienteSMTP getCliSMTP() {
        return cliSMTP;
    }
    public void setCliSMTP(ClienteSMTP cliSMTP) {
        this.cliSMTP = cliSMTP;
    }
    
    
    public static void main(String[] args) throws IOException {
        ClienteUSR cli = new ClienteUSR();
        Scanner input = new Scanner(System.in);
        String comando;
        while (true){
            Runtime.getRuntime().exec("clear");
            System.out.println(""
                    + "1 = Enviar mensaje.#"
                    + "2 = Recibir mensaje.#"
                    + "3 = Consultar BaseDeDatos.#"
                    + "0 = Salir.#");
            comando = input.nextLine();
            cli.validarSIN(comando);
            cli.validarOP(comando);
        }
    }

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
}
