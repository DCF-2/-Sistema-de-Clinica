/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.repositorios;

import com.web3.clinica.sistemaclinica.model.entities.Consulta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALUNO
 */
public class ConsultaRepository {
    
    private static final List<Consulta> consultas = new ArrayList<>();
    private static int contadorCodigo = 1; // Para gerar ID automático

    /**
     * INSERIR (O "C" do CRUD)
     * @param c
     */
    public static void agendar(Consulta c) {
        c.setCodigo(contadorCodigo++);
        consultas.add(c);
    }

    /**
     * ATUALIZAR (O "U" do CRUD)
     * Usado principalmente para adicionar o Prontuário
     * @param cAtualizada
     */
    public static void atualizar(Consulta cAtualizada){
        for(int i = 0; i < consultas.size(); i++){
            if(consultas.get(i).getCodigo() == cAtualizada.getCodigo()){
                
                // Atualiza os campos que podem ser mudados
                consultas.get(i).setDatahora(cAtualizada.getDatahora());
                consultas.get(i).setDatahoravolta(cAtualizada.getDatahoravolta());
                consultas.get(i).setObservacao(cAtualizada.getObservacao());
                
                // O mais importante: linkar o prontuário
                if(cAtualizada.getProntuario() != null){
                     consultas.get(i).setProntuario(cAtualizada.getProntuario());
                }
                
                return;
            }
        }
    }

    /**
     * LER - LISTAR POR MÉDICO (O "R" do CRUD - Leitura)
     * Requisito: "ver, em sua página inicial, todas as consultas marcadas para ele
     * e que ainda não foram realizadas (não possuem prontuários)."
     * @param crmMedico
     * @return 
     */
    public static List<Consulta> listarPorMedicoSemProntuario(String crmMedico) {
        List<Consulta> consultasDoMedico = new ArrayList<>();
        
        for(Consulta c : consultas){
            // 1. A consulta é deste médico?
            // 2. A consulta AINDA NÃO TEM prontuário?
            if(c.getMedico().getCrm().equals(crmMedico) && c.getProntuario() == null){
                consultasDoMedico.add(c);
            }
        }
        return consultasDoMedico;
    }
    
    /**
     * LER - BUSCAR POR ID (O "R" do CRUD - Leitura)
     * @param codigo
     * @return 
     */
    public static Consulta buscarPorCodigo(int codigo){
        for(Consulta c : consultas){
            if(c.getCodigo() == codigo){
                return c;
            }
        }
        return null;
    }
    
    // O 'DELETAR' (CRUD) não é necessário para os requisitos atuais.
}