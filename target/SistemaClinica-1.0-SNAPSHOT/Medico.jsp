<%-- 
    Document   : Medico
    Created on : 6 de nov. de 2025, 10:32:48
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.Medico"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medicos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        ${pageScope.pacientes.size()}

        <%

            List<Medico> medicos = (List) session.getAttribute("medicos");

            String msg = (String) session.getAttribute("msg");

            if (msg != null) {
        %>
        <small class="alert"><%= msg%></small>
        <%
                session.removeAttribute("msg");
            }
        %>

        <%-- Botão para voltar para a Home (Index.jsp) --%>
        <a href="${pageContext.request.contextPath}/Index.jsp" 
           class="btn btn-outline-dark">
            Voltar para Home
        </a>

        <h1>Medico Cadastrados</h1>
        <a href="#" class="btn btn-success"
           data-bs-toggle="modal" data-bs-target="#modalCadastro">cadastrar novo Medico</a>
        <%
            if (medicos == null || medicos.isEmpty()) {
        %>
        <h4>Não existem Medicos cadastrados</h4>
        <%
        } else {
        %>

        <table class="table">
            <tr>
                <th>Nome</th>
                <th>CRM</th>
                <th>Contato</th>
                <th>Ações</th>
            </tr>

            <%
                for (Medico m : medicos) {

                    request.setAttribute("m", m);
            %>

            <tr>
                <td><%= m.getNome()%></td>
                <td><%= m.getCrm()%></td>
                <td><%= m.getContato()%></td>
                <td>

                    <a href="Medico?crm=<%= m.getCrm()%>&op=visualizar"
                       class="btn btn-success">Visualizar</a>

                    <a href="Medico?crm=<%= m.getCrm()%>&op=alterar"
                       class="btn btn-info">alterar</a>

                    <a href="Medico?crm=${m.crm}&op=deletar"
                       class="btn btn-warning">deletar</a></td>

            <a href="Medico?crm="></a>
        </tr>

        <%
            }
        %>
    </table>
    <%
        }
    %>

    <div class="modal fade" id="modalCadastro" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Cadastro de Medico</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <%@include file="medico/CadastroMedico.jsp" %>
                </div>

            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
