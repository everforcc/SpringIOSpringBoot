package design.jdkp.base;

public class Client {

    public static void main(String[] args) {
        JDKProxy jdkProxy = new JDKProxy();
        Rent rentProxy = (Rent) jdkProxy.createProxy(new Host());
        rentProxy.rent();
    }

}
