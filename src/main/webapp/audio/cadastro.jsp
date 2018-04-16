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
        <h1>Cadastro do audio!</h1>
        <%
            Usuario user = (Usuario) request.getSession(false).getAttribute("user");
        %>
        <%= user %>
        <form method="POST" action="cadastro" enctype="multipart/form-data">
            <div>
                <label for="titulo">Titulo do audio: </label>
                <input type="text" name="titulo"/>
            </div>
            <div>
                <label for="arquivo">Arquivo de audio: </label>
                <input type="file" name="arquivo"/>
            </div>
            <input type="submit" value="Enviar"/>
        </form>
    </body>
</html>
