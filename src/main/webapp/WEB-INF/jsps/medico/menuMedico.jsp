<%-- 
    Document   : menuMedico
    Created on : 13 de nov. de 2025, 17:32:33
    Author     : davic
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- 1. URI Corrigida --%>

<%-- Links do Bootstrap (necessários em todas as páginas) --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<%-- 2. VERIFICA SESSÃO: "medicoLogado" --%>
<c:if test="${sessionScope.medicoLogado == null}">
    <%-- 3. REDIRECIONA: "LoginMedico.jsp" --%>
    <%-- Ajuste o caminho se o LoginMedico.jsp não estiver na raiz --%>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <script>location.href = "${contextPath}/LoginMedico.jsp"</script>
</c:if>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <%-- 4. HOME LINK: Aponta para o Servlet de Consultas --%>
        <a class="navbar-brand" href="Consultas">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <%-- 5. LINK PRINCIPAL: Aponta para "Consultas" --%>
                    <a class="nav-link active" aria-current="page" href="Consultas">Minhas Consultas</a>
                </li>
                
                <%-- Links adicionais (opcional) --%>
                <li class="nav-item">
                     <%-- Link para o CRUD de Pacientes (se o médico puder ver todos) --%>
                    <a class="nav-link" href="Paciente">Pacientes</a>
                </li>
                
            </ul>
            <div class="d-flex">
                <ul class="navbar-nav"> <%-- Corrigido: <li> não pode ser filho de <div>, envolvi com <ul> --%>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <%-- 6. MENSAGEM: Usa "medicoLogado.nome" --%>
                            Bem vindo, ${sessionScope.medicoLogado.nome}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown"> <%-- Adicionado "dropdown-menu-end" para alinhar à direita --%>
                            <%-- 7. LINK PERFIL: Aponta para "Medico?op=perfil" --%>
                            <li><a class="dropdown-item" href="Medico?op=perfil">Ver perfil</a></li>
                            <li><a class="dropdown-item" href="#" data-bs-toggle='modal'
                                   data-bs-target="#modalAlterarSenha">Alterar senha</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <%-- 8. LINK LOGOUT: Aponta para "Medico?op=logout" --%>
                            <li><a class="dropdown-item" href="Medico?op=logout">Sair</a></li>
                        </ul>
                    </li>
                 </ul>
            </div>
        </div>
    </div>
</nav>

<%-- 9. MODAL DE ALTERAR SENHA: Action aponta para "Medico" --%>
<div class="modal fade" id="modalAlterarSenha" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Alterar Senha</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form method="post" action="Medico">
                    <input type="hidden" name="op" value="alterarSenha"/>
                    <table class="table">
                        <tr>
                            <td>Senha Atual</td>
                            <td><input type="password" name="atual" class="form-control" required/></td>
                        </tr>
                        <tr>
                            <td>Nova Senha</td>
                            <td><input type="password" name="nova" class="form-control" required/></td>
                        </tr>
                        <tr>
                            <td>Confirmação</td>
                            <td><input type="password" name="confirm" class="form-control" required/></td>
                        </tr>
                        
                    </table>
                    <button class="btn btn-primary">Alterar</button>
                </form>
            </div>

        </div>
    </div>
</div>

<%-- Script de Alerta (Opcional, mas útil) --%>
<c:if test="${not empty msg}">
    <div class="container">
        <small id='alert' class="alert alert-${(svd eq 'ok')?'success':'danger'}">${msg}</small>
        <c:remove var="svd" scope="session"/>
        <c:remove var="msg" scope="session"/>
        <script>
            setTimeout(function () {
                document.getElementById("alert").remove();
            }, 4000)
        </script>
    </div>
</c:if>