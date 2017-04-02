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
public class Operacion implements Serializable {
    
    private String nomOp;
    private List<Object> parametros;

    public String getNomOp() {
        return nomOp;
    }

    public void setNomOp(String nomOp) {
        this.nomOp = nomOp;
    }

    public List<Object> getParametros() {
        return parametros;
    }

    public void setParametros(List<Object> parametros) {
        this.parametros = parametros;
    }
    
    
    
}
