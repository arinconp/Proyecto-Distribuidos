/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsd1;

import objetos.Paquete;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class ClientSD1 {
    
    
    private static Socket socket;
    private static String ip;
    private static Integer puerto;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        Integer opc = 0;
        String servicio;
        
        while(opc != 2){
        System.out.println("1.Servicio");
        System.out.println("2.Salir");
        System.out.println("Seleccione una opcion: ");
        opc = sc.nextInt();
            if(opc ==1){
                System.out.println("Buscar servicio: ");
                servicio = sc.next();
                List<Object> respuestas = connect(servicio);
                List<Object> params = new ArrayList<>();
                if ((Boolean)respuestas.get(respuestas.size()-1) == true){
                    System.out.println("Servicio encontrado, estos son los parametros esperados");
                    int tam = respuestas.size()-2;
                    for(int i = 0;i < tam;i++){
                        System.out.println((String)respuestas.get(i)+", digite valor: ");
                        params.add(sc.next());
                    }
                    System.out.println("El retorno de la función es de tipo " + (String)respuestas.get(tam));
                    Object respuesta = procesarServicio(params);
                    System.out.println("La respuesta es: " + respuesta.toString());
                }else
                    System.out.println("No se encontró el servicio");
            }
        }
    }
    
    public static Object procesarServicio(List<Object> params){
        Paquete paquete = null;
        try{
            paquete = new Paquete();
            paquete.setIpOrigen(ip);
            paquete.setPuertoOrigen(puerto);
            paquete.setRespuesta(params);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(paquete);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object res = in.readObject();
            return res;
        }catch(Exception e){
        }
        return null;
    }
    
    
    public static List<Object> connect (String servicio){
        List<Object> res = null;
       try{
           Paquete paquete = new Paquete();
           ip = Inet4Address.getLocalHost().getHostName();
           paquete.setIpOrigen(ip);
           puerto = 1595;
           paquete.setPuertoOrigen(puerto);
           paquete.setOperacion(2);
           List<Object> params = new ArrayList<>();
           params.add(servicio);
           paquete.setRespuesta(params);
           socket = new Socket("192.168.0.3",1595);
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           out.writeObject(paquete);
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
           paquete = (Paquete)in.readObject();
           res = paquete.getRespuesta();
        } catch (Exception ex) {
            //Logger.getLogger(ServerSD1.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return res;

    }
    
}
