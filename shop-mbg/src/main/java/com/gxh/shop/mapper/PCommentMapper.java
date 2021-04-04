package com.gxh.shop.mapper;

import com.gxh.shop.model.PComment;
import com.gxh.shop.model.PCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PCommentMapper {
    long countByExample(PCommentExample example);

    int deleteByExample(PCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PComment record);

    int insertSelective(PComment record);

    List<PComment> selectByExampleWithBLOBs(PCommentExample example);

    List<PComment> selectByExample(PCommentExample example);

    PComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PComment record, @Param("example") PCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") PComment record, @Param("example") PCommentExample example);

    int updateByExample(@Param("record") PComment record, @Param("example") PCommentExample example);

    int updateByPrimaryKeySelective(PComment record);

    int updateByPrimaryKeyWithBLOBs(PComment record);

    int updateByPrimaryKey(PComment record);
}