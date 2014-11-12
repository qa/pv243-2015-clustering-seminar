/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package cz.muni.fi.pv243.seminar.clustering.ejb.remote.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cz.muni.fi.pv243.seminar.clustering.ejb.remote.stateful.StatefulRemote;

/**
 * @author Ondrej Chaloupka
 */
public class StatefulRemoteClient {

    public static void main(String[] args) throws Exception {
        StatefulRemote statefulBean = lookupStatefulRemote();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Send string to server and lookup the list of them
        // Watch how the cluster behaves when one of the nodes goes down
        Thread.sleep(500); // wait for cluster is prepared
        System.out.println("To exit enter 'q', to print list of strings 'print', to add string to list on server 'add':");
        System.out.print("$ ");
        String input = br.readLine().trim();

        while (!"q".equals(input) && !"quit".equals(input)) {

            // printing strings from server
            if(input.startsWith("print")) {
                System.out.println("Printing strings from server");
                int number = 1;
                for(String str: statefulBean.getStrings()) {
                    System.out.println(String.format("String %d: %s", number++, str));
                }
            }

            // adding strings to server
            if(input.startsWith("add")) {
                String[] splittedString = input.split(" ", 2);
                String stringToAdd;
                if(splittedString.length <= 1) {
                    System.out.print("Get me string to add: ");
                    stringToAdd = br.readLine();
                } else {
                    stringToAdd = splittedString[1];
                }
                statefulBean.addString(stringToAdd);
                System.out.println("String '" + stringToAdd + "' was send to server.");
            }

            System.out.print("$ ");
            input = br.readLine().trim();
        }
    }

    /**
     * Do remote lookup.
     *
     * @see https://docs.jboss.org/author/display/WFLY8/EJB+invocations+from+a+remote+client+using+JNDI
     */
    private static StatefulRemote lookupStatefulRemote() throws NamingException {
        final Properties jndiProperties = new Properties();

        // Set up properties
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context ctx = new InitialContext(jndiProperties);

        // JNDI lookup for stateful bean
        return (StatefulRemote) ctx
                .lookup("ejb:/server-side//StatefulBean!" + StatefulRemote.class.getName() + "?stateful");
    }
}
