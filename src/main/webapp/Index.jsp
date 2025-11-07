<%-- 
    Document   : Index
    Created on : 5 de nov. de 2025, 16:06:33
    Author     : davic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sistema de Clínica</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
            color: #333;
            text-align: center;
        }
        h1 {
            color: #0056b3;
        }
        nav {
            margin-top: 40px;
            display: flex;
            justify-content: center;
        }
        nav ul {
            list-style: none;
            padding: 0;
        }
        nav li {
            margin: 15px 0;
        }
        nav a {
            display: inline-block;
            padding: 15px 30px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            width: 300px; /* Largura fixa para os botões */
            font-size: 1.1em;
            transition: background-color 0.3s;
        }
        nav a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <h1>Bem-vindo ao Sistema de Clínica</h1>
    <p>Selecione o módulo que deseja gerenciar:</p>

    <nav>
        <ul>
            <li>
                <a href="Paciente">Gerenciar Pacientes</a>
            </li>
            <li>
                <a href="Medico">Gerenciar Médicos</a>
            </li>
            <li>
                <a href="Medicamento">Gerenciar Medicamentos</a>
            </li>
            <li>
                <a href="Indicador">Gerenciar Indicadores de Exame</a>
            </li>
        </ul>
    </nav>

</body>
</html>
