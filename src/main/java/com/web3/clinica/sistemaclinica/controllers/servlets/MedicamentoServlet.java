/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Medicamento;
import com.web3.clinica.sistemaclinica.model.negocios.MedicamentoNegocio;

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
@WebServlet(name = "MedicamentoServlet", urlPatterns = {"/Medicamento"})
public class MedicamentoServlet extends HttpServlet {

   private final MedicamentoNegocio medicamentoNegocio = new MedicamentoNegocio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("op");
        if (acao == null) {
            acao = "listar";
        }

        if (acao.equals("deletar")) {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            try {
                medicamentoNegocio.deletar(codigo);
                request.getSession().setAttribute("msg", "Medicamento deletado com sucesso!");

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao deletar: " + e.getMessage());
            }

            response.sendRedirect("Medicamento");
            return;
        }

        if (acao.equals("alterar")) {
            try {
                Medicamento m = medicamentoNegocio.buscarPorCodigo(Integer.parseInt(request.getParameter("codigo")));

                if (m == null) {
                    throw new Exception("Medicamento não encontrado para alteração.");
                }

                request.setAttribute("medicamento", m);

                getServletContext().getRequestDispatcher("/medicamento/CadastroMedicamento.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao preparar alteração: " + e.getMessage());
                response.sendRedirect("Medicamento");
            }

            return;
        }

        if (acao.equals("visualizar")) {

            try {
                Medicamento m = medicamentoNegocio.buscarPorCodigo(Integer.parseInt(request.getParameter("codigo")));

                if (m == null) {
                    throw new Exception("Medicamento não encontrado.");
                }
                request.setAttribute("medicamento", m);
                getServletContext().getRequestDispatcher("/medicamento/visualizarmedicamento.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao visualizar paciente: " + e.getMessage());
                response.sendRedirect("Medicamento");
            }

            return;
        }

        List<Medicamento> medicamentos = medicamentoNegocio.listar();

        HttpSession session = request.getSession();
        session.setAttribute("medicamentos", medicamentos);

        response.sendRedirect("Medicamento.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        int dosagem = Integer.parseInt(request.getParameter("dosagem"));
        String tipoDosagem = request.getParameter("tipoDosagem");
        String observacao = request.getParameter("observacao");

        Medicamento m = new Medicamento();
        m.setCodigo(codigo);
        m.setNome(nome);
        m.setDescricao(descricao);
        m.setDosagem(dosagem);
        m.setTipoDosagem(tipoDosagem);
        m.setObservacao(observacao);

        MedicamentoNegocio negocio = new MedicamentoNegocio();

        try {
            
            if (op != null && op.equals("alterar")) {
                negocio.atualizar(m);
                request.getSession().setAttribute("msg", "Medicamento Alterado com Sucesso!");
            } else {
                negocio.cadastrar(m); 
                request.getSession().setAttribute("msg", "Medicamento Cadastrado com Sucesso!");
            }
            response.sendRedirect("Medicamento");

        } catch (Exception e) {

            request.setAttribute("msgErro", e.getMessage());

            request.setAttribute("medicamento", m);
            
           
            getServletContext().getRequestDispatcher("/medicamento/CadastroMedicamento.jsp")
                   .forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}