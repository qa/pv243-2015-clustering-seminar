Remote EJB (Clustered)
======================

This example demonstrates EJBs in clustered environment.

Running:
--------

1. Build client and server

        mvn clean install

2. Copy server-side.jar to both servers

        cd server-side/target
        cp server-side.jar WF-1/standalone/deployments
        cp server-side.jar WF-2/standalone/deployments

3. Start both servers

        WF-1/bin/standalone.sh -c standalone-ha.xml -Djboss.node.name=`whoami`
        WF-2/bin/standalone.sh -c standalone-ha.xml -Djboss.socket.binding.port-offset=100 -Djboss.node.name=`whoami`2

4. Run the client

        cd client-side
        mvn exec:java -Dexec.mainClass="cz.muni.fi.pv243.seminar.clustering.ejb.remote.client.StatelessRemoteClient"
        mvn exec:java -Dexec.mainClass="cz.muni.fi.pv243.seminar.clustering.ejb.remote.client.StatefulRemoteClient"

Don't forget to rerun 'mvn clean install' when you make changes to the code.
