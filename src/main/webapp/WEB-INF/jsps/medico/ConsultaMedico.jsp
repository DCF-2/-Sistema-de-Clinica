<%-- 
    Document   : MedicoConsultas
    Created on : 14 de nov. de 2025, 10:08:36
    Author     : ALUNO
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- URI CORRIGIDA --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Minhas Consultas</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </head>
    <body>
        <%-- 1. INCLUI O MENU (que tem o controle de sessão) --%>
        <jsp:include page="menuMedico.jsp"/>
        
        <div class="container">
            
            <h1 class="mt-3">Consultas Agendadas (Pendentes)</h1>
            
            <%-- Botão para Agendar (adaptado) --%>
            <button class="btn btn-success" data-bs-toggle="modal"
                    data-bs-target='#modalAgendamento'>
                Agendar Nova Consulta
            </button>
            
            <%-- Mensagem de Sucesso/Erro (vinda da sessão) --%>
            <c:if test="${not empty sessionScope.msg}">
                <div class="alert alert-info mt-3">
                    ${sessionScope.msg}
                </div>
                <c:remove var="msg" scope="session"/>
            </c:if>

            <%-- 2. TABELA DE CONSULTAS (Adaptada) --%>
            <table class="table table-striped mt-3" >
                <tr class="table-dark">
                    <td>Paciente</td>
                    <td>Data/Hora</td>
                    <td>Observações</td>
                    <td>Retorno Marcado</td>
                    <td>Ações</td>
                </tr>
                
                <%-- Loop usa "listaConsultas" (do Servlet) --%>
                <c:forEach var="c" items="${requestScope.listaConsultas}">
                    <tr >
                        <td>${c.paciente.nome}</td>
                        <td>${c.dataHora}</td>
                        <td>${c.observacao}</td>
                        <td>${c.dataHoraVolta}</td>
                        <td>
                            <%-- Fase 7 (Futuro): Link para atender --%>
                            <a href="Consultas?op=atender&id=${c.codigo}" class="btn btn-primary btn-sm">Atender</a>
                        </td>
                    </tr>
                </c:forEach>
                
            </table>

            <%-- 3. MODAL DE AGENDAMENTO (Adaptado) --%>
            <div class="modal fade" id="modalAgendamento" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Agendar Nova Consulta</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            
                            <%-- Action aponta para "Consultas" (Servlet) --%>
                            <form method="post" action="Consultas">
                                <%-- "op" oculto para o servlet saber o que fazer --%>
                                <input type="hidden" name="op" value="agendar"/>
                                
                                <table class="table">
                                    <tr>
                                        <td>Paciente</td>
                                        <td>
                                            <%-- Loop usa "listaPacientes" (do Servlet) --%>
                                            <select name="cpfPaciente" class="form-control" required>
                                                <option value="">Selecione...</option>
                                                <c:forEach var="p" items="${requestScope.listaPacientes}">
                                                    <option value="${p.cpf}">${p.nome} - (${p.cpf})</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Data e Hora</td>
                                        <%-- Input de data/hora (HTML5) --%>
                                        <td><input type="datetime-local" name="dataHora" class="form-control" required/></td>
                                    </tr>
                                    <tr>
                                        <td>Data Retorno (Opcional)</td>
                                        <td><input type="datetime-local" name="dataHoraVolta" class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>Observação (Queixa)</td>
                                        <td><textarea class="form-control" name='observacao'></textarea></td>
                                    </tr>
                                </table>
                                <button type="submit" class="btn btn-primary">Agendar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
        </div> <%-- Fim do .container --%>
    </body>
</html>
