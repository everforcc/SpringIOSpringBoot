package spi;

public class DBSearch implements Search{
    @Override
    public void searchDoc(String kw) {
        System.out.println("db: " + kw);
    }
}
