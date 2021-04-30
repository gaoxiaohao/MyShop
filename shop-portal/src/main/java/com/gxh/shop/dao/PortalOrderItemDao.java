package com.gxh.shop.dao;

import com.gxh.shop.model.OOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface PortalOrderItemDao {
    /**
     * 批量插入
     */
    int insertList(@Param("list") List<OOrderItem> list);
}
