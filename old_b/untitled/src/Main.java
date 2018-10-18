import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {


    public static void main(String[] args) {
        Buyers buyers = new Buyers();
        buyers.creatingMassOfBuyers();


    }
}
class Store{
    private int product=10;
    public synchronized void get() {
        while (product<1) {
            try {
                wait();
            }
            catch (InterruptedException ignored) {
            }
        }
        product--;
        System.out.println("Покупатель купил 1 товар");
        System.out.println("Товаров на складе: " + product);
        notify();
    }
}

class Cass1 implements Runnable{

    private Store store;
    Cass1(Store store){
        this.store=store;
    }
    public void run(){
        for (int i = 1; i < 5; i++) store.get();
    }
}
class Cass2 implements Runnable{

    private Store store;
    Cass2(Store store){
        this.store=store;
    }
    void checkBuyerPriority(){

    }
    public void run(){
        for (int i = 1; i < 5; i++) store.get();
    }
}


class Buyers {
    //добавление случайного приоритета покупателям
    private final Random random = new Random();
    int buyerPriority;
    int buyerNumber=0;

    private int setBuyerPriority() {
        return buyerPriority = (random.nextInt(9)+1);
    }
    void creatingMassOfBuyers(){
        int[][] massOfBuyers = new int[2][10];
        buyerNumber=1;
        for(int i=0;i<10;i++) {

            massOfBuyers[0][i] = i+1;
            massOfBuyers[1][i] = setBuyerPriority();;
            System.out.println("Покупатель №" + massOfBuyers[0][i] + " имеет приоритет " + massOfBuyers[1][i]);
        }
        Arrays.sort(massOfBuyers, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        });
        for(int i=0;i<10;i++) System.out.println("Покупатель №" + massOfBuyers[0][i] + " имеет приоритет " + massOfBuyers[1][i]);
    }

   /* void createBuyer(buyerPriority) {
        //задаём случайный приоритет покупателю
        setBuyerPriority();
        //запоминаем покупателя, чтобы не дать тому-же покупателю


    }*/
}