<%-- 
    Document   : edit
    Created on : 26-ene-2016, 10:10:14
    Author     : izv
--%>

<%@page import="hibernate.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Persona p = (Persona)request.getAttribute("persona");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Persona</h1>
        
        <form action="index.html" method="POST">
            nombre:<input type="text" name="nombre" value="<%=p.getNombre() %>" /><br>
            apellidos:<input type="text" name="apellidos" value="<%=p.getApellidos()%>" /><br>
            dni:<input type="text" name="dni" value="<%=p.getDni() %>" /><br>
            <input type="submit" value="Editar" />
            <input type="hidden" name="pkdni" value="<%=p.getDni() %>" />
            <input type="hidden" name="tabla" value="persona" />
            <input type="hidden" name="op" value="update" />
            <input type="hidden" name="accion" value="do" />
        </form>
    </body>
</html>
