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
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.service.ILegacyFollowService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 关注
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/follow")
public class LegacyFollowController extends BaseController {

    private final ILegacyFollowService legacyFollowService;

    /**
     * 查询关注列表
     */
    @SaCheckPermission("web:follow:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyFollowVo> list(LegacyFollowBo bo, PageQuery pageQuery) {
        return legacyFollowService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出关注列表
     */
    @SaCheckPermission("web:follow:export")
    @Log(title = "关注", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyFollowBo bo, HttpServletResponse response) {
        List<LegacyFollowVo> list = legacyFollowService.queryList(bo);
        ExcelUtil.exportExcel(list, "关注", LegacyFollowVo.class, response);
    }

    /**
     * 获取关注详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:follow:query")
    @GetMapping("/{id}")
    public R<LegacyFollowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyFollowService.queryById(id));
    }

    /**
     * 新增关注
     */
    @SaCheckPermission("web:follow:add")
    @Log(title = "关注", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyFollowBo bo) {
        return toAjax(legacyFollowService.insertByBo(bo));
    }

    /**
     * 修改关注
     */
    @SaCheckPermission("web:follow:edit")
    @Log(title = "关注", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyFollowBo bo) {
        return toAjax(legacyFollowService.updateByBo(bo));
    }

    /**
     * 删除关注
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:follow:remove")
    @Log(title = "关注", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyFollowService.deleteWithValidByIds(List.of(ids), true));
    }
}
