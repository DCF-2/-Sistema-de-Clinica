<%-- 
    Document   : visualizarPaciente
    Created on : 6 de nov. de 2025, 09:10:23
    Author     : davic
--%>

<%@page import="com.web3.clinica.sistemaclinica.model.entities.Paciente"%>
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
        Paciente p = (Paciente) request.getAttribute("paciente");
        
        if (p != null) {
    %>

    <div class="card shadow-sm">
        <h3 class="card-header">
            Detalhes do Paciente
        </h3>
        <div class="card-body">
            
            <p><strong>Nome:</strong> <%= p.getNome() %></p>
            <p><strong>CPF:</strong> <%= p.getCpf() %></p>
            <hr>
            <p><strong>Endereço:</strong> <%= p.getEndereço() %></p>
            <p><strong>Contato:</strong> <%= p.getContato() %></p>
            <p><strong>Plano:</strong> <%= p.getPlanoSaude()%></p>
            
        </div>
        <div class="card-footer text-center">
            <a href="Paciente" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    </div>

    <% 
        } else {
    %>
        <div class="alert alert-danger">
            <h1>Paciente não encontrado!</h1>
            <a href="Paciente" class="btn btn-primary">Voltar para a Lista</a>
        </div>
    <% 
        } 
    %>

</body>
</html>
