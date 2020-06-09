package com.framework.mapper.base;

import com.framework.base.BaseMapper;
import com.framework.model.SystemUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/31 16:50
 */
public interface SystemUserMapper extends JpaRepository<SystemUserModel,Long> {

}
