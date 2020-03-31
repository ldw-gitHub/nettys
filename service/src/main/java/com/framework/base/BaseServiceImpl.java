package com.framework.base;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

/**
 * 模板service层,service实现可继承
 * @author sky_luan
 */
@Slf4j
public class BaseServiceImpl<T> implements BaseService<T>{
	
	@Autowired
	BaseMapper<T> baseMapper;
	
	/**
	 * 增
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean insert(T t) throws Exception{
		int count = baseMapper.insertSelective(t);
		return count>0?true:false;
	}
	
	/**
	 * 修改
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean updateById(T t) throws Exception{
		int count = baseMapper.updateByPrimaryKeySelective(t);
		return count>0?true:false;
	}
	
	/**
	 * 修改
	 * @param example
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	@Transactional
	public boolean updateByExampleSelective(Example example, T t) throws Exception{
		int count = baseMapper.updateByExampleSelective(t, example);
		return count>0?true:false;
	}
	
	/**
	 * 删除
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	@Transactional
	public boolean delete(T t) throws Exception{
		int count = baseMapper.delete(t);
		return count>0?true:false;
	}
	
	/**
	 * 删除
	 * @param key 主键
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	@Transactional
	public boolean deleteByPrimaryKey(Object key) throws Exception{
		int count = baseMapper.deleteByPrimaryKey(key);
		return count>0?true:false;
	}
	
	/**
	 * 删除
	 * @param example
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	@Transactional
	public boolean deleteByExample(Example example) throws Exception{
		int count = baseMapper.deleteByExample(example);
		return count>0?true:false;
	}
	
	/**
	 * 根据主键查找，是否存在
	 * @param key
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean existsWithPrimaryKey(Object key) throws Exception{
		return baseMapper.existsWithPrimaryKey(key);
	}
	
	/**
	 * 条件查询list
	 * @param t
	 * @return List
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public List<T> select(T t) throws Exception{
		List<T> list = baseMapper.select(t);
		return list;
	}
	
	/**
	 * 
	 * 条件查询list
	 * @param example
	 * @return List
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public List<T> select(Example example) throws Exception{
		List<T> list = baseMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 根据主键查询单个
	 * @param key
	 * @return T
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public T selectByPrimaryKey(Object key) throws Exception{
		T t = baseMapper.selectByPrimaryKey(key);
		return t;
	}
	
	/**
	 * 条件查询一个
	 * @param t
	 * @return T
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public T selectOne(T t) throws Exception{
		t = baseMapper.selectOne(t);
		return t;
	}
	
	/**
	 * 条件查询数量
	 * @param t
	 * @return int
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public int selectCount(T t) throws Exception{
		return baseMapper.selectCount(t);
	}
	
	/**
	 * 条件查询数量
	 * @param example
	 * @return int
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public int selectCountByExample(Example example) throws Exception{
		return baseMapper.selectCountByExample(example);
	}
	
	
}