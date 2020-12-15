package SERVIDOR;

import java.io.*;
import java.net.*;

/**
 *
 * @author eyver-dev
 */
public class ServidorSMTP {
    String HOST_SERVIDOR = "127.0.0.1";
    int HOST_PUERTOS = 5000;

    public ServidorSMTP() throws IOException {
        ServerSocket socSer = new ServerSocket(HOST_PUERTOS);
        for (int nro = 0; nro < 3; nro++){
            Socket socCli = socSer.accept();
            DataOutputStream salida = new DataOutputStream (socCli.getOutputStream());
            salida.writeBytes("[T]"+ nro);
            socCli.close();
        }
    }
    
}
