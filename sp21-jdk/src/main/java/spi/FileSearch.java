package spi;

public class FileSearch implements Search{
    @Override
    public void searchDoc(String kw) {
        System.out.println("文件: " + kw);
    }
}
