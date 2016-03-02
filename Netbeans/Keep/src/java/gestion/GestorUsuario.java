/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import hibernate.HibernateUtil;
import hibernate.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONObject;



/**
 *
 * @author izv
 */
public class GestorUsuario {
    
    
    
    
    public static JSONObject  getLogin(String login, String pass) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String hql = "from Usuario where login = :login and pass = :pass";
        Query q = sesion.createQuery(hql);
        q.setString("login", login);
        q.setString("pass", pass);
        List<Usuario> usuarios = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        //{"r": true}
        //{"r": false}
        JSONObject obj = new JSONObject();
        if(usuarios.isEmpty()){
            obj.put("r", false);
        }else{
            obj.put("r", true);
        }
        return obj;
    }
}
