package com.ruoyi.web.controller.system;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.uuid.Seq;
import com.ruoyi.common.utils.uuid.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.service.ISysFileInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import static com.ruoyi.common.utils.uuid.Seq.getId;

/**
 * 文件信息Controller
 *
 * @author ruoyi
 * @date 2023-12-01
 */
@RestController
@RequestMapping("/fileInfo")
public class SysFileInfoController extends BaseController
{
    @Autowired
    private ISysFileInfoService sysFileInfoService;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/addFile")
    public SysFileInfo addSave(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, SysFileInfo fileInfo) throws IOException, IOException {
        // 获取resources文件夹的路径
        Resource resource = resourceLoader.getResource("classpath:resources/");
        File staticFolder = resource.getFile();

        // 根据当前时间创建文件夹
        String folderName = new SimpleDateFormat("yyyyMMdd_HH").format(new Date());
        File folder = new File(staticFolder, folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // 获取文件后缀，创建新的文件名
        String extension = FileUploadUtils.getExtension(file);
        String newFileName = generateRandomFileName()+"."+extension;

        // 将文件保存到新创建的文件夹下
        Path filePath = Paths.get(folder.getAbsolutePath(), newFileName);
        Files.write(filePath, file.getBytes());
        System.out.println("文件保存路径：" + folderName + "/" + newFileName);

        // 获取文件大小
        long size = file.getSize();
        fileInfo.setFileName(fileName);
        fileInfo.setFilePath(folderName + "/" + newFileName);
        fileInfo.setFileSize(size);
        fileInfo.setFileType(extension);
        sysFileInfoService.insertSysFileInfo(fileInfo);
        return fileInfo;
    }

    public static String generateRandomFileName() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    @Log(title = "下载文件", businessType = BusinessType.EXPORT)
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        // 获取resources文件夹的路径
        Resource localResource = resourceLoader.getResource("classpath:resources/");
        File staticFolder = localResource.getFile();

        // 将文件保存到新创建的文件夹下
        String absFilePath = staticFolder.getAbsolutePath()+ "/" + resource;
        System.out.println("文件下载地址：" + absFilePath);
        FileUtils.writeBytes(absFilePath, response.getOutputStream());
    }

    /**
     * 获取下载路径
    */
     @GetMapping("/common/download/getDownloadUrl")
     @Log(title = "获取文件路径", businessType = BusinessType.EXPORT)
     public AjaxResult getDownloadUrl(String resource, HttpServletRequest request, HttpServletResponse response) throws Exception {
         // 本地资源路径
         String localPath = RuoYiConfig.getUploadPath();
         // 数据库资源地址
         String downloadPath = localPath + StringUtils.substringAfter(resource, "/profile/upload");
         return success(downloadPath);
     }



    /**
     * 查询文件信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysFileInfo sysFileInfo)
    {
        startPage();
        List<SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFileInfo sysFileInfo)
    {
        List<SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        ExcelUtil<SysFileInfo> util = new ExcelUtil<SysFileInfo>(SysFileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(sysFileInfoService.selectSysFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.insertSysFileInfo(sysFileInfo));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.updateSysFileInfo(sysFileInfo));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileInfoService.deleteSysFileInfoByFileIds(fileIds));
    }
}
