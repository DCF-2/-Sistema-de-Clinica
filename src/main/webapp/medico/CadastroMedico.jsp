<%-- 
    Document   : CadastroMedicojsp
    Created on : 5 de nov. de 2025, 09:33:31
    Author     : davic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Medico</title>

    </head>
    <body>
        <h1>${(requestScope.medico ne null)?'Alterar':'Cadastro de novo'} Medico!</h1>
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

        <form method="post" action="Medico">
            ${(requestScope.medico ne null)?'<input type="hidden" name="op" value="alterar"/>':''}

            CRM: <input type="text" name="crm"
                           ${(requestScope.medico ne null)?'readonly="true" value="'
                             .concat(requestScope.medico.crm).concat('"'):''}/><br/>
            Nome: <input type="text" name="nome" value="${(requestScope.medico ne null)?requestScope.medico.nome:''}"/><br/>
            Especialidade: <input type="text" name="especialidade" value="${(requestScope.medico ne null)?requestScope.medico.especialidade:''}" /><br/>
            Contato: <input type = "text" name="contato" value="${(requestScope.medico ne null)?requestScope.medico.contato:''}"/><br/>
            <button class="btn btn-primary">${(requestScope.medico ne null)?'alterar':'cadastrar'}</button>
        </form>
    </body>
</html>
