package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrderReturnReason;
import com.gxh.shop.model.OOrderReturnReasonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderReturnReasonMapper {
    long countByExample(OOrderReturnReasonExample example);

    int deleteByExample(OOrderReturnReasonExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrderReturnReason record);

    int insertSelective(OOrderReturnReason record);

    List<OOrderReturnReason> selectByExample(OOrderReturnReasonExample example);

    OOrderReturnReason selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrderReturnReason record, @Param("example") OOrderReturnReasonExample example);

    int updateByExample(@Param("record") OOrderReturnReason record, @Param("example") OOrderReturnReasonExample example);

    int updateByPrimaryKeySelective(OOrderReturnReason record);

    int updateByPrimaryKey(OOrderReturnReason record);
}