package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PlatformFileDetail;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-11-23
 */
public interface IPlatformFileDetailService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public PlatformFileDetail selectPlatformFileDetailById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PlatformFileDetail> selectPlatformFileDetailList(PlatformFileDetail platformFileDetail);

    /**
     * 新增【请填写功能名称】
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 结果
     */
    public int insertPlatformFileDetail(PlatformFileDetail platformFileDetail);

    /**
     * 修改【请填写功能名称】
     *
     * @param platformFileDetail 【请填写功能名称】
     * @return 结果
     */
    public int updatePlatformFileDetail(PlatformFileDetail platformFileDetail);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deletePlatformFileDetailByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deletePlatformFileDetailById(Long id);
}
