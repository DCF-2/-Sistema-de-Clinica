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
import com.web3.clinica.sistemaclinica.model.entities.Prontuario;

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
        HttpSession session = request.getSession();
        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        // 1. VERIFICAR SESSÃO (Segurança)
        if (medicoLogado == null) {
            session.setAttribute("msg", "Você precisa estar logado para ver suas consultas.");
            response.sendRedirect("LoginMedico.jsp");
            return;
        }
        
        String op = request.getParameter("op");

        // FLUXO PARA ATENDIMENTO (TAREFA 4)
        if ("atender".equals(op)) {
            try {
                int idConsulta = Integer.parseInt(request.getParameter("id"));
                Consulta consulta = consultaNegocio.buscarPorCodigo(idConsulta);
                
                // Medida de segurança: verifica se a consulta é mesmo deste médico
                if (consulta == null || !consulta.getMedico().getCrm().equals(medicoLogado.getCrm())) {
                    throw new Exception("Consulta não encontrada ou não pertence a este médico.");
                }

                request.setAttribute("consultaParaAtender", consulta);
                // Vamos criar este JSP no próximo passo
                request.getRequestDispatcher("/WEB-INF/medico/Atendimento.jsp").forward(request, response);
                
            } catch (Exception e) {
                session.setAttribute("msg", "Erro ao buscar consulta: " + e.getMessage());
                response.sendRedirect("Consultas");
            }
            return; // Importante: 'return' para parar aqui
        }else if ("historico".equals(op)) {
            try {
                // 1. Busca o histórico (consultas COM prontuário)
                List<Consulta> historico = consultaNegocio.listarPorMedicoComProntuario(medicoLogado.getCrm());
                request.setAttribute("listaHistorico", historico);
                
                // 2. Encaminha para o novo JSP (que criaremos a seguir)
                request.getRequestDispatcher("/WEB-INF//medico/Historico.jsp").forward(request, response);

            } catch (ServletException | IOException e) {
                session.setAttribute("msg", "Erro ao carregar histórico: " + e.getMessage());
                response.sendRedirect("Consultas"); // Volta p/ dashboard
            }
            return; // Importante: 'return' para parar aqui
        }

        // 2. BUSCAR DADOS PARA O DASHBOARD
        try {
            // A. Lista de consultas PENDENTES (só deste médico) - Para a Tabela
            List<Consulta> listaConsultas = consultaNegocio.listarPorMedicoSemProntuario(medicoLogado.getCrm());
            request.setAttribute("listaConsultas", listaConsultas);

            // B. Lista de TODOS os pacientes (do CRUD Admin) - Para o Modal de Agendamento
            List<Paciente> listaPacientes = PacienteRepository.lerTudo();
            request.setAttribute("listaPacientes", listaPacientes);

            // 3. ENVIAR PARA O JSP
            // (Vamos criar este JSP no próximo passo)
            request.getRequestDispatcher("/WEB-INF/medico/ConsultaMedico.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            session.setAttribute("msg", "Erro ao carregar dados: " + e.getMessage());
            response.sendRedirect("LoginMedico.jsp"); // Manda pro login se der erro
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

        // 1. VERIFICAR SESSÃO (Segurança)
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
                String cpfPaciente = request.getParameter("cpfPaciente");
                String dataHora = request.getParameter("dataHora");
                String dataHoraVolta = request.getParameter("dataHoraVolta");
                String observacao = request.getParameter("observacao");

                // Buscar o objeto Paciente (do repositório Admin)
                Paciente paciente = PacienteRepository.Ler(cpfPaciente);

                // Montar o objeto Consulta
                Consulta novaConsulta = new Consulta();
                novaConsulta.setMedico(medicoLogado);
                novaConsulta.setPaciente(paciente);
                novaConsulta.setDataHora(dataHora);
                novaConsulta.setDataHoraVolta(dataHoraVolta);
                novaConsulta.setObservacao(observacao);

                // Chamar a camada de negócio para validar e salvar
                consultaNegocio.agendar(novaConsulta);
                
                session.setAttribute("msg", "Consulta agendada com sucesso!");

            } catch (Exception e) {
                // Se o ConsultaNegocio der 'throw', captura aqui
                session.setAttribute("msg", "Erro ao agendar: " + e.getMessage());
            }
        }else if ("salvarProntuario".equals(op)) {
            try {
                // 1. Pegar os dados do formulário Atendimento.jsp
                int idConsulta = Integer.parseInt(request.getParameter("idConsulta"));
                String desc = request.getParameter("prontuarioDescricao");
                String obs = request.getParameter("prontuarioObservacao");

                // 2. Criar o objeto Prontuario (conforme diagrama)
                Prontuario p = new Prontuario();
                p.setDescricao(desc);
                p.setObservacao(obs);
                // (Vamos assumir que o ProntuarioRepository não é necessário
                // e que o objeto Prontuario "vive" dentro da Consulta)
                
                // 3. Buscar a consulta original
                Consulta consulta = consultaNegocio.buscarPorCodigo(idConsulta);
                
                // 4. Linkar o prontuário à consulta
                consulta.setProntuario(p);
                
                // 5. Salvar a atualização
                // (O ConsultaNegocio já tem a validação que impede descrição vazia)
                consultaNegocio.atualizar(consulta);
                
                session.setAttribute("msg", "Atendimento finalizado e prontuário salvo!");

            } catch (Exception e) {
                session.setAttribute("msg", "Erro ao salvar prontuário: " + e.getMessage());
            }
        }

        // No fim, sempre redireciona para o doGet (para atualizar a lista)
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
