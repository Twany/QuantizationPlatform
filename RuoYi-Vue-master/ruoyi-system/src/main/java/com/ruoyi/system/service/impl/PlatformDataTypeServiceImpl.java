package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlatformDataTypeMapper;
import com.ruoyi.system.domain.PlatformDataType;
import com.ruoyi.system.service.IPlatformDataTypeService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-28
 */
@Service
public class PlatformDataTypeServiceImpl implements IPlatformDataTypeService
{
    @Autowired
    private PlatformDataTypeMapper platformDataTypeMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public PlatformDataType selectPlatformDataTypeById(Long id)
    {
        return platformDataTypeMapper.selectPlatformDataTypeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param platformDataType 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PlatformDataType> selectPlatformDataTypeList(PlatformDataType platformDataType)
    {
        return platformDataTypeMapper.selectPlatformDataTypeList(platformDataType);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param platformDataType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPlatformDataType(PlatformDataType platformDataType)
    {
        return platformDataTypeMapper.insertPlatformDataType(platformDataType);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param platformDataType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePlatformDataType(PlatformDataType platformDataType)
    {
        return platformDataTypeMapper.updatePlatformDataType(platformDataType);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePlatformDataTypeByIds(Long[] ids)
    {
        return platformDataTypeMapper.deletePlatformDataTypeByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePlatformDataTypeById(Long id)
    {
        return platformDataTypeMapper.deletePlatformDataTypeById(id);
    }
}
