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
import org.dromara.web.domain.bo.LegacyUserBo;
import org.dromara.web.domain.vo.LegacyUserVo;
import org.dromara.web.domain.LegacyUser;
import org.dromara.web.mapper.LegacyUserMapper;
import org.dromara.web.service.ILegacyUserService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@RequiredArgsConstructor
@Service
public class LegacyUserServiceImpl implements ILegacyUserService {

    private final LegacyUserMapper baseMapper;

    /**
     * 查询用户
     */
    @Override
    public LegacyUserVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户列表
     */
    @Override
    public TableDataInfo<LegacyUserVo> queryPageList(LegacyUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LegacyUser> lqw = buildQueryWrapper(bo);
        Page<LegacyUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<LegacyUserVo> queryList(LegacyUserBo bo) {
        LambdaQueryWrapper<LegacyUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LegacyUser> buildQueryWrapper(LegacyUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LegacyUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), LegacyUser::getPhone, bo.getPhone());
        lqw.like(StringUtils.isNotBlank(bo.getUsername()), LegacyUser::getUsername, bo.getUsername());
        lqw.eq(StringUtils.isNotBlank(bo.getPassword()), LegacyUser::getPassword, bo.getPassword());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), LegacyUser::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getPosition()), LegacyUser::getPosition, bo.getPosition());
        lqw.eq(StringUtils.isNotBlank(bo.getCompany()), LegacyUser::getCompany, bo.getCompany());
        lqw.eq(StringUtils.isNotBlank(bo.getJuePower()), LegacyUser::getJuePower, bo.getJuePower());
        lqw.eq(bo.getGoodNum() != null, LegacyUser::getGoodNum, bo.getGoodNum());
        lqw.eq(bo.getReadNum() != null, LegacyUser::getReadNum, bo.getReadNum());
        lqw.between(params.get("beginCreateDate") != null && params.get("endCreateDate") != null,
            LegacyUser::getCreateDate ,params.get("beginCreateDate"), params.get("endCreateDate"));
        lqw.between(params.get("beginModifiedDate") != null && params.get("endModifiedDate") != null,
            LegacyUser::getModifiedDate ,params.get("beginModifiedDate"), params.get("endModifiedDate"));
        return lqw;
    }

    /**
     * 新增用户
     */
    @Override
    public Boolean insertByBo(LegacyUserBo bo) {
        LegacyUser add = MapstructUtils.convert(bo, LegacyUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户
     */
    @Override
    public Boolean updateByBo(LegacyUserBo bo) {
        LegacyUser update = MapstructUtils.convert(bo, LegacyUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LegacyUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
