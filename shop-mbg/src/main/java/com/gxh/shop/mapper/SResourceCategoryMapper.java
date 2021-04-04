package com.gxh.shop.mapper;

import com.gxh.shop.model.SResourceCategory;
import com.gxh.shop.model.SResourceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SResourceCategoryMapper {
    long countByExample(SResourceCategoryExample example);

    int deleteByExample(SResourceCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SResourceCategory record);

    int insertSelective(SResourceCategory record);

    List<SResourceCategory> selectByExample(SResourceCategoryExample example);

    SResourceCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SResourceCategory record, @Param("example") SResourceCategoryExample example);

    int updateByExample(@Param("record") SResourceCategory record, @Param("example") SResourceCategoryExample example);

    int updateByPrimaryKeySelective(SResourceCategory record);

    int updateByPrimaryKey(SResourceCategory record);
}