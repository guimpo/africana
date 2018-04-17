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
    </head>
    <body>
        <%
            Usuario user = (Usuario) request.getSession(false).getAttribute("user");
            String queryTitulo = request.getParameter("titulo");
            List<Audio> audios = null;
            if (queryTitulo == null) {
                audios = new AudioDao().getAudiosByUsuario(user);
            } else {
                audios = new AudioDao().getAudiosByUsuario(user);
            }
            String uploadPath = request.getServletContext().getRealPath("")
                + File.separator + "uploads";
        %>
        <a href="../usuario/sair">Deslogar</a>
        <a href="cadastro">Cadastro de Audio</a>
        <form action="lista" method="GET">
            <div>
                <label for="titulo">Buscar por Titulo</label>
                <input type="text" name="titulo"/>
                <input type="submit" value="Buscar" />
            </div>
        </form>
        <table>
            <thead>
                <th>Titulo</th>
                <th>Arquivo</th>
            </thead>
            <tbody>
        <%
            for(Audio audio : audios) {
        %>
        <tr>
            <td><%= audio.getTitulo() %></td>
            <td><a href="download?filename=<%= audio.getTitulo() %>">Arquivo</a></td>
        </tr>
        <% } %>
            </tbody>
        </table>
    </body>
</html>
