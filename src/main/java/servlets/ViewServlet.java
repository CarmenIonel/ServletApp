package servlets;

import dao.CityDAO;
import dao.FlightDAO;
import entity.Flight;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

    private FlightDAO flightDAO;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        request.getRequestDispatcher("view.html").include(request, response);

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        flightDAO=new FlightDAO(sf);

        List<Flight> flights = flightDAO.findAll();

        if(flights.isEmpty()){
            out.print("No flights available!");
        }
        else{

            out.println("<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n" +
                    "<html>\n" +
                    "<head><title>View Flights</title></head>\n"+
                    "<body bgcolor = \"#f0f0f0\">\n" +"<table>"+
                    "<tr><td>Flight number</td><td>Airplane type</td><td>City departure</td><td>City arrival</td><td>Departure time</td> <td>Arrival time</td>"

            );

            for(int i=0;i<flights.size();i++) {
                out.println("<tr><td>"+flights.get(i).getFlightNumber()+"</td><td>"+flights.get(i).getAirplaneType()
                        +"</td><td>"+flights.get(i).getDepartureCity()+"</td><td>"+flights.get(i).getArrivalCity()+"</td><td>"+flights.get(i).getDepartureTime()+"</td><td>"+flights.get(i).getArrivalTime()+"</td></tr>");
            }
            out.println("<table></body></html>");
        }

        out.close();
    }
}
