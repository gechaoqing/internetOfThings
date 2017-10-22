package com.cg.mina.dev;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by gecha on 2017/10/1.
 */
public class CgMinaServer {

    private final static Logger logger = LoggerFactory.getLogger(CgMinaServer.class);

    public static int SERVER_PORT=9099;
    public static void main(String[] args) throws Exception {
        serverInit();
    }
    private static void serverInit() throws Exception{
        //创建I/O Service
        IoAcceptor acceptor = new NioSocketAcceptor();
        //记录日志和打印事件的消息
        acceptor.getFilterChain().addLast("logger",new LoggingFilter());
        //编码数据，将传递的数据编码成文本
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        //创建I/O Handler
        setServiceHandler(acceptor);
        acceptor.bind(new InetSocketAddress(SERVER_PORT));
    }

    private static void setServiceHandler(IoAcceptor acceptor){
        acceptor.setHandler(new IoHandler() {
            @Override
            public void sessionCreated(IoSession ioSession) throws Exception {
                logger.debug("session Created,attrs:{}",ioSession.getAttributeKeys());
            }

            @Override
            public void sessionOpened(IoSession ioSession) throws Exception {
                logger.debug("session Opened,attrs:{}",ioSession.getAttributeKeys());
            }

            @Override
            public void sessionClosed(IoSession ioSession) throws Exception {
                logger.debug("session Closed,attrs:{},config:{}",ioSession.getAttributeKeys(),ioSession.getConfig());
            }

            @Override
            public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
                logger.info("sessionIdle, status:{}",idleStatus);
            }

            @Override
            public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
                logger.error("exception Caught:{}",throwable.getLocalizedMessage());
                ioSession.close(true);
            }

            @Override
            public void messageReceived(IoSession ioSession, Object o) throws Exception {
                logger.info("message Received:{}",o);
                ioSession.write(o);
            }

            @Override
            public void messageSent(IoSession ioSession, Object o) throws Exception {
                logger.info("message Sent:{}",o);
            }
        });
    }
}
