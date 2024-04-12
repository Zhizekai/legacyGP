package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.web.domain.LegacyPraise;
import org.dromara.web.domain.LegacyUser;
import org.dromara.web.mapper.LegacyPraiseMapper;
import org.springframework.stereotype.Service;
import org.dromara.web.domain.bo.LegacyArticleBo;
import org.dromara.web.domain.vo.LegacyArticleVo;
import org.dromara.web.domain.LegacyArticle;
import org.dromara.web.mapper.LegacyArticleMapper;
import org.dromara.web.service.ILegacyArticleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文章Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-27
 */
@RequiredArgsConstructor
@Service
public class LegacyArticleServiceImpl implements ILegacyArticleService {

    private final LegacyArticleMapper baseMapper;

    private final LegacyPraiseMapper legacyPraiseMapper;

    // 获取文章详细信息
    @Override
    public LegacyArticleVo queryById(Long id){
        // 文章要和作者进行关联
        MPJLambdaWrapper<LegacyArticle> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper
            .selectAll(LegacyArticle.class)
            .eq(LegacyArticle::getId, id)
            .selectAssociation(LegacyUser.class, LegacyArticleVo::getUser)
            .leftJoin(LegacyUser.class, LegacyUser::getId, LegacyArticle::getAuthor);
        LegacyArticleVo legacyArticleVo = baseMapper.selectJoinOne(LegacyArticleVo.class, mpjLambdaWrapper);
        MPJLambdaWrapper<LegacyPraise> legacyPraiseMPJLambdaWrapper = new MPJLambdaWrapper<>();
        legacyPraiseMPJLambdaWrapper.eq(LegacyPraise::getTargetId, legacyArticleVo.getId());
        Long praiseCount = legacyPraiseMapper.selectCount(legacyPraiseMPJLambdaWrapper);
        legacyArticleVo.setPraiseCount(praiseCount);
        legacyArticleVo.setCollectionCount(Long.parseLong("0"));
        return legacyArticleVo;
    }

    /**
     * 查询文章列表
     */
    @Override
    public TableDataInfo<LegacyArticleVo> queryPageList(LegacyArticleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyArticle> lqw = buildQueryWrapper(bo);
        Page<LegacyArticleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文章列表
     */
    @Override
    public List<LegacyArticleVo> queryList(LegacyArticleBo bo) {
        LambdaQueryWrapper<LegacyArticle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyArticle> buildQueryWrapper(LegacyArticleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyArticle> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), LegacyArticle::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getIntroduction()), LegacyArticle::getIntroduction, bo.getIntroduction());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), LegacyArticle::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getCategory()), LegacyArticle::getCategory, bo.getCategory());
        lqw.eq(bo.getStatus() != null, LegacyArticle::getStatus, bo.getStatus());
        lqw.eq(bo.getPageView() != null, LegacyArticle::getPageView, bo.getPageView());
        lqw.eq(bo.getAuthor() != null, LegacyArticle::getAuthor, bo.getAuthor());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyArticle::getCreateDate ,params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyArticle::getModifiedDate ,params.get("beginModifiedDate"), params.get("endModifiedDate"));
        return lqw;
    }


    // 前台新增文章
    @Override
    public LegacyArticle insertFrontArticleByBo(LegacyArticleBo bo) {
        LegacyArticle add = MapstructUtils.convert(bo, LegacyArticle.class);
//        validEntityBeforeSave(add); // 数据校验
        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        assert add != null;
        add.setAuthor(loginIdAsLong);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
            return add;
        }else {
            return null;
        }
    }

    /**
     * 新增文章
     */
    @Override
    public Boolean insertByBo(LegacyArticleBo bo) {
        LegacyArticle add = MapstructUtils.convert(bo, LegacyArticle.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文章
     */
    @Override
    public Boolean updateByBo(LegacyArticleBo bo) {
        LegacyArticle update = MapstructUtils.convert(bo, LegacyArticle.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyArticle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文章
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
