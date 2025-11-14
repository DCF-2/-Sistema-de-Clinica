<%-- 
    Document   : menuMedico
    Created on : 14 de nov. de 2025, 15:13:24
    Author     : davic
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 1. Links do Bootstrap --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<%-- 2. Script de Segurança (Verifica a sessão) --%>
<c:if test="${sessionScope.medicoLogado == null}">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <%-- Se não estiver logado, redireciona para o LoginMedico.jsp (que está FORA do WEB-INF) --%>
    <script>location.href = "${contextPath}/LoginMedico.jsp";</script>
</c:if>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/Consultas">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <%-- Link para o CRUD Admin de Pacientes (Portal Admin) --%>
                    <a class="nav-link" href="${pageContext.request.contextPath}/Paciente">Gerir Pacientes</a>
                </li>

                <%-- --- ADICIONE ESTA LINHA --- --%>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Consultas?op=historico">Histórico (Prontuários)</a>
                </li>
            </ul>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Bem vindo, ${sessionScope.medicoLogado.nome}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <%-- O Servlet de logout é o LoginMedicoServlet --%>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoginMedico?op=logout">Sair</a></li>
                        </ul>
                    </li>
                 </ul>
            </div>
        </div>
    </div>
</nav>

<%-- Alerta de Mensagens (útil para o agendamento) --%>
<c:if test="${not empty sessionScope.msg}">
    <div class="container mt-2">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            ${sessionScope.msg}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="msg" scope="session"/>
    </div>
</c:if>
