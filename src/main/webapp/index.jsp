<%-- 
    Document   : index
    Created on : 15/04/2018, 23:04:03
    Author     : josevictor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AudioManager - Home</title>
        <link rel="stylesheet" href="./styles/bulma.css"/>
        <link rel="stylesheet" href="./styles/style.css"/>
        <link rel="stylesheet" href="./styles/landing.css"/>
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
                                <img src="./img/logo.png" alt="Logo">
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
                                    <a class="button is-white is-outlined" href="./">
                                        <span class="icon">
                                            <i class="fa fa-home"></i>
                                        </span>
                                        <span><fmt:message key="site.home"/></span>
                                    </a>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="./usuario/entrar">
                                        <span class="icon">
                                            <i class="fa fa-sign-in"></i>
                                        </span>
                                        <span><fmt:message key="site.entrar"/></span>
                                    </a>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="./usuario/cadastro">
                                        <span class="icon">
                                            <i class="fa fa-user-plus"></i>
                                        </span>
                                        <span><fmt:message key="site.cadastrar"/></span>
                                    </a>
                                </span>
                                <c:set var="clientLocale" value="${pageContext.request.locale}" />
                                ${clientLocale}
                                <c:if test="${clientLocale == 'en_US'}">
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="./localizacao?local=br">
                                        <span><fmt:message key="site.local"/></span>
                                    </a>
                                </span>
                                </c:if>
                                <c:if test="${clientLocale != 'en_US'}">
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="./localizacao?local=us">
                                        <span><fmt:message key="site.local"/></span>
                                    </a>
                                </span>
                                </c:if>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="https://github.com/josevictorferreira/audiomanager">
                                        <span class="icon">
                                            <i class="fa fa-github"></i>
                                        </span>
                                        <span><fmt:message key="site.codigo"/></span>
                                    </a>
                                </span>
                            </div>
                        </div>
                </nav>
            </div>

            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-6 is-offset-3">
                        <h1 class="title">
                            <fmt:message key="site.titulo"/>
                        </h1>
                        <h2 class="subtitle">
                            <fmt:message key="site.descricao"/>
                        </h2>
                    </div>
                </div>
            </div>

        </section>
        <script async type="text/javascript" src="./js/bulma.js"></script>
    </body>
</html>
