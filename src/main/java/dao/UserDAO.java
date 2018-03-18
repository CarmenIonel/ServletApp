package dao;

import entity.User;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {

    private SessionFactory factory;

    public UserDAO(SessionFactory factory) {
        this.factory = factory;
    }

    //create
    public void addUser(User user){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.save(user);
            tx.commit();
        }catch(HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    //find by username
    public User findByUsername(String username){
        Session session=factory.openSession();
        Transaction tx = null;
        List<User> users =null;
        try{
            tx=session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE username = :username");
            query.setParameter("username", username);
            users= query.list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        if(users.isEmpty())
            return null;
        else
            return users.get(0);
    }

    //find all
    public List<User> findAll(){
        Session session=factory.openSession();
        Transaction tx=null;
        List<User> users = null;
        try{
            tx=session.beginTransaction();
            users=session.createQuery("FROM User").list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        return users;
    }

    //delete
    public void deleteUser(User user){
        Session session=factory.openSession();
        Transaction tx=null;
        try{
            tx=session.beginTransaction();
            session.delete(user);
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    //update
    public void updateUser(User user){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.update(user);
            tx.commit();
        }catch (HibernateError e) {
            if (tx != null)
                tx.rollback();
        }finally {
            session.close();
        }
    }
}
