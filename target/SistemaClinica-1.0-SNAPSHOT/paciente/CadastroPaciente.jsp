<%-- 
    Document   : CadastroPacientejsp
    Created on : 5 de nov. de 2025, 09:32:08
    Author     : davic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Paciente</title>

    </head>
    <body>
        <h1>${(requestScope.paciente ne null)?'Alterar':'Cadastro de novo'} Paciente!</h1>
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

        <form method="post" action="Paciente">
            ${(requestScope.paciente ne null)?'<input type="hidden" name="op" value="alterar"/>':''}

            CPF: <input type="text" name="cpf"
                           ${(requestScope.paciente ne null)?'readonly="true" value="'
                             .concat(requestScope.paciente.cpf).concat('"'):''}/><br/>
            Nome: <input type="text" name="nome" value="${(requestScope.paciente ne null)?requestScope.paciente.nome:''}"/><br/>
            Endereço: <input type="text" name="endereco" value="${(requestScope.paciente ne null)?requestScope.paciente.endereço:''}" /><br/>
            Contato: <input type = "text" name="contato" value="${(requestScope.paciente ne null)?requestScope.paciente.contato:''}"/><br/>
            Plano de Saúde: <textarea name="planoSaude">${(requestScope.paciente ne null)?requestScope.paciente.planoSaude:''}</textarea><br/>
            <button class="btn btn-primary">${(requestScope.paciente ne null)?'alterar':'cadastrar'}</button>
        </form>
    </body>
</html>
