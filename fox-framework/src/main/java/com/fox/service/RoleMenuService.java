package com.fox.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fox.domain.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {
    //修改角色-保存修改好的角色信息
    void deleteRoleMenuByRoleId(Long id);

}