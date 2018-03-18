package dao;

import entity.City;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO {

    private SessionFactory factory;

    public CityDAO(SessionFactory factory) {
        this.factory = factory;
    }

    //create
    public void addCity(City city){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.save(city);
            tx.commit();
        }catch(HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    //find by city
    public City findByName(String name){
        Session session=factory.openSession();
        Transaction tx = null;
        List<City> cities =null;
        try{
            tx=session.beginTransaction();
            Query query = session.createQuery("FROM City WHERE name = :name");
            query.setParameter("name", name);
            cities= query.list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        if(cities.isEmpty())
            return null;
        else
            return cities.get(0);
    }

    //find all
    public List<City> findAll(){
        Session session=factory.openSession();
        Transaction tx=null;
        List<City> cities = null;
        try{
            tx=session.beginTransaction();
            cities=session.createQuery("FROM City").list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        return cities;
    }

    //delete
    public void deleteCity(City city){
        Session session=factory.openSession();
        Transaction tx=null;
        try{
            tx=session.beginTransaction();
            session.delete(city);
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }
    //update
    public void updateCity(City city){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.update(city);
            tx.commit();
        }catch (HibernateError e) {
            if (tx != null)
                tx.rollback();
        }finally {
            session.close();
        }
    }
}
