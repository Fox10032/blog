package com.fox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fox.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper extends BaseMapper<User> {
}
