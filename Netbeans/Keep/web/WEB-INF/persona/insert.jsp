<%-- 
    Document   : insert
    Created on : 02-feb-2016, 8:33:58
    Author     : izv
--%>

<%@page import="java.util.List"%>
<%@page import="hibernate.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Añadir nueva persona</h2>
        <form id="formulario" action="index.html" method="POST">
            <label for="dni">DNI:</label>
            <input type="text" name="dni" id="dni"/>
            <br/>
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" id="nombre"/>
            <br/>
            <label for="apellidos">Apellidos:</label>
            <input type="text" name="apellidos" id="apellidos"/>
            
            <input type="submit" name="boton" id="boton" value="Aceptar"/>
            
            <input type="hidden" name="tabla" value="persona" />
            <input type="hidden" name="op" value="create" />
            <input type="hidden" name="accion" value="do" />
            <input type="hidden" name="origen" value="android" />
        </form>
    </body>
</html>
