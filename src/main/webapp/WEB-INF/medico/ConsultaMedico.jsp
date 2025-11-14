<%-- 
    Document   : ConsultaMedico
    Created on : 14 de nov. de 2025, 15:12:57
    Author     : davic
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Minhas Consultas</title>
    </head>
    <body>
        
        <%-- 1. PUXA O MENU (Que já tem o Bootstrap e a verificação de login) --%>
        <jsp:include page="menuMedico.jsp"/>
        
        <div class="container">
            
            <h1 class="mt-3">Consultas Agendadas (Pendentes)</h1>
            
            <%-- Botão para abrir o Modal de Agendamento --%>
            <button class="btn btn-success" data-bs-toggle="modal"
                    data-bs-target='#modalAgendamento'>
                Agendar Nova Consulta
            </button>
            
            <%-- 2. TABELA DE CONSULTAS --%>
            <%-- Esta tabela lê o "listaConsultas" enviado pelo ConsultaServlet --%>
            <table class="table table-striped mt-3" >
                <tr class="table-dark">
                    <td>Paciente</td>
                    <td>Data/Hora</td>
                    <td>Observações (Queixa)</td>
                    <td>Retorno Marcado</td>
                    <td>Ações</td>
                </tr>
                
                <%-- Se a lista estiver vazia, mostra uma mensagem --%>
                <c:if test="${empty requestScope.listaConsultas}">
                    <tr>
                        <td colspan="5">Nenhuma consulta pendente encontrada.</td>
                    </tr>
                </c:if>
                
                <%-- Loop pelas consultas --%>
                <c:forEach var="c" items="${requestScope.listaConsultas}">
                    <tr >
                        <td>${c.paciente.nome}</td>
                        <td>${c.dataHora}</td>
                        <td>${c.observacao}</td>
                        <td>${c.dataHoraVolta}</td>
                        <td>
                            <%-- Tarefa 4 (Futuro): Link para atender --%>
                            <a href="Consultas?op=atender&id=${c.codigo}" class="btn btn-primary btn-sm">Atender</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <%-- 3. MODAL DE AGENDAMENTO (Formulário) --%>
            <div class="modal fade" id="modalAgendamento" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Agendar Nova Consulta</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            
                            <%-- O formulário envia (POST) para o ConsultaServlet --%>
                            <form method="post" action="Consultas">
                                <input type="hidden" name="op" value="agendar"/>
                                
                                <div class="mb-3">
                                    <label class="form-label">Paciente</label>
                                    <%-- Este <select> lê o "listaPacientes" enviado pelo ConsultaServlet --%>
                                    <select name="cpfPaciente" class="form-control" required>
                                        <option value="">Selecione...</option>
                                        <c:forEach var="p" items="${requestScope.listaPacientes}">
                                            <option value="${p.cpf}">${p.nome} - (CPF: ${p.cpf})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Data e Hora da Consulta</label>
                                        <input type="datetime-local" name="dataHora" class="form-control" required/>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Data Retorno (Opcional)</label>
                                        <input type="datetime-local" name="dataHoraVolta" class="form-control"/>
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <label class="form-label">Observação (Queixa Inicial)</label>
                                    <textarea class="form-control" name='observacao'></textarea>
                                </div>
                                
                                <button type="submit" class="btn btn-primary">Agendar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
        </div> <%-- Fim do .container --%>
    </body>
</html>
