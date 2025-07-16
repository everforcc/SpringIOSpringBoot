package cn.cc.busi.autosql.dao;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommonDelDao {
    
    /**
     * 查询表数据量
     * @param tableName 表名
     * @return 当前数据量
     */
    @SelectProvider(type = SqlProvider.class, method = "buildSelectCountSql")
    int selectCount(@Param("tableName") String tableName);
    
    /**
     * 查询需要删除的ID列表
     * @param tableName 表名
     * @param deleteCount 需要删除的数量
     * @return 需要删除的ID列表
     */
    @SelectProvider(type = SqlProvider.class, method = "buildSelectIdsSql")
    List<Long> selectIdsToDelete(@Param("tableName") String tableName, @Param("deleteCount") int deleteCount);
    
    /**
     * 批量删除数据
     * @param tableName 表名
     * @param ids 需要删除的ID列表
     * @return 删除的行数
     */
    @DeleteProvider(type = SqlProvider.class, method = "buildDeleteSql")
    int deleteByIds(@Param("tableName") String tableName, @Param("ids") List<Long> ids);
    
    class SqlProvider {
        
        // 构建查询数据量的SQL
        public String buildSelectCountSql(@Param("tableName") String tableName) {
            return "SELECT COUNT(*) FROM " + tableName;
        }
        
        // 构建查询需要删除ID的SQL
        public String buildSelectIdsSql(
                @Param("tableName") String tableName, 
                @Param("deleteCount") int deleteCount) {
            return "SELECT id FROM " + tableName + 
                   " ORDER BY create_time ASC " +
                   " LIMIT " + deleteCount;
        }
        
        // 构建批量删除的SQL
        public String buildDeleteSql(
                @Param("tableName") String tableName, 
                @Param("ids") List<Long> ids) {
            StringBuilder sql = new StringBuilder("DELETE FROM " + tableName + " WHERE id IN (");
            for (int i = 0; i < ids.size(); i++) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(ids.get(i));
            }
            sql.append(")");
            return sql.toString();
        }
    }
    
    
    /**
     * 自动清理数据
     * @param tableName 表名
     * @param maxCount 最大数据量
     * @param maxRate 最大比率
     * @param minRate 最小比率
     */
    default void autoCleanData(String tableName, int maxCount, double maxRate, double minRate) {
        int nowCount = selectCount(tableName);
        System.out.println("当前数据量：" + nowCount);
        if (nowCount > maxCount * maxRate) {
            int deleteCount = (int) (nowCount - maxCount * minRate);
            System.out.println("需要删除的数据量：" + deleteCount);
            List<Long> ids = selectIdsToDelete(tableName, deleteCount);
            System.out.println("需要删除的ID列表：" + ids);
            if (!ids.isEmpty()) {
                int result = deleteByIds(tableName, ids);
                if(1 != result){
                    System.err.println("删除失败");
                }
            }
        }else {
            System.out.println("nowCount: " + nowCount);
            System.out.println("maxCount * maxRate: " + maxCount * maxRate);
            System.out.println("不需要清理");
        }
    }
}