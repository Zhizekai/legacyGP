package org.dromara.web.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.web.domain.vo.LegacyShortmsgVo;
import org.dromara.web.domain.bo.LegacyShortmsgBo;
import org.dromara.web.service.ILegacyShortmsgService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 短消息
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/shortmsg")
public class LegacyShortmsgController extends BaseController {

    private final ILegacyShortmsgService legacyShortmsgService;

    /**
     * 查询短消息列表
     */
    @SaCheckPermission("web:shortmsg:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyShortmsgVo> list(LegacyShortmsgBo bo, PageQuery pageQuery) {
        return legacyShortmsgService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出短消息列表
     */
    @SaCheckPermission("web:shortmsg:export")
    @Log(title = "短消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyShortmsgBo bo, HttpServletResponse response) {
        List<LegacyShortmsgVo> list = legacyShortmsgService.queryList(bo);
        ExcelUtil.exportExcel(list, "短消息", LegacyShortmsgVo.class, response);
    }

    /**
     * 获取短消息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:shortmsg:query")
    @GetMapping("/{id}")
    public R<LegacyShortmsgVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyShortmsgService.queryById(id));
    }

    /**
     * 新增短消息
     */
    @SaCheckPermission("web:shortmsg:add")
    @Log(title = "短消息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyShortmsgBo bo) {
        return toAjax(legacyShortmsgService.insertByBo(bo));
    }

    /**
     * 修改短消息
     */
    @SaCheckPermission("web:shortmsg:edit")
    @Log(title = "短消息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyShortmsgBo bo) {
        return toAjax(legacyShortmsgService.updateByBo(bo));
    }

    /**
     * 删除短消息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:shortmsg:remove")
    @Log(title = "短消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyShortmsgService.deleteWithValidByIds(List.of(ids), true));
    }
}
