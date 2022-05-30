/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-27 15:21
 * Copyright
 */

package cn.cc.sp04mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 注册乐观锁插件
 */
//@MapperScan(basePackages = {"cn.cc.sp04mybatisplus.mapper","cn.cc.sp04mybatisplus.mapper"},annotationClass = Mapper.class)
@EnableTransactionManagement // 事务
@Configuration //
@Slf4j
public class MybatisPlusConfig {

    /**
     * 乐观锁插件
     * 版本不同插件也不同，详情看官网
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        log.info("加载乐观锁插件");
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        log.info("加载分页插件");
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 数据库类型(根据类型获取应使用的分页方言,参见 插件#findIDialect 方法)
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 旧版新能分析插件
        // new PerformanceInterceptor()
        return mybatisPlusInterceptor;
    }

    /**
     * 分页插件
     * 暂时不需要更详细的设置
     * @return
     */
//    @Bean
//    public PaginationInnerInterceptor mybatisPaginationInnerInterceptor(){
//        log.info("加载分页插件");
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
//        paginationInnerInterceptor.setMaxLimit(5L);
//        return paginationInnerInterceptor;
//    }


}
