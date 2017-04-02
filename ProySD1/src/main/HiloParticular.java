/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import objetos.Paquete;

/**
 *
 * @author USUARIO
 */
public class HiloParticular extends Thread implements Runnable {
    
    private Socket cliente;
    
    public HiloParticular(Socket cliente){
        this.cliente = cliente;
    }
    
    public void run(){
        try{
            ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
            Paquete paquete = (Paquete)input.readObject();
            switch(paquete.getOperacion()){
                case 1:
                    registrar(paquete);
                    break;
                case 2:
                    buscarOperacion(paquete);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                cliente.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void registrar(Paquete paquete) throws Exception{
        String ipreg = paquete.getIpOrigen();
        int pureg = paquete.getPuertoOrigen();
        Hilo.agregarServidor(ipreg, pureg);
    }
    
    public void buscarOperacion(Paquete paquete) throws Exception{
        List<Object> respuesta = new ArrayList<Object>();
        Method metodos [];
        Class parametros [];
        Class clase;
        double total = 0;
        Boolean flag = false;
        String servicioaBuscar = (String)paquete.getRespuesta().get(0);
        try{
            clase = Class.forName("objetos.Operaciones");
            metodos = clase.getDeclaredMethods();
            
            for(int i = 0; i< metodos.length; i++){
                if(servicioaBuscar.equals(metodos[i].getName())){
                    flag = true;
                    parametros = metodos[i].getParameterTypes();
                        for(int j = 0; j< parametros.length; j++){
                            respuesta.add(parametros[j].getName());
                        }
                    respuesta.add(metodos[i].getReturnType().getName());
                }
            }
            ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
            respuesta.add(flag);
            paquete.setRespuesta(respuesta);
            out.writeObject(paquete);
            if(flag==true){
                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                paquete = (Paquete)in.readObject();
                paquete.getRespuesta().add(servicioaBuscar);
                // llamar metodo para distribuir carga
                total = distribuir(paquete, servicioaBuscar);
                ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                output.writeObject(total);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public double distribuir(Paquete paquete, String servicioaBuscar) throws Exception{
        Map<String,Integer> directorio = Hilo.getServidores();
        List<HiloParalelismo> hilosp = new ArrayList<>();
        Set<String> set = directorio.keySet();
        int totalServidores = directorio.size();
        int inicio = 0;
        int fin = 0;
        if(paquete.getRespuesta().get(0) instanceof String){
            inicio = Integer.parseInt((String)paquete.getRespuesta().get(0));
            fin = Integer.parseInt((String)paquete.getRespuesta().get(1));
        }else{
            inicio = (Integer)paquete.getRespuesta().get(0);
            fin = (Integer)paquete.getRespuesta().get(1);
        }
        int input = fin - inicio;
        int inputDivido = 0;
        if(totalServidores != 0)
            inputDivido = input / totalServidores;
        fin = inicio + inputDivido;
        for (String string : set) {
            HiloParalelismo hpl = new HiloParalelismo(paquete, string,directorio.get(string),
                    new Integer(inicio),new Integer(fin),servicioaBuscar);
            hpl.start();
            hilosp.add(hpl);
            inicio = fin + 1;
            fin = inicio + inputDivido;
        }
        for (HiloParalelismo hpf : hilosp) {
            hpf.join();
        }
        double total = 0;
        boolean nulo = false;
        for (HiloParalelismo hpf : hilosp) {
            if(hpf.getRespuesta() == null){
                nulo = true;
                break;
            }else
                total += (double)hpf.getRespuesta();
        }
        if(nulo)
            total = distribuir(paquete, servicioaBuscar);
        return total;
    }
    
}
