/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

import com.web3.clinica.sistemaclinica.model.entities.Consulta;
import com.web3.clinica.sistemaclinica.model.repositorios.ConsultaRepository;
import java.util.List;

/**
 *
 * @author davic
 */
public class ConsultaNegocio {
   
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
        if (c.getDataHora() == null || c.getDataHora().trim().isEmpty()) {
            throw new Exception("A data e hora da consulta são obrigatórias.");
        }
        
        // REQUISITO: Não validar duplicidade de horário.

        // Se tudo OK, salva.
        ConsultaRepository.agendar(c);
    }

    
    public void atualizar(Consulta c) throws Exception {
        
        // Validação: A descrição do prontuário (conforme diagrama) não pode estar vazia
        if(c.getProntuario() == null || c.getProntuario().getDescricao() == null || 
           c.getProntuario().getDescricao().trim().isEmpty()){
            
            throw new Exception("A descrição do prontuário é obrigatória.");
        }

        ConsultaRepository.atualizar(c);
    }

   
    public List<Consulta> listarPorMedicoSemProntuario(String crmMedico) {
        return ConsultaRepository.listarPorMedicoSemProntuario(crmMedico);
    }

    
    public Consulta buscarPorCodigo(int codigo) {
        return ConsultaRepository.buscarPorCodigo(codigo);
    }
}
