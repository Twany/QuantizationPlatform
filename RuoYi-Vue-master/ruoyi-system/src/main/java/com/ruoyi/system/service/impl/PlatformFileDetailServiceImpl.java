package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlatformFileDetailMapper;
import com.ruoyi.system.domain.PlatformFileDetail;
import com.ruoyi.system.service.IPlatformFileDetailService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class PlatformFileDetailServiceImpl implements IPlatformFileDetailService
{
    @Autowired
    private PlatformFileDetailMapper platformFileDetailMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public PlatformFileDetail selectPlatformFileDetailById(Long id)
    {
        return platformFileDetailMapper.selectPlatformFileDetailById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PlatformFileDetail> selectPlatformFileDetailList(PlatformFileDetail platformFileDetail)
    {
        return platformFileDetailMapper.selectPlatformFileDetailList(platformFileDetail);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPlatformFileDetail(PlatformFileDetail platformFileDetail)
    {
        platformFileDetail.setCreateTime(DateUtils.getNowDate());
        return platformFileDetailMapper.insertPlatformFileDetail(platformFileDetail);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePlatformFileDetail(PlatformFileDetail platformFileDetail)
    {
        return platformFileDetailMapper.updatePlatformFileDetail(platformFileDetail);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePlatformFileDetailByIds(Long[] ids)
    {
        return platformFileDetailMapper.deletePlatformFileDetailByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePlatformFileDetailById(Long id)
    {
        return platformFileDetailMapper.deletePlatformFileDetailById(id);
    }
}
