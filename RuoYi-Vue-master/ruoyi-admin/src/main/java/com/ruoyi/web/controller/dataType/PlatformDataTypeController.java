package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PlatformDataType;
import com.ruoyi.system.service.IPlatformDataTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/dataType")
public class PlatformDataTypeController extends BaseController
{
    @Autowired
    private IPlatformDataTypeService platformDataTypeService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformDataType platformDataType)
    {
        startPage();
        List<PlatformDataType> list = platformDataTypeService.selectPlatformDataTypeList(platformDataType);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformDataType platformDataType)
    {
        List<PlatformDataType> list = platformDataTypeService.selectPlatformDataTypeList(platformDataType);
        ExcelUtil<PlatformDataType> util = new ExcelUtil<PlatformDataType>(PlatformDataType.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(platformDataTypeService.selectPlatformDataTypeById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformDataType platformDataType)
    {
        return toAjax(platformDataTypeService.insertPlatformDataType(platformDataType));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformDataType platformDataType)
    {
        return toAjax(platformDataTypeService.updatePlatformDataType(platformDataType));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(platformDataTypeService.deletePlatformDataTypeByIds(ids));
    }
}
