package com.cc.sp90utils.http.vo;

import com.cc.sp90utils.enums.impl.FileMediumEnum;
import com.cc.sp90utils.enums.impl.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端传来的业务数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSiteReqVO {

    private String weburl;

    private String webname;

    /**
     * web,pc
     */
    private String sofytype;
    /**
     * 小说
     */
    private String webtype;

    private FileTypeEnum filetype = FileTypeEnum.TXT;
    private FileMediumEnum medium = FileMediumEnum.WINDOWS;

}
