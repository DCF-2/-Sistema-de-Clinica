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
 * @author davic
 */
public class ConsultaRepository {
    private static final List<Consulta> consultas = new ArrayList<>();
    private static int contadorCodigo = 1; // Para gerar ID automático

    
    public static void agendar(Consulta c) {
        c.setCodigo(contadorCodigo++);
        consultas.add(c);
    }

    
    public static void atualizar(Consulta cAtualizada) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getCodigo() == cAtualizada.getCodigo()) {
                
                // Atualiza os campos que podem ser mudados (se necessário)
                consultas.get(i).setDataHora(cAtualizada.getDataHora());
                consultas.get(i).setDataHoraVolta(cAtualizada.getDataHoraVolta());
                consultas.get(i).setObservacao(cAtualizada.getObservacao());

                // O mais importante: linkar o prontuário
                if (cAtualizada.getProntuario() != null) {
                    consultas.get(i).setProntuario(cAtualizada.getProntuario());
                }
                
                return;
            }
        }
    }

    
    public static List<Consulta> listarPorMedicoSemProntuario(String crmMedico) {
        List<Consulta> consultasDoMedico = new ArrayList<>();

        for (Consulta c : consultas) {
            // 1. A consulta é deste médico?
            // 2. A consulta AINDA NÃO TEM prontuário?
            if (c.getMedico().getCrm().equals(crmMedico) && c.getProntuario() == null) {
                consultasDoMedico.add(c);
            }
        }
        return consultasDoMedico;
    }

    
    public static Consulta buscarPorCodigo(int codigo) {
        for (Consulta c : consultas) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }
}
