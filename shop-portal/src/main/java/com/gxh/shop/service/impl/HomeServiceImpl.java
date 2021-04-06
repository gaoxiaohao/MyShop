package com.gxh.shop.service.impl;

import com.gxh.shop.component.TtlSender;
import com.gxh.shop.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gxh
 */
@Service
public class HomeServiceImpl implements HomeService {


    @Autowired
    private TtlSender ttlSender;
    @Override
    public String getMessage() {
        ttlSender.sendMessage(1L,10000);
        return "1";
    }
}
