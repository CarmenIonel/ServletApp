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

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {

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
        request.getRequestDispatcher("/create.html").include(request,response);

        String flightNumber = request.getParameter("flightNumber");
        String airplaneType = request.getParameter("airplaneType");
        String departureCity = request.getParameter("departureCity");
        String departureTime = request.getParameter("departureTime");
        String arrivalCity = request.getParameter("arrivalCity");
        String arrivalTime = request.getParameter("arrivalTime");

        City departCity=null;
        City arrivCity=null;

        if(departureCity.equals("")) {
            ok = false;
        }
        else {
            departCity = cityDAO.findByName(departureCity);
        }

        if(arrivalCity.equals("")) {
            ok = false;
        }
        else{
            arrivCity = cityDAO.findByName(arrivalCity);
        }

        if(departCity==null || arrivCity==null) {
            city = false;
        }

        if(flightNumber.equals("") || airplaneType.equals("") || departureTime.equals("") || arrivalTime.equals("") ){
            ok=false;
        }

        if (ok && city){
            Flight flight=new Flight(flightNumber,airplaneType,departureCity,departureTime,arrivalCity,arrivalTime);
            flightDAO.addFlight(flight);
            ServletContext servletContext = getServletContext();
            servletContext.getRequestDispatcher("/adminIndex.html").forward(request, response);

        }
        else {
            if(ok==false)
                out.print("Fill all data!");
            else
                if(city==false)
                    out.print("Departure city or arrival city does not exists!");
        }

        out.close();

    }
}
