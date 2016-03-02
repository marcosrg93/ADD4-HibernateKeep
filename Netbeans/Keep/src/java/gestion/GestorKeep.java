package gestion;

import hibernate.HibernateUtil;
import hibernate.Keep;
import hibernate.Usuario;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorKeep {

    public static JSONObject addKeep(Keep k, String usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Usuario u = (Usuario) sesion.get(Usuario.class, usuario);
        k.setUsuario(u);
        sesion.save(k);
        Long id = ((BigInteger) sesion.createSQLQuery("select last_insert_id()").uniqueResult())
                .longValue();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        JSONObject obj = new JSONObject();
        obj.put("r", id);
        return obj;
    }

   
     
     
    public static JSONObject getKeeps(String usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String hql = "from Keep where login = :login";
        Query q = sesion.createQuery(hql);
        q.setString("login", usuario);
        List<Keep> keeps = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        //{"r": true}
        //{"r": false}
        JSONArray array = new JSONArray();
        for (Keep k : keeps) {
            JSONObject obj = new JSONObject();
            if(k.getIdAndroid() != null){
            obj.put("ida", k.getIdAndroid());
            }else{
            obj.put("ida", 0);
            }
            obj.put("id", k.getId());
            obj.put("cont", k.getContenido());
            obj.put("est", k.getEstado());
            array.put(obj);
        }
        JSONObject obj2 = new JSONObject();
        obj2.put("r", array);
        return obj2;
    }

    public static JSONObject removeKeep(Keep k, String usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        Usuario u = (Usuario) sesion.get(Usuario.class, usuario);
        k.setUsuario(u);
        String hql = "delete from Keep where login = :login and idAndroid= :idan";
        Query q = sesion.createQuery(hql);
        q.setString("login", usuario);
        q.setInteger("idan", k.getIdAndroid());
        q.executeUpdate();
        /*
        sesion.delete(k);      
        sesion.
         */
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return new JSONObject();
    }

    public static List<Keep> getKeep() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String hql = "from Keep";
        Query q = sesion.createQuery(hql);
        List<Keep> personas = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return personas;
    }

    public static Keep getNota(int id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        Keep p = (Keep) sesion.get(Keep.class, id);

        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return p;
    }

    public static void insertKeep(Keep p) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        System.out.println(p.toString());
        sesion.save(p);
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
    }

    public static void updateKeep(int id, Keep p) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        String hql = "update Keep set contenido=?,estado=? where id=? ";
        Query query = sesion.createQuery(hql);
        query.setString(0, p.getContenido());
        query.setString(1, p.getEstado());

        query.setInteger(2, id);
        query.executeUpdate();

        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();

    }

    
      public static void updateIdAndroid(int id,Keep k) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        String hql = "update Keep set idAndroid=? where id=? ";
        Query query = sesion.createQuery(hql);
        query.setInteger(0, k.getIdAndroid());
        

        query.setInteger(1, id);
        query.executeUpdate();

        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
         
         
     }
    
    public static void deleteKeep(int id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        Keep p = (Keep) sesion.load(Keep.class, id);
        sesion.delete(p);

        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
    }

}
