/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web3.clinica.sistemaclinica.controllers.servlets;

import com.web3.clinica.sistemaclinica.model.entities.Medico;
import com.web3.clinica.sistemaclinica.model.negocios.MedicoNegocio;
import com.web3.clinica.sistemaclinica.model.repositorios.MedicoRepository;
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

    String op = request.getParameter("op");

    // 1. AÇÃO PADRÃO É LISTAR (PÚBLICO)
    if (op == null) {
        op = "listar";
    }

    switch (op) {

        case "logout":
            request.getSession().removeAttribute("medicoLogado");
            request.getSession().invalidate();
            response.sendRedirect("LoginMedico.jsp");
            break;

        case "perfil":
            // 2. VERIFICA SESSÃO APENAS EM AÇÕES PRIVADAS
            if (request.getSession().getAttribute("medicoLogado") == null) {
                response.sendRedirect("LoginMedico.jsp");
                return;
            }
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/jsps/medico/perfilMedico.jsp")
                    .forward(request, response);
            break;

        case "deletar":
            String crm = request.getParameter("crm");
            try {
                medicoNegocio.deletar(crm);
                request.getSession().setAttribute("msg", "Medico deletado com sucesso!");
            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro ao deletar: " + e.getMessage());
            }
            response.sendRedirect("Medico?op=listar"); // Volta para a lista
            break;

        case "alterar":
            try {
                Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));
                if (m == null) throw new Exception("Médico não encontrado.");
                request.setAttribute("medico", m);
                getServletContext().getRequestDispatcher("/WEB-INF/jsps/medico/CadastroMedico.jsp")
                        .forward(request, response);
            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro: " + e.getMessage());
                response.sendRedirect("Medico?op=listar");
            }
            break;

        case "visualizar":
             try {
                Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));
                if (m == null) throw new Exception("Médico não encontrado.");
                request.setAttribute("medico", m);
                getServletContext().getRequestDispatcher("/WEB-INF/jsps/medico/visualizarMedico.jsp")
                        .forward(request, response);
            } catch (Exception e) {
                request.getSession().setAttribute("msg", "Erro: " + e.getMessage());
                response.sendRedirect("Medico?op=listar");
            }
            break;

        // 3. "HOME" NÃO EXISTE MAIS AQUI
        case "listar":
        default:
            // 4. AÇÃO PADRÃO: MOSTRA A LISTA PÚBLICA (CRUD)
            List<Medico> medicos = medicoNegocio.listar();
            request.setAttribute("medicos", medicos);
            request.getRequestDispatcher("/WEB-INF/jsps/medico/Medico.jsp").forward(request, response);
            break;
    }
}

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String op = request.getParameter("op");

    // --- Bloco de Alterar Senha (Seu código original, corrigido) ---
    if (op != null && op.equals("alterarSenha")) {

        Medico m = (Medico) request.getSession().getAttribute("medicoLogado");

        // CORREÇÃO 3 (comparar senha, não crm)
        if (m.getSenha() == null || !m.getSenha().equals(request.getParameter("atual"))) {
            request.setAttribute("msg", "Você errou a senha atual!");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/jsps/medico/indexMedico.jsp")
                    .forward(request, response);
            return; // Adicionado 'return'
        }
        if (!request.getParameter("nova").equals(request.getParameter("confirm"))) {
            request.setAttribute("msg", "A nova senha e a confirmação não batem!");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/jsps/medico/indexMedico.jsp")
                    .forward(request, response);
            return; // Adicionado 'return'
        }

        MedicoRepository.alterarSenha(m.getCrm(), request.getParameter("nova"));

        request.setAttribute("msg", "A senha foi alterada com sucesso!");
        request.setAttribute("svd", "ok");
        
        // CORREÇÃO 4 (caminho do JSP)
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsps/medico/indexMedico.jsp") 
                .forward(request, response);

        return;
    }

    // --- Bloco de Login (Seu código original, corrigido) ---
    if (op != null && op.equals("login")) {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Medico m = MedicoRepository.login(login, senha);

        if (m == null) {
            request.getSession().setAttribute("msg", "Erro ao logar! O CRM e/ou a Senha estão incorretos!");
            response.sendRedirect("LoginMedico.jsp");
            return;
        }

        // CORREÇÃO 1 (Nome da sessão)
        request.getSession().setAttribute("medicoLogado", m); 

        response.sendRedirect("Consultas");
        return;

    }

    // --- Bloco de Cadastro e Alteração (Reestruturado) ---
    
    // 1. Leia os campos comuns (que existem tanto no cadastro quanto na alteração)
    String crm = request.getParameter("crm");
    String nome = request.getParameter("nome");
    String especialidade = request.getParameter("especialidade");
    String contato = request.getParameter("contato");

    // 2. Crie o objeto e preencha com os campos comuns
    Medico m = new Medico();
    m.setCrm(crm);
    m.setNome(nome);
    m.setEspecialidade(especialidade);
    m.setContato(contato);

    MedicoNegocio negocio = new MedicoNegocio();

    try {

        if (op != null && op.equals("alterar")) {
           
            
            // CORREÇÃO 2: Buscar o médico antigo para não perder a senha
            Medico medicoAntigo = negocio.buscarPorCrm(crm);
            if (medicoAntigo != null) {
                m.setSenha(medicoAntigo.getSenha()); // Preserva a senha antiga
            }
            
            negocio.atualizar(m);
            request.getSession().setAttribute("msg", "Medico Alterado com Sucesso!");

        } else {
          
            String senha = request.getParameter("senha");
            String confirma = request.getParameter("confirm");

            if (senha == null || !senha.equals(confirma)) {
                request.getSession().setAttribute("msg", "Erro ao validar a senha! Os campos não conferem.");
                response.sendRedirect("LoginMedico.jsp");
                return;
            }
            
            m.setSenha(senha); // Define a nova senha
            
            negocio.cadastrar(m);
            request.getSession().setAttribute("msg", "Medico Cadastrado com Sucesso!");
        }
        
        // Se tudo deu certo, volta para a lista
        response.sendRedirect("Medico");

    } catch (Exception e) {

        // 1. Salve a mensagem de erro na SESSÃO (para sobreviver ao redirect)
        request.getSession().setAttribute("msg", "Erro ao cadastrar: " + e.getMessage());
        
        // 2. Mude de 'forward' para 'redirect'
        response.sendRedirect("Medico");
    }
}

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
