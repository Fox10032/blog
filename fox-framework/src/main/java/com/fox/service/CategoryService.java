package com.fox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.domain.Category;
import com.fox.domain.ResponseResult;
import com.fox.vo.CategoryVo;
import com.fox.vo.PageVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    //查询文章分类的接口
    ResponseResult getCategoryList();

    //写博客-查询文章分类的接口
    List<CategoryVo> listAllCategory();
    //分页查询分类列表
    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}