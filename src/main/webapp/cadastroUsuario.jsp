<%-- 
    Document   : cadastroUsuario
    Created on : Apr 12, 2018, 4:32:49 AM
    Author     : paulo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Usuário</title>
    </head>
    <body>
        <h1>Cadastro</h1>
        <form action="cadastro" method='POST'>
           <label for="nome">Nome</label>
           <input type="text" name="nome" id="email" value="nome"><br>
           <label for="email">Email</label>
           <input type="text" name="email" id="email" value="email"><br>
           <label for="senha">Senha</label>
           <input type="password" name="senha" id="senha" value="senha"><br>
           <input type="submit" value="cadastrar">
        </form>
    </body>
</html>
