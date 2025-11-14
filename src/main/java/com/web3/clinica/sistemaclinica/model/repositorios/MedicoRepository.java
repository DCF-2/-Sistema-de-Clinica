/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.repositorios;


import com.web3.clinica.sistemaclinica.model.entities.Medico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davic
 */
public class MedicoRepository {
    private static final List<Medico> medicos;
    
    static{
         medicos = new ArrayList<>();
    }
    
     public static Medico login(String crmLogin, String senha){ // Mudei o nome da variavel para ficar claro
    for(Medico m: medicos){
        // Comparar CRM em vez de Nome garante unicidade
        if(m.getCrm().equals(crmLogin) && m.getSenha().equals(senha)){
            return m;
        }
    }
    return null;
}
    
    public static void inserir(Medico m){
        MedicoRepository.medicos.add(m);
    }
    
    public static void atualizar(Medico m){
         for(Medico mAux: medicos){
            if(mAux.getCrm().equals(m.getCrm())){
                mAux.setNome(m.getNome());
                mAux.setEspecialidade(m.getEspecialidade());
                mAux.setContato(m.getContato());
                
                return;
            }
        }
     }
    
    public static Medico Ler(String crm){
        for(Medico mAux: medicos){
            if(mAux.getCrm().equals(crm)){
               return mAux;
            }
        }
        return null;
    }
    
    public static void deletar(String crm){
        
        for(Medico mAux: medicos){
            if(mAux.getCrm().equals(crm)){
                medicos.remove(mAux);
               
                return;
            }
            
        }
        
    }
     public List<Medico> listar() {
        return MedicoRepository.lerTudo();
    }
    
    public static List<Medico> lerTudo(){
        return medicos;
    }
    
     public static void alterarSenha(String crm, String senha){
        
        for(Medico mAux: medicos){
            if(mAux.getCrm().equals(crm)){
                mAux.setSenha(senha);
                return;
            }
        }
        
    }
}
