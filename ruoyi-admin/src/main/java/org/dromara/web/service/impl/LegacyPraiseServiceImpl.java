package org.dromara.web.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.web.domain.LegacyComment;
import org.dromara.web.domain.LegacyUser;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.springframework.stereotype.Service;
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.domain.LegacyPraise;
import org.dromara.web.mapper.LegacyPraiseMapper;
import org.dromara.web.service.ILegacyPraiseService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 点赞Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@RequiredArgsConstructor
@Service
public class LegacyPraiseServiceImpl implements ILegacyPraiseService {

    private final LegacyPraiseMapper baseMapper;

    /**
     * 查询点赞
     */
    @Override
    public LegacyPraiseVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询点赞列表
     */
    @Override
    public TableDataInfo<LegacyPraiseVo> queryPageList(LegacyPraiseBo bo, PageQuery pageQuery) {
        MPJLambdaWrapper<LegacyPraise> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(LegacyPraise.class)
            .selectAssociation(LegacyUser.class, LegacyPraiseVo::getLegacyUser)
            .leftJoin(LegacyUser.class, LegacyUser::getId, LegacyPraise::getCreateBy);
        Page<LegacyPraiseVo> result = baseMapper.selectVoPage(pageQuery.build(), wrapper);
        return TableDataInfo.build(result);
    }

    /**
     * 查询点赞列表
     */
    @Override
    public List<LegacyPraiseVo> queryList(LegacyPraiseBo bo) {
        LambdaQueryWrapper<LegacyPraise> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyPraise> buildQueryWrapper(LegacyPraiseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyPraise> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTargetId() != null, LegacyPraise::getTargetId, bo.getTargetId());
        lqw.eq(StringUtils.isNotBlank(bo.getTargetType()), LegacyPraise::getTargetType, bo.getTargetType());
        lqw.eq(bo.getTargetUser() != null, LegacyPraise::getTargetUser, bo.getTargetUser());
        lqw.eq(bo.getType() != null, LegacyPraise::getType, bo.getType());
        lqw.eq(bo.getCreateDate() != null, LegacyPraise::getCreateDate, bo.getCreateDate());
        lqw.eq(bo.getModifiedDate() != null, LegacyPraise::getModifiedDate, bo.getModifiedDate());
        return lqw;
    }

    /**
     * 新增点赞
     */
    @Override
    public Boolean insertByBo(LegacyPraiseBo bo) {
        LegacyPraise add = MapstructUtils.convert(bo, LegacyPraise.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改点赞
     */
    @Override
    public Boolean updateByBo(LegacyPraiseBo bo) {
        LegacyPraise update = MapstructUtils.convert(bo, LegacyPraise.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyPraise entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除点赞
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
