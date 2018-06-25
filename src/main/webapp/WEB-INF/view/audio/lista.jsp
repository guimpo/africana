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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JSP Page</title>
  <link rel="stylesheet" href="../styles/bulma.css" />
  <link rel="stylesheet" href="../styles/style.css" />
  <link rel="stylesheet" href="../styles/login.css" />
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha256-eZrrJcwDc/3uDhsdt61sL2oOBY362qM3lon1gyExkL0="
    crossorigin="anonymous" />
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
                <h1 class="subtitle">
                  <fmt:message key="audio.usuario" />, ${sessionScope.user.nome}</h1>
              </span>
              <span class="navbar-item">
                <a class="button is-white is-outlined" href="../audio/cadastro">
                  <span class="icon">
                    <i class="fa fa-plus-circle"></i>
                  </span>
                  <span>
                    <fmt:message key="audio.adicionar" />
                  </span>
                </a>
              </span>
              <span class="navbar-item">
                <a class="button is-white is-outlined" href="sair">
                  <span class="icon">
                    <i class="fa fa-sign-out"></i>
                  </span>
                  <span>
                    <fmt:message key="audio.sair" />
                  </span>
                </a>
              </span>
            </div>
          </div>
      </nav>
      
        <div class="hero-body">
          <div class="container">
            <div class="card">
              <div class="card-content">
                <div class="content">
                  <div class="control has-icons-left has-icons-right">
                    <form action="lista" method="GET">
                      <fmt:message key="audio.busca" var="buscalabel" />
                      <input class="input is-large" type="text" placeholder="${buscalabel}" name="titulo">
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
        <button id="listar">Listar</button>
      
      <section class="box cta">
        <div class="container">
          <div class="card">
            <c:if test="${sessionScope.suMessage != null}">
              <div class="notification is-success">
                <strong>
                  <fmt:message key="site.sucesso" />,</strong>
                <c:out value='${sessionScope.suMessage}' />
                <c:set var="suMessage" value="" scope="session" />
              </div>
            </c:if>
            <c:if test="${sessionScope.erMessage != null}">
              <div class="notification is-danger">
                <strong>
                  <fmt:message key="site.erro" />,</strong>
                <c:out value='${sessionScope.erMessage}' />
                <c:set var="erMessage" value="" scope="session" />
              </div>
            </c:if>
            <div class="card-header">
              <p class="card-header-title">Audios</p>
            </div>
          </div>
          <div class="card-table">
            <div class="content">
              <table id="tabela" class="is-full-width is-stripped">
                <thead>
                  <th>
                    <fmt:message key="audio.titulo" />
                  </th>
                  <th>
                    <fmt:message key="audio.arquivo" />
                  </th>
                </thead>
                <tbody>
                  <c:forEach items="${sessionScope.audios}" var="audio">
                    <!-- <tr>
                      <td>${audio.titulo}</td>
                              <td><a href="download?filename=${audio.caminho}">Download</a></td>
                    </tr> -->
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </section>
      </div>
  </section>
</body>
<script>
  function downloadAudio(obj) {
    console.log(obj.value);

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/api/audio2/" + obj.value);
    xhr.setRequestHeader("Authorization", "Basic " + sessionStorage.getItem("basicAuth"));
    xhr.setRequestHeader("Content-type", "application/download;");
    xhr.responseType = 'arraybuffer',
    xhr.onreadystatechange = function () {
      if (xhr.status == 200 && xhr.readyState == 4) {
        // Create a new Blob object using the 
        //response data of the onload object
        var blob = new Blob([this.response], { type: 'application/octet-stream' });
        //Create a link element, hide it, direct 
        //it towards the blob, and then 'click' it programatically
        let a = document.createElement("a");
        a.style = "display: none";
        document.body.appendChild(a);
        //Create a DOMString representing the blob 
        //and point the link element towards it
        let url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = 'myFile.mp3';
        //programatically click the link to trigger the download
        a.click();
        //release the reference to the file by revoking the Object URL
        window.URL.revokeObjectURL(url);
      }
    }
    xhr.send();
  };

  $("#listar").click(function (event) {
    event.preventDefault();
    // var email = $('#email').val();
    // var senha = $('#senha').val();

    // sessionStorage.setItem("basicAuth", btoa(email + ":" + senha));

    $.ajax({
      url: '/api/audio2',
      type: 'GET',
      //              dataType: 'json',
      contentType: "application/json; charset=utf-8",
      //data: dados,
      beforeSend: function (xhr) {
        xhr.setRequestHeader("Authorization", "Basic " + sessionStorage.getItem("basicAuth"));
      },
      success: function (data) {
        //console.log(data);
        data.forEach(element => {
          var novaLinha = 
            "<tr>"
          +  "<td>" 
          +    element.titulo
          +  "</td>"
          +  "<td>"
          +    "<button onclick='downloadAudio(this)'"
          +      "value='" + element.id + "'>Download"
          +    "</button>"
          +  "</td>"
          + "</tr>";
          $("#tabela tbody").append(novaLinha);
        });
      },
      error: function () {
        console.log('erro');
      }
    });
  });

  // $("#formularioUpload").submit(function (event) {

  //   //stop submit the form, we will post it manually.
  //   event.preventDefault();

  //   // Get form
  //   var form = $('#formularioUpload')[0];

  //   // Create an FormData object 
  //   var data = new FormData(form);

  //   // If you want to add an extra field for the FormData
  //   // data.append("CustomField", "This is some extra data, testing");

  //   // disabled the submit button
  //   // $("#btnSubmit").prop("disabled", true);

  //   $.ajax({
  //     type: "POST",
  //     enctype: 'multipart/form-data',
  //     url: "/api/audio2",
  //     data: data,
  //     processData: false,
  //     contentType: false,
  //     cache: false,
  //     timeout: 600000,
  //     beforeSend: function (xhr) {
  //       xhr.setRequestHeader("Authorization", "Basic " + sessionStorage.getItem("basicAuth"));
  //     },
  //     success: function (data) {

  //       //$("#result").text(data);
  //       console.log("SUCCESS : ", data);
  //       //$("#btnSubmit").prop("disabled", false);

  //     },
  //     error: function (e) {

  //       $("#result").text(e.responseText);
  //       console.log("ERROR : ", e);
  //       //$("#btnSubmit").prop("disabled", false);

  //     }
  //   });

  // });
</script>

</html>