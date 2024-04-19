package org.dromara.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.web.domain.LegacyComment;
import org.dromara.web.domain.LegacyFollow;
import org.dromara.web.domain.LegacyPraise;
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.mapper.LegacyCommentMapper;
import org.dromara.web.mapper.LegacyFollowMapper;
import org.dromara.web.mapper.LegacyPraiseMapper;
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

    private final LegacyPraiseMapper legacyPraiseMapper;

    private final LegacyCommentMapper legacyCommentMapper;

    private final LegacyFollowMapper legacyFollowMapper;

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
//        QueryWrapper<LegacyMessage> lqw1 = new  QueryWrapper<>();
//        System.out.println(userId);
//        lqw1.select("count(*) as num,user_id,type")
//            .eq(userId != null, "user_id", userId)
//            .eq("status", 0)
//            .groupBy("type");
//
//        List<Map<String, Object>> aLong = baseMapper.selectMaps(lqw1);


        // 统计所有未读的评论
        QueryWrapper<LegacyComment> legacyCommentQueryWrapper = new QueryWrapper<>();
        legacyCommentQueryWrapper.eq("target_user",userId)
            .eq("source_type",2)
            .eq("read_status", 0);
        Long commentCount = legacyCommentMapper.selectCount(legacyCommentQueryWrapper);

        // 统计所有未读的点赞
        QueryWrapper<LegacyPraise> legacyPraiseQueryWrapper = new QueryWrapper<>();
        legacyPraiseQueryWrapper.eq("target_user",userId).eq("read_status", 0);
        Long praiseCount = legacyPraiseMapper.selectCount(legacyPraiseQueryWrapper);

        // 统计所有未读的关注
        QueryWrapper<LegacyFollow> legacyFollowQueryWrapper = new QueryWrapper<>();
        legacyFollowQueryWrapper.eq("user_id",userId).eq("read_status", 0);
        Long followCount = legacyFollowMapper.selectCount(legacyFollowQueryWrapper);


        // TODO 统计函数需要优化
        Map<String, Long> map = new HashMap<String, Long>();

        map.put("comment",commentCount);
        map.put("praise",  praiseCount);
        map.put("follow", followCount);
        map.put("total", commentCount+praiseCount+followCount);

//        map.put("comment", commentCount);
//        for (Map<String, Object> a : aLong) {
//            if ((Integer)a.get("type") == 1) {
//
//            }
//            if ((Integer)a.get("type") == 2) {
//                map.put("praise", praiseCount);
//            }
//            if ((Integer)a.get("type") == 3) {
//                map.put("follow", followCount);
//            }
//        }
//        map.put("total",commentCount+praiseCount+followCount);

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



    // 阅读评论消息
    @Override
    public LegacyCommentVo readCommentMessage(LegacyCommentBo bo){
        bo.setReadStatus(1);

        LegacyComment updateEntity = MapstructUtils.convert(bo, LegacyComment.class);
        int i = legacyCommentMapper.updateById(updateEntity);
        if (i != 0) {
            assert updateEntity != null;
            return legacyCommentMapper.selectVoById(updateEntity.getId());
        }else {
            return null;
        }
    }


    // 阅读点赞消息
    @Override
    public LegacyPraiseVo readPraiseMessage(LegacyPraiseBo bo){

        bo.setReadStatus(1);
        LegacyPraise updateEntity = MapstructUtils.convert(bo, LegacyPraise.class);
        int i = legacyPraiseMapper.updateById(updateEntity);
        if (i != 0) {
            assert updateEntity != null;
            return legacyPraiseMapper.selectVoById(updateEntity.getId());
        }else {
            return null;
        }
    }

    // 阅读关注消息
    @Override
    public LegacyFollowVo readFollowMessage(LegacyFollowBo bo){
        bo.setReadStatus(1);
        LegacyFollow updateEntity = MapstructUtils.convert(bo, LegacyFollow.class);
        int i = legacyFollowMapper.updateById(updateEntity);
        if (i != 0) {
            assert updateEntity != null;
            return legacyFollowMapper.selectVoById(updateEntity.getId());
        }else {
            return null;
        }
    }




}





