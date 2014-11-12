package cz.muni.fi.pv243.seminar.clustering;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @see http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "HttpSessionServlet", urlPatterns = {"/session"})
public class HttpSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(HttpSessionServlet.class.getName());

    public static final String KEY = HttpSessionServlet.class.getName();
    public static final String READONLY = "readonly";
    public static final String INVALIDATE = "invalidate";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        // Creating new session, storing SerialBean
        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());
            session.setAttribute(KEY, new SerialBean());
        }
        SerialBean bean = (SerialBean) session.getAttribute(KEY);

        // Output the serial bean as plain text in response
        resp.setContentType("text/plain");

        // Optional readonly scenario (only for the bored)
        // Readonly?
        if (req.getParameter(READONLY) != null) {
            // Immediately write response and return
            resp.getWriter().print(bean.getSerial());
            return;
        }

        int serial = bean.getSerialAndIncrement();

        // Now store bean in the session
        session.setAttribute(KEY, bean);

        // Response
        resp.getWriter().print(serial);

        // Invalidate?
        if (req.getParameter(HttpSessionServlet.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet using Session to store SerialBean object with the serial.";
    }
}
