package com.gxh.shop.controller;

import com.gxh.shop.api.CommonPage;
import com.gxh.shop.api.CommonResult;
import com.gxh.shop.model.SMenu;
import com.gxh.shop.model.SResource;
import com.gxh.shop.model.SRole;
import com.gxh.shop.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gxh
 */
@RestController
@Api(tags = "RoleController-角色管理")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("添加角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SRole role) {
        int count = roleService.update(id, role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = roleService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SRole>> listAll() {
        List<SRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SRole> roleList = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        SRole umsRole = new SRole();
        umsRole.setStatus(status);
        int count = roleService.update(id, umsRole);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SMenu>> listMenu(@PathVariable Long roleId) {
        List<SMenu> roleList = roleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SResource>> listResource(@PathVariable Long roleId) {
        List<SResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}
