package design.jdkp.aop;

public class UserDaoService {
    public static void main(String[] args) {
        //动态代理创建类
        UserDaoProxy userDaoProxy = new UserDaoProxy();
        //设置切面
        userDaoProxy.setMyAspect(new MyAspect());
        //设置代理对象
        userDaoProxy.setObject(new UserDaoImpl());
        //增强类
        UserDao proxy = (UserDao) userDaoProxy.createProxy();
        //调用方法
        //添加用户
        System.out.println("================");
        proxy.addUser();
        //删除用户
        System.out.println("================");
        proxy.deleteUser();
        //查询用户
        System.out.println("================");
        proxy.searchUser();
        //更新用户
        System.out.println("================");
        proxy.UpdateUser();
    }
}
