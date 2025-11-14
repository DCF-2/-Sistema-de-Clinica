/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.negocios.MedicoNegocio;
import com.web3.clinica.sistemaclinica.model.repositorios.MedicoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author davic
 */
@WebServlet(name = "LoginMedicoServlet", urlPatterns = {"/LoginMedico"})
public class LoginMedicoServlet extends HttpServlet {

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
                response.sendRedirect("LoginMedico.jsp");
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
        String op = request.getParameter("op");
        HttpSession session = request.getSession();

        // --- FLUXO 1: LOGIN ---
        if ("login".equals(op)) {
            String crm = request.getParameter("login");
            String senha = request.getParameter("senha");

            Medico m = MedicoRepository.login(crm, senha);

            if (m != null) {
                // Sucesso! Coloca na sessão
                session.setAttribute("medicoLogado", m);
                // Redireciona para o Dashboard (ConsultaServlet)
                response.sendRedirect("Consultas");
            } else {
                // Falha
                session.setAttribute("msg", "CRM ou Senha incorretos!");
                response.sendRedirect("LoginMedico.jsp");
            }
            return;
        }

        // --- FLUXO 2: CADASTRO (op == null ou "cadastrar") ---
        try {
            String crm = request.getParameter("crm");
            String nome = request.getParameter("nome");
            String especialidade = request.getParameter("especialidade");
            String contato = request.getParameter("contato");
            String senha = request.getParameter("senha");
            String confirma = request.getParameter("confirm");

            // Validação de Senha
            if (senha == null || senha.trim().isEmpty() || !senha.equals(confirma)) {
                throw new Exception("As senhas não conferem ou estão vazias.");
            }

            Medico m = new Medico();
            m.setCrm(crm);
            m.setNome(nome);
            m.setEspecialidade(especialidade);
            m.setContato(contato);
            m.setSenha(senha);

            // Usa o MedicoNegocio (do Portal Admin) para cadastrar
            MedicoNegocio negocio = new MedicoNegocio();
            negocio.cadastrar(m); // O Negocio já tem as validações de duplicidade

            session.setAttribute("msg", "Médico cadastrado com sucesso! Faça o login.");
            response.sendRedirect("LoginMedico.jsp");

        } catch (Exception e) {
            session.setAttribute("msg", "Erro ao cadastrar: " + e.getMessage());
            response.sendRedirect("LoginMedico.jsp");
        }
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
    



