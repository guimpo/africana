<%-- 
    Document   : login
    Created on : Apr 10, 2018, 11:24:08 PM
    Author     : paulo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Página de Login</h1>
        <form action="Login" method='POST'>
           <label for="email">Email</label>
           <input type="text" name="email" id="email" value="email"><br>
           <label for="senha">Senha</label>
           <input type="password" name="senha" id="senha" value="senha"><br>
           <input type="submit" value="logar">
        </form>
    </body>
</html>
