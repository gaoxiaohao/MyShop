package com.gxh.shop.mapper;

import com.gxh.shop.model.PAlbum;
import com.gxh.shop.model.PAlbumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PAlbumMapper {
    long countByExample(PAlbumExample example);

    int deleteByExample(PAlbumExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PAlbum record);

    int insertSelective(PAlbum record);

    List<PAlbum> selectByExample(PAlbumExample example);

    PAlbum selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PAlbum record, @Param("example") PAlbumExample example);

    int updateByExample(@Param("record") PAlbum record, @Param("example") PAlbumExample example);

    int updateByPrimaryKeySelective(PAlbum record);

    int updateByPrimaryKey(PAlbum record);
}