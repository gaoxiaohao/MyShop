package com.gxh.shop.mapper;

import com.gxh.shop.model.PAlbumPic;
import com.gxh.shop.model.PAlbumPicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PAlbumPicMapper {
    long countByExample(PAlbumPicExample example);

    int deleteByExample(PAlbumPicExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PAlbumPic record);

    int insertSelective(PAlbumPic record);

    List<PAlbumPic> selectByExample(PAlbumPicExample example);

    PAlbumPic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PAlbumPic record, @Param("example") PAlbumPicExample example);

    int updateByExample(@Param("record") PAlbumPic record, @Param("example") PAlbumPicExample example);

    int updateByPrimaryKeySelective(PAlbumPic record);

    int updateByPrimaryKey(PAlbumPic record);
}