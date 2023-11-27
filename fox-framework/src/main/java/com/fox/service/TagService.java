package com.fox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.domain.ResponseResult;
import com.fox.domain.Tag;
import com.fox.dto.TabListDto;
import com.fox.dto.TagListDto;
import com.fox.vo.PageVo;
import com.fox.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {
    //查询标签列表
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
    //新增标签
    ResponseResult addTag(TabListDto tagListDto);
    //删除标签
    ResponseResult deleteTag(Long id);
    //修改标签-①根据标签的id来查询标签
    ResponseResult getLableById(Long id);
    //修改标签-②根据标签的id来修改标签
    ResponseResult myUpdateById(TagVo tagVo);

    //写博文-查询文章标签的接口
    List<TagVo> listAllTag();
}
