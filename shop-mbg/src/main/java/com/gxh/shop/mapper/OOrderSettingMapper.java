package com.gxh.shop.mapper;

import com.gxh.shop.model.OOrderSetting;
import com.gxh.shop.model.OOrderSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OOrderSettingMapper {
    long countByExample(OOrderSettingExample example);

    int deleteByExample(OOrderSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OOrderSetting record);

    int insertSelective(OOrderSetting record);

    List<OOrderSetting> selectByExample(OOrderSettingExample example);

    OOrderSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OOrderSetting record, @Param("example") OOrderSettingExample example);

    int updateByExample(@Param("record") OOrderSetting record, @Param("example") OOrderSettingExample example);

    int updateByPrimaryKeySelective(OOrderSetting record);

    int updateByPrimaryKey(OOrderSetting record);
}