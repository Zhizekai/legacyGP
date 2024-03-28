package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.web.domain.bo.LegacyUserBo;
import org.dromara.web.domain.vo.LegacyUserVo;
import org.dromara.web.service.ILegacyUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 *
 * @author Lion Li
 * @date 2024-03-28
 */
//@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/user")
@Tag(name = "前台用户模块")
public class LegacyUserFrontController extends BaseController {

    private final ILegacyUserService legacyUserService;

    /**
     * 查询用户列表
     */
    @SaIgnore
    @GetMapping("/list")
    @Operation(summary = "获取前台用户列表")
    public TableDataInfo<LegacyUserVo> list(LegacyUserBo bo, PageQuery pageQuery) {
        return legacyUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取用户详细信息
     *
     * @param id 主键
     */
    @SaIgnore
    @GetMapping("/{id}")
    @Operation(summary = "获取前台用户详情信息")
    public R<LegacyUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyUserService.queryById(id));
    }

    /**
     * 新增用户
     */
    @SaIgnore
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
     * 注销用户
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
