package com.cg.dev.internet.things.activemq;

import javax.jms.*;

/**
 * Created by gecha on 2017/10/1.
 */
public class Producter extends MQ<MessageProducer>{

    public void init() throws Exception{
        super.init(true,Session.SESSION_TRANSACTED);
    }
    public void sendMessage(String distName) throws JMSException,InterruptedException{
        Queue queue = getSession().createQueue(distName);
        MessageProducer messageProducer = getFromThreadLocal();
        if(messageProducer==null){
            messageProducer = session.createProducer(queue);
            threadLocal.set(messageProducer);
        }
        while(true){
            Thread.sleep(1000);
            int num = count.getAndIncrement();
            String msgStr = "我是大厂家，正常生产:"+num;
            TextMessage msg = session.createTextMessage(msgStr);
            System.out.println(msgStr);
            messageProducer.send(msg);
            session.commit();
        }
    }


}
