package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.web.domain.LegacyUser;
import org.dromara.web.domain.bo.LegacyUserBo;
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
    public LegacyFollowVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    // 获取用户是否关注了当前用户
    @Override
    public LegacyFollow checkIsFollow(Long userId) {
        LegacyFollowBo bo = new LegacyFollowBo();
        bo.setUserId(userId);
        bo.setFansId(StpUtil.getLoginIdAsLong());
        MPJLambdaWrapper<LegacyFollow> lqw = buildQueryWrapper(bo);
        return baseMapper.selectOne(lqw);
    }

    /**
     * 查询关注列表
     */
    @Override
    public TableDataInfo<LegacyFollowVo> queryPageList(LegacyFollowBo bo, PageQuery pageQuery) {


        MPJLambdaWrapper<LegacyFollow> lqw = buildQueryWrapper(bo);
        lqw.selectAll(LegacyFollow.class)
            .selectAssociation(LegacyUser.class, LegacyFollowVo::getLegacyUser)
            .leftJoin(LegacyUser.class, LegacyUser::getId, LegacyFollow::getFansId);
        Page<LegacyFollowVo> result = baseMapper.selectJoinPage(pageQuery.build(),LegacyFollowVo.class,  lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关注列表
     */
    @Override
    public List<LegacyFollowVo> queryList(LegacyFollowBo bo) {
        MPJLambdaWrapper<LegacyFollow> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private MPJLambdaWrapper<LegacyFollow> buildQueryWrapper(LegacyFollowBo bo) {
        Map<String, Object> params = bo.getParams();
        MPJLambdaWrapper<LegacyFollow> lqw = new MPJLambdaWrapper<LegacyFollow>();
        lqw.eq(bo.getUserId() != null, LegacyFollow::getUserId, bo.getUserId());
        lqw.eq(bo.getFansId() != null, LegacyFollow::getFansId, bo.getFansId());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyFollow::getCreateDate, params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyFollow::getModifiedDate, params.get("beginModifiedDate"), params.get("endModifiedDate"));
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

    // 关注，取消关注用户
    @Override
    public Boolean followToggle(LegacyFollowBo bo) {
        MPJLambdaWrapper<LegacyFollow> wrapper = new MPJLambdaWrapper<>();
        wrapper
            .eq(bo.getUserId() != null, LegacyFollow::getUserId, bo.getUserId())
            .eq(bo.getFansId() != null, LegacyFollow::getFansId, bo.getFansId());
        LegacyFollow legacyFollow = baseMapper.selectOne(wrapper);
        if (legacyFollow != null) {
            int i = baseMapper.deleteById(legacyFollow.getId());
            return i > 0;
        }else {
            LegacyFollow add = MapstructUtils.convert(bo, LegacyFollow.class);
            validEntityBeforeSave(add);
            return baseMapper.insert(add) > 0;
        }
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
    private void validEntityBeforeSave(LegacyFollow entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关注
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
