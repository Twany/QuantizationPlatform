package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 platform_file_detail
 *
 * @author ruoyi
 * @date 2023-11-23
 */
public class PlatformFileDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fileName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fileType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fileDesc;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fileUrl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long fileSize;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long uploaderId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String uploaderName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long parentFileId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileType()
    {
        return fileType;
    }
    public void setFileDesc(String fileDesc)
    {
        this.fileDesc = fileDesc;
    }

    public String getFileDesc()
    {
        return fileDesc;
    }
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }
    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize()
    {
        return fileSize;
    }
    public void setUploaderId(Long uploaderId)
    {
        this.uploaderId = uploaderId;
    }

    public Long getUploaderId()
    {
        return uploaderId;
    }
    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }

    public String getUploaderName()
    {
        return uploaderName;
    }
    public void setParentFileId(Long parentFileId)
    {
        this.parentFileId = parentFileId;
    }

    public Long getParentFileId()
    {
        return parentFileId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fileName", getFileName())
                .append("fileType", getFileType())
                .append("fileDesc", getFileDesc())
                .append("createTime", getCreateTime())
                .append("fileUrl", getFileUrl())
                .append("fileSize", getFileSize())
                .append("uploaderId", getUploaderId())
                .append("uploaderName", getUploaderName())
                .append("parentFileId", getParentFileId())
                .toString();
    }
}
