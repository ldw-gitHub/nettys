package com.framework.constant;

/**
 * 
 * redis键集合
 * 需要标注类型，以及包含字段
 * @author ldw
 */
public class RedisKey {

	/**
	 * 键值对 整个项目的redis键都需要带上这个
	 * 
	 */
	public final static String DB_NAME = "netty:";

	//===============================文件存储============================================
	public final static String DIR_PATH = DB_NAME + "dir:path:";
	//===============================文件存储============================================
	
	//===============================token相关============================================
	/**
	 * web jwt token 类型：Map key: username(account 账户名)
	 * k-value:token,id,account,username,roleId,type,email,mobile,photo 
	 * ttl:有
	 */
	public final static String ADMIN_TOKEN = DB_NAME + "admin:token:";
	//===============================token相关============================================

}
