package design.jdkp.aop;

//业务实现：目标类，目标类的每个方法都可以时连接点，当被增强后的方法被称为切入点
public class UserDaoImpl implements UserDao {
    public void addUser() {
        System.out.println("添加用户");
    }

    public void deleteUser() {
        System.out.println("删除用户");
    }

    public void searchUser() {
        System.out.println("查询用户");
    }

    public void UpdateUser() {
        System.out.println("更新用户");
    }
}
