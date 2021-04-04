package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrderItem;
import com.gxh.shop.model.OOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderItemMapper {
    long countByExample(OOrderItemExample example);

    int deleteByExample(OOrderItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrderItem record);

    int insertSelective(OOrderItem record);

    List<OOrderItem> selectByExample(OOrderItemExample example);

    OOrderItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrderItem record, @Param("example") OOrderItemExample example);

    int updateByExample(@Param("record") OOrderItem record, @Param("example") OOrderItemExample example);

    int updateByPrimaryKeySelective(OOrderItem record);

    int updateByPrimaryKey(OOrderItem record);
}