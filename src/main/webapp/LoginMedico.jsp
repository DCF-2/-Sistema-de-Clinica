<%--
    Document   : LoginMedico.jsp
    Created on : 14 de nov. de 2025
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%-- Bootstrap 5 --%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

        <title>Login do Médico</title>
        
        <style>
            body { background-color: #f4f4f4; }
            .login-container { max-width: 400px; margin-top: 100px; }
        </style>
    </head>
    <body>
        
        <%-- Se já estiver logado, redireciona para o dashboard --%>
        <c:if test="${sessionScope.medicoLogado ne null}">
            <c:redirect url="Consultas"/>
        </c:if>
        
        <div class="container login-container card p-4 shadow-sm">
            <h1 class="text-center">Bem-vindo Médico!</h1>
            
            <%-- FORMULÁRIO DE LOGIN --%>
            <form method="post" action="LoginMedico">
                <input type="hidden" name="op" value="login"/>
                
                <div class="mb-3">
                    <label for="loginCRM" class="form-label">CRM</label>
                    <input type="text" name="login" id="loginCRM" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="loginSenha" class="form-label">Senha</label>
                    <input type="password" name="senha" id="loginSenha" class="form-control" required>
                </div>
                
                <button type="submit" class="btn btn-primary w-100">Entrar</button>

                <div class="text-center mt-3">
                    <p>Não tem conta? <a href="#" data-bs-toggle="modal" data-bs-target="#modalCadastro">Registe-se</a></p>
                </div>
            </form>
            
            <%-- Exibe mensagem de erro/sucesso (vinda do Servlet) --%>
            <c:if test="${not empty sessionScope.msg}">
                <div class="alert alert-info mt-3">
                    ${sessionScope.msg}
                </div>
                <c:remove var="msg" scope="session"/>
            </c:if>

        </div>

        
        <%-- MODAL DE CADASTRO --%>
        <div class="modal fade" id="modalCadastro" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Registo de Novo Médico</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        
                        <%-- O 'action' aponta para o mesmo Servlet (sem 'op', para cair no 'else') --%>
                        <form method="post" action="LoginMedico">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">CRM</label>
                                    <input type="text" name="crm" class="form-control" required/>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Nome Completo</label>
                                    <input type="text" name="nome" class="form-control" required/>
                                </div>
                            </div>
                            <div class="row">
                                 <div class="col-md-6 mb-3">
                                    <label class="form-label">Especialidade</label>
                                    <input type="text" name="especialidade" class="form-control" required/>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Contato (Telefone)</label>
                                    <input type="text" name="contato" class="form-control"/>
                                </div>
                            </div>
                             <hr>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Senha</label>
                                    <input type="password" name="senha" class="form-control" required/>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Confirmar Senha</label>
                                    <input type="password" name="confirm" class="form-control" required/>
                                </div>
                            </div>
                            
                            <button type="submit" class="btn btn-success w-100">Criar Conta</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
    </body>
</html>