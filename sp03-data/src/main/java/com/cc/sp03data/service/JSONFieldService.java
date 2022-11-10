/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 14:51
 * Copyright
 */

package com.cc.sp03data.service;

import com.cc.sp03data.dao.IJSONFieldDao;
import com.cc.sp03data.dto.NovelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

}
