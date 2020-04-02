package com.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 * @author sky_luan
 */
@Component
@ConfigurationProperties(prefix = "project.config")
public class ProjectConfig {

	/**
	 * token秘钥
	 */
	private String tokenSecurt;
	/**
	 * token有效期
	 */
	private Long tokenTtl;

	public String getTokenSecurt() {
		return tokenSecurt;
	}

	public void setTokenSecurt(String tokenSecurt) {
		this.tokenSecurt = tokenSecurt;
	}

	public Long getTokenTtl() {
		return tokenTtl;
	}

	public void setTokenTtl(Long tokenTtl) {
		this.tokenTtl = tokenTtl;
	}
}
