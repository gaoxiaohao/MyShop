package com.gxh.shop.mapper;

import com.gxh.shop.model.PProductCategory;
import com.gxh.shop.model.PProductCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductCategoryMapper {
    long countByExample(PProductCategoryExample example);

    int deleteByExample(PProductCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProductCategory record);

    int insertSelective(PProductCategory record);

    List<PProductCategory> selectByExampleWithBLOBs(PProductCategoryExample example);

    List<PProductCategory> selectByExample(PProductCategoryExample example);

    PProductCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProductCategory record, @Param("example") PProductCategoryExample example);

    int updateByExampleWithBLOBs(@Param("record") PProductCategory record, @Param("example") PProductCategoryExample example);

    int updateByExample(@Param("record") PProductCategory record, @Param("example") PProductCategoryExample example);

    int updateByPrimaryKeySelective(PProductCategory record);

    int updateByPrimaryKeyWithBLOBs(PProductCategory record);

    int updateByPrimaryKey(PProductCategory record);
}