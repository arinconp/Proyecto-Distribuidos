/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class HiloOperacion extends Thread implements Runnable{
    
    private int puerto = 1595;

    public void run(){
        Boolean escuchada = true;
        ServerSocket server = null;
        try{
            server = new ServerSocket(puerto);
            while(escuchada == true){
                Socket socket = server.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Paquete paquete = (Paquete)in.readObject();
                Class cls = Class.forName("objetos.Operaciones");
		Object obj = cls.newInstance();
                Class[] params = new Class[2];
                params[0] = Integer.class;
                params[1] = Integer.class;
		Method method = cls.getDeclaredMethod(
                        (String)paquete.getRespuesta().get(paquete.getRespuesta().size()-1), params);
                System.out.println(method.getName()+" " +paquete.getRespuesta().get(0)+" "+paquete.getRespuesta().get(1));
                Object resultado = method.invoke(obj, paquete.getRespuesta().get(0),paquete.getRespuesta().get(1));
                System.out.println("respuesta: " + resultado.toString());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(resultado);
                socket.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
