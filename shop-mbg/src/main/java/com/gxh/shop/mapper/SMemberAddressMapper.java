package com.gxh.shop.mapper;

import com.gxh.shop.model.SMemberAddress;
import com.gxh.shop.model.SMemberAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMemberAddressMapper {
    long countByExample(SMemberAddressExample example);

    int deleteByExample(SMemberAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SMemberAddress record);

    int insertSelective(SMemberAddress record);

    List<SMemberAddress> selectByExample(SMemberAddressExample example);

    SMemberAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SMemberAddress record, @Param("example") SMemberAddressExample example);

    int updateByExample(@Param("record") SMemberAddress record, @Param("example") SMemberAddressExample example);

    int updateByPrimaryKeySelective(SMemberAddress record);

    int updateByPrimaryKey(SMemberAddress record);
}