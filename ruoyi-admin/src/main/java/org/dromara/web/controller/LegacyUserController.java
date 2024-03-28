package org.dromara.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.dromara.web.domain.vo.LegacyUserVo;
import org.dromara.web.domain.bo.LegacyUserBo;
import org.dromara.web.service.ILegacyUserService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/web/user")
@Tag(name = "后台用户模块")
public class LegacyUserController extends BaseController {

    private final ILegacyUserService legacyUserService;

    /**
     * 查询用户列表
     */
    @SaCheckPermission("web:user:list")
    @GetMapping("/list")
    public TableDataInfo<LegacyUserVo> list(LegacyUserBo bo, PageQuery pageQuery) {
        return legacyUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户列表
     */
    @SaCheckPermission("web:user:export")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LegacyUserBo bo, HttpServletResponse response) {
        List<LegacyUserVo> list = legacyUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户", LegacyUserVo.class, response);
    }

    /**
     * 获取用户详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("web:user:query")
    @GetMapping("/{id}")
    public R<LegacyUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyUserService.queryById(id));
    }

    /**
     * 新增用户
     */
    @SaCheckPermission("web:user:add")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyUserBo bo) {
        return toAjax(legacyUserService.insertByBo(bo));
    }

    /**
     * 修改用户
     */
    @SaCheckPermission("web:user:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyUserBo bo) {
        return toAjax(legacyUserService.updateByBo(bo));
    }

    /**
     * 删除用户
     *
     * @param ids 主键串
     */
    @SaCheckPermission("web:user:remove")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyUserService.deleteWithValidByIds(List.of(ids), true));
    }
}
