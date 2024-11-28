package cn.cc.valid.controller;

import cn.cc.core.domain.R;
import cn.cc.valid.dto.ValidDto;
import cn.cc.valid.service.IValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/valid")
public class ValidController {

    @Autowired
    IValidService iValidService;

    @PostMapping("/isave")
    public R<Void> valid(@RequestBody ValidDto validDto){
        return iValidService.iSave(validDto);
    }

    @PostMapping("/noGroup")
    public R<Void> noGroup(@RequestBody ValidDto validDto){
        return iValidService.noGroup(validDto);
    }

    @PostMapping("/all")
    public R<Void> all(@RequestBody ValidDto validDto){
        return iValidService.all(validDto);
    }

}
