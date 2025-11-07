package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Paciente;
import com.web3.clinica.sistemaclinica.model.negocios.PacienteNegocio; 

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(name = "PacienteServlet", urlPatterns = {"/Paciente"})
public class PacienteServlet extends HttpServlet {

    private final PacienteNegocio pacienteNegocio = new PacienteNegocio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("op");
        if (acao == null) {
            acao = "listar";
        }

        if (acao.equals("deletar")) {
            String cpf = request.getParameter("cpf");
            try {
                pacienteNegocio.deletar(cpf);
                request.getSession().setAttribute("msg", "Paciente deletado com sucesso!");

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao deletar: " + e.getMessage());
            }

            response.sendRedirect("Paciente");
            return;
        }

        if (acao.equals("alterar")) {
            try {
                Paciente p = pacienteNegocio.buscarPorCpf(request.getParameter("cpf"));

                if (p == null) {
                    throw new Exception("Paciente não encontrado para alteração.");
                }

                request.setAttribute("paciente", p);

                getServletContext().getRequestDispatcher("/paciente/CadastroPaciente.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao preparar alteração: " + e.getMessage());
                response.sendRedirect("Paciente");
            }

            return;
        }

        if (acao.equals("visualizar")) {

            try {
                Paciente p = pacienteNegocio.buscarPorCpf(request.getParameter("cpf"));

                if (p == null) {
                    throw new Exception("Paciente não encontrado.");
                }
                request.setAttribute("paciente", p);
                getServletContext().getRequestDispatcher("/paciente/visualizarPaciente.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao visualizar paciente: " + e.getMessage());
                response.sendRedirect("Paciente");
            }

            return;
        }

        List<Paciente> pacientes = pacienteNegocio.listar();

        HttpSession session = request.getSession();
        session.setAttribute("pacientes", pacientes);

        response.sendRedirect("Paciente.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");

        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String contato = request.getParameter("contato");
        String plano = request.getParameter("planoSaude");

        Paciente p = new Paciente();
        p.setCpf(cpf);
        p.setNome(nome);
        p.setEndereço(endereco);
        p.setContato(contato);
        p.setPlanoSaude(plano);

        PacienteNegocio negocio = new PacienteNegocio();

        try {
            
            if (op != null && op.equals("alterar")) {
                negocio.atualizar(p);
                request.getSession().setAttribute("msg", "Paciente Alterado com Sucesso!");
            } else {
                negocio.cadastrar(p); 
                request.getSession().setAttribute("msg", "Paciente Cadastrado com Sucesso!");
            }
            response.sendRedirect("Paciente");

        } catch (Exception e) {

            request.setAttribute("msgErro", e.getMessage());

            request.setAttribute("paciente", p);
            
           
            getServletContext().getRequestDispatcher("/paciente/CadastroPaciente.jsp")
                   .forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
