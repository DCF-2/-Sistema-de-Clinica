<%-- 
    Document   : visualizarIndicadorExme
    Created on : 6 de nov. de 2025, 10:31:32
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.IndicadorExame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detalhes do Paciente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { padding: 20px; }
        .card { max-width: 600px; margin: auto; }
        .card-header { background-color: #007bff; color: white; }
        .card-body p { margin-bottom: 5px; }
        .card-body p strong { display: inline-block; width: 120px; }
    </style>
</head>
<body class="bg-light">

    <%-- Pega o objeto 'paciente' que o Servlet enviou --%>
    <% 
        IndicadorExame ie = (IndicadorExame) request.getAttribute("indicadoresexames");
        
        if (ie != null) {
    %>

    <div class="card shadow-sm">
        <h3 class="card-header">
            Detalhes do Indicador de Exame
        </h3>
        <div class="card-body">
            
            <p><strong>Indicador:</strong> <%= ie.getIndicador()%></p>
            <p><strong>Código:</strong> <%= ie.getCodigo() %></p>
            <hr>
            <p><strong>Descrição:</strong> <%= ie.getDescricao()%></p>
            <p><strong>Max Valor de Referencia:</strong> <%= ie.getMaxValorReferencia()%></p>
            <p><strong>Mim Valor de Referencia:</strong> <%= ie.getMinValorReferencia()%></p>
            
        </div>
        <div class="card-footer text-center">
            <a href="Indicador" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    </div>

    <% 
        } else {
    %>
        <div class="alert alert-danger">
            <h1>Indicador não encontrado!</h1>
            <a href="Indicador" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    <% 
        } 
    %>

</body>
</html>
