package com.gxh.shop.mapper;

import com.gxh.shop.model.OCartItem;
import com.gxh.shop.model.OCartItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OCartItemMapper {
    long countByExample(OCartItemExample example);

    int deleteByExample(OCartItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OCartItem record);

    int insertSelective(OCartItem record);

    List<OCartItem> selectByExample(OCartItemExample example);

    OCartItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OCartItem record, @Param("example") OCartItemExample example);

    int updateByExample(@Param("record") OCartItem record, @Param("example") OCartItemExample example);

    int updateByPrimaryKeySelective(OCartItem record);

    int updateByPrimaryKey(OCartItem record);
}