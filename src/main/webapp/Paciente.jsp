<%-- 
    Document   : Paciente
    Created on : 6 de nov. de 2025, 07:54:23
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.Paciente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pacientes</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        ${pageScope.pacientes.size()}

        <%

            List<Paciente> pacientes = (List) session.getAttribute("pacientes");

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

        <h1>Paciente Cadastrados</h1>
        <a href="#" class="btn btn-success"
           data-bs-toggle="modal" data-bs-target="#modalCadastro">cadastrar novo Paciente</a>
        <%
            if (pacientes == null || pacientes.isEmpty()) {
        %>
        <h4>Não existem Pacientes cadastrados</h4>
        <%
        } else {
        %>

        <table class="table">
            <tr>
                <th>Nome</th>
                <th>CPF</th>
                <th>Contato</th>
                <th>Ações</th>
            </tr>

            <%
                for (Paciente p : pacientes) {

                    request.setAttribute("p", p);
            %>

            <tr>
                <td><%= p.getNome()%></td>
                <td><%= p.getCpf()%></td>
                <td><%= p.getContato()%></td>
                <td>

                    <a href="Paciente?cpf=<%= p.getCpf()%>&op=visualizar"
                       class="btn btn-success">Visualizar</a>

                    <a href="Paciente?cpf=<%= p.getCpf()%>&op=alterar"
                       class="btn btn-info">alterar</a>

                    <a href="Paciente?cpf=${p.cpf}&op=deletar"
                       class="btn btn-warning">deletar</a></td>

            <a href="Paciente?cpf="></a>
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
                    <h5 class="modal-title" id="exampleModalLabel">Cadastro de Paciente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <%@include file="paciente/CadastroPaciente.jsp" %>
                </div>

            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
