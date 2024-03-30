package org.dromara.web.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.domain.LegacyFollow;
import org.dromara.web.mapper.LegacyFollowMapper;
import org.dromara.web.service.ILegacyFollowService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关注Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@RequiredArgsConstructor
@Service
public class LegacyFollowServiceImpl implements ILegacyFollowService {

    private final LegacyFollowMapper baseMapper;

    /**
     * 查询关注
     */
    @Override
    public LegacyFollowVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询关注列表
     */
    @Override
    public TableDataInfo<LegacyFollowVo> queryPageList(LegacyFollowBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyFollow> lqw = buildQueryWrapper(bo);
        Page<LegacyFollowVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关注列表
     */
    @Override
    public List<LegacyFollowVo> queryList(LegacyFollowBo bo) {
        LambdaQueryWrapper<LegacyFollow> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyFollow> buildQueryWrapper(LegacyFollowBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyFollow> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, LegacyFollow::getUserId, bo.getUserId());
        lqw.eq(bo.getFansId() != null, LegacyFollow::getFansId, bo.getFansId());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyFollow::getCreateDate ,params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyFollow::getModifiedDate ,params.get("beginModifiedDate"), params.get("endModifiedDate"));
        return lqw;
    }

    /**
     * 新增关注
     */
    @Override
    public Boolean insertByBo(LegacyFollowBo bo) {
        LegacyFollow add = MapstructUtils.convert(bo, LegacyFollow.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改关注
     */
    @Override
    public Boolean updateByBo(LegacyFollowBo bo) {
        LegacyFollow update = MapstructUtils.convert(bo, LegacyFollow.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyFollow entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关注
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
