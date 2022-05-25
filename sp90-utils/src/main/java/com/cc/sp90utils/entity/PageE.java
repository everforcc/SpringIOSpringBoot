package com.cc.sp90utils.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class PageE<E> {

    private int page;

    private int num;

    private int totalCount;

    private int pageCount;

    private List<E> eList;

    /**
     * 搜索条件单独处理，不同的类没法合并
     */

}
