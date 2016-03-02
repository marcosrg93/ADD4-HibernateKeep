<%-- 
    Document   : login
    Created on : 20-feb-2016, 2:05:35
    Author     : marco
--%>

<%@page import="hibernate.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



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

        <title>Signin</title>

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
                                    <li><a href="#">Sobre Nosotros</a></li>
                                    <li class="active"><a href="login.html">Iniciar Sesión</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>


                    <div class="inner cover">

                        <form class="form-signin" >
                            <h2 class="form-signin-heading">Iniciar Sesión</h2>
                            <label for="inputEmail" name="login" class="sr-only">Emai</label>
                            <input type="email" name="login" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                            <label for="inputPassword" class="sr-only">Contraseña</label>
                            <input type="password" name="pass" id="inputPassword" class="form-control" placeholder="Password" required>
                            
                            <%
                               
                                //localhost:8080/Keep/go?tabla=usuario&op=login&login=pepe&pass=pepe&origen=web&accion=
                            %>
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                            <input type="hidden" name="tabla" value="usuario" />
                            <input type="hidden" name="op" value="login" />
                            <input type="hidden" name="origen" value="web" />
                            <input type="hidden" name="accion" value="" />

                            
                        </form>
                    </div>

                    <div class="mastfoot">
                        <div class="inner">
                            <p>Cover template for <a href="http://getbootstrap.com">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>
                        </div>
                    </div>

                </div>

            </div>
            <!-- /container -->
        </div>

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="js/ie10-viewport-bug-workaround.js"></script>
    </body>

</html>
