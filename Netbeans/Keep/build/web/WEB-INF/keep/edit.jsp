

<%@page import="hibernate.Usuario"%>
<%@page import="hibernate.Keep"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Keep k = (Keep) request.getAttribute("keep");
%>
<%
    Usuario u = (Usuario)request.getAttribute("login");
    
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
                                    <li><a href="go?tabla=keep&op=read&accion=&origen=web&login=<%=u.getLogin()%>&pass=<%=u.getPass()%>">Notas</a></li>
                                    <li><a href="#">Sobre Nosotros</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>


                    <h1>Editar Nota</h1>

                    <form class="form-signin"  action="go?tabla=keep&op=update&origen=web&accion=do&id=<%=k.getId()%>" method="POST">
                        <span class="label label-default">Autor:</span>
                        <br/>
                        <input class="form-control" type="text" name="autor" value="<%=k.getUsuario().getLogin()%>" readonly="true">
                        <br/>
                        <span class="label label-default">Contenido:</span>
                        <br/>
                        <input class="form-control" type="text" name="contenido" value="<%=k.getContenido()%>" >
                        <br/>
                        <span class="label label-default">Estado:</span><br/>
                        <select name="estado" value="<%=k.getEstado()%>" >
                            <option >estable</option>
                            <option >inestable</option>
                        </select>
                      
                        <br/>
                        <input class="btn btn-lg btn-warning" type="submit" value="Editar" />
                        <input type="hidden" name="id" value="<%=k.getId()%>" />
                         <input type="hidden" name="log" value="<%=u.getLogin()%>" />
                        <input type="hidden" name="pass" value="<%=u.getPass()%>" />
                        
                    </form>



                </div>
            </div>
        </div>

    </body>
</html>