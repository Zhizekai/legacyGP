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
import org.dromara.web.domain.vo.LegacyMessageVo;
import org.dromara.web.domain.bo.LegacyMessageBo;
import org.dromara.web.service.ILegacyMessageService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 消息
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/message")
public class LegacyMessageController extends BaseController {

    private final ILegacyMessageService legacyMessageService;

    /**
     * 查询消息列表
     */
    @SaCheckPermission("web:message:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyMessageVo> list(LegacyMessageBo bo, PageQuery pageQuery) {
        return legacyMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出消息列表
     */
    @SaCheckPermission("web:message:export")
    @Log(title = "消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyMessageBo bo, HttpServletResponse response) {
        List<LegacyMessageVo> list = legacyMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "消息", LegacyMessageVo.class, response);
    }

    /**
     * 获取消息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:message:query")
    @GetMapping("/{id}")
    public R<LegacyMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyMessageService.queryById(id));
    }

    /**
     * 新增消息
     */
    @SaCheckPermission("web:message:add")
    @Log(title = "消息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyMessageBo bo) {
        return toAjax(legacyMessageService.insertByBo(bo));
    }

    /**
     * 修改消息
     */
    @SaCheckPermission("web:message:edit")
    @Log(title = "消息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyMessageBo bo) {
        return toAjax(legacyMessageService.updateByBo(bo));
    }

    /**
     * 删除消息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:message:remove")
    @Log(title = "消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyMessageService.deleteWithValidByIds(List.of(ids), true));
    }
}
