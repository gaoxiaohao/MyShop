package com.gxh.shop.mapper;

import com.gxh.shop.model.PProduct;
import com.gxh.shop.model.PProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PProductMapper {
    long countByExample(PProductExample example);

    int deleteByExample(PProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PProduct record);

    int insertSelective(PProduct record);

    List<PProduct> selectByExampleWithBLOBs(PProductExample example);

    List<PProduct> selectByExample(PProductExample example);

    PProduct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PProduct record, @Param("example") PProductExample example);

    int updateByExampleWithBLOBs(@Param("record") PProduct record, @Param("example") PProductExample example);

    int updateByExample(@Param("record") PProduct record, @Param("example") PProductExample example);

    int updateByPrimaryKeySelective(PProduct record);

    int updateByPrimaryKeyWithBLOBs(PProduct record);

    int updateByPrimaryKey(PProduct record);
}