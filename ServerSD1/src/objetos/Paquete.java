/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class Paquete implements Serializable {

    private String ipOrigen;
    private Integer puertoOrigen;
    /**
     * 1 = Registro
     * 2 = solicitud de operaciones despachador  servidor
     * 3 = haga la operacion
     * 4 = Respuesta
     */
    private int operacion;
    private String ipDestino;
    private Integer puertoDestino;
    private List<Object> respuesta;

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public Integer getPuertoOrigen() {
        return puertoOrigen;
    }

    public void setPuertoOrigen(Integer puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public Integer getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(Integer puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public List<Object> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<Object> respuesta) {
        this.respuesta = respuesta;
    }    
}
