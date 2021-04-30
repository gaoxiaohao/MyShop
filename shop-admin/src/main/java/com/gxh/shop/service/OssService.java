package com.gxh.shop.service;

import com.gxh.shop.dto.OssCallbackResult;
import com.gxh.shop.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gxh
 */
public interface OssService {

    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}
