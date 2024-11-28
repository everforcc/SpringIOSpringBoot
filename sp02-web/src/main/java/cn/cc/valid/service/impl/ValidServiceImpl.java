package cn.cc.valid.service.impl;

import cn.cc.core.domain.R;
import cn.cc.core.validate.ISave;
import cn.cc.valid.dto.ValidDto;
import cn.cc.valid.service.IValidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
public class ValidServiceImpl implements IValidService {

    @Override
    @Validated({ISave.class})
    public R<Void> iSave(@Valid ValidDto validDto) {
//    public R<Void> iSave(ValidDto validDto) {
        log.info("校验成功: " + validDto.toString());
        return R.ok();
    }

    @Override
    public R<Void> noGroup(ValidDto validDto) {
        log.info("校验成功: " + validDto.toString());
        return R.ok();
    }

    @Override
    public R<Void> all(@Valid ValidDto validDto) {
        log.info("校验成功: " + validDto.toString());
        return R.ok();
    }

}
