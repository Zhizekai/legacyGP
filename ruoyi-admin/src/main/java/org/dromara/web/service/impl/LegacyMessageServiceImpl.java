package org.dromara.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.web.domain.bo.LegacyMessageBo;
import org.dromara.web.domain.vo.LegacyMessageVo;
import org.dromara.web.domain.LegacyMessage;
import org.dromara.web.mapper.LegacyMessageMapper;
import org.dromara.web.service.ILegacyMessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 消息Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@RequiredArgsConstructor
@Service
public class LegacyMessageServiceImpl implements ILegacyMessageService {

    private final LegacyMessageMapper baseMapper;

    /**
     * 查询消息
     */
    @Override
    public LegacyMessageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询消息列表
     */
    @Override
    public TableDataInfo<LegacyMessageVo> queryPageList(LegacyMessageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyMessage> lqw = buildQueryWrapper(bo);
        Page<LegacyMessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询消息列表
     */
    @Override
    public List<LegacyMessageVo> queryList(LegacyMessageBo bo) {
        LambdaQueryWrapper<LegacyMessage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyMessage> buildQueryWrapper(LegacyMessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyMessage> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, LegacyMessage::getUserId, bo.getUserId());
        lqw.eq(bo.getSourceId() != null, LegacyMessage::getSourceId, bo.getSourceId());
        lqw.eq(bo.getType() != null, LegacyMessage::getType, bo.getType());
        lqw.eq(bo.getStatus() != null, LegacyMessage::getStatus, bo.getStatus());
        lqw.eq(bo.getCreateDate() != null, LegacyMessage::getCreateDate, bo.getCreateDate());
        lqw.eq(bo.getModifiedDate() != null, LegacyMessage::getModifiedDate, bo.getModifiedDate());
        return lqw;
    }


    @Override
    // 统计消息
    public Map<String, Long> countMessage(Long userId) {

        // 我怀疑Wrappers也是框架作者从mp核心代码中拿出来的，这个玩意mp对外api也没有写
        QueryWrapper<LegacyMessage> lqw1 = new  QueryWrapper<>();
        System.out.println(userId);
        lqw1.select("count(*) as num")
            .eq(userId != null, "user_id", userId)
            .eq("status", 0)
            .groupBy("type");
        List<Map<String, Object>> aLong = baseMapper.selectMaps(lqw1);
        System.out.println(aLong);
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("comment", (Long) aLong.get(0).get("num"));
        map.put("praise",  (Long) aLong.get(1).get("num"));
        map.put("follow",  (Long) aLong.get(2).get("num"));

        return map;
    }

    /**
     * 新增消息
     */
    @Override
    public Boolean insertByBo(LegacyMessageBo bo) {
        LegacyMessage add = MapstructUtils.convert(bo, LegacyMessage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改消息
     */
    @Override
    public Boolean updateByBo(LegacyMessageBo bo) {
        LegacyMessage update = MapstructUtils.convert(bo, LegacyMessage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyMessage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除消息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
