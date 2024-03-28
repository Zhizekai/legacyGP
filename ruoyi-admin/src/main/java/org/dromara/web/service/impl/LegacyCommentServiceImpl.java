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
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.domain.LegacyComment;
import org.dromara.web.mapper.LegacyCommentMapper;
import org.dromara.web.service.ILegacyCommentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 评论Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@RequiredArgsConstructor
@Service
public class LegacyCommentServiceImpl implements ILegacyCommentService {

    private final LegacyCommentMapper baseMapper;

    /**
     * 查询评论
     */
    @Override
    public LegacyCommentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询评论列表
     */
    @Override
    public TableDataInfo<LegacyCommentVo> queryPageList(LegacyCommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyComment> lqw = buildQueryWrapper(bo);
        Page<LegacyCommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询评论列表
     */
    @Override
    public List<LegacyCommentVo> queryList(LegacyCommentBo bo) {
        LambdaQueryWrapper<LegacyComment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyComment> buildQueryWrapper(LegacyCommentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyComment> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSourceId() != null, LegacyComment::getSourceId, bo.getSourceId());
        lqw.eq(bo.getSourceType() != null, LegacyComment::getSourceType, bo.getSourceType());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), LegacyComment::getType, bo.getType());
        lqw.eq(bo.getParentId() != null, LegacyComment::getParentId, bo.getParentId());
        lqw.eq(bo.getReplyId() != null, LegacyComment::getReplyId, bo.getReplyId());
        lqw.eq(bo.getTargetUser() != null, LegacyComment::getTargetUser, bo.getTargetUser());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), LegacyComment::getContent, bo.getContent());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyComment::getCreateDate ,params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyComment::getModifiedDate ,params.get("beginModifiedDate"), params.get("endModifiedDate"));
        return lqw;
    }

    /**
     * 新增评论
     */
    @Override
    public Boolean insertByBo(LegacyCommentBo bo) {
        LegacyComment add = MapstructUtils.convert(bo, LegacyComment.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改评论
     */
    @Override
    public Boolean updateByBo(LegacyCommentBo bo) {
        LegacyComment update = MapstructUtils.convert(bo, LegacyComment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyComment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除评论
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
