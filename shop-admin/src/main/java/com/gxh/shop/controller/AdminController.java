package com.gxh.shop.controller;


import cn.hutool.core.collection.CollUtil;
import com.gxh.shop.api.CommonPage;
import com.gxh.shop.api.CommonResult;
import com.gxh.shop.dto.UpdateAdminPasswordParam;
import com.gxh.shop.model.SAdmin;
import com.gxh.shop.model.SRole;
import com.gxh.shop.service.AdminService;
import com.gxh.shop.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gxh
 */
@Slf4j
@Api(tags = "AdminController-管理员管理")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;


    
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult<SAdmin> register(@Validated @RequestBody SAdmin admin){
        SAdmin sAdmin = adminService.register(admin);
        if (sAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(sAdmin);
    }


    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@Validated @RequestBody SAdmin admin) {
        String token = adminService.login(admin.getUsername(), admin.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }


    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String,Object>> getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        SAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<SRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(SRole::getName)
                    .collect(Collectors.toList());
            data.put("roles",roles);
        }
                                                            
        return CommonResult.success(data);
    }


    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<SAdmin> getItem(@PathVariable Long id) {
        SAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult<Integer> update(@PathVariable Long id, @RequestBody SAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public CommonResult<Integer> updatePassword(@Validated @RequestBody UpdateAdminPasswordParam param) {
        int status = adminService.updatePassword(param);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        SAdmin umsAdmin = new SAdmin();
        umsAdmin.setStatus(status);
        int count = adminService.update(id,umsAdmin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SRole>> getRoleList(@PathVariable Long adminId) {
        List<SRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }
}
