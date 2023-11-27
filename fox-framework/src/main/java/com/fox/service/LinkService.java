package com.fox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.domain.Link;
import com.fox.domain.ResponseResult;
import com.fox.vo.PageVo;

public interface LinkService  extends IService<Link>{
    //查询友链
    ResponseResult getAllLink();
    //分页查询友链
    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}
