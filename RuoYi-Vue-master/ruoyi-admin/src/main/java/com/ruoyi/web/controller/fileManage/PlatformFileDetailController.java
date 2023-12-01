package com.ruoyi.web.controller.fileManage;

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
import com.ruoyi.system.domain.PlatformFileDetail;
import com.ruoyi.system.service.IPlatformFileDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/fileManage/fileList")
public class PlatformFileDetailController extends BaseController
{
    @Autowired
    private IPlatformFileDetailService platformFileDetailService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list/{parentId}")
    public TableDataInfo list(@PathVariable Integer parentId)
    {
        PlatformFileDetail platformFileDetail = new PlatformFileDetail();
        platformFileDetail.setParentFileId(Long.valueOf(parentId));
        List<PlatformFileDetail> list = platformFileDetailService.selectPlatformFileDetailList(platformFileDetail);
        return getDataTable(list);
    }

    /**
     * 通过文件parent id和数据源种类id获取详细信息
     */
    @GetMapping(value = "/{parentId}/{typeId}")
    public TableDataInfo getInfoByIdAndTypeId(@PathVariable("parentId") Long parentId,@PathVariable("typeId") Long typeId)
    {
        PlatformFileDetail platformFileDetail = new PlatformFileDetail();
        platformFileDetail.setParentFileId(Long.valueOf(parentId));
        platformFileDetail.setFileTypeId(Long.valueOf(typeId));
        List<PlatformFileDetail> list = platformFileDetailService.selectPlatformFileDetailList(platformFileDetail);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformFileDetail platformFileDetail)
    {
        List<PlatformFileDetail> list = platformFileDetailService.selectPlatformFileDetailList(platformFileDetail);
        ExcelUtil<PlatformFileDetail> util = new ExcelUtil<PlatformFileDetail>(PlatformFileDetail.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(platformFileDetailService.selectPlatformFileDetailById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformFileDetail platformFileDetail)
    {
        System.out.println(platformFileDetail.toString());
        return toAjax(platformFileDetailService.insertPlatformFileDetail(platformFileDetail));
    }

    // 下载文件
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws Exception
    {
        //PlatformFileDetail platformFileDetail = platformFileDetailService.selectPlatformFileDetailById(id);
        //platformFileDetailService.downloadFile(platformFileDetail, response);
    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformFileDetail platformFileDetail)
    {
        return toAjax(platformFileDetailService.updatePlatformFileDetail(platformFileDetail));
    }

    /**
     * 删除【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(platformFileDetailService.deletePlatformFileDetailByIds(ids));
    }
}
