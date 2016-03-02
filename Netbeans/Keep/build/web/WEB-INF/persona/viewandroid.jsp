<%-- 
    Document   : viewandroid
    Created on : 03-feb-2016, 14:29:30
    Author     : marco
--%>
<%@page import="java.util.List"%>
<%@page import="hibernate.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Persona> lista = (List<Persona>)request.getAttribute("listado");
%>
 <%
                for(Persona p: lista){
                    %>
                    
                        <%= p.getDni() %>
                        <%= p.getNombre()%>
                        <%= p.getApellidos()%>
                       <% 
                       
                       %>
                    <%
                }
                %>         
