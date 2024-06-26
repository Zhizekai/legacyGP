package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
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
import org.dromara.web.domain.bo.LegacyCommentBo;
import org.dromara.web.domain.bo.LegacyFollowBo;
import org.dromara.web.domain.bo.LegacyMessageBo;
import org.dromara.web.domain.bo.LegacyPraiseBo;
import org.dromara.web.domain.vo.LegacyCommentVo;
import org.dromara.web.domain.vo.LegacyFollowVo;
import org.dromara.web.domain.vo.LegacyMessageVo;
import org.dromara.web.domain.vo.LegacyPraiseVo;
import org.dromara.web.service.ILegacyCommentService;
import org.dromara.web.service.ILegacyFollowService;
import org.dromara.web.service.ILegacyMessageService;
import org.dromara.web.service.ILegacyPraiseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通知消息模块，包括点赞，收藏，新增粉丝等各种消息。也就是站内消息
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/messages")
@Tag(name = "前台用户站内消息模块")
public class LegacyMessageFrontController extends BaseController {

    private final ILegacyMessageService legacyMessageService;

    private final ILegacyCommentService legacyCommentService;
    private final ILegacyPraiseService legacyPraiseService;
    private final ILegacyFollowService legacyFollowService;

    // 查询消息列表
    @GetMapping("/list")
    @Operation(summary = "获取前台站内消息列表")
    public TableDataInfo<LegacyMessageVo> list(LegacyMessageBo bo, PageQuery pageQuery) {
        return legacyMessageService.queryPageList(bo, pageQuery);
    }
    @GetMapping("/preview")
    @Operation(summary = "统计前台用户的消息数量")
    public R<Map<String, Long>> countMessage(Long userId) {
        Long userIdByToken = StpUtil.getLoginIdAsLong();
        return R.ok(legacyMessageService.countMessage(userIdByToken));
    }

    // 获取消息详细信息
    @GetMapping("/{id}")
    @Operation(summary = "根据id获取前台站内消息列表")
    public R<LegacyMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyMessageService.queryById(id));
    }

    // 新增消息
    @RepeatSubmit()
    @PostMapping("/create")
    @Operation(summary = "创建前台站内消息")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LegacyMessageBo bo) {
        return toAjax(legacyMessageService.insertByBo(bo));
    }

    // 修改消息
    @RepeatSubmit()
    @PostMapping("/editMessage")
    @Operation(summary = "修改前台站内消息")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyMessageBo bo) {
        return toAjax(legacyMessageService.updateByBo(bo));
    }

    // 删除消息
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除前台站内消息")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyMessageService.deleteWithValidByIds(List.of(ids), true));
    }


    // 阅读评论消息
    @PostMapping("/readCommentMessage")
    @Operation(summary = "阅读评论消息")
    public R<LegacyCommentVo> readCommentMessage(@RequestBody LegacyCommentBo bo){
        Boolean aBoolean = legacyCommentService.updateByBo(bo);
        if (aBoolean) {
            return R.ok(legacyCommentService.queryById(bo.getId()));
        }else {
            return R.fail("评论消息已读失败");
        }
    }

    // 阅读点赞消息
    @PostMapping("/readPraiseMessage")
    @Operation(summary = "阅读点赞消息")
    public R<LegacyPraiseVo> readPraiseMessage(@RequestBody LegacyPraiseBo bo){
        Boolean aBoolean = legacyPraiseService.updateByBo(bo);
        if (aBoolean) {
            return R.ok(legacyPraiseService.queryById(bo.getId()));
        }else {
            return R.fail("点赞消息已读失败");
        }
    }

    // 阅读关注消息
    @PostMapping("/readFollowMessage")
    @Operation(summary = "阅读关注消息")
    public R<LegacyFollowVo> readFollowMessage(@RequestBody LegacyFollowBo bo){
        Boolean aBoolean = legacyFollowService.updateByBo(bo);
        if (aBoolean) {
            return R.ok(legacyFollowService.queryById(bo.getId()));
        }else {
            return R.fail("关注消息已读失败");
        }
    }
}
