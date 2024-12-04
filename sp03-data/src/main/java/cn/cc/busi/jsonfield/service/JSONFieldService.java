/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 14:51
 * Copyright
 */

package cn.cc.busi.jsonfield.service;

import cn.cc.busi.jsonfield.dao.IJSONFieldDao;
import cn.cc.dto.NovelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class JSONFieldService {

    @Resource
    IJSONFieldDao ijsonFieldDao;

    @Transactional(rollbackFor = Exception.class)
    public NovelDto jsonFieldT(NovelDto novelDto) {
        ijsonFieldDao.insertNovel(novelDto);
        return novelDto;
    }


    public List<NovelDto> list(NovelDto novelDto) {
        return ijsonFieldDao.list(novelDto);
    }

}
