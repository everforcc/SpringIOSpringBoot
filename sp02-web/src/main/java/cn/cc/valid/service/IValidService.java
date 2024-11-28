package cn.cc.valid.service;

import cn.cc.core.domain.R;
import cn.cc.valid.dto.ValidDto;

import javax.validation.Valid;

public interface IValidService {

    R<Void> iSave(@Valid ValidDto validDto);

    R<Void> noGroup(ValidDto validDto);

    R<Void> all(@Valid ValidDto validDto);
}
