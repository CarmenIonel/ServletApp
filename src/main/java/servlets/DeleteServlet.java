package servlets;

import dao.CityDAO;
import dao.FlightDAO;
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

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

    private FlightDAO flightDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        flightDAO=new FlightDAO(sf);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/delete.html").include(request,response);

        String flightNumber = request.getParameter("flightNumber");

        if(flightNumber.equals("")){
            out.print("Please fiil the flight number!");
        }
        else{
            Flight flight=flightDAO.findByFlightNumber(flightNumber);
            if(flight==null){
                out.print("This flight does not exists!");
            }
            else{
                flightDAO.deleteFlight(flight);
                ServletContext servletContext = getServletContext();
                servletContext.getRequestDispatcher("/adminIndex.html").forward(request, response);
            }
        }
        out.close();
    }
}




