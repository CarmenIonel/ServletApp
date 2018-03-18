package servlets;

import dao.UserDAO;
import entity.User;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{

    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        userDAO = new UserDAO(new Configuration().configure().buildSessionFactory());

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/login.html").include(request,response);


        String password = request.getParameter("password");
        String username = request.getParameter("username");

        User user = null;
        user= userDAO.findByUsername(username);

        if(user ==null || !(user.getPassword().equals(password))){
            out.print("Invalid username or password!");
        }else {
            if (user.getRole().equals("admin")) {
                ServletContext servletContext = getServletContext();
                servletContext.getRequestDispatcher("/adminIndex.html").forward(request, response);
            }
            if (user.getRole().equals("user")) {
                ServletContext servletContext = getServletContext();
                servletContext.getRequestDispatcher("/userIndex.html").forward(request, response);
            }

            out.print("<br> Welcome"+user.getName());
            Cookie ck=new Cookie("username", username);
            response.addCookie(ck);
        }

        out.close();
    }
}
