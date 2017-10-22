package com.cg.dev.internet.things.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gecha on 2017/10/1.
 */
public class MQ<T> {
    protected String USERNAME= ActiveMQConnection.DEFAULT_USER;
    protected String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;
    protected String URL=ActiveMQConnection.DEFAULT_BROKER_URL;
    protected AtomicInteger count = new AtomicInteger(0);
    private ConnectionFactory connectionFactory;
    private Connection connection;
    protected Session session;
    protected ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public T getFromThreadLocal() throws JMSException{
        if(threadLocal.get()!=null){
            return threadLocal.get();
        }
        return null;
    }

    protected void init(boolean transacted, int acknowledgeMode)throws Exception{
        setConnectionFactory(new ActiveMQConnectionFactory(USERNAME,PASSWORD,URL));
        setConnection(getConnectionFactory().createConnection());
        getConnection().start();
        setSession(getConnection().createSession(transacted,acknowledgeMode));
    }
}
