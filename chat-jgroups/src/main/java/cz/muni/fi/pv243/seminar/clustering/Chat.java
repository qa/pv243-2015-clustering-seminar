package cz.muni.fi.pv243.seminar.clustering;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * @author Radoslav Husar
 */
public class Chat extends ReceiverAdapter {

    JChannel channel;
    String user_name = System.getProperty("user.name", "n/a");
    final List<String> state = new LinkedList<>();

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("New view: " + new_view);
    }

    @Override
    public void receive(Message msg) {
        String line = msg.getObject().toString();
        System.out.println(line);
        synchronized (state) {
            state.add(line);
        }
    }

    private void start() throws Exception {
        channel = new JChannel("udp.xml");
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    /**
     * This method reads input from user and sends over to other chat clients over the channel.
     */
    private void eventLoop() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (;;) {
            try {
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if (line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                line = "[" + user_name + "] " + line;
                Message msg = new Message(null, null, line);
                channel.send(msg);
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Chat().start();
    }
}
