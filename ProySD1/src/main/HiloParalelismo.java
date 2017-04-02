/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.corba.se.spi.ior.ObjectId;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import objetos.Paquete;

/**
 *
 * @author USUARIO
 */
public class HiloParalelismo extends Thread implements Runnable {
    private Paquete paquetep;
    private String ip;
    private int puerto;
    private Object respuesta;
    private int inicio;
    private int fin;
    private String operacion;
    private List<Object> params;

    public HiloParalelismo(Paquete paquetep, String ip, int puerto,int inicio, int fin,String operacion) {
        this.paquetep = new Paquete();
        this.ip = ip;
        this.puerto = puerto;
        this.inicio = inicio;
        this.fin = fin;
        this.operacion = operacion;
        this.params = new ArrayList<>();
        this.params.add(inicio);
        this.params.add(fin);
        this.params.add(operacion);
    }

   
    public void run(){
        Socket socket = null;
        try{
            socket = new Socket(ip,puerto);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            paquetep.setRespuesta(this.params);
            out.writeObject(paquetep);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            this.respuesta = (Object)in.readObject();
            socket.close();
        }catch(ConnectException e){
            Hilo.remove(ip);
            System.out.println("Servidor con ip: " + ip +" fue removido");
            this.respuesta = null;
        }catch(Exception e){
            
        }
    }
    
    public synchronized Object getRespuesta(){
        return this.respuesta;
    }
    
}
