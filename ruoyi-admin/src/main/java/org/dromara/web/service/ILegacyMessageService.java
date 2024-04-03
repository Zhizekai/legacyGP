package org.dromara.web.service;

import org.dromara.web.domain.vo.LegacyMessageVo;
import org.dromara.web.domain.bo.LegacyMessageBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 消息Service接口
 *
 * @author Lion Li
 * @date 2024-03-28
 */
public interface ILegacyMessageService {

    /**
     * 查询消息
     */
    LegacyMessageVo queryById(Long id);

    /**
     * 查询消息列表
     */
    TableDataInfo<LegacyMessageVo> queryPageList(LegacyMessageBo bo, PageQuery pageQuery);



    // 查询消息数量
    Map<String, Long> countMessage(Long userId);
    /**
     * 查询消息列表
     */
    List<LegacyMessageVo> queryList(LegacyMessageBo bo);

    /**
     * 新增消息
     */
    Boolean insertByBo(LegacyMessageBo bo);

    /**
     * 修改消息
     */
    Boolean updateByBo(LegacyMessageBo bo);

    /**
     * 校验并批量删除消息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
