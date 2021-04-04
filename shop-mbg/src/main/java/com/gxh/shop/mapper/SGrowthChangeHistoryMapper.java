package com.gxh.shop.mapper;

import com.gxh.shop.model.SGrowthChangeHistory;
import com.gxh.shop.model.SGrowthChangeHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SGrowthChangeHistoryMapper {
    long countByExample(SGrowthChangeHistoryExample example);

    int deleteByExample(SGrowthChangeHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SGrowthChangeHistory record);

    int insertSelective(SGrowthChangeHistory record);

    List<SGrowthChangeHistory> selectByExample(SGrowthChangeHistoryExample example);

    SGrowthChangeHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SGrowthChangeHistory record, @Param("example") SGrowthChangeHistoryExample example);

    int updateByExample(@Param("record") SGrowthChangeHistory record, @Param("example") SGrowthChangeHistoryExample example);

    int updateByPrimaryKeySelective(SGrowthChangeHistory record);

    int updateByPrimaryKey(SGrowthChangeHistory record);
}