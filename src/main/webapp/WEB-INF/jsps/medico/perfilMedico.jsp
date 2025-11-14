<%-- 
    Document   : perfilMedico
    Created on : 13 de nov. de 2025, 17:33:51
    Author     : davic
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil Medico</title>
    </head>
    <body>

        <jsp:directive.include file="menuMedico.jsp"/>

        <h1>Dados do Medico</h1>
        <form method="Post" action="Medico">
            <input type='hidden' name='op' value='alterar'/>
            <table>
                <table class="table">
                    <tr>
                        <td>CRM</td>
                        <td><input type="text" name="crm" class="form-control" readonly='true'
                                   value='${sessionScope['medicoLogado'].crm}'/></td>
                    </tr>
                    <tr>
                        <td>Nome</td>
                        <td><input type="text" name="nome" class="form-control"
                                   value='${sessionScope.medicoLogado.nome}'/></td>
                    </tr>
                    <tr>
                        <td>Especialidade</td>
                        <td><input type="text" name="vulgo"  class="form-control"
                                   value='${sessionScope.medicoiroLogado.especialidade}'/></td>
                    </tr>
                    <tr>
                        <td>Contato</td>
                        <td><input type="text" name="contato" class="form-control"
                                   value='${sessionScope.medicoLogado.contato}'/></td>
                    </tr>
                    
                </table>
                <button class="btn btn-primary">alterar</button>

            </table>

        </form>
    </body>
</html>

