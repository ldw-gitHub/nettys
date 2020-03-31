package com.framework.base;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * 模板service层,service实现可继承
 * @author sky_luan
 */
public interface BaseService<T> {
	
	/**
	 * 增
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean insert(T t) throws Exception;
	
	/**
	 * 修改
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean updateById(T t) throws Exception;
	
	/**
	 * 修改
	 * @param example
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean updateByExampleSelective(Example example, T t) throws Exception;
	
	/**
	 * 删除
	 * @param t
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean delete(T t) throws Exception;
	
	/**
	 * 删除
	 * @param key 主键
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean deleteByPrimaryKey(Object key) throws Exception;
	
	/**
	 * 删除
	 * @param example
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean deleteByExample(Example example) throws Exception;
	
	/**
	 * 根据主键查找，是否存在
	 * @param key
	 * @return boolean
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public boolean existsWithPrimaryKey(Object key) throws Exception;
	
	/**
	 * 条件查询list
	 * @param t
	 * @return List
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public List<T> select(T t) throws Exception;
	
	/**
	 * 
	 * 条件查询list
	 * @param example
	 * @return List
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public List<T> select(Example example) throws Exception;
	
	/**
	 * 根据主键查询单个
	 * @param key
	 * @return T
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public T selectByPrimaryKey(Object key) throws Exception;
	
	/**
	 * 条件查询一个
	 * @param t
	 * @return T
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public T selectOne(T t) throws Exception;
	
	/**
	 * 条件查询数量
	 * @param t
	 * @return int
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public int selectCount(T t) throws Exception;
	
	/**
	 * 条件查询数量
	 * @param example
	 * @return int
	 * @throws Exception 
	 * @date 2018年5月31日
	 * @author sky_luan
	 */
	public int selectCountByExample(Example example) throws Exception;
	
	
}
