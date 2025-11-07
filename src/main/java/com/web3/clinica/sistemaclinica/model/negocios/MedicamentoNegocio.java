/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

import com.web3.clinica.sistemaclinica.model.entities.Medicamento;
import com.web3.clinica.sistemaclinica.model.repositorios.MedicamentoRepository;
import java.util.List;

/**
 *
 * @author davic
 */
public class MedicamentoNegocio {

    public void cadastrar(Medicamento m) throws Exception {

        if (m.getCodigo() <= 0) {
            throw new Exception("O Código do medicamento é inválido.");
        }
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new Exception("O Nome do medicamento é obrigatório.");
        }

        Medicamento existente = MedicamentoRepository.Ler(m.getCodigo());
        if (existente != null) {
            throw new Exception("Já existe um medicamento com este Código.");
        }

        MedicamentoRepository.inserir(m);
    }

    public void atualizar(Medicamento m) throws Exception {

        if (MedicamentoRepository.Ler(m.getCodigo()) == null) {
            throw new Exception("Medicamento não encontrado. Impossível atualizar.");
        }

        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new Exception("O Nome do medicamento não pode ficar em branco.");
        }

        MedicamentoRepository.atualizar(m);
    }

    public void deletar(int codigo) throws Exception {

        if (MedicamentoRepository.Ler(codigo) == null) {
            throw new Exception("Medicamento não encontrado. Impossível deletar.");
        }

        MedicamentoRepository.deletar(codigo);
    }

    public List<Medicamento> listar() {
        return MedicamentoRepository.lerTudo();
    }

    public Medicamento buscarPorCodigo(int codigo) {
        return MedicamentoRepository.Ler(codigo);
    }
}
