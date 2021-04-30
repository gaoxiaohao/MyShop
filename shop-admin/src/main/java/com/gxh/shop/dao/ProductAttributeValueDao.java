package com.gxh.shop.dao;

import com.gxh.shop.model.PProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gxh
 */
public interface ProductAttributeValueDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PProductAttributeValue> productAttributeValueList);
}
