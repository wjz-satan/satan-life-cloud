package com.satan.life.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.user.entity.User;
import com.satan.life.user.mapper.UserMapper;
import com.satan.life.user.service.UserService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expire}")
    private Long jwtExpire;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> register(User user) {
        // 校验参数
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            return R.error(501, "用户名已存在");
        }

        // 检查邮箱是否已存在
        if (StringUtils.hasText(user.getEmail())) {
            wrapper.clear();
            wrapper.eq("email", user.getEmail());
            if (userMapper.selectCount(wrapper) > 0) {
                return R.error(501, "邮箱已被注册");
            }
        }

        // 检查手机号是否已存在
        if (StringUtils.hasText(user.getPhone())) {
            wrapper.clear();
            wrapper.eq("phone", user.getPhone());
            if (userMapper.selectCount(wrapper) > 0) {
                return R.error(501, "手机号已被注册");
            }
        }

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认启用
        }
        if (user.getGender() == null) {
            user.setGender(0); // 默认未知
        }

        // 保存用户
        int result = userMapper.insert(user);
        if (result > 0) {
            return R.success("注册成功");
        }
        return R.<Object>error(ResultCode.ERROR);
    }

    @Override
    public R<?> login(String username, String password) {
        // 校验参数
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("deleted", 0);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return R.error(501, "用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            return R.error(501, "用户已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return R.error(501, "用户名或密码错误");
        }

        // 生成token
        String token = generateToken(user);

        // 存储用户信息到Redis
        redisTemplate.opsForValue().set("user:token:" + user.getId(), token, jwtExpire, TimeUnit.SECONDS);

        // 返回结果
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userInfo", user);
        return R.success(data);
    }

    @Override
    public R<?> getUserInfo(Long userId) {
        // 校验参数
        if (userId == null || userId <= 0) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null || user.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 脱敏处理
        user.setPassword(null);
        return R.success(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateUserInfo(User user) {
        // 校验参数
        if (user == null || user.getId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User existUser = userMapper.selectById(user.getId());
        if (existUser == null || existUser.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 更新用户信息（排除敏感字段）
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setNickname(user.getNickname());
        updateUser.setAvatar(user.getAvatar());
        updateUser.setGender(user.getGender());
        updateUser.setUpdateTime(LocalDateTime.now());
        updateUser.setUpdateBy(user.getId());

        int result = userMapper.updateById(updateUser);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> changePassword(Long userId, String oldPassword, String newPassword) {
        // 校验参数
        if (userId == null || !StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null || user.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return R.error(501, "旧密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(userId);

        int result = userMapper.updateById(user);
        if (result > 0) {
            // 清除旧token
            redisTemplate.delete("user:token:" + userId);
            return R.success("密码修改成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> resetPassword(String username, String email, String newPassword) {
        // 校验参数
        if (!StringUtils.hasText(username) || !StringUtils.hasText(email) || !StringUtils.hasText(newPassword)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("email", email).eq("deleted", 0);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return R.error(501, "用户名或邮箱错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(user);
        if (result > 0) {
            // 清除旧token
            redisTemplate.delete("user:token:" + user.getId());
            return R.success("密码重置成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> changeStatus(Long userId, Integer status) {
        // 校验参数
        if (userId == null || status == null || (status != 0 && status != 1)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null || user.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 更新状态
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(user);
        if (result > 0) {
            // 如果禁用用户，清除token
            if (status == 0) {
                redisTemplate.delete("user:token:" + userId);
            }
            return R.success("状态修改成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> bindPhone(Long userId, String phone, String code) {
        // 校验参数
        if (userId == null || !StringUtils.hasText(phone) || !StringUtils.hasText(code)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null || user.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 校验验证码（简化实现，实际项目中需要从Redis获取验证码进行校验）
        String cacheCode = (String) redisTemplate.opsForValue().get("sms:code:" + phone);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return R.error(501, "验证码错误或已过期");
        }

        // 检查手机号是否已被其他用户绑定
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone).ne("id", userId).eq("deleted", 0);
        if (userMapper.selectCount(wrapper) > 0) {
            return R.error(501, "手机号已被绑定");
        }

        // 绑定手机号
        user.setPhone(phone);
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(user);
        if (result > 0) {
            // 删除验证码
            redisTemplate.delete("sms:code:" + phone);
            return R.success("手机号绑定成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> bindEmail(Long userId, String email, String code) {
        // 校验参数
        if (userId == null || !StringUtils.hasText(email) || !StringUtils.hasText(code)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null || user.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 校验验证码（简化实现，实际项目中需要从Redis获取验证码进行校验）
        String cacheCode = (String) redisTemplate.opsForValue().get("email:code:" + email);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return R.<Object>error(501, "验证码错误或已过期");
        }

        // 检查邮箱是否已被其他用户绑定
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email).ne("id", userId).eq("deleted", 0);
        if (userMapper.selectCount(wrapper) > 0) {
            return R.<Object>error(501, "邮箱已被绑定");
        }

        // 绑定邮箱
        user.setEmail(email);
        user.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(user);
        if (result > 0) {
            // 删除验证码
            redisTemplate.delete("email:code:" + email);
            return R.success("邮箱绑定成功");
        }
        return R.error(ResultCode.ERROR);
    }

    /**
     * 生成JWT Token
     */
    private String generateToken(User user) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtExpire * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("nickname", user.getNickname());
        claims.put("roleIds", user.getRoleIds());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}