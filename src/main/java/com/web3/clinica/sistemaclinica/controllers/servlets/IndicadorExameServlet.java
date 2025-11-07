/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.IndicadorExame;
import com.web3.clinica.sistemaclinica.model.negocios.IndicadorExameNegocio;
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
@WebServlet(name = "IndicadorExame", urlPatterns = {"/Indicador"})
public class IndicadorExameServlet extends HttpServlet {

   private final IndicadorExameNegocio indicadorexameNegocio = new IndicadorExameNegocio();

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
                indicadorexameNegocio.deletar(codigo);
                request.getSession().setAttribute("msg", "Indicador deletado com sucesso!");

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao deletar: " + e.getMessage());
            }

            response.sendRedirect("Indicador");
            return;
        }

        if (acao.equals("alterar")) {
            try {
                IndicadorExame ie = indicadorexameNegocio.buscarPorCodigo(Integer.parseInt((request.getParameter("codigo"))));

                if (ie == null) {
                    throw new Exception("Indicador não encontrado para alteração.");
                }

                request.setAttribute("indicadoresexames", ie);

                getServletContext().getRequestDispatcher("/indicadorexame/CadastroIndicadorExame.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao preparar alteração: " + e.getMessage());
                response.sendRedirect("Indicador");
            }

            return;
        }

        if (acao.equals("visualizar")) {

            try {
                IndicadorExame ie = indicadorexameNegocio.buscarPorCodigo(Integer.parseInt((request.getParameter("codigo"))));

                if (ie == null) {
                    throw new Exception("Indicador não encontrado.");
                }
                request.setAttribute("indicadoresexames", ie);
                getServletContext().getRequestDispatcher("/indicadorexame/visualizarIndicadorExame.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao visualizar paciente: " + e.getMessage());
                response.sendRedirect("Indicador");
            }

            return;
        }

        List<IndicadorExame> indicadoresexames = indicadorexameNegocio.listar();

        HttpSession session = request.getSession();
        session.setAttribute("indicadoresexames", indicadoresexames);

        response.sendRedirect("IndicadorExame.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String indicador = request.getParameter("indicador");
        String descricao = request.getParameter("descricao");
        double minValorReferencia = Double.parseDouble(request.getParameter("minValorReferencia")); // CORRIGIDO
        double maxValorReferencia = Double.parseDouble(request.getParameter("maxValorReferencia")); // CORRIGIDO

        IndicadorExame ie = new IndicadorExame();
        ie.setCodigo(codigo);
        ie.setIndicador(indicador);
        ie.setDescricao(descricao);
        ie.setMinValorReferencia(minValorReferencia);
        ie.setMaxValorReferencia(maxValorReferencia);

        IndicadorExameNegocio negocio = new IndicadorExameNegocio();

        try {
            
            if (op != null && op.equals("alterar")) {
                negocio.atualizar(ie);
                request.getSession().setAttribute("msg", "Indicador Alterado com Sucesso!");
            } else {
                negocio.cadastrar(ie); 
                request.getSession().setAttribute("msg", "Indicador Cadastrado com Sucesso!");
            }
            response.sendRedirect("Indicador");

        } catch (Exception e) {

            request.setAttribute("msgErro", e.getMessage());

            request.setAttribute("indicadoresexames", ie);
            
           
            getServletContext().getRequestDispatcher("/indicadorexame/CadastroIndicadorExame.jsp")
                   .forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}