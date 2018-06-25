<%-- 
    Document   : cadastro
    Created on : 14/04/2018, 17:39:27
    Author     : josevictor
--%>

<%@page import="com.utfpr.audiomanager.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
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
                                    <h1><fmt:message key="audio.usuario"/> ${sessionScope.user.nome}</h1>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="lista">
                                        <span class="icon">
                                            <i class="fa fa-list-ul"></i>
                                        </span>
                                        <span><fmt:message key="audio.lista"/></span>
                                    </a>
                                </span>
                                <span class="navbar-item">
                                    <a class="button is-white is-outlined" href="sair">
                                        <span class="icon">
                                            <i class="fa fa-sign-out"></i>
                                        </span>
                                        <span><fmt:message key="audio.sair"/></span>
                                    </a>
                                </span>
                            </div>
                        </div>
                </nav>
            </div>
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white"><fmt:message key="audio.cadastrar"/></h3>
                        <p class="subtitle has-text-white"><fmt:message key="audio.descricao"/></p>
                        <div class="box">
                            <c:if test="${sessionScope.suMessage != null}">
                                <div class="notification is-success">
                                    <strong><fmt:message key="site.sucesso"/></strong>
                                    <c:out value='${sessionScope.suMessage}'/>
                                    <c:set var="suMessage" value="" scope="session"  />
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.erMessage != null}">
                                <div class="notification is-danger">
                                    <strong><fmt:message key="site.erro"/></strong>
                                    <c:out value='${sessionScope.erMessage}'/>
                                    <c:set var="erMessage" value="" scope="session"  />
                                </div>
                            </c:if>
                            <form id="formularioUpload">
                                <div class="field">
                                    <div class="control">
                                        <fmt:message key="campo.titulo" var="titulolabel"/>
                                        <input class="input is-large" type="text" placeholder="${titulolabel}" autofocus="" name="titulo">
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
                                                    <fmt:message key="campo.arquivo"/>
                                                </span>
                                            </span>
                                            <span class="file-name">
                                            </span>
                                        </label>
                                    </div>
                                </div>
                                <button type="submit" class="button is-block is-info is-large is-fullwidth"><fmt:message key="audio.cadastrar"/></button>
                            </form>
                        </div>
                        <p class="has-text-white">
                            <a href="lista"><fmt:message key="audio.lista"/></a>
                        </p>
                    </div>
                </div>
            </div>
        </section>
        <script>
            $("#formularioUpload").submit(function (event) {

                //stop submit the form, we will post it manually.
                event.preventDefault();

                // Get form
                var form = $('#formularioUpload')[0];

                // Create an FormData object 
                var data = new FormData(form);

                // If you want to add an extra field for the FormData
               // data.append("CustomField", "This is some extra data, testing");

                // disabled the submit button
                // $("#btnSubmit").prop("disabled", true);

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "/api/audio2",
                    data: data,
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 600000,
                    beforeSend: function (xhr) {
                      xhr.setRequestHeader("Authorization", "Basic " + sessionStorage.getItem("basicAuth"));
                    },
                    success: function (data) {

                        //$("#result").text(data);
                        console.log("SUCCESS : ", data);
                        //$("#btnSubmit").prop("disabled", false);

                    },
                    error: function (e) {

                        $("#result").text(e.responseText);
                        console.log("ERROR : ", e);
                        //$("#btnSubmit").prop("disabled", false);

                    }
                });
              });
        </script>
        
    </body>
</html>
