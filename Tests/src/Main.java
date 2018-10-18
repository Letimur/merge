import java.util.ArrayList;
import java.util.Collection;

class NewMain {


    public static class MainInner1 {

        private final Collection<String> collection1;

        public MainInner1(final Collection<String> collection) {
            this.collection1 = collection;
            final Collection<String> collection2 = new ArrayList<String>();
        }
    }


    public static void main(String[] args) {
        final  MainInner1 mainInner1 = new MainInner1(new ArrayList<String>());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//public class Main {
//
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
//}
