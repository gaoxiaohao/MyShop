package com.gxh.shop.mapper;

import com.gxh.shop.model.PFeightTemplate;
import com.gxh.shop.model.PFeightTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PFeightTemplateMapper {
    long countByExample(PFeightTemplateExample example);

    int deleteByExample(PFeightTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PFeightTemplate record);

    int insertSelective(PFeightTemplate record);

    List<PFeightTemplate> selectByExample(PFeightTemplateExample example);

    PFeightTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PFeightTemplate record, @Param("example") PFeightTemplateExample example);

    int updateByExample(@Param("record") PFeightTemplate record, @Param("example") PFeightTemplateExample example);

    int updateByPrimaryKeySelective(PFeightTemplate record);

    int updateByPrimaryKey(PFeightTemplate record);
}