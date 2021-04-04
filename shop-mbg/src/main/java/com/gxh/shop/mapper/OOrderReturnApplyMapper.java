package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrderReturnApply;
import com.gxh.shop.model.OOrderReturnApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderReturnApplyMapper {
    long countByExample(OOrderReturnApplyExample example);

    int deleteByExample(OOrderReturnApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrderReturnApply record);

    int insertSelective(OOrderReturnApply record);

    List<OOrderReturnApply> selectByExample(OOrderReturnApplyExample example);

    OOrderReturnApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrderReturnApply record, @Param("example") OOrderReturnApplyExample example);

    int updateByExample(@Param("record") OOrderReturnApply record, @Param("example") OOrderReturnApplyExample example);

    int updateByPrimaryKeySelective(OOrderReturnApply record);

    int updateByPrimaryKey(OOrderReturnApply record);
}