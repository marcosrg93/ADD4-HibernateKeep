<%@page import="java.util.List"%>
<%@page import="hibernate.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Persona> lista = (List<Persona>)request.getAttribute("listado");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Yo soy view.jsp.</h1>
        <a href="index.html?tabla=persona&op=create&accion=view&origen=android">Añadir una nueva persona</a>
        <table border="1">
            <thead>
                <tr>
                    <th>DNI</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Operacion</th>
                </tr>
            </thead>
            <tbody>
                <%
                for(Persona p: lista){
                    %>
                    <tr>
                        <td><%= p.getDni() %></td>
                        <td><%= p.getNombre()%></td>
                        <td><%= p.getApellidos()%></td>
                        <td><a href="index.html?tabla=persona&op=update&accion=view&dni=<%= p.getDni() %>">Editar</a></td>
                        <td><a href="index.html?tabla=persona&op=delete&accion=do&dni=<%= p.getDni() %>" class="borrar">Borrar</a></td>
                    </tr>
                    <%
                }
                %>               
            </tbody>
        </table>
            <script>
                var elementos=document.getElementsByClassName("borrar");
                for(var i=0;i<elementos.length;i++){
                    agregarEventoClick(elementos[i]);
                }
                function agregarEventoClick(elemento){
                    elemento.addEventListener("click",function(event){
                       var respuesta=confirm("Seguro?"); 
                       if(!respuesta){
                           event.preventDefault();
                       }
                    });
                }
            </script>
    </body>
</html>
