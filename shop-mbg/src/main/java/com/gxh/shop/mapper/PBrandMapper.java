package com.gxh.shop.mapper;

import com.gxh.shop.model.PBrand;
import com.gxh.shop.model.PBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PBrandMapper {
    long countByExample(PBrandExample example);

    int deleteByExample(PBrandExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PBrand record);

    int insertSelective(PBrand record);

    List<PBrand> selectByExampleWithBLOBs(PBrandExample example);

    List<PBrand> selectByExample(PBrandExample example);

    PBrand selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PBrand record, @Param("example") PBrandExample example);

    int updateByExampleWithBLOBs(@Param("record") PBrand record, @Param("example") PBrandExample example);

    int updateByExample(@Param("record") PBrand record, @Param("example") PBrandExample example);

    int updateByPrimaryKeySelective(PBrand record);

    int updateByPrimaryKeyWithBLOBs(PBrand record);

    int updateByPrimaryKey(PBrand record);
}