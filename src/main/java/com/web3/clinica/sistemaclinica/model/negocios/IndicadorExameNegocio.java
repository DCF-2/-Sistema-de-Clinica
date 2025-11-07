/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.negocios;

import com.web3.clinica.sistemaclinica.model.entities.IndicadorExame;
import com.web3.clinica.sistemaclinica.model.repositorios.IndicadorExameRepository;
import java.util.List;

/**
 *
 * @author davic
 */
public class IndicadorExameNegocio {

    public void cadastrar(IndicadorExame ie) throws Exception {

        if (ie.getCodigo() <= 0) {
            throw new Exception("O Código do indicador é inválido.");
        }
        if (ie.getIndicador() == null || ie.getIndicador().trim().isEmpty()) {
            throw new Exception("O nome do Indicador (ex: Glicose) é obrigatório.");
        }

        if (ie.getMinValorReferencia() >= ie.getMaxValorReferencia()) {
            throw new Exception("O Valor Mínimo de Referência deve ser MENOR que o Valor Máximo.");
        }

        IndicadorExame existente = IndicadorExameRepository.Ler(ie.getCodigo());
        if (existente != null) {
            throw new Exception("Já existe um Indicador de Exame com este Código.");
        }

        IndicadorExameRepository.inserir(ie);
    }

    public void atualizar(IndicadorExame ie) throws Exception {

        if (IndicadorExameRepository.Ler(ie.getCodigo()) == null) {
            throw new Exception("Indicador de Exame não encontrado. Impossível atualizar.");
        }

        if (ie.getIndicador() == null || ie.getIndicador().trim().isEmpty()) {
            throw new Exception("O nome do Indicador não pode ficar em branco.");
        }

        if (ie.getMinValorReferencia() >= ie.getMaxValorReferencia()) {
            throw new Exception("O Valor Mínimo de Referência deve ser MENOR que o Valor Máximo.");
        }

        IndicadorExameRepository.atualizar(ie);
    }

    public void deletar(int codigo) throws Exception {

        if (IndicadorExameRepository.Ler(codigo) == null) {
            throw new Exception("Indicador de Exame não encontrado. Impossível deletar.");
        }

        IndicadorExameRepository.deletar(codigo);
    }

    public List<IndicadorExame> listar() {
        return IndicadorExameRepository.lerTudo();
    }

    public IndicadorExame buscarPorCodigo(int codigo) {
        return IndicadorExameRepository.Ler(codigo);
    }
}
