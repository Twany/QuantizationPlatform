package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 platform_data_type
 *
 * @author ruoyi
 * @date 2023-12-28
 */
public class PlatformDataType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 数据类型名 */
    @Excel(name = "数据类型名")
    private String dataTypeName;

    /** 数据类型描述 */
    @Excel(name = "数据类型描述")
    private String dataTypeDesc;

    /** 权限标识，如：file:type:future */
    @Excel(name = "权限标识，如：file:type:future")
    private String dataPermission;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDataTypeName(String dataTypeName)
    {
        this.dataTypeName = dataTypeName;
    }

    public String getDataTypeName()
    {
        return dataTypeName;
    }
    public void setDataTypeDesc(String dataTypeDesc)
    {
        this.dataTypeDesc = dataTypeDesc;
    }

    public String getDataTypeDesc()
    {
        return dataTypeDesc;
    }
    public void setDataPermission(String dataPermission)
    {
        this.dataPermission = dataPermission;
    }

    public String getDataPermission()
    {
        return dataPermission;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dataTypeName", getDataTypeName())
                .append("dataTypeDesc", getDataTypeDesc())
                .append("dataPermission", getDataPermission())
                .toString();
    }
}
