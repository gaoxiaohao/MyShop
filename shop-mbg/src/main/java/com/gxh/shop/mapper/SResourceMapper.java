package com.gxh.shop.mapper;

import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SResourceMapper {
    long countByExample(SResourceExample example);

    int deleteByExample(SResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SResource record);

    int insertSelective(SResource record);

    List<SResource> selectByExample(SResourceExample example);

    SResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SResource record, @Param("example") SResourceExample example);

    int updateByExample(@Param("record") SResource record, @Param("example") SResourceExample example);

    int updateByPrimaryKeySelective(SResource record);

    int updateByPrimaryKey(SResource record);
}