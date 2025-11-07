/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.repositorios.MedicoRepository;
import java.util.List;

/**
 *
 * @author davic
 */
public class MedicoNegocio {

    public void cadastrar(Medico m) throws Exception {

        if (m.getCrm() == null || m.getCrm().trim().isEmpty()) {
            throw new Exception("O CRM do médico é obrigatório.");
        }
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new Exception("O Nome do médico é obrigatório.");
        }
        if (m.getEspecialidade() == null || m.getEspecialidade().trim().isEmpty()) {
            throw new Exception("A Especialidade do médico é obrigatória.");
        }

        Medico existente = MedicoRepository.Ler(m.getCrm());
        if (existente != null) {
            throw new Exception("Já existe um médico cadastrado com este CRM.");
        }

        MedicoRepository.inserir(m);
    }

    public void atualizar(Medico m) throws Exception {

        if (MedicoRepository.Ler(m.getCrm()) == null) {
            throw new Exception("Médico não encontrado. Impossível atualizar.");
        }

        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new Exception("O Nome não pode ficar em branco.");
        }
        if (m.getEspecialidade() == null || m.getEspecialidade().trim().isEmpty()) {
            throw new Exception("A Especialidade não pode ficar em branco.");
        }

        MedicoRepository.atualizar(m);
    }

    public void deletar(String crm) throws Exception {

        if (MedicoRepository.Ler(crm) == null) {
            throw new Exception("Médico não encontrado. Impossível deletar.");
        }

        MedicoRepository.deletar(crm);
    }

     public List<Medico> listar() {
        return MedicoRepository.lerTudo();
    }

    public Medico buscarPorCrm(String crm) {
        return MedicoRepository.Ler(crm);
    }
}
