package com.cg.dev.internet.things.activemq;

import javax.jms.*;

/**
 * Created by gecha on 2017/10/1.
 */
public class Consumer extends MQ<MessageConsumer>{
    public void init() throws Exception{
        super.init(false, Session.AUTO_ACKNOWLEDGE);
    }
    public void getMessage(String distName) throws JMSException,InterruptedException{
        Queue queue = session.createQueue(distName);
        MessageConsumer consumer = getFromThreadLocal();
        if(consumer==null){
            consumer = session.createConsumer(queue);
            threadLocal.set(consumer);
        }
        while(true){
            Thread.sleep(1000);
            TextMessage msg = (TextMessage) consumer.receive();
            if(msg!=null) {
                msg.acknowledge();
                System.out.println(Thread.currentThread().getName()+": Consumer:我是消费者，我正在消费Msg"+msg.getText()+"--->"+count.getAndIncrement());
            }else {
                break;
            }
        }
    }
}
