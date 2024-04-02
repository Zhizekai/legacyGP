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
import org.dromara.web.domain.bo.LegacyUserBo;
import org.dromara.web.domain.vo.LegacyUserVo;
import org.dromara.web.service.ILegacyUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 用户
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/users")
@Tag(name = "前台用户模块")
public class LegacyUserFrontController extends BaseController {

    private final ILegacyUserService legacyUserService;

    /**
     * 查询用户列表
     */

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
    @GetMapping("/info/{id}")
    @Operation(summary = "获取前台用户详情信息")
    public R<LegacyUserVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {

        // TODO 根据token获取用户id
        return R.ok(legacyUserService.queryById(id));
    }

    /**
     * 新增用户
     */

    @RepeatSubmit()
    @PostMapping("/create")
    @Operation(summary = "用户注册")
    public R<Void> add(@RequestBody LegacyUserBo bo) {
        return toAjax(legacyUserService.insertByBo(bo));
    }


    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public R<LegacyUserVo> frontLogin(@RequestBody LegacyUserBo bo) {

        LegacyUserBo legacyUserBo = new LegacyUserBo();
        legacyUserBo.setPhone(bo.getPhone());

        List<LegacyUserVo> legacyUserVos = legacyUserService.queryList(legacyUserBo);
        if (legacyUserVos.size() != 0) {
            String password = legacyUserVos.get(0).getPassword();
            if (Objects.equals(password, bo.getPassword())) {
                LegacyUserVo legacyUserVo = legacyUserVos.get(0);
                //  使用sa-token生成token
                StpUtil.login(legacyUserVo.getId());
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                legacyUserVo.setToken(tokenInfo.getTokenValue());

                // 添加关注数和粉丝数
                LegacyUserVo vo = legacyUserService.queryUserFollowAndFans(legacyUserVo);

                return R.ok(vo);
            } else {
                return R.fail(20001,"请检查用户名和密码");
            }
        } else {
            return R.fail(20002, "请先注册");
        }


    }

    /**
     * 修改用户信息
     */
    @RepeatSubmit()
    @PutMapping("/update/{id}")
    public R<Void> edit(@PathVariable Long id, @RequestBody LegacyUserBo bo) {

        bo.setId(id);
        return toAjax(legacyUserService.updateByBo(bo));
    }

    /**
     * 注销用户
     *
     * @param ids 主键串
     */
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyUserService.deleteWithValidByIds(List.of(ids), true));
    }
}
