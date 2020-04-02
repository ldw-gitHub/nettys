package com.framework.service.base.Impl;

import com.framework.base.BaseServiceImpl;
import com.framework.mapper.base.SystemUserMapper;
import com.framework.model.SystemUserModel;
import com.framework.service.base.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/31 15:53
 */
@Service
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserModel> implements SystemUserService {
    @Autowired
    SystemUserMapper systemUserMapper;
    @Override
    public SystemUserModel queryModelByUsername(String username) throws Exception {
        SystemUserModel model = new SystemUserModel();
        model.setAccount(username);
        return systemUserMapper.selectOne(model);
    }
}
