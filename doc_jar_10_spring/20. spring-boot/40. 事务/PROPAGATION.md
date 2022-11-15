<span  style="font-family: Simsun,serif; font-size: 17px; ">

### ### @Transactional(propagation = Propagation.REQUIRED)

### 七种

K | V
---|---
PROPAGATION_REQUIRED | row 1 col 2
PROPAGATION_SUPPORTS | 当前方法不需要事务上下文，但是如果存在当前失误的话，那么该方法会在这个十五中运行
PROPAGATION_MANDATORY | 必须在事务中，否则异常
PROPAGATION_REQUIRES_NEW | 方法必须运行在他自己的事务中,新的事物江北启动，
PROPAGATION_NOT_SUPPORTED |
PROPAGATION_NEVER | 表示当前方法不需要不应该运行在事务上下文中，如果当前有事务在运行，抛异常
PROPAGATION_NESTED |

### 失效的场景

- [原文](https://blog.csdn.net/jiahao1186/article/details/122484466)

1. 未启用事务管理
2. 方法不是public
3. 数据源未配置事务管理器
4. 自身调用问题 ！！！
5. 异常类型错误
6. 异常为捕获
7. 业务和spring不在同一个线程

</span>