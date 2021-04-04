package com.gxh.shop.mapper;

import com.gxh.shop.model.OCompanyAddress;
import com.gxh.shop.model.OCompanyAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OCompanyAddressMapper {
    long countByExample(OCompanyAddressExample example);

    int deleteByExample(OCompanyAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OCompanyAddress record);

    int insertSelective(OCompanyAddress record);

    List<OCompanyAddress> selectByExample(OCompanyAddressExample example);

    OCompanyAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OCompanyAddress record, @Param("example") OCompanyAddressExample example);

    int updateByExample(@Param("record") OCompanyAddress record, @Param("example") OCompanyAddressExample example);

    int updateByPrimaryKeySelective(OCompanyAddress record);

    int updateByPrimaryKey(OCompanyAddress record);
}