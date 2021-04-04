package com.gxh.shop.mapper;

import com.gxh.shop.model.PCommentReplay;
import com.gxh.shop.model.PCommentReplayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PCommentReplayMapper {
    long countByExample(PCommentReplayExample example);

    int deleteByExample(PCommentReplayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PCommentReplay record);

    int insertSelective(PCommentReplay record);

    List<PCommentReplay> selectByExample(PCommentReplayExample example);

    PCommentReplay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PCommentReplay record, @Param("example") PCommentReplayExample example);

    int updateByExample(@Param("record") PCommentReplay record, @Param("example") PCommentReplayExample example);

    int updateByPrimaryKeySelective(PCommentReplay record);

    int updateByPrimaryKey(PCommentReplay record);
}