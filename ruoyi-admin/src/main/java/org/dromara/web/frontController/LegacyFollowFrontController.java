package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
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
import org.dromara.web.domain.LegacyFollow;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.service.ILegacyFollowService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关注
 *
 * @author Lion Li
 * @date 2024-03-30
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/follows")
@Tag(name = "前台用户关注模块")
public class LegacyFollowFrontController extends BaseController {

    private final ILegacyFollowService legacyFollowService;

    /**
     * 查询关注列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有关注列表")
    public TableDataInfo<LegacyFollowVo> list(LegacyFollowBo bo, PageQuery pageQuery) {
        return legacyFollowService.queryPageList(bo, pageQuery);
    }


    // 获取用户新增粉丝列表
    @GetMapping("/lists")
    @Operation(summary = "获取我的新增粉丝列表")
    public TableDataInfo<LegacyFollowVo> listMessage(PageQuery pageQuery) {
        LegacyFollowBo legacyFollowBo = new LegacyFollowBo();
        legacyFollowBo.setUserId(StpUtil.getLoginIdAsLong());
        return legacyFollowService.queryPageList(legacyFollowBo, pageQuery);
    }

    // 检查是否关注了某个用户
    @PostMapping("/is-follow")
    @Operation(summary = "检查是否关注了某个用户")
    public R<Boolean> checkFollow(@RequestBody LegacyFollow follow) {
        LegacyFollow legacyFollow = legacyFollowService.checkIsFollow(follow.getUserId());
        if (legacyFollow != null) {
            return R.ok(Boolean.TRUE);
        }else {
            return R.ok(Boolean.FALSE);
        }
    }


    // 获取关注详细信息
    @GetMapping("/{id}")
    @Operation(summary = "获取关注详细信息")
    public R<LegacyFollowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyFollowService.queryById(id));
    }

    // 关注和取消关注
    @RepeatSubmit()
    @PostMapping("/toggle")
    @Operation(summary = "用户关注和取消关注接口")
    public R<String> add(@RequestBody LegacyFollowBo bo) {

        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        bo.setFansId(loginIdAsLong);
        Boolean aBoolean = legacyFollowService.followToggle(bo);
        if (aBoolean) {
            LegacyFollow legacyFollow = legacyFollowService.checkIsFollow(bo.getUserId());
            if (legacyFollow != null) {
                return R.ok("关注成功");
            }else {
                return R.ok("取消关注成功");
            }
        }else {
            return R.fail("操作失败");
        }
    }

    /**
     * 修改关注
     */
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyFollowBo bo) {
        return toAjax(legacyFollowService.updateByBo(bo));
    }

    // 取消关注
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyFollowService.deleteWithValidByIds(List.of(ids), true));
    }
}
