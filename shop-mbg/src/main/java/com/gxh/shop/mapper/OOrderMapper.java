package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrder;
import com.gxh.shop.model.OOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderMapper {
    long countByExample(OOrderExample example);

    int deleteByExample(OOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrder record);

    int insertSelective(OOrder record);

    List<OOrder> selectByExample(OOrderExample example);

    OOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrder record, @Param("example") OOrderExample example);

    int updateByExample(@Param("record") OOrder record, @Param("example") OOrderExample example);

    int updateByPrimaryKeySelective(OOrder record);

    int updateByPrimaryKey(OOrder record);
}