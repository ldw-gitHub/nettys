package com.framework.service.base;

import com.framework.base.BaseService;
import com.framework.model.SystemUserModel;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/31 15:04
 */
public interface SystemUserService {

    SystemUserModel queryModelByUsername(String username) throws Exception;

    Long insertUserModel(SystemUserModel model) throws Exception;

}
