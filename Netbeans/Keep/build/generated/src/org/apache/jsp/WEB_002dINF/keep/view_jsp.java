package org.apache.jsp.WEB_002dINF.keep;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import hibernate.Keep;
import java.util.List;

public final class view_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    List<Keep> lista = (List<Keep>)request.getAttribute("listado");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Yo soy view.jsp.</h1>\n");
      out.write("        <a href=\"index.html?tabla=persona&op=create&accion=view&origen=android\">Añadir una nueva persona</a>\n");
      out.write("        <table border=\"1\">\n");
      out.write("            <thead>\n");
      out.write("                <tr>\n");
      out.write("                    <th>DNI</th>\n");
      out.write("                    <th>Nombre</th>\n");
      out.write("                    <th>Apellidos</th>\n");
      out.write("                    <th>Operacion</th>\n");
      out.write("                </tr>\n");
      out.write("            </thead>\n");
      out.write("            <tbody>\n");
      out.write("                ");

                for(Keep p: lista){
                    
      out.write("\n");
      out.write("                    <tr>\n");
      out.write("                        <td>");
      out.print( p.getEstado() );
      out.write("</td>\n");
      out.write("                        <td>");
      out.print( p.getContenido() );
      out.write("</td>\n");
      out.write("                        <td>");
      out.print( p.getEstado() );
      out.write("</td>\n");
      out.write("                        <td><a href=\"index.html?tabla=persona&op=update&accion=view&dni=");
      out.print( p.getId() );
      out.write("\">Editar</a></td>\n");
      out.write("                        <td><a href=\"index.html?tabla=persona&op=delete&accion=do&dni=");
      out.print( p.getId() );
      out.write("\" class=\"borrar\">Borrar</a></td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");

                }
                
      out.write("               \n");
      out.write("            </tbody>\n");
      out.write("        </table>\n");
      out.write("            <script>\n");
      out.write("                var elementos=document.getElementsByClassName(\"borrar\");\n");
      out.write("                for(var i=0;i<elementos.length;i++){\n");
      out.write("                    agregarEventoClick(elementos[i]);\n");
      out.write("                }\n");
      out.write("                function agregarEventoClick(elemento){\n");
      out.write("                    elemento.addEventListener(\"click\",function(event){\n");
      out.write("                       var respuesta=confirm(\"Seguro?\"); \n");
      out.write("                       if(!respuesta){\n");
      out.write("                           event.preventDefault();\n");
      out.write("                       }\n");
      out.write("                    });\n");
      out.write("                }\n");
      out.write("            </script>\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
