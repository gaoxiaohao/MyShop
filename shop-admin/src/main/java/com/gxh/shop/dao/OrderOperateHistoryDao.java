package com.gxh.shop.dao;

import com.gxh.shop.model.OOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderOperateHistoryDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<OOrderOperateHistory> orderOperateHistoryList);
}
