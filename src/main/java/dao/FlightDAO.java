package dao;

import entity.City;
import entity.Flight;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FlightDAO {

    private SessionFactory factory;

    public FlightDAO(SessionFactory factory) {
        this.factory = factory;
    }

    //create
    public void addFlight(Flight flight){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.save(flight);
            tx.commit();
        }catch(HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    //find by departure city
    public Flight findByDepartureCity(City city){
        Session session=factory.openSession();
        Transaction tx = null;
        List<Flight> flights =null;
        try{
            tx=session.beginTransaction();
            Query query = session.createQuery("FROM Flight WHERE departureCity = :city");
            query.setParameter("city", city);
            flights= query.list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        if(flights.isEmpty())
            return null;
        else
            return flights.get(0);
    }

    //find by flight number
    public Flight findByFlightNumber(String flightNumber){
        Session session=factory.openSession();
        Transaction tx = null;
        List<Flight> flights =null;
        try{
            tx=session.beginTransaction();
            Query query = session.createQuery("FROM Flight WHERE flightNumber = :flightNumber");
            query.setParameter("flightNumber", flightNumber);
            flights= query.list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        if(flights.isEmpty())
            return null;
        else
            return flights.get(0);
    }

    //find by departure city and arrival city
    public Flight findByDepartureCityAndArrivalCity(City departureCity, City arrivalCity){
        Session session=factory.openSession();
        Transaction tx = null;
        List<Flight> flights =null;
        try{
            tx=session.beginTransaction();
            Query query = session.createQuery("FROM Flight WHERE departureCity = ? AND arrivalCity = ?");
            query.setParameter(0, departureCity);
            query.setParameter(1, arrivalCity);
            flights= query.list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        if(flights.isEmpty())
            return null;
        else
            return flights.get(0);
    }

    //find all
    public List<Flight> findAll(){
        Session session=factory.openSession();
        Transaction tx=null;
        List<Flight> flights = null;
        try{
            tx=session.beginTransaction();
            flights=session.createQuery("FROM Flight").list();
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        return flights;
    }

    //delete
    public void deleteFlight(Flight flight){
        Session session=factory.openSession();
        Transaction tx=null;
        try{
            tx=session.beginTransaction();
            session.delete(flight);
            tx.commit();
        }catch (HibernateError e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    //update
    public void updateFlight(Flight flight){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.update(flight);
            tx.commit();
        }catch (HibernateError e) {
            if (tx != null)
                tx.rollback();
        }finally {
            session.close();
        }
    }
}
