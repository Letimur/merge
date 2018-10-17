import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.logging.SimpleFormatter;
import java.util.jar.Manifest;

public class Main {

    //  private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        System.setProperty("console.encoding","Cp866");
        final Store store = new Store();
        Cass cass1 = new Cass(store, 1);
        Cass cass2 = new Cass(store, 2);
        // logger.info("Запускаются 2 потока");
        //System.out.println();

        new Thread(cass1).start();
        new Thread(cass2).start();

    }
}
// Класс Магазин, хранящий произведенные товары

class Store {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static FileHandler fh;

    protected int finiki = 6;
    protected int apples = 6;
    protected int oranges = 6;
    Random random = new Random();

    int sec = (random.nextInt(7)+3);
    int product_first = (random.nextInt(2)+ 1);
    int product_next = (random.nextInt(2)+ 1);
    protected int customer = 1;

    public void get(int num) {
        synchronized (this) {

            /*try {
                fh = new FileHandler("C:\\Users\\ulyanov\\Desktop\\log\\store.log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.setUseParentHandlers(false);*/

            sec = (random.nextInt(2)+ 3);
            product_next = (random.nextInt(2)+ 1);
            if (finiki <= 0 && product_next == 1) {
                try {
                    logger.info("dates has ended");
                    System.out.println("------------------------------------------");
                    System.out.println("Stuff has ended");
                    System.out.println("------------------------------------------");
                    wait(10000);
                    this.put_finiki();

                } catch (InterruptedException e) {
                }
            }

            if ((apples <= 0 && product_next == 2) || (oranges <= 0 && product_next == 3)) {
                try {
                    if (apples <= 0) {
                        logger.info("Apples has ended");
                    }
                    if (oranges <= 0) {
                        logger.info("Oranges has ended");
                    }
                    System.out.println("------------------------------------------");
                    System.out.println("Stuff has ended");
                    System.out.println("------------------------------------------");
                    wait(10000);
                    this.put_oranges_apples();

                } catch (InterruptedException e) {
                }
            }
            switch (product_first) {
                case 1:
                    logger.info("Buyer " + customer + " on cass " + num + " bought 1 date fruit");
                    finiki--;
                    System.out.println("Buyer " + customer + " on cass " + num + " bought 1 date fruit");
                    break;
                case 2:
                    logger.info("Buyer " + customer + " on cass " + num + " bought 1 apple");
                    apples--;
                    System.out.println("Buyer " + customer + " on cass " + num + " bought 1 apple");
                    break;
                case 3:
                    logger.info("Buyer " + customer + " on cass " + num + " bought 1 orange");
                    oranges--;
                    System.out.println("Buyer " + customer + " on cass " + num + " bought 1 orange");
                    break;
            }System.out.println("Dates in storage: " + finiki);
            System.out.println("Apples in storage: " + apples);
            System.out.println("Oranges in storage: " + oranges);
            customer++;
            product_first = product_next;
            try {
                System.out.println("------------------------------------------");
                System.out.println("Next buyer wait " + sec + "sec.");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println();
                wait(sec * 1000);
            } catch (InterruptedException e) {
            }
        }

    }

    //пополнение фиников
    public void put_finiki() {
        synchronized (this) {

            if (finiki <= 0) {
                logger.info("Dates incoming");
                finiki = 6;
                System.out.println("------------------------------------------");
                System.out.println("Dates incoming");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println();
                notify();
            }
        }
    }

    //пополнение яблок/апельсинов
    public void put_oranges_apples() {
        synchronized (this) {

            if (apples <= 0) {
                logger.info("Apples incoming");
                apples = 6;
                System.out.println("------------------------------------------");
                System.out.println("Apples incoming");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println();
                notify();
            }
            if (oranges <= 0) {
                logger.info("Oranges incoming");
                oranges = 6;
                System.out.println("------------------------------------------");
                System.out.println("Oranges incoming");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println();
                notify();
            }
        }
    }
}
// класс Кассы

class Cass implements Runnable {

    final Store store;
    final int num;

    Cass(Store store, int x) {
        this.store = store;
        this.num = x;
    }

    //  @Override
    public void run() {
        System.out.println("Buyer come on cass # " + num);
        System.out.println();
        while (store.customer <= 40) {
            store.get(num);
        }
        /* System.out.println("------------------------------------------");
        System.out.println("Товар закончился");
        System.out.println("------------------------------------------");*/
    }
}