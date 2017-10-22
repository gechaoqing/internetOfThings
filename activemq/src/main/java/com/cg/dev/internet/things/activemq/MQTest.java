package com.cg.dev.internet.things.activemq;

/**
 * Created by gecha on 2017/10/1.
 */
public class MQTest {
    public static void main(String[] args) throws Exception {
        Producter producter = new Producter();
        producter.init();
        for(int i=0;i<5;i++){
            new Thread(new ProductMq(producter)).start();
        }
    }

    private static class ProductMq implements Runnable{
        private Producter producter;
        ProductMq(Producter producter){
            this.producter = producter;
        }
        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
