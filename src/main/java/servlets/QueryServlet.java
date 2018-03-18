package servlets;

import Utilities.LocalTime;
import dao.CityDAO;
import dao.FlightDAO;
import entity.City;
import entity.Flight;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet{

    private FlightDAO flightDAO;
    private CityDAO cityDAO;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        cityDAO=new CityDAO(sf);
        flightDAO=new FlightDAO(sf);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("query.html").include(request, response);

        String flightNumber = request.getParameter("flightNumber");

        if(flightNumber.equals("")){
            out.print("Please fiil the flight number!");
        }
        else {
            Flight flight = flightDAO.findByFlightNumber(flightNumber);

            if (flight == null) {
                out.print("This flight does not exists!");
            }
            else{
                LocalTime localTime=new LocalTime();
                City arrCity=cityDAO.findByName(flight.getArrivalCity());
                City depCity=cityDAO.findByName(flight.getDepartureCity());
                String arrivalTime="";
                String departureTime="";

                arrivalTime=localTime.getLocalTime(arrCity);
                departureTime=localTime.getLocalTime(depCity);

                out.println("<p>The departure city is:"+flight.getDepartureCity()+"and the local time is:"+departureTime+"</p>");
                out.println("<p>The arrival city is: "+flight.getArrivalCity()+" and the local time is:"+arrivalTime+"</p>");
            }

        }
    }

}
