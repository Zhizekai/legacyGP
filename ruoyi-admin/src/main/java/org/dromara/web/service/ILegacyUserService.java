package org.dromara.web.service;

import org.dromara.web.domain.LegacyUser;
import org.dromara.web.domain.vo.LegacyUserVo;
import org.dromara.web.domain.bo.LegacyUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service接口
 *
 * @author Lion Li
 * @date 2024-03-28
 */
public interface ILegacyUserService {

    /**
     * 查询用户
     */
    LegacyUserVo queryById(Long id);

    /**
     * 查询用户列表
     */
    TableDataInfo<LegacyUserVo> queryPageList(LegacyUserBo bo, PageQuery pageQuery);

    /**
     * 查询用户列表
     */
    List<LegacyUserVo> queryList(LegacyUserBo bo);


    // 给用户对象添加粉丝数和关注数
    LegacyUserVo queryUserFollowAndFans(LegacyUserVo legacyUserVo);

    /**
     * 新增用户
     */
    Boolean insertByBo(LegacyUserBo bo);

    /**
     * 修改用户
     */
    Boolean updateByBo(LegacyUserBo bo);

    /**
     * 校验并批量删除用户信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
