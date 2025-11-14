<%-- 
    Document   : Atendimento
    Created on : 14 de nov. de 2025, 15:44:18
    Author     : davic
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atendimento (Prontuário)</title>
    </head>
    <body>
        <%-- 1. Inclui o menu (com Bootstrap e verificação de login) --%>
        <jsp:include page="menuMedico.jsp"/>
        
        <div class="container">
            <h1 class="mt-3">Registo de Prontuário</h1>
            
            <%-- Variável 'consulta' vem do 'consultaParaAtender' (do Servlet) --%>
            <c:set var="consulta" value="${requestScope.consultaParaAtender}"/>
            
            <%-- 2. Box de Informações (Dados que não mudam) --%>
            <div class="card bg-light mb-3">
                <div class="card-header">
                    <strong>Paciente:</strong> ${consulta.paciente.nome} 
                    (CPF: ${consulta.paciente.cpf})
                </div>
                <div class="card-body">
                    <p><strong>Data/Hora da Consulta:</strong> ${consulta.dataHora}</p>
                    <p><strong>Queixa Inicial:</strong> ${consulta.observacao}</p>
                </div>
            </div>

            <%-- 3. Formulário do Prontuário (Baseado no Diagrama) --%>
            <form method="post" action="${pageContext.request.contextPath}/Consultas">
                <%-- Envia o 'op' para o doPost saber o que fazer --%>
                <input type="hidden" name="op" value="salvarProntuario"/>
                <%-- Envia o ID da consulta que estamos a atender --%>
                <input type="hidden" name="idConsulta" value="${consulta.codigo}"/>
                
                <div class="mb-3">
                    <label class="form-label"><strong>Descrição (Sintomas e Diagnóstico)</strong></label>
                    <textarea name="prontuarioDescricao" class="form-control" rows="5" required></textarea>
                </div>
                
                <div class="mb-3">
                    <label class="form-label"><strong>Observação (Medicação, etc.)</strong></label>
                    <textarea name="prontuarioObservacao" class="form-control" rows="3"></textarea>
                </div>
                
                <button type="submit" class="btn btn-success">Finalizar e Salvar Prontuário</button>
                <a href="${pageContext.request.contextPath}/Consultas" class="btn btn-secondary">Cancelar</a>
            </form>
            
        </div>
    </body>
</html>
