/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.repositorios;

import com.web3.clinica.sistemaclinica.model.entities.Paciente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davic
 */
public class PacienteRepository {
    private static final List<Paciente> pacientes;
    
    static{
         pacientes = new ArrayList<>();
    }
    
     public static void inserir(Paciente p){
        PacienteRepository.pacientes.add(p);
    }
     
     public static void atualizar(Paciente p){
         for(Paciente pAux: pacientes){
            if(pAux.getCpf().equals(p.getCpf())){
                pAux.setEndereço(p.getEndereço());
                pAux.setNome(p.getNome());
                pAux.setPlanoSaude(p.getPlanoSaude());
                
                return;
            }
        }
     }
     
    public static Paciente Ler(String cpf){
        for(Paciente pAux: pacientes){
            if(pAux.getCpf().equals(cpf)){
               return pAux;
            }
        }
        return null;
    }
    
    public static void deletar(String cpf){
        
        for(Paciente pAux: pacientes){
            if(pAux.getCpf().equals(cpf)){
                pacientes.remove(pAux);
                
                return;
            }
            
        }
        
    }
    
    public static List<Paciente> lerTudo(){
        return pacientes;
    }
    
}
