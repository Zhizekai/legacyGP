package org.dromara.web.service;

import org.dromara.web.domain.LegacyShortmsg;
import org.dromara.web.domain.vo.LegacyShortmsgVo;
import org.dromara.web.domain.bo.LegacyShortmsgBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 短消息Service接口
 *
 * @author Lion Li
 * @date 2024-03-28
 */
public interface ILegacyShortmsgService {

    /**
     * 查询短消息
     */
    LegacyShortmsgVo queryById(Long id);

    /**
     * 查询短消息列表
     */
    TableDataInfo<LegacyShortmsgVo> queryPageList(LegacyShortmsgBo bo, PageQuery pageQuery);

    /**
     * 查询短消息列表
     */
    List<LegacyShortmsgVo> queryList(LegacyShortmsgBo bo);

    /**
     * 新增短消息
     */
    Boolean insertByBo(LegacyShortmsgBo bo);

    /**
     * 修改短消息
     */
    Boolean updateByBo(LegacyShortmsgBo bo);

    /**
     * 校验并批量删除短消息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
