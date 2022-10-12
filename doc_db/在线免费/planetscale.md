<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [官网-planetscale](https://app.planetscale.com/everforcc/oneforall)
- 用户名密码

~~~
Username:a0yhrwlul53j
Password:pscale_pw_-Yb-3tlCQj1TxxksyjnWlIB3g-ZfgKgLGjQgQ7tiGm8
~~~

- jdbc连接

~~~
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection(
  "jdbc:mysql://6ocz8lzsq4hd.us-east-4.psdb.cloud/oneforall?sslMode=VERIFY_IDENTITY",
  "a0yhrwlul53j",
  "pscale_pw_-Yb-3tlCQj1TxxksyjnWlIB3g-ZfgKgLGjQgQ7tiGm8");
~~~

- plsql没配置好,可以用idea

</span>