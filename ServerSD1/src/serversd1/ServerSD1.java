/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversd1;

import objetos.Paquete;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.HiloOperacion;

/**
 *
 * @author USUARIO
 */
public class ServerSD1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        registro();
        HiloOperacion operacion = new HiloOperacion();
        operacion.start();
        
    }
    
    public static void registro(){
    
        Paquete ss = new Paquete();
        
        try{
            ss.setIpOrigen(Inet4Address.getLocalHost().getHostAddress());
            ss.setPuertoOrigen(1595);
            ss.setOperacion(1);
            Socket socket = new Socket("192.168.0.3",1595);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(ss);
            socket.close();
        
        } catch (Exception ex) {
            //Logger.getLogger(ServerSD1.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    
    }
    
}
