/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.Serializable;

/**
 *
 * @author USUARIO
 */
public class Operaciones implements Serializable{
    
    public Double sumaPares(Integer desde, Integer hasta){
    
        double resultado = 0;
        
            for(; desde<=hasta; desde++){
                if(desde%2==0)
                    resultado += desde;
                
            }
    return resultado;
    }
    
    
    public Double sumaImpares(Integer desde, Integer hasta){
    
        double resultado = 0;
        
            for(; desde<=hasta; desde++){
                if(desde%2 != 0)
                    resultado += desde;
                
            }
    return resultado;
    }
        
    public Double sumaMult5(Integer desde, Integer hasta){
    
        double resultado = 0;
        
            for(; desde <= hasta; desde++){
                if(desde%5 == 0)
                    resultado += desde;
                
            }
    return resultado;
    }
    
    public Double sumaMult3(Integer desde, Integer hasta){
    
        double resultado = 0;
        
            for(; desde <= hasta; desde++){
                if(desde%3 == 0)
                    resultado += desde;
                
            }
    return resultado;
    }
    
}
