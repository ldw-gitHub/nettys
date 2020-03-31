package com.framework.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理用户
 *
 * @author ldw
 */
@Entity
@Table(name = "t_sys_user")
public class SystemUserModel implements Serializable {

    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = 3733424915965473037L;
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * 用户账号
     */
    @Column(name = "account")
    private String account;
    /**
     * 登录密码，MD5加密
     */
    @Column(name = "password")
    private String password;
    /**
     * 用户姓名
     */
    @Column(name = "username")
    private String username;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;
    /**
     * 状态
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 创建人和修改人
     */
    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_id")
    private Long updateId;

    @Column(name = "update_name")
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
