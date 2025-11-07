<%-- 
    Document   : CadastroMedicamentojsp
    Created on : 5 de nov. de 2025, 09:42:12
    Author     : davic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Medicamento</title>

    </head>
    <body>
        <h1>${(requestScope.medicamento ne null)?'Alterar':'Cadastro de novo'} Medicamento!</h1>
        <% 
            String msgErro = (String) request.getAttribute("msgErro");
            if (msgErro != null) {
        %>
            <h3 style="color: red; background-color: #ffe0e0; padding: 10px;">
                <%= msgErro %>
            </h3>
        <%
            }
        %>

        <form method="post" action="Medicamento">
            ${(requestScope.medicamento ne null)?'<input type="hidden" name="op" value="alterar"/>':''}

            Código: <input type="text" name="codigo"
                           ${(requestScope.medicamento ne null)?'readonly="true" value="'
                             .concat(requestScope.medicamento.codigo).concat('"'):''}/><br/>
            Nome: <input type="text" name="nome" value="${(requestScope.medicamento ne null)?requestScope.medicamento.nome:''}"/><br/>
            Dosagem: <input type="text" name="dosagem" value="${(requestScope.medicamento ne null)?requestScope.medicamento.dosagem:''}"/><br/>
            Tipo de Dosagem: <input type = "text" name="tipoDosagem" value="${(requestScope.medicamento ne null)?requestScope.medicamento.tipoDosagem:''}"/><br/>
            Descrição: <textarea name="descricao">${(requestScope.medicamento ne null)?requestScope.medicamento.descricao:''}</textarea><br/>
            Observação: <textarea name="observacao">${(requestScope.medicamento ne null)?requestScope.medicamento.observacao:''}</textarea><br/>
            <button class="btn btn-primary">${(requestScope.medicamento ne null)?'alterar':'cadastrar'}</button>
        </form>
    </body>
</html>
