package com.cg.dev.internet.things.activemq;

/**
 * Created by gecha on 2017/10/1.
 */
public class ConsumerTest {
    public static void main(String[] args) throws Exception {
        Consumer comsumer = new Consumer();
        comsumer.init();
        new Thread(new ConsumerMq(comsumer)).start();
        new Thread(new ConsumerMq(comsumer)).start();
        new Thread(new ConsumerMq(comsumer)).start();
        new Thread(new ConsumerMq(comsumer)).start();
    }

    private static class ConsumerMq implements Runnable {
        Consumer comsumer;

        public ConsumerMq(Consumer comsumer) {
            this.comsumer = comsumer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    comsumer.getMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
