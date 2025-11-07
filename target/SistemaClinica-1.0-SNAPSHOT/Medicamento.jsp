<%-- 
    Document   : Medicamento
    Created on : 6 de nov. de 2025, 07:55:38
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.Medicamento"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Medicamentos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        ${pageScope.medicamentos.size()}

        <%

            List<Medicamento> medicamentos = (List) session.getAttribute("medicamentos");

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

        <h1>Medicamento Cadastrados</h1>
        <a href="#" class="btn btn-success"
           data-bs-toggle="modal" data-bs-target="#modalCadastro">cadastrar novo Medicamento</a>
        <%
            if (medicamentos == null || medicamentos.isEmpty()) {
        %>
        <h4>Não existem medicamentos cadastrados</h4>
        <%
        } else {
        %>

        <table class="table">
            <tr>
                <th>Nome</th>
                <th>Código</th>
                <th>Dosagem</th>
                <th>Ações</th>
            </tr>

            <%
                for (Medicamento m : medicamentos) {

                    request.setAttribute("m", m);
            %>

            <tr>
                <td><%= m.getNome()%></td>
                <td><%= m.getCodigo()%></td>
                <td><%= m.getDosagem()%></td>
                <td>

                    <a href="Medicamento?codigo=<%= m.getCodigo()%>&op=visualizar"
                       class="btn btn-success">Visualizar</a>

                    <a href="Medicamento?codigo=<%= m.getCodigo()%>&op=alterar"
                       class="btn btn-info">alterar</a>

                    <a href="Medicamento?codigo=${m.codigo}&op=deletar"
                       class="btn btn-warning">deletar</a></td>

            <a href="Medicamento?codigo="></a>
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
                    <h5 class="modal-title" id="exampleModalLabel">Cadastro de Medicamento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <%@include file="medicamento/CadastroMedicamento.jsp" %>
                </div>

            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

