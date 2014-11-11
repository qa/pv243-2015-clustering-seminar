Highly Available WebApplication
===============================

This example demonstrates session distribution in clustered environment.

Running:
--------

1. Build WebApp

        mvn clean package

2. Copy highly-available-webapp.war to both servers

        cp target/highly-available-webapp.war WF-1/standalone/deployments
        cp target/highly-available-webapp.war WF-2/standalone/deployments

3. Start both servers

        WF-1/bin/standalone.sh -c standalone-ha.xml -Djboss.node.name=`whoami`
        WF-2/bin/standalone.sh -c standalone-ha.xml -Djboss.socket.binding.port-offset=100 -Djboss.node.name=`whoami`2
