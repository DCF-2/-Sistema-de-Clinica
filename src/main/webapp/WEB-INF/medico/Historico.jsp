<%-- 
    Document   : Historico
    Created on : 14 de nov. de 2025, 16:44:22
    Author     : davic
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Histórico de Atendimentos</title>
    </head>
    <body>
        
        <%-- 1. PUXA O MENU (Que já tem o Bootstrap e a verificação de login) --%>
        <jsp:include page="menuMedico.jsp"/>
        
        <div class="container">
            
            <h1 class="mt-3">Histórico de Atendimentos</h1>
            <p>Consultas que já foram finalizadas e possuem prontuário.</p>
            
            <%-- 2. TABELA DO HISTÓRICO --%>
            <%-- Esta tabela lê o "listaHistorico" enviado pelo ConsultaServlet --%>
            <table class="table table-striped mt-3" >
                <tr class="table-dark">
                    <td>Paciente</td>
                    <td>Data/Hora</td>
                    <td>Diagnóstico/Descrição (Prontuário)</td>
                    <td>Observações (Prontuário)</td>
                    <td>Retorno Marcado</td>
                </tr>
                
                <%-- Se a lista estiver vazia, mostra uma mensagem --%>
                <c:if test="${empty requestScope.listaHistorico}">
                    <tr>
                        <td colspan="5">Nenhum atendimento finalizado encontrado.</td>
                    </tr>
                </c:if>
                
                <%-- Loop pelo histórico --%>
                <c:forEach var="h" items="${requestScope.listaHistorico}">
                    <tr >
                        <td>${h.paciente.nome}</td>
                        <td>${h.dataHora}</td>
                        <%-- Puxa os dados de dentro do Prontuário --%>
                        <td>${h.prontuario.descricao}</td>
                        <td>${h.prontuario.observacao}</td>
                        <td>${h.dataHoraVolta}</td>
                    </tr>
                </c:forEach>
            </table>
            
        </div> <%-- Fim do .container --%>
    </body>
</html>