/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.repositorios;

import com.web3.clinica.sistemaclinica.model.entities.IndicadorExame;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davic
 */
public class IndicadorExameRepository {
    private static final List<IndicadorExame> indicadoresexames;
    
    static{
         indicadoresexames = new ArrayList<>();
    }
    
     public static void inserir(IndicadorExame ie){
        IndicadorExameRepository.indicadoresexames.add(ie);
    }
     
     public static void atualizar(IndicadorExame ie){
         for(IndicadorExame ieAux: indicadoresexames){
            if(ieAux.getCodigo() == ie.getCodigo()){
               ieAux.setIndicador(ie.getIndicador());
               ieAux.setDescricao(ie.getDescricao());
               ieAux.setMaxValorReferencia(ie.getMaxValorReferencia());
               ieAux.setMinValorReferencia(ie.getMinValorReferencia());
                return;
            }
        }
     }
     
    public static IndicadorExame Ler(int codigo){
        for(IndicadorExame ieAux: indicadoresexames){
            if(ieAux.getCodigo() == codigo){
               return ieAux;
            }
        }
        return null;
    }
    
    public static void deletar(int codigo){
        
        for(IndicadorExame ieAux: indicadoresexames){
            if(ieAux.getCodigo() == codigo){
                indicadoresexames.remove(ieAux);
                
                return;
            }
            
        }
        
    }
    
    public static List<IndicadorExame> lerTudo(){
        return indicadoresexames;
    }
}
