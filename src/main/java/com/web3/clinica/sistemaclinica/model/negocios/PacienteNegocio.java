/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

import com.web3.clinica.sistemaclinica.model.entities.Paciente;
import com.web3.clinica.sistemaclinica.model.repositorios.PacienteRepository;
import java.util.List;

/**
 *
 * @author davic
 */
public class PacienteNegocio {

    public void cadastrar(Paciente p) throws Exception {
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new Exception("Nome do paciente é obrigatório.");
        }

        if (p.getCpf() == null || p.getCpf().trim().isEmpty()) {
            throw new Exception("CPF é obrigatório.");
        }

        // Verifica se CPF já existe
        Paciente existente = PacienteRepository.Ler(p.getCpf());
        if (existente != null) {
            throw new Exception("Já existe um paciente com esse CPF.");
        }

        // Tudo certo → chama o repositório
        PacienteRepository.inserir(p);
    }

    public void atualizar(Paciente p) throws Exception {
        if (PacienteRepository.Ler(p.getCpf()) == null) {
            throw new Exception("Paciente não encontrado para atualização.");
        }
        PacienteRepository.atualizar(p);
    }

    public void deletar(String cpf) throws Exception {
        if (PacienteRepository.Ler(cpf) == null) {
            throw new Exception("Paciente não encontrado para exclusão.");
        }
        PacienteRepository.deletar(cpf);
    }

    public List<Paciente> listar() {
        return PacienteRepository.lerTudo();
    }

    public Paciente buscarPorCpf(String cpf) {
        return PacienteRepository.Ler(cpf);
    }
}
