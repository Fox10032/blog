package com.fox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fox.mapper.ArticleVoMapper;
import com.fox.service.ArticleVoService;
import com.fox.vo.ArticleVo;
import org.springframework.stereotype.Service;

@Service
public class ArticleVoServiceImpl extends ServiceImpl<ArticleVoMapper, ArticleVo> implements ArticleVoService {

}
