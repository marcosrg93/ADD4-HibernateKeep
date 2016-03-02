/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import gestion.GestorKeep;
import gestion.GestorUsuario;
import hibernate.Keep;
import hibernate.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author izv
 */
@WebServlet(name = "Controlador", urlPatterns = {"/go"})
public class Controlador extends HttpServlet {

    enum Camino {
        forward, redirect, print;
    }

    class Destino {

        public Camino camino;
        public String url;
        public String texto;

        public Destino() {
        }

        public Destino(Camino camino, String url, String texto) {
            this.camino = camino;
            this.url = url;
            this.texto = texto;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tabla = request.getParameter("tabla");//persona, idioma, etc.
        String op = request.getParameter("op");//login, create, read, update, delete
        String accion = request.getParameter("accion");//view, do
        String origen = request.getParameter("origen");//android, web
        Destino destino = handle(request, response, tabla, op, accion, origen);
        if (destino == null) {
            destino = new Destino(Camino.forward, "/WEB-INF/index.jsp", "");
        }
        if (destino.camino == Camino.forward) {
            request.getServletContext().
                    getRequestDispatcher(destino.url).forward(request, response);
        } else if (destino.camino == Camino.redirect) {
            response.sendRedirect(destino.url);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(destino.texto);
            }
        }
    }

    private Destino handle(HttpServletRequest request, HttpServletResponse response,
            String tabla, String op, String accion, String origen) {
        if (origen == null) {
            origen = "";
        }
        if (tabla == null || op == null || accion == null) {
            tabla = "usuario";
            op = "view";
            accion = "";
        }
        switch (tabla) {
            case "usuario":
                return handleUsuario(request, response, op, accion, origen);
            case "keep":
                return handleKeep(request, response, op, accion, origen);
            default:
        }
        return null;
    }

    private Destino handleUsuario(HttpServletRequest request, HttpServletResponse response,
            String op, String accion, String origen) {
        Destino d = new Destino();
        switch (op) {
            case "login":
                if (origen.equals("android")) {
                    System.out.println("Log desde ADNROID");
                    JSONObject obj = GestorUsuario.getLogin(request.getParameter("login"),
                            request.getParameter("pass"));
                    return new Destino(Camino.print, "", obj.toString());
                    //http://192.168.208.208:8080/Keep/go?tabla=usuario&op=login&login=pepe&pass=pepe&origen=android&accion=
                }
                if (origen.equals("web")) {
                    JSONObject obj = GestorUsuario.getLogin(request.getParameter("login"),
                            request.getParameter("pass"));
                    System.out.println(obj.toString());
                    if (obj.toString().contains("true")) {
                        return new Destino(Camino.redirect, "go?tabla=keep&op=read&accion=&origen=web&login="
                                + request.getParameter("login") + "&pass=" + request.getParameter("pass"), "");
                    }
                    return new Destino(Camino.forward, "/index.jsp", "");
                    //http://192.168.208.208:8080/Keep/go?tabla=usuario&op=login&login=pepe&pass=pepe&origen=android&accion=
                }else if (origen.equals("log")) {

                    return new Destino(Camino.forward, "/login.jsp", "");
                }/* else if (origen.equals("web")) {

                    return new Destino(Camino.forward, "/index.jsp", "");
                } */
            case "view":
                if (origen.equals("web")) {
                    List<Keep> lista = GestorKeep.getKeep();
                    request.setAttribute("listado", lista);
                    return new Destino(Camino.forward, "/WEB-INF/keep/view.jsp", lista + "");
                }

        }

        return null;
    }

    private Destino handleKeep(HttpServletRequest request, HttpServletResponse response,
            String op, String accion, String origen) {

        switch (op) {
            case "create":
                if (origen.equals("android")) {
                    System.out.println("CREAR desde ADNROID");
                    Keep k = new Keep(null, request.getParameter("idAndroid"),
                            request.getParameter("contenido"), null, "estable");
                    JSONObject obj = GestorKeep.addKeep(k,
                            request.getParameter("login"));
                    // GestorKeep.addKeep(k);
                    return new Destino(Camino.print, "", obj.toString());
                }
                if (accion.equals("do")) {
                    if (origen.equals("web")) {
                        //Crear objeto
                        Usuario u = new Usuario(request.getParameter("autor"), request.getParameter("pass"));
                        Keep k = new Keep();
                        k.setId(Integer.parseInt(request.getParameter("id")));
                        k.setContenido(request.getParameter("contenido"));
                        k.setEstado(request.getParameter("estado"));
                        System.out.println(k.toString() + " LOGIN" + u.getLogin());
                        GestorKeep.addKeep(k, u.getLogin());
                        System.out.println("INSERTO DATOS");
                        // destino.url = "index.html?tabla=persona&op=read&accion=view";
                         return new Destino(Camino.redirect, "go?tabla=keep&op=read&accion=&origen=web&login="
                            + request.getParameter("autor") + "&pass=" + request.getParameter("pass"), "");
                    }

                } else {
                    System.out.println("VEO EL INSERT JSP");
                    Usuario u = new Usuario(request.getParameter("login"), request.getParameter("pass"));

                    System.out.println(request.getParameter("login") + " " + request.getParameter("pass"));
                    request.setAttribute("login", u);
                    System.out.println(u.getLogin());
                    List<Keep> lista = GestorKeep.getKeep();
                    request.setAttribute("listado", lista);
                    System.out.println(lista.size() + 1);
                    return new Destino(Camino.forward, "/WEB-INF/keep/insert.jsp", "");
                }

            case "read":
                if (origen.equals("android")) {
                    System.out.println("Leo desde ADNROID");
                    JSONObject obj = GestorKeep.getKeeps(request.getParameter("login"));
                    return new Destino(Camino.print, "", obj.toString());
                }
                if (origen.equals("web")) {
                    List<Keep> lista = GestorKeep.getKeep();
                    request.setAttribute("listado", lista);
                    return new Destino(Camino.forward, "/WEB-INF/keep/view.jsp", "");
                }
            //go?tabla=keep&op=update&accion=view&id=<%= p.getId()%>"
            case "update":

                if (origen.equals("android")) {
                    System.out.println("UPDATE ID desde ADNROID");
                    int id = Integer.parseInt(request.getParameter("id"));
                    Keep k = new Keep(null, request.getParameter("idAndroid"),
                            null, null, "estable");
                    GestorKeep.updateIdAndroid(id, k);
                    // GestorKeep.addKeep(k);
                    return new Destino(Camino.print, "", " ");
                }
                if (accion.equals("do")) {
                    //editar objeto
                    Keep p = new Keep();
                    p.setEstado(request.getParameter("estado"));
                    p.setContenido(request.getParameter("contenido"));
                    int id = Integer.parseInt(request.getParameter("id"));
                    GestorKeep.updateKeep(id, p);
                    System.out.println("");
                    return new Destino(Camino.redirect, "go?tabla=keep&op=read&accion=&origen=web&login="
                            + request.getParameter("log") + "&pass=" + request.getParameter("pass"), "");
                } else {
                     Usuario u = new Usuario(request.getParameter("login"), request.getParameter("pass"));

                    System.out.println(request.getParameter("login") + " " + request.getParameter("pass"));
                    request.setAttribute("login", u);
                    System.out.println(u.getLogin());
                    Keep k = GestorKeep.getNota(Integer.parseInt(request.getParameter("id")));
                    System.out.println(request.getParameter("id"));
                    request.setAttribute("keep", k);
                    System.out.println(k.getContenido());
                    return new Destino(Camino.forward, "/WEB-INF/keep/edit.jsp", "");

                }

            case "delete":
                if (origen.equals("android")) {
                    System.out.println("BORRAR desde ADNROID");
                    Keep k = new Keep(null, request.getParameter("idAndroid"),
                            request.getParameter("contenido"), null, "estable");
                    JSONObject obj = GestorKeep.removeKeep(k, request.getParameter("login"));
                    return new Destino(Camino.print, "", obj.toString());
                }
                if (origen.equals("web")) {
                    if (accion.equals("do")) {
                        //borrar objeto
                        int id = Integer.parseInt(request.getParameter("id"));
                        GestorKeep.deleteKeep(id);
                        return new Destino(Camino.redirect, "go?tabla=keep&op=read&accion=&origen=web", "");
                    }
                }

        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*
        1 tabla :  keep, usuario
        2 op: login, create, read, update, delete
        3 accion: view, do
        4 origen: android, web
    
    
    
    
     */
}
