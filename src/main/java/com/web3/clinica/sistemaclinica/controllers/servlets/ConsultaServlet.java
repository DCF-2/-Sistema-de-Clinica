/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Consulta;
import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.entities.Paciente;
import com.web3.clinica.sistemaclinica.model.negocios.ConsultaNegocio;
import com.web3.clinica.sistemaclinica.model.repositorios.PacienteRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author ALUNO
 */
@WebServlet(name = "ConsultaServlet", urlPatterns = {"/Consultas"})
public class ConsultaServlet extends HttpServlet {
    private final ConsultaNegocio consultaNegocio = new ConsultaNegocio();
    
   
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. VERIFICAR SESSÃO
        HttpSession session = request.getSession();
        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        if (medicoLogado == null) {
            session.setAttribute("msg", "Você precisa estar logado para ver suas consultas.");
            response.sendRedirect("LoginMedico.jsp");
            return;
        }

        // 2. BUSCAR DADOS PARA O DASHBOARD (indexMedico.jsp)
        try {
            // A. Lista de consultas PENDENTES (só deste médico) - Para a Tabela
            List<Consulta> listaConsultas = consultaNegocio.listarPorMedicoSemProntuario(medicoLogado.getCrm());
            request.setAttribute("listaConsultas", listaConsultas);

            // B. Lista de TODOS os pacientes - Para o Modal de Agendamento
            List<Paciente> listaPacientes = PacienteRepository.lerTudo();
            request.setAttribute("listaPacientes", listaPacientes);

            // 3. ENVIAR PARA O JSP
            request.getRequestDispatcher("/WEB-INF/jsps/medico/ConsultaMedico.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            session.setAttribute("msg", "Erro ao carregar dados: " + e.getMessage());
            response.sendRedirect("LoginMedico.jsp"); // Manda pro login se der erro grave
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        // 1. VERIFICAR SESSÃO
        if (medicoLogado == null) {
            session.setAttribute("msg", "Sua sessão expirou. Faça o login novamente.");
            response.sendRedirect("LoginMedico.jsp");
            return;
        }
        
        String op = request.getParameter("op");
        
        // 2. LÓGICA PARA AGENDAR (vem do modal)
        if ("agendar".equals(op)) {
            try {
                // Pegar dados do formulário
                String cpfPaciente = request.getParameter("cpf");
                String dataHora = request.getParameter("datahora");
                String dataHoraVolta = request.getParameter("datahoravolta");
                String observacao = request.getParameter("observacao");

                // Buscar o objeto Paciente
                Paciente paciente = PacienteRepository.Ler(cpfPaciente);

                // Montar o objeto Consulta
                Consulta novaConsulta = new Consulta();
                novaConsulta.setMedico(medicoLogado);
                novaConsulta.setPaciente(paciente);
                novaConsulta.setDatahora(dataHora);
                novaConsulta.setDatahoravolta(dataHoraVolta);
                novaConsulta.setObservacao(observacao);

                // Chamar a camada de negócio para validar e salvar
                consultaNegocio.agendar(novaConsulta);
                
                session.setAttribute("msg", "Consulta agendada com sucesso!");

            } catch (Exception e) {
                // Se o ConsultaNegocio der 'throw', captura aqui
                session.setAttribute("msg", "Erro ao agendar: " + e.getMessage());
            }
        }
        
        // 3. LÓGICA FUTURA (FASE 7)
        // else if("salvarProntuario".equals(op)){
        //     ...
        // }

        // No fim, sempre redireciona para o doGet
        response.sendRedirect("Consultas");
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
