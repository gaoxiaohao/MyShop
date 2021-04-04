package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrderOperateHistory;
import com.gxh.shop.model.OOrderOperateHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderOperateHistoryMapper {
    long countByExample(OOrderOperateHistoryExample example);

    int deleteByExample(OOrderOperateHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrderOperateHistory record);

    int insertSelective(OOrderOperateHistory record);

    List<OOrderOperateHistory> selectByExample(OOrderOperateHistoryExample example);

    OOrderOperateHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrderOperateHistory record, @Param("example") OOrderOperateHistoryExample example);

    int updateByExample(@Param("record") OOrderOperateHistory record, @Param("example") OOrderOperateHistoryExample example);

    int updateByPrimaryKeySelective(OOrderOperateHistory record);

    int updateByPrimaryKey(OOrderOperateHistory record);
}