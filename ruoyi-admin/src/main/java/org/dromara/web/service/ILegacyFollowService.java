package org.dromara.web.service;

import org.dromara.web.domain.LegacyFollow;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关注Service接口
 *
 * @author Lion Li
 * @date 2024-03-30
 */
public interface ILegacyFollowService {

    /**
     * 查询关注
     */
    LegacyFollowVo queryById(Long id);

    LegacyFollow checkIsFollow(Long userId);

    /**
     * 查询关注列表
     */
    TableDataInfo<LegacyFollowVo> queryPageList(LegacyFollowBo bo, PageQuery pageQuery);

    /**
     * 查询关注列表
     */
    List<LegacyFollowVo> queryList(LegacyFollowBo bo);

    /**
     * 新增关注
     */
    Boolean insertByBo(LegacyFollowBo bo);


    // 关注和取消关注用户
    Boolean followToggle(LegacyFollowBo bo);

    /**
     * 修改关注
     */
    Boolean updateByBo(LegacyFollowBo bo);

    /**
     * 校验并批量删除关注信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
