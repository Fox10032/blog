package com.fox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fox.domain.Article;
import com.fox.vo.ArticleVo;
import org.springframework.stereotype.Service;

@Service
public interface ArticleVoMapper extends BaseMapper<ArticleVo> {

}