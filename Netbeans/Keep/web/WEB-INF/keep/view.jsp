<%-- 
    Document   : view
    Created on : 20-feb-2016, 0:35:51
    Author     : marco
--%>

<%@page import="hibernate.Keep"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Keep> lista = (List<Keep>) request.getAttribute("listado");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="favicon.ico">

        <title>Signin Template for Bootstrap</title>

        <!-- Bootstrap core CSS -->
        <link href="bootstrap.min.css" rel="stylesheet">

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="cover.css" rel="stylesheet">

        <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
        <script src="js/ie-emulation-modes-warning.js"></script>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">
                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand">Notas</h3>
                            <nav>
                                <ul class="nav masthead-nav">
                                    <li ><a href="go?tabla=usuario&op=login&origen=web&accion=">Inicio</a></li>
                                    <li><a href="go?tabla=keep&op=read&accion=&origen=web&login=<%=request.getParameter("login")%>&pass=<%=request.getParameter("pass")%>">Notas</a></li>
                                    <li><a href="#">Sobre Nosotros</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>

                    <h1>Listado de Notas</h1>

                    <table border="1" class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Estado</th>
                                <th>Contenido</th>
                                <th>Ruta</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Keep p : lista) {
                            %>
                            <tr>
                                <td><%=p.getUsuario().getLogin()%></td>
                                <td><%= p.getEstado()%></td>
                                <td><%= p.getContenido()%></td>
                                <td><%= p.getRuta()%></td>
                                
                                <td><a href="go?tabla=keep&op=update&accion=&id=<%= p.getId()%>&login=<%=request.getParameter("login")%>&pass=<%=request.getParameter("pass")%>"><button type="button" class="btn btn-lg btn-warning">Editar</button></a></td>
                                <td><a href="go?tabla=keep&op=delete&origen=web&accion=do&id=<%= p.getId()%>" class="borrar"><button type="button" class="btn btn-lg btn-danger">Borrar</button></a></td>
                            </tr>
                            <%
                                }
                            %>               
                        </tbody>
                    </table>
                    
                        <a href="go?tabla=keep&op=create&accion=view&origen=web&login=<%=request.getParameter("login")%>&pass=<%=request.getParameter("pass")%>">
                            <button type="button" class="btn btn-lg btn-success">Añadir una nueva Nota</button>
                            </a>
                    <script>
                        var elementos = document.getElementsByClassName("borrar");
                        for (var i = 0; i < elementos.length; i++) {
                            agregarEventoClick(elementos[i]);
                        }
                        function agregarEventoClick(elemento) {
                            elemento.addEventListener("click", function (event) {
                                var respuesta = confirm("¿Borrar Nota?");
                                if (!respuesta) {
                                    event.preventDefault();
                                }
                            });
                        }
                    </script>
                </div>
            </div>
        </div>

    </body>
</html>