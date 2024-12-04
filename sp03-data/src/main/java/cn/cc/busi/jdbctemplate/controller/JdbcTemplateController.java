package cn.cc.busi.jdbctemplate.controller;

import cn.cc.dto.NovelDto;
import cn.cc.mapper.NovelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/template")
public class JdbcTemplateController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private NovelMapper novelMapper;

    /**
     * 使用 JdbcTemplate 发送脚本
     */
    @GetMapping("/jdbcTemplate")
    public List<Map<String, Object>> jdbc() {
        // 查询数据库的所有信息
        String sql = "select * from cc_website";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 使用 mybatis
     */
    @GetMapping("/mybatis")
    public List<NovelDto> novelList() {
        return novelMapper.queryNovelList();
    }

}
