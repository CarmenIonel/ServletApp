package servlets;


import dao.CityDAO;
import dao.FlightDAO;
import entity.City;
import entity.Flight;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {

    private FlightDAO flightDAO;
    private CityDAO cityDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        boolean ok=true;
        boolean city=true;

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        cityDAO = new CityDAO(sf);
        flightDAO=new FlightDAO(sf);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/update.html").include(request,response);

        String flightNumber = request.getParameter("flightNumber");
        String airplaneType = request.getParameter("airplaneType");
        String departureCity = request.getParameter("departureCity");
        String departureTime = request.getParameter("departureTime");
        String arrivalCity = request.getParameter("arrivalCity");
        String arrivalTime = request.getParameter("arrivalTime");

        City departCity=null;
        City arrivCity=null;

        if(flightNumber.equals("")){
            out.print("Please fill the flight number!");
        }
        else{
            Flight flight=flightDAO.findByFlightNumber(flightNumber);
            if(flight==null){
                out.print("This flight does not exists!");
            }
            else{
                if(!(departureCity.equals(""))) {
                    departCity = cityDAO.findByName(departureCity);
                    if(departCity==null){
                        out.print("The departure city does not exists!");
                    }
                    else{
                        flight.setDepartureCity(departCity.getName());
                    }
                }
                if(!(arrivalCity.equals(""))) {
                    arrivCity = cityDAO.findByName(arrivalCity);
                    if(arrivCity==null){
                        out.print("The arrival city does not exists!");
                    }
                    else{
                        flight.setArrivalCity(arrivCity.getName());
                    }
                }
                if(!(airplaneType.equals(""))){
                    flight.setAirplaneType(airplaneType);
                }
                if(!(departureTime.equals(""))){
                    flight.setDepartureTime(departureTime);
                }
                if(!(arrivalTime.equals(""))){
                    flight.setArrivalTime(arrivalTime);
                }

                flightDAO.updateFlight(flight);
                ServletContext servletContext = getServletContext();
                servletContext.getRequestDispatcher("/adminIndex.html").forward(request, response);
            }
        }
        out.close();
    }
}