<%-- 
    Document   : lista
    Created on : 15/04/2018, 21:36:50
    Author     : josevictor
--%>

<%@page import="java.io.File"%>
<%@page import="com.utfpr.audiomanager.model.Audio"%>
<%@page import="java.util.List"%>
<%@page import="com.utfpr.audiomanager.dao.AudioDao"%>
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
                                    <h1 class="subtitle">Usuario, <%= currentUser.getNome() %></h1>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="cadastro">
                                        <span class="icon">
                                            <i class="fa fa-plus-circle"></i>
                                        </span>
                                        <span>Adicionar Audio</span>
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
                <section class="hero is-info">
                    <div class="hero-body">
                        <div class="container">
                            <div class="card">
                                <div class="card-content">
                                    <div class="content">
                                        <div class="control has-icons-left has-icons-right">
                                            <form action="lista" method="GET">
                                                <input class="input is-large" type="text" placeholder="Buscar por Titulo" name="titulo">
                                                <span class="icon is-medium is-left">
                                                    <i class="fa fa-search"></i>
                                                </span>
                                                <button type="submit" class="icon is-medium is-right" value="Buscar">
                                                    <i class="fa fa-empire"></i>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <section class="box cta">
                    <div class="container">
                        <div class="card">
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
                            <div class="card-header">
                                <p class="card-header-title">Audios</p>
                            </div>
                        </div>
                        <div class="card-table">
                            <div class="content">
                                <table class="is-full-width is-stripped">
                                    <thead>
                                    <th>Titulo</th>
                                    <th>Arquivo</th>
                                    </thead>
                                    <tbody>
                                        <%
                                            Usuario user = (Usuario) request.getSession(false).getAttribute("user");
                                            String queryTitulo = request.getParameter("titulo");
                                            List<Audio> audios = null;
                                            if (queryTitulo == null) {
                                                audios = new AudioDao().getAudiosByUsuario(user);
                                            } else {
                                                audios = new AudioDao().getAudiosByUsuarioAndTitulo(user, queryTitulo);
                                            }
                                            String uploadPath = request.getServletContext().getRealPath("")
                                                    + File.separator + "uploads";
                                        %>
                                        <%
                                            for (Audio audio : audios) {
                                        %>
                                        <tr>
                                            <td><%= audio.getTitulo()%></td>
                                            <td><a href="download?filename=<%= audio.getCaminho()%>">Download</a></td>
                                        </tr>
                                        <% }%>        
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>                    
                </section>
            </div>
        </section>
    </body>
</html>
