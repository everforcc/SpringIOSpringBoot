package cn.cc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * 简易多 MySQL 主机批量执行工具。
 *
 * <p>使用方式示例：
 * <pre>
 * List&lt;String&gt; hosts = Arrays.asList("10.0.0.11", "10.0.0.12");
 * String sql = "UPDATE user SET status = ? WHERE id = ?";
 * Map&lt;String, JDBCUtils.ExecutionResult&gt; result =
 *     JDBCUtils.executeOnHosts(hosts, "prod_db", 3306, "ops_user", "secret", sql,
 *         (host, ps) -&gt; {
 *             ps.setString(1, "ACTIVE");
 *             ps.setLong(2, 123L);
 *         });
 * </pre>
 */
public final class JDBCUtils {

    public static void main(String[] args) {
        // 开始时间
        long startTime = System.currentTimeMillis();
        System.out.println("开始执行");
        List<String> hosts = Arrays.asList("192.168.0.30", "192.168.0.5");
        String sql = "insert into gen_table(table_id,table_name) " +
                "values(?, ?);";
        Map<String, JDBCUtils.ExecutionResult> result =
                JDBCUtils.executeOnHosts(hosts, "bmclocal", 3306, "root", "znkj123456", sql,
                        (host, ps) -> {
                            try {
                                ps.setLong(1, 5L);
                                ps.setString(2, "JDBCUtilsTest");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
        result.forEach((host, result1) -> {
            System.out.println("执行结果：" + result1);
        });
        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("执行完毕，耗时：" + (endTime - startTime) + "ms");
    }

    private static final String URL_TEMPLATE =
            "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8"
                    + "&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("缺失 mysql-connector-java 依赖", e);
        }
    }

    private JDBCUtils() {
    }

    /**
     * 在多台 MySQL 主机上执行相同的更新/插入语句。
     *
     * @param hosts           需要执行的主机 IP 列表
     * @param database        目标数据库名
     * @param port            MySQL 端口
     * @param username        账号
     * @param password        密码
     * @param sql             待执行的 DML 语句（可包含 ? 占位符）
     * @param parameterBinder 设置 PreparedStatement 参数的回调
     * @return 每个主机的执行结果 Map
     */
    public static Map<String, ExecutionResult> executeOnHosts(
            List<String> hosts,
            String database,
            int port,
            String username,
            String password,
            String sql,
            BiConsumer<String, PreparedStatement> parameterBinder) {

        if (hosts == null || hosts.isEmpty()) {
            return Collections.emptyMap();
        }
        Objects.requireNonNull(database, "database 不能为空");
        Objects.requireNonNull(username, "username 不能为空");
        Objects.requireNonNull(password, "password 不能为空");
        Objects.requireNonNull(sql, "sql 不能为空");

        Map<String, ExecutionResult> resultMap = new LinkedHashMap<>();

        for (String host : hosts) {
            System.out.println("正在执行 SQL 语句：" + sql + "，目标主机：" + host);
            String url = buildJdbcUrl(host, port, database);
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                if (parameterBinder != null) {
                    parameterBinder.accept(host, preparedStatement);
                }

                int affected = preparedStatement.executeUpdate();
                resultMap.put(host, ExecutionResult.success(affected));
            } catch (SQLException ex) {
                resultMap.put(host, ExecutionResult.failure(ex));
            }
            System.out.println("执行结果：" + resultMap.get(host));
        }

        return resultMap;
    }

    private static String buildJdbcUrl(String host, int port, String database) {
        return String.format(URL_TEMPLATE, host, port, database);
    }

    /**
     * 单个主机执行结果。
     */
    public static final class ExecutionResult {
        private final boolean success;
        private final int rowsAffected;
        private final String errorMessage;

        private ExecutionResult(boolean success, int rowsAffected, String errorMessage) {
            this.success = success;
            this.rowsAffected = rowsAffected;
            this.errorMessage = errorMessage;
        }

        public static ExecutionResult success(int rowsAffected) {
            return new ExecutionResult(true, rowsAffected, null);
        }

        public static ExecutionResult failure(Exception ex) {
            return new ExecutionResult(false, -1, ex.getMessage());
        }

        public boolean isSuccess() {
            return success;
        }

        public int getRowsAffected() {
            return rowsAffected;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return "ExecutionResult{"
                    + "success=" + success
                    + ", rowsAffected=" + rowsAffected
                    + ", errorMessage='" + errorMessage + '\''
                    + '}';
        }
    }

}