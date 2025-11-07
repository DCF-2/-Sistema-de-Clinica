/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.repositorios;

import com.web3.clinica.sistemaclinica.model.entities.Medicamento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davic
 */
public class MedicamentoRepository {
    private static final List<Medicamento> medicamentos;
    
    static{
         medicamentos = new ArrayList<>();
    }
    
     public static void inserir(Medicamento mm){
        MedicamentoRepository.medicamentos.add(mm);
    }
     
     public static void atualizar(Medicamento mm){
         for(Medicamento mmAux: medicamentos){
            if(mmAux.getCodigo() == mm.getCodigo()){
               mmAux.setNome(mm.getNome());
               mmAux.setDescricao(mm.getDescricao());
               mmAux.setDosagem(mm.getDosagem());
               mmAux.setTipoDosagem(mm.getTipoDosagem());
               mmAux.setObservacao(mm.getObservacao());
                return;
            }
        }
     }
     
    public static Medicamento Ler(int codigo){
        for(Medicamento mmAux: medicamentos){
            if(mmAux.getCodigo() == codigo){
               return mmAux;
            }
        }
        return null;
    }
    
    public static void deletar(int codigo){
        
        for(Medicamento mmAux: medicamentos){
            if(mmAux.getCodigo() == codigo){
                medicamentos.remove(mmAux);
                
                return;
            }
            
        }
        
    }
    
    public static List<Medicamento> lerTudo(){
        return medicamentos;
    }
}
