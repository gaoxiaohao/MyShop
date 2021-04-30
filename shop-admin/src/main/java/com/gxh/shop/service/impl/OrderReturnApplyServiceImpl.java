package com.gxh.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxh.shop.dao.OrderReturnApplyDao;
import com.gxh.shop.dto.OrderReturnApplyResult;
import com.gxh.shop.dto.ReturnApplyQueryParam;
import com.gxh.shop.dto.UpdateStatusParam;
import com.gxh.shop.mapper.OOrderReturnApplyMapper;
import com.gxh.shop.model.OOrderReturnApply;
import com.gxh.shop.model.OOrderReturnApplyExample;
import com.gxh.shop.service.OrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gxh
 */
@Service
public class OrderReturnApplyServiceImpl implements OrderReturnApplyService {

    @Autowired
    private OrderReturnApplyDao returnApplyDao;
    @Autowired
    private OOrderReturnApplyMapper returnApplyMapper;
    

    @Override
    public List<OOrderReturnApply> list(ReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return returnApplyDao.getList(queryParam);
    }

    @Override
    public int delete(List<Long> ids) {
        OOrderReturnApplyExample example = new OOrderReturnApplyExample();
        example.createCriteria().andIdIn(ids).andStatusEqualTo(3);
        return returnApplyMapper.deleteByExample(example);
    }

    @Override
    public int updateStatus(Long id, UpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OOrderReturnApply returnApply = new OOrderReturnApply();
        if(status.equals(1)){
            //确认退货
            returnApply.setId(id);
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else if(status.equals(2)){
            //完成退货
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        }else if(status.equals(3)){
            //拒绝退货
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else{
            return 0;
        }
        return returnApplyMapper.updateByPrimaryKeySelective(returnApply);
    }

    @Override
    public OrderReturnApplyResult getItem(Long id) {
        return returnApplyDao.getDetail(id);
    }
}
