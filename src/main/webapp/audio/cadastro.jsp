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
    </head>
    <body>
        <h1>Bem vindo ao cadastro do audio!</h1>
        <%
            Usuario user = (Usuario) request.getSession(false).getAttribute("user");
        %>
        <%= user %>
        <form action="cadastro">
            <input type="text" name="titulo"/>
            <input type="submit" value="Enviar"/>
        </form>
    </body>
</html>
