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
import org.dromara.web.domain.bo.LegacyShortmsgBo;
import org.dromara.web.domain.vo.LegacyShortmsgVo;
import org.dromara.web.domain.LegacyShortmsg;
import org.dromara.web.mapper.LegacyShortmsgMapper;
import org.dromara.web.service.ILegacyShortmsgService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 短消息Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@RequiredArgsConstructor
@Service
public class LegacyShortmsgServiceImpl implements ILegacyShortmsgService {

    private final LegacyShortmsgMapper baseMapper;

    /**
     * 查询短消息
     */
    @Override
    public LegacyShortmsgVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询短消息列表
     */
    @Override
    public TableDataInfo<LegacyShortmsgVo> queryPageList(LegacyShortmsgBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyShortmsg> lqw = buildQueryWrapper(bo);
        Page<LegacyShortmsgVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询短消息列表
     */
    @Override
    public List<LegacyShortmsgVo> queryList(LegacyShortmsgBo bo) {
        LambdaQueryWrapper<LegacyShortmsg> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyShortmsg> buildQueryWrapper(LegacyShortmsgBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyShortmsg> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), LegacyShortmsg::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getImages()), LegacyShortmsg::getImages, bo.getImages());
        lqw.eq(StringUtils.isNotBlank(bo.getSmGroup()), LegacyShortmsg::getSmGroup, bo.getSmGroup());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyShortmsg::getCreateDate ,params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyShortmsg::getModifiedDate ,params.get("beginModifiedDate"), params.get("endModifiedDate"));
        return lqw;
    }

    /**
     * 新增短消息
     */
    @Override
    public Boolean insertByBo(LegacyShortmsgBo bo) {
        LegacyShortmsg add = MapstructUtils.convert(bo, LegacyShortmsg.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改短消息
     */
    @Override
    public Boolean updateByBo(LegacyShortmsgBo bo) {
        LegacyShortmsg update = MapstructUtils.convert(bo, LegacyShortmsg.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyShortmsg entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除短消息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
