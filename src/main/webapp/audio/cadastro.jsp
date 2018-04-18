<%-- 
    Document   : cadastro
    Created on : 14/04/2018, 17:39:27
    Author     : josevictor
--%>

<%@page import="com.utfpr.audiomanager.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../styles/bulma.css"/>
        <link rel="stylesheet" href="../styles/style.css"/>
        <link rel="stylesheet" href="../styles/login.css"/>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha256-eZrrJcwDc/3uDhsdt61sL2oOBY362qM3lon1gyExkL0=" crossorigin="anonymous" />
    </head>
    <body>
        <section class="hero is-info is-fullheight">
            <div class="hero-head">
                <nav class="navbar">
                    <div class="container">
                        <div class="navbar-brand">
                            <a class="navbar-item" href="../">
                                <img src="../img/logo.png" alt="Logo">
                                <h1 class="title">AudioManager</h1>
                            </a>
                            <span class="navbar-burger burger" data-target="navbarMenu">
                                <span></span>
                                <span></span>
                                <span></span>
                            </span>
                        </div>
                        <div id="navbarMenu" class="navbar-menu">
                            <div class="navbar-end">
                                <span class="navbar-item">
                                    <%
                                        Usuario currentUser = (Usuario) request.getSession().getAttribute("user");
                                    %>
                                    <h1>Usuario, <%= currentUser.getNome() %></h1>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="lista">
                                        <span class="icon">
                                            <i class="fa fa-list-ul"></i>
                                        </span>
                                        <span>Listagem</span>
                                    </a>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="sair">
                                        <span class="icon">
                                            <i class="fa fa-sign-out"></i>
                                        </span>
                                        <span>Sair</span>
                                    </a>
                                </span>
                            </div>
                        </div>
                </nav>
            </div>
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white">Cadastrar Audio</h3>
                        <p class="subtitle has-text-white">Cadastre um novo arquivo MP3 vinculado a sua conta.</p>
                        <div class="box">
                            <%
                                String error = (String) session.getAttribute("er-message");
                                String success = (String) session.getAttribute("su-message");
                                if (error != null) {
                            %>
                            <div class="notification is-danger">
                                <strong>Erro</strong>
                                <%= error %>
                                <% session.removeAttribute("er-message"); %>
                            </div>
                            <% } else if (success != null) { %>
                            <div class="notification is-success">
                                <strong>Sucesso</strong>
                                <%= success %>
                                <% session.removeAttribute("su-message"); %>
                            </div>
                            <% } %>
                            <form action="cadastro" method="POST" enctype="multipart/form-data">
                                <div class="field">
                                    <div class="control">
                                        <input class="input is-large" type="text" placeholder="Titulo do Audio" autofocus="" name="titulo">
                                    </div>
                                </div>
                                <div class="field">
                                    <div class="file is-right is-info">
                                        <label class="file-label">
                                            <input class="file-input" type="file" name="arquivo" placeholder="Arquivo"/>
                                            <span class="file-cta">
                                                <span class="file-icon">
                                                    <i class="fas fa-upload"></i>
                                                </span>
                                                <span class="file-label">
                                                    Arquivo
                                                </span>
                                            </span>
                                            <span class="file-name">
                                            </span>
                                        </label>
                                    </div>
                                </div>
                                <button class="button is-block is-info is-large is-fullwidth">Cadastrar Audio</button>
                            </form>
                        </div>
                        <p class="has-text-white">
                            <a href="lista">Listagem</a>
                        </p>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
