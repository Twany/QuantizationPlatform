package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PlatformDataType;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-28
 */
public interface PlatformDataTypeMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public PlatformDataType selectPlatformDataTypeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param platformDataType 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PlatformDataType> selectPlatformDataTypeList(PlatformDataType platformDataType);

    /**
     * 新增【请填写功能名称】
     *
     * @param platformDataType 【请填写功能名称】
     * @return 结果
     */
    public int insertPlatformDataType(PlatformDataType platformDataType);

    /**
     * 修改【请填写功能名称】
     *
     * @param platformDataType 【请填写功能名称】
     * @return 结果
     */
    public int updatePlatformDataType(PlatformDataType platformDataType);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deletePlatformDataTypeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformDataTypeByIds(Long[] ids);
}
