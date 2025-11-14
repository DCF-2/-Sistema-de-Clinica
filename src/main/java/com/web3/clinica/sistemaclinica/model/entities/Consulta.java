/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.web3.clinica.sistemaclinica.model.entities;

/**
 *
 * @author davic
 */
public class Consulta {
    private int codigo;
    private String dataHora;
    private String dataHoraVolta;
    private String observacao;
    
    // Relacionamentos (Agregação)
    private Medico medico;
    private Paciente paciente;
    
    // Relacionamento (Composição)
    private Prontuario prontuario; 

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDataHoraVolta() {
        return dataHoraVolta;
    }

    public void setDataHoraVolta(String dataHoraVolta) {
        this.dataHoraVolta = dataHoraVolta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }
}
