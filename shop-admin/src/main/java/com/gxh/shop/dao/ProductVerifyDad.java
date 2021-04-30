package com.gxh.shop.dao;

import com.gxh.shop.model.PProductVertify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface ProductVerifyDad {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PProductVertify> list);
}
