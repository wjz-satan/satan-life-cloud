package com.satan.life.user.controller;

import com.satan.life.user.entity.User;
import com.satan.life.user.service.UserService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R<?> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R<?> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return userService.login(username, password);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public R<?> getUserInfo(@RequestParam Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public R<?> updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password/change")
    public R<?> changePassword(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String oldPassword = params.get("oldPassword").toString();
        String newPassword = params.get("newPassword").toString();
        return userService.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * 重置密码
     */
    @PostMapping("/password/reset")
    public R<?> resetPassword(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String email = params.get("email");
        String newPassword = params.get("newPassword");
        return userService.resetPassword(username, email, newPassword);
    }

    /**
     * 启用/禁用用户
     */
    @PutMapping("/status")
    public R<?> changeStatus(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer status = Integer.valueOf(params.get("status").toString());
        return userService.changeStatus(userId, status);
    }

    /**
     * 绑定手机号
     */
    @PostMapping("/phone/bind")
    public R<?> bindPhone(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String phone = params.get("phone").toString();
        String code = params.get("code").toString();
        return userService.bindPhone(userId, phone, code);
    }

    /**
     * 绑定邮箱
     */
    @PostMapping("/email/bind")
    public R<?> bindEmail(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String email = params.get("email").toString();
        String code = params.get("code").toString();
        return userService.bindEmail(userId, email, code);
    }
}