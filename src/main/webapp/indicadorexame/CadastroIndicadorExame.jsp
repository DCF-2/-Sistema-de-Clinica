<%-- 
    Document   : CadastroIndicadorExame
    Created on : 5 de nov. de 2025, 09:47:40
    Author     : davic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Indicador</title>

    </head>
    <body>
        <h1>${(requestScope.indicadoresexames ne null)?'Alterar':'Cadastro de novo'} Indicador!</h1>
        <%
            String msgErro = (String) request.getAttribute("msgErro");
            if (msgErro != null) {
        %>
        <h3 style="color: red; background-color: #ffe0e0; padding: 10px;">
            <%= msgErro%>
        </h3>
        <%
            }
        %>

        <form method="post" action="Indicador">
            ${(requestScope.indicadoresexames ne null)?'<input type="hidden" name="op" value="alterar"/>':''}

            Código: <input type="text" name="codigo"
                           ${(requestScope.indicadoresexames ne null)?'readonly="true" value="'
                             .concat(requestScope.indicadoresexames.codigo).concat('"'):''}/><br/>

            Indicador: <input type="text" name="indicador" value="${(requestScope.indicadoresexames ne null)?requestScope.indicadoresexames.indicador:''}"/><br/>

            Descrição: <input type="text" name="descricao" value="${(requestScope.indicadoresexames ne null)?requestScope.indicadoresexames.descricao:''}" /><br/>

            Max Valor de Referência: <input type="text" name="maxValorReferencia" value="${(requestScope.indicadoresexames ne null)?requestScope.indicadoresexames.maxValorReferencia:''}"/><br/>

            Min Valor de Referência: <input type="text" name="minValorReferencia" value="${(requestScope.indicadoresexames ne null)?requestScope.indicadoresexames.minValorReferencia:''}"/><br/>

            <button class="btn btn-primary">${(requestScope.indicadoresexames ne null)?'alterar':'cadastrar'}</button>
        </form>
    </body>
</html>

