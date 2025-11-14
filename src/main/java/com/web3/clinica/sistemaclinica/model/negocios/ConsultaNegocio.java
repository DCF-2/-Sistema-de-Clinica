/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

/**
 *
 * @author ALUNO
 */


import com.web3.clinica.sistemaclinica.model.entities.Consulta;
import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.repositorios.ConsultaRepository;
import java.util.List;

public class ConsultaNegocio {

    /**
     * C (Create) - Agendar
     * Valida os dados antes de chamar o repositório.
     * @param c
     * @throws java.lang.Exception
     */
    public void agendar(Consulta c) throws Exception {

        // Validação 1: O médico foi identificado (via sessão)?
        if (c.getMedico() == null) {
            throw new Exception("Médico não identificado. Faça o login novamente.");
        }

        // Validação 2: O paciente foi selecionado?
        if (c.getPaciente() == null) {
            throw new Exception("É obrigatório selecionar um paciente para a consulta.");
        }

        // Validação 3: A data e hora foram preenchidas?
        if (c.getDatahora() == null || c.getDatahora().trim().isEmpty()) {
            throw new Exception("A data e hora da consulta são obrigatórias.");
        }
        
        // REQUISITO: Não validar duplicidade de horário.

        // Se tudo OK, salva.
        ConsultaRepository.agendar(c);
    }

    /**
     * U (Update) - Atualizar (Usado para adicionar o Prontuário)
     * @param c
     * @throws java.lang.Exception
     */
    public void atualizar(Consulta c) throws Exception {
        
        // Validação: A descrição do prontuário (conforme diagrama) não pode estar vazia
        if(c.getProntuario() == null || c.getProntuario().getDescricao() == null || 
           c.getProntuario().getDescricao().trim().isEmpty()){
            
            throw new Exception("A descrição do prontuário é obrigatória.");
        }

        ConsultaRepository.atualizar(c);
    }

    /**
     * R (Read) - Listar (Apenas repassa a chamada)
     * Lista apenas as consultas do médico que ainda não têm prontuário.
     * @param crmMedico
     * @return 
     */
    public List<Consulta> listarPorMedicoSemProntuario(String crmMedico) {
        return ConsultaRepository.listarPorMedicoSemProntuario(crmMedico);
    }

    /**
     * R (Read) - Buscar (Apenas repassa a chamada)
     * @param codigo
     * @return 
     */
    public Consulta buscarPorCodigo(int codigo) {
        return ConsultaRepository.buscarPorCodigo(codigo);
    }
    
    // (Não precisamos do "D" (Delete) por enquanto)
}
