package com.gxh.shop.mapper;

import com.gxh.shop.model.PMemberPrice;
import com.gxh.shop.model.PMemberPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PMemberPriceMapper {
    long countByExample(PMemberPriceExample example);

    int deleteByExample(PMemberPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PMemberPrice record);

    int insertSelective(PMemberPrice record);

    List<PMemberPrice> selectByExample(PMemberPriceExample example);

    PMemberPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PMemberPrice record, @Param("example") PMemberPriceExample example);

    int updateByExample(@Param("record") PMemberPrice record, @Param("example") PMemberPriceExample example);

    int updateByPrimaryKeySelective(PMemberPrice record);

    int updateByPrimaryKey(PMemberPrice record);
}