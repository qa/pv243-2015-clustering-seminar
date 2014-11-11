package cz.muni.fi.pv243.seminar.clustering;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

import org.wildfly.clustering.web.annotation.Immutable;

/**
 * TODO: Looks like there is a bug here somewhere :-/ Please fix.
 *
 * @author Radoslav Husar
 */
@Immutable
public class SerialBean implements HttpSessionActivationListener {

    private static final Logger log = Logger.getLogger(SerialBean.class.getName());

    private int serial;

    public int getSerial() {
        return serial;
    }

    public int getSerialAndIncrement() {
        int retVal = this.getSerial();
        serial++;
        return retVal;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    /**
     * @see http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html
     * @param stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {

        // TODO: serialize the object (use just the default method?)

        // TODO: log we are writing the session
    }

    /**
     * @see http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {

        // TODO: deserialize the object

        // TODO: add logging
    }

    /**
     * @see http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionActivationListener.html
     * @param se
     */
    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {

        // TODO: add logging
    }

    /**
     * @see http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionActivationListener.html
     * @param se
     */
    @Override
    public void sessionDidActivate(HttpSessionEvent se) {

        // TODO: add logging
    }
}
