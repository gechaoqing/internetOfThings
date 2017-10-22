package com.cg.mina.dev;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by gecha on 2017/10/1.
 */
public class CgMinaClient {
    private static final Logger logger = LoggerFactory.getLogger(CgMinaClient.class);

    public static void main(String[] args) {
        clientInit();
    }
    private static void clientInit(){
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10000);
        connector.getFilterChain().addLast("logger",new LoggingFilter());
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        setClientHandler(connector);
        ConnectFuture future = connector.connect(new InetSocketAddress("localhost",CgMinaServer.SERVER_PORT));
        future.awaitUninterruptibly();
        IoSession session = future.getSession();
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }

    private static void setClientHandler(IoConnector connector){
        connector.setHandler(new IoHandler() {
            @Override
            public void sessionCreated(IoSession session) throws Exception {

            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                for(int i=0;i<10;i++){
                    session.write("the index is "+i);
                }
                session.write("the index send complete!");
                session.write("bye!");
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {

            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

            }

            @Override
            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                logger.error(cause.getMessage(), cause);
                session.close(true);
            }

            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                logger.info("client messageReceived:{}",message);
                if("bye!".equals(message)){
                    session.close(true);
                }
            }

            @Override
            public void messageSent(IoSession session, Object message) throws Exception {
                logger.info("client messageSent:{}",message);
            }
        });
    }
}
