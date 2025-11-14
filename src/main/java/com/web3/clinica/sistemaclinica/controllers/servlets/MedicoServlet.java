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

        // 1. CORREÇÃO DO ERRO: Mover a verificação de null para o TOPO
        if (op == null) {
            
            // Se não houver 'op', verificamos se ele está logado.
            // Se estiver, mandamos para a Home. Se não, mandamos para o Login.
            if(request.getSession().getAttribute("medicoLogado") != null){
                op = "home"; // Ação padrão para médico logado
            } else {
                response.sendRedirect("LoginMedico.jsp"); // Ação padrão se não logado
                return;
            }
        }

        // 2. CORREÇÃO DE LÓGICA: Mover os IFs para dentro do SWITCH
        switch (op) {

            case "logout":
                request.getSession().removeAttribute("medicoLogado"); // Padronizar nome
                request.getSession().invalidate();
                response.sendRedirect("LoginMedico.jsp");
                break; // 'break' é suficiente, 'return' também funciona

            case "perfil":
                // Verifique se o JSP existe ou mude o caminho
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/jsps/perfilMedico.jsp") 
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
                response.sendRedirect("Medico"); // Redireciona para o 'listar'
                break;

            case "alterar":
                try {
                    Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));
                    if (m == null) {
                        throw new Exception("medico não encontrado para alteração.");
                    }
                    request.setAttribute("medico", m);
                    // Caminho correto (baseado nos seus arquivos anteriores)
                    getServletContext().getRequestDispatcher("/medico/CadastroMedico.jsp") 
                            .forward(request, response);
                } catch (Exception e) {
                    request.getSession().setAttribute("msg", "Erro ao preparar alteração: " + e.getMessage());
                    response.sendRedirect("Medico");
                }
                break;

            case "visualizar":
                try {
                    Medico m = medicoNegocio.buscarPorCrm(request.getParameter("crm"));
                    if (m == null) {
                        throw new Exception("Medico não encontrado.");
                    }
                    request.setAttribute("medico", m);
                    // Caminho correto (baseado nos seus arquivos anteriores)
                    getServletContext().getRequestDispatcher("/medico/visualizarMedico.jsp") 
                            .forward(request, response);
                } catch (Exception e) {
                    request.getSession().setAttribute("msg", "Erro ao visualizar paciente: " + e.getMessage());
                    response.sendRedirect("Medico");
                }
                break;

            case "home": // Onde o médico logado "aterrissa"
                // TODO: Aqui você deve buscar as CONSULTAS e mandar para a Home do Médico
                // (Fase 3 do roadmap)
                
                // Por enquanto, vamos manter a lista de Médicos (como no Medico.jsp)
                List<Medico> medicosHome = medicoNegocio.listar();
                request.setAttribute("medicos", medicosHome);
                request.getRequestDispatcher("Medico.jsp").forward(request, response);
                break;
                
            case "listar": // Ação padrão que estava no final
            default:
                List<Medico> medicos = medicoNegocio.listar();
                request.setAttribute("medicos", medicos); // Usar setAttribute
                request.getRequestDispatcher("Medico.jsp").forward(request, response); // Usar forward
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");
        
        if(op!=null && op.equals("alterarSenha")){
            
            Medico m = (Medico)request.getSession()
                    .getAttribute("medicoLogado");
            
            if(!m.getCrm().equals(request.getParameter("atual"))){
                request.setAttribute("msg", "você errou a senha atual!");
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/jsps/indexMedico.jsp")
                        .forward(request, response);
            }
            if(!request.getParameter("nova").equals(request.getParameter("confirm"))){
                request.setAttribute("msg", "A nova senha e a confirmação não batem!");
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/jsps/indexMedico.jsp")
                        .forward(request, response);
            }
            
            MedicoRepository.alterarSenha(m.getCrm(), request.getParameter("nova"));
            
            request.setAttribute("msg", "A senha foi alterada com sucesso!");
            request.setAttribute("svd", "ok");
            getServletContext()
                        .getRequestDispatcher("/WEB-INF/jsps/indexHospedeiro.jsp")
                        .forward(request, response);
            
            return;
        }
        
        if(op != null && op.equals("login")){
            
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            
            Medico m = MedicoRepository.login(login, senha);
            
            if(m == null){
                request.getSession().setAttribute("msg", "Erro ao logar! A senha e/ou o Nome estão incorretos!");
                response.sendRedirect("LoginMedico.jsp");
                return;
            }
            
            request.getSession().setAttribute("hospedeiroLogado", m);
            
            getServletContext().getRequestDispatcher("/WEB-INF/jsps/indexMedico.jsp")
                    .forward(request, response);
            return;
            
        }
        
        String senha = request.getParameter("senha");
        String confirma = request.getParameter("confirm");
        
        if(op == null && !senha.equals(confirma)){
            
            request.getSession().setAttribute("msg","Erro ao validar a senha!");
            response.sendRedirect("LoginMedico.jsp");
            return;
        }

        String crm = request.getParameter("crm");
        String nome = request.getParameter("nome");
        String especialidade = request.getParameter("especialidade");
        String contato = request.getParameter("contato");
        

        Medico m = new Medico();
        m.setCrm(crm);
        m.setNome(nome);
        m.setEspecialidade(especialidade);
        m.setContato(contato);
        m.setSenha(senha);
        
        

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
