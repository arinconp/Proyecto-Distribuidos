/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import objetos.Paquete;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class Hilo extends Thread implements Runnable {
    
    private static Map<String,Integer> directorio;
    private final int port = 1595;
    
    public Hilo(){
        directorio = new HashMap<>();
    }
    
    public void run(){

        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            while(true){
               Socket cliente = server.accept();
               HiloParticular hp = new HiloParticular(cliente);
               hp.start();
            }
        }  catch (Exception ex) {
               ex.printStackTrace();
           }
    }

    public static void agregarServidor(String ip, Integer puerto){
        directorio.put(ip,puerto);
        System.out.println("El servidor: "+ip+" con puerto: "+puerto);
    }
    
    public static synchronized Map<String,Integer> getServidores(){
        return directorio;
    }
    
    public static void remove(String key){
        directorio.remove(key);
    }
    
}
