package com.satan.life.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.finance.entity.Transaction;
import com.satan.life.finance.mapper.TransactionMapper;
import com.satan.life.finance.service.TransactionService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 交易记录服务实现类
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    @Resource
    private TransactionMapper transactionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addTransaction(Transaction transaction) {
        // 校验参数
        if (transaction == null || transaction.getUserId() == null || transaction.getTransactionType() == null ||
                transaction.getCategoryId() == null || transaction.getAmount() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查交易类型
        if (transaction.getTransactionType() != 1 && transaction.getTransactionType() != 2) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }

        // 检查金额
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }

        // 设置交易时间
        if (transaction.getTransactionTime() == null) {
            transaction.setTransactionTime(LocalDateTime.now());
        }

        // 保存交易记录
        int result = transactionMapper.insert(transaction);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateTransaction(Transaction transaction) {
        // 校验参数
        if (transaction == null || transaction.getId() == null || transaction.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查交易记录是否存在
        Transaction existTransaction = transactionMapper.selectById(transaction.getId());
        if (existTransaction == null || existTransaction.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 检查是否为用户自己的交易记录
        if (!existTransaction.getUserId().equals(transaction.getUserId())) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }

        // 更新交易记录信息
        Transaction updateTransaction = new Transaction();
        updateTransaction.setId(transaction.getId());
        if (transaction.getTransactionType() != null) {
            updateTransaction.setTransactionType(transaction.getTransactionType());
        }
        if (transaction.getCategoryId() != null) {
            updateTransaction.setCategoryId(transaction.getCategoryId());
        }
        if (transaction.getAmount() != null) {
            updateTransaction.setAmount(transaction.getAmount());
        }
        if (transaction.getTransactionTime() != null) {
            updateTransaction.setTransactionTime(transaction.getTransactionTime());
        }
        if (StringUtils.hasText(transaction.getDescription())) {
            updateTransaction.setDescription(transaction.getDescription());
        }
        if (StringUtils.hasText(transaction.getLocation())) {
            updateTransaction.setLocation(transaction.getLocation());
        }
        if (StringUtils.hasText(transaction.getPaymentMethod())) {
            updateTransaction.setPaymentMethod(transaction.getPaymentMethod());
        }
        updateTransaction.setUpdateTime(LocalDateTime.now());

        int result = transactionMapper.updateById(updateTransaction);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteTransaction(Long transactionId, Long userId) {
        // 校验参数
        if (transactionId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查交易记录是否存在
        Transaction transaction = transactionMapper.selectById(transactionId);
        if (transaction == null || transaction.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 检查是否为用户自己的交易记录
        if (!transaction.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }

        // 删除交易记录（逻辑删除）
        int result = transactionMapper.deleteById(transactionId);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<?> getTransactionList(Map<String, Object> params) {
        // 获取查询参数
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) : 1;
        Integer limit = params.get("limit") != null ? Integer.valueOf(params.get("limit").toString()) : 10;
        Integer type = params.get("type") != null ? Integer.valueOf(params.get("type").toString()) : null;
        Long categoryId = params.get("categoryId") != null ? Long.valueOf(params.get("categoryId").toString()) : null;
        String startDate = params.get("startDate") != null ? params.get("startDate").toString() : null;
        String endDate = params.get("endDate") != null ? params.get("endDate").toString() : null;

        // 构建查询条件
        QueryWrapper<Transaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("deleted", 0)
                .orderByDesc("transaction_time");

        if (type != null) {
            wrapper.eq("transaction_type", type);
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            wrapper.between("transaction_time", startDate + " 00:00:00", endDate + " 23:59:59");
        }

        // 分页查询
        IPage<Transaction> pageData = transactionMapper.selectPage(new Page<>(page, limit), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("list", pageData.getRecords());
        return R.success(result);
    }

    @Override
    public R<?> getTransactionDetail(Long transactionId, Long userId) {
        // 校验参数
        if (transactionId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询交易记录详情
        Transaction transaction = transactionMapper.selectById(transactionId);
        if (transaction == null || transaction.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 检查是否为用户自己的交易记录
        if (!transaction.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }

        return R.success(transaction);
    }

    @Override
    public R<?> getMonthlyStatistics(Long userId, Integer year, Integer month) {
        // 校验参数
        if (userId == null || year == null || month == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 计算月份的开始和结束时间
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);

        // 构建查询条件
        QueryWrapper<Transaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0);

        // 查询收入总和
        wrapper.select("sum(amount) as total")
                .eq("type", 1);
        Map<String, Object> incomeMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal income = incomeMap.get("total") != null ? new BigDecimal(incomeMap.get("total").toString()) : BigDecimal.ZERO;

        // 查询支出总和
        wrapper.clear();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0)
                .select("sum(amount) as total")
                .eq("type", 2);
        Map<String, Object> expenseMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal expense = expenseMap.get("total") != null ? new BigDecimal(expenseMap.get("total").toString()) : BigDecimal.ZERO;

        // 计算结余
        BigDecimal balance = income.subtract(expense);

        // 构建结果
        Map<String, Object> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);
        result.put("balance", balance);

        // 获取分类统计（收入）
        wrapper.clear();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0)
                .eq("type", 1)
                .select("category_id", "sum(amount) as amount")
                .groupBy("category_id");
        List<Map<String, Object>> incomeCategoryStats = transactionMapper.selectMaps(wrapper);
        result.put("incomeCategoryStats", incomeCategoryStats);

        // 获取分类统计（支出）
        wrapper.clear();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0)
                .eq("type", 2)
                .select("category_id", "sum(amount) as amount")
                .groupBy("category_id");
        List<Map<String, Object>> expenseCategoryStats = transactionMapper.selectMaps(wrapper);
        result.put("expenseCategoryStats", expenseCategoryStats);

        return R.success(result);
    }

    @Override
    public R<?> getYearlyStatistics(Long userId, Integer year) {
        // 校验参数
        if (userId == null || year == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 计算年份的开始和结束时间
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);

        // 构建查询条件
        QueryWrapper<Transaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0);

        // 查询收入总和
        wrapper.select("sum(amount) as total")
                .eq("type", 1);
        Map<String, Object> incomeMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal income = incomeMap.get("total") != null ? new BigDecimal(incomeMap.get("total").toString()) : BigDecimal.ZERO;

        // 查询支出总和
        wrapper.clear();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0)
                .select("sum(amount) as total")
                .eq("type", 2);
        Map<String, Object> expenseMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal expense = expenseMap.get("total") != null ? new BigDecimal(expenseMap.get("total").toString()) : BigDecimal.ZERO;

        // 计算结余
        BigDecimal balance = income.subtract(expense);

        // 构建结果
        Map<String, Object> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);
        result.put("balance", balance);

        // 获取月度统计
        List<Map<String, Object>> monthlyStats = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> monthlyStat = new HashMap<>();
            monthlyStat.put("month", month);

            // 计算当月的开始和结束时间
            LocalDate monthStartDate = LocalDate.of(year, month, 1);
            LocalDate monthEndDate = monthStartDate.plusMonths(1).minusDays(1);
            LocalDateTime monthStartTime = LocalDateTime.of(monthStartDate, LocalTime.MIN);
            LocalDateTime monthEndTime = LocalDateTime.of(monthEndDate, LocalTime.MAX);

            // 查询当月收入
            wrapper.clear();
            wrapper.eq("user_id", userId)
                    .between("transaction_time", monthStartTime, monthEndTime)
                    .eq("deleted", 0)
                    .select("sum(amount) as total")
                    .eq("type", 1);
            Map<String, Object> monthIncomeMap = transactionMapper.selectMaps(wrapper).get(0);
            BigDecimal monthIncome = monthIncomeMap.get("total") != null ? new BigDecimal(monthIncomeMap.get("total").toString()) : BigDecimal.ZERO;
            monthlyStat.put("income", monthIncome);

            // 查询当月支出
            wrapper.clear();
            wrapper.eq("user_id", userId)
                    .between("transaction_time", monthStartTime, monthEndTime)
                    .eq("deleted", 0)
                    .select("sum(amount) as total")
                    .eq("type", 2);
            Map<String, Object> monthExpenseMap = transactionMapper.selectMaps(wrapper).get(0);
            BigDecimal monthExpense = monthExpenseMap.get("total") != null ? new BigDecimal(monthExpenseMap.get("total").toString()) : BigDecimal.ZERO;
            monthlyStat.put("expense", monthExpense);

            // 计算当月结余
            monthlyStat.put("balance", monthIncome.subtract(monthExpense));

            monthlyStats.add(monthlyStat);
        }

        result.put("monthlyStats", monthlyStats);
        return R.success(result);
    }

    @Override
    public R<?> getCustomPeriodStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        // 校验参数
        if (userId == null || startDate == null || endDate == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查日期范围
        if (startDate.isAfter(endDate)) {
            return R.<Object>error(500, "开始日期不能晚于结束日期");
        }

        // 计算时间范围
        LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);

        // 构建查询条件
        QueryWrapper<Transaction> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0);

        // 查询收入总和
        wrapper.select("sum(amount) as total")
                .eq("type", 1);
        Map<String, Object> incomeMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal income = incomeMap.get("total") != null ? new BigDecimal(incomeMap.get("total").toString()) : BigDecimal.ZERO;

        // 查询支出总和
        wrapper.clear();
        wrapper.eq("user_id", userId)
                .between("transaction_time", startTime, endTime)
                .eq("deleted", 0)
                .select("sum(amount) as total")
                .eq("type", 2);
        Map<String, Object> expenseMap = transactionMapper.selectMaps(wrapper).get(0);
        BigDecimal expense = expenseMap.get("total") != null ? new BigDecimal(expenseMap.get("total").toString()) : BigDecimal.ZERO;

        // 计算结余
        BigDecimal balance = income.subtract(expense);

        // 构建结果
        Map<String, Object> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);
        result.put("balance", balance);
        result.put("startDate", startDate);
        result.put("endDate", endDate);

        return R.success(result);
    }
}