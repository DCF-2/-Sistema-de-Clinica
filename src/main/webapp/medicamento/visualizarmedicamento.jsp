<%-- 
    Document   : visualizarmedicamento
    Created on : 6 de nov. de 2025, 10:31:58
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.Medicamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detalhes do Medicamento</title>
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
        Medicamento m = (Medicamento) request.getAttribute("medicamento");
        
        if (m != null) {
    %>

    <div class="card shadow-sm">
        <h3 class="card-header">
            Detalhes do Medicamento
        </h3>
        <div class="card-body">
            <p><strong>Código</strong><%= m.getCodigo() %></p>
            <p><strong>Nome:</strong> <%= m.getNome() %></p>
            <p><strong>Descrição:</strong> <%= m.getDescricao()%></p>
            <hr>
            <p><strong>Observação:</strong> <%= m.getObservacao()%></p>
            <p><strong>Dosagem:</strong> <%= m.getDosagem()%></p>
            <p><strong>Tipo de Dosagem:</strong> <%= m.getTipoDosagem()%></p>
            
        </div>
        <div class="card-footer text-center">
            <a href="Medicamento" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    </div>

    <% 
        } else {
    %>
        <div class="alert alert-danger">
            <h1>Medicamento não encontrado!</h1>
            <a href="Medicamento" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    <% 
        } 
    %>

</body>
</html>
