/**
 * @Description
 * @Author everforcc
 * @Date 2023-03-30 18:55
 * Copyright
 */

package cn.cc.utils;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class JDBCBatchDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * JDBC-SQL批量操作<br>
     * 本方法只支持在事务中使用，connection的提交和回滚都交由Spring管理
     *
     * @param sql    SQL
     * @param params SQL参数
     * @throws Exception 应用级异常
     */
    public void batchExcuteTx(String sql, List<Object[]> params) throws Exception {
        if (params.isEmpty()) {
            log.info("数据库批量操作没有绑定参数，语句不执行。SQL：{}", sql);
            return;
        }
        log.info("批量操作执行SQL：{}", sql);
        Connection connection = sqlSessionTemplate.getConnection();
        // 是否自动提交
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                Object[] data = params.get(i);
                log.info("绑定SQL参数：{}", Arrays.asList(data));
                for (int j = 1; j <= data.length; j++) {
                    statement.setObject(j, data[j - 1]);
                }
                statement.addBatch();

                if (i != 0 && i % 10000 == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
            log.info("导入成功");
        } catch (Exception e) {
            log.error("批量保存失败", e);
            throw new RuntimeException("批量保存失败，失败原因：" + e.getMessage(), e);
        }
    }

}