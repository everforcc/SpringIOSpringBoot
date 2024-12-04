package cn.cc.busi.autosql.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface ITestProvider {

//    @SelectProvider(method = "selectTest", type = SqlContext.class)
//    String tSqlContext(String from, @Param("id") String id, @Param("effect") String effect, @Param("status") String status);

    /**
     * 如果报错就接口和 type 实现方法都加上   @Param("id")
     * #{} 拿到的参数都会有 '' 包括
     *
     * @param from   类型判断
     * @param id     参数
     * @param effect 参数
     * @param status 参数
     * @return
     */
    @SelectProvider(method = "selectTest", type = SqlContext.class)
    String tSqlContext(@Param("from") String from, @Param("id") String id , @Param("effect") String effect, @Param("status") String status);

}
