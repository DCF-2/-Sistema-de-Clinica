/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.negocios.MedicoNegocio;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author davic
 */
@WebServlet(name = "MedicoServlet", urlPatterns = {"/Medico"})
public class MedicoServlet extends HttpServlet {

    
    private final MedicoNegocio medicoNegocio = new MedicoNegocio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("op");
        if (acao == null) {
            acao = "listar";
        }

        if (acao.equals("deletar")) {
            String crm = request.getParameter("crm");
            try {
                medicoNegocio.deletar(crm);
                request.getSession().setAttribute("msg", "Medico deletado com sucesso!");

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao deletar: " + e.getMessage());
            }

            response.sendRedirect("Medico");
            return;
        }

        if (acao.equals("alterar")) {
            try {
                Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));

                if (m == null) {
                    throw new Exception("medico não encontrado para alteração.");
                }

                request.setAttribute("medico", m);

                getServletContext().getRequestDispatcher("/medico/CadastroMedico.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao preparar alteração: " + e.getMessage());
                response.sendRedirect("Medico");
            }

            return;
        }

        if (acao.equals("visualizar")) {

            try {
                Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));

                if (m == null) {
                    throw new Exception("Medico não encontrado.");
                }
                request.setAttribute("medico", m);
                getServletContext().getRequestDispatcher("/medico/visualizarMedico.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao visualizar paciente: " + e.getMessage());
                response.sendRedirect("Medico");
            }

            return;
        }

        List<Medico> medicos = medicoNegocio.listar();

        HttpSession session = request.getSession();
        session.setAttribute("medicos", medicos);

        response.sendRedirect("Medico.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");

        String crm = request.getParameter("crm");
        String nome = request.getParameter("nome");
        String especialidade = request.getParameter("especialidade");
        String contato = request.getParameter("contato");
        

        Medico m = new Medico();
        m.setCrm(crm);
        m.setNome(nome);
        m.setEspecialidade(especialidade);
        m.setContato(contato);
        
        

        MedicoNegocio negocio = new MedicoNegocio();

        try {
            
            if (op != null && op.equals("alterar")) {
                negocio.atualizar(m);
                request.getSession().setAttribute("msg", "Medico Alterado com Sucesso!");
            } else {
                negocio.cadastrar(m); 
                request.getSession().setAttribute("msg", "Medico Cadastrado com Sucesso!");
            }
            response.sendRedirect("Medico");

        } catch (Exception e) {

            request.setAttribute("msgErro", e.getMessage());

            request.setAttribute("medico", m);
            
           
            getServletContext().getRequestDispatcher("/medico/CadastroMedico.jsp")
                   .forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
