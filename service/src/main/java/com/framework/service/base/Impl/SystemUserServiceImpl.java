package com.framework.service.base.Impl;

import com.framework.mapper.base.SystemUserMapper;
import com.framework.model.SystemUserModel;
import com.framework.service.base.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/31 15:53
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    SystemUserMapper systemUserMapper;

    /**
     * jpa 根据对象查询
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public SystemUserModel queryModelByUsername(String username) throws Exception {
        SystemUserModel model = new SystemUserModel();
        model.setAccount(username);
        Example<SystemUserModel> example = Example.of(model);
//        return systemUserMapper.findOne(example);
        return null;
    }

    /**
     * @description:  使用jpa返回插入后主键
     * @author: liudawei
     * @date: 2020/4/7 15:29
     * @param: model
     * @return: java.lang.Long
     */
    @Override
    public Long insertUserModel(SystemUserModel model) throws Exception {
//        SystemUserModel save = systemUserMapper.save(model);
        int save = systemUserMapper.insert(model);
        return 1L;
    }
}
