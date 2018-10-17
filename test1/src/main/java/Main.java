import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.logging.SimpleFormatter;

public class Main {

    //  private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

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
                    logger.info("Закончились финики");
                    System.out.println("------------------------------------------");
                    System.out.println("Товар закончился");
                    System.out.println("------------------------------------------");
                    wait(10000);
                    this.put_finiki();

                } catch (InterruptedException e) {
                }
            }

            if ((apples <= 0 && product_next == 2) || (oranges <= 0 && product_next == 3)) {
                try {
                    if (apples <= 0) {
                        logger.info("Яблоки закончились");
                    }
                    if (oranges <= 0) {
                        logger.info("Апельсины закончились");
                    }
                    System.out.println("------------------------------------------");
                    System.out.println("Товар закончился");
                    System.out.println("------------------------------------------");
                    wait(10000);
                    this.put_oranges_apples();

                } catch (InterruptedException e) {
                }
            }
            switch (product_first) {
                case 1:
                    logger.info("Покупатель " + customer + " на кассе " + num + " купил 1 финик");
                    finiki--;
                    System.out.println("Покупатель " + customer + " на кассе " + num + " купил 1 финик");
                    break;
                case 2:
                    logger.info("Покупатель " + customer + " на кассе " + num + " купил 1 яблоко");
                    apples--;
                    System.out.println("Покупатель " + customer + " на кассе " + num + " купил 1 яблоко");
                    break;
                case 3:
                    logger.info("Покупатель " + customer + " на кассе " + num + " купил 1 апельсин");
                    oranges--;
                    System.out.println("Покупатель " + customer + " на кассе " + num + " купил 1 апельсин");
                    break;
            }System.out.println("Фиников на складе: " + finiki);
            System.out.println("Яблок на складе: " + apples);
            System.out.println("Апельсинов на складе: " + oranges);
            customer++;
            product_first = product_next;
            try {
                System.out.println("------------------------------------------");
                System.out.println("Следующий покупатель ждёт " + sec + "сек.");
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
                logger.info("Поступление фиников");
                finiki = 6;
                System.out.println("------------------------------------------");
                System.out.println("Поступление фиников");
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
                logger.info("Поступление яблок");
                apples = 6;
                System.out.println("------------------------------------------");
                System.out.println("Поступление яблок");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println();
                notify();
            }
            if (oranges <= 0) {
                logger.info("Поступление апельсинов");
                oranges = 6;
                System.out.println("------------------------------------------");
                System.out.println("Поступление апельсинов");
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
        System.out.println("Покупатель вошел на кассу " + num);
        System.out.println();
        while (store.customer <= 40) {
            store.get(num);
        }
        /* System.out.println("------------------------------------------");
        System.out.println("Товар закончился");
        System.out.println("------------------------------------------");*/
    }
}