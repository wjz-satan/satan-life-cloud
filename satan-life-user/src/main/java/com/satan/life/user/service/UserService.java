package com.satan.life.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.user.entity.User;
import com.satan.life.common.result.R;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    R<?> register(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，包含token等信息
     */
    R<?> login(String username, String password);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    R<?> getUserInfo(Long userId);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    R<?> updateUserInfo(User user);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    R<?> changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 忘记密码，重置密码
     * @param username 用户名
     * @param email 邮箱
     * @param newPassword 新密码
     * @return 重置结果
     */
    R<?> resetPassword(String username, String email, String newPassword);

    /**
     * 启用/禁用用户
     * @param userId 用户ID
     * @param status 状态(0:禁用,1:启用)
     * @return 操作结果
     */
    R<?> changeStatus(Long userId, Integer status);

    /**
     * 绑定手机号
     * @param userId 用户ID
     * @param phone 手机号
     * @param code 验证码
     * @return 绑定结果
     */
    R<?> bindPhone(Long userId, String phone, String code);

    /**
     * 绑定邮箱
     * @param userId 用户ID
     * @param email 邮箱
     * @param code 验证码
     * @return 绑定结果
     */
    R<?> bindEmail(Long userId, String email, String code);
}