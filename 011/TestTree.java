import java.util.*;
public class TestTree {
    public static void main(String[] args) {
        new TestTree().go();
    }

    public void go() {
        Book b1= new Book("How Cats Work");
        Book b2= new Book("Remix Your Body");
        Book b3= new Book("Finding Emo");

        Set<Book> bookSet = new TreeSet<>();
        bookSet.add(b1);
        bookSet.add(b2);
        bookSet.add(b3);

        System.out.println(bookSet);
    }    
}
