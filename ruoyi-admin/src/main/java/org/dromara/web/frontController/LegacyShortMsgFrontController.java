package org.dromara.web.frontController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.vo.SysOssUploadVo;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.service.ISysOssService;
import org.dromara.web.domain.bo.LegacyShortmsgBo;
import org.dromara.web.domain.vo.LegacyShortmsgVo;
import org.dromara.web.service.ILegacyShortmsgService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 短消息，类似知乎的想法
 *
 * @author Lion Li
 * @date 2024-03-28
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/web/shortMsg")
@Tag(name = "前台短消息沸点模块")
public class LegacyShortMsgFrontController extends BaseController {

    private final ILegacyShortmsgService legacyShortMsgService;
    private final ISysOssService ossService;

    // 查询短消息列表



    @GetMapping("/lists")
    public TableDataInfo<LegacyShortmsgVo> list(LegacyShortmsgBo bo, PageQuery pageQuery) {

//        long loginIdAsLong = StpUtil.getLoginIdAsLong();
//        bo.setCreatedBy(loginIdAsLong);

        TableDataInfo<LegacyShortmsgVo> legacyShortmsgVoTableDataInfo = legacyShortMsgService.queryPageList(bo, pageQuery);
        for ( LegacyShortmsgVo vo : legacyShortmsgVoTableDataInfo.getRows()) {
            String images = vo.getImages();
            if (images != null && !images.equals("")) {
                vo.setImgList( Arrays.asList(images.split(",")));
            }
        }
        return legacyShortmsgVoTableDataInfo;
    }


    @GetMapping("/myLists")
    public TableDataInfo<LegacyShortmsgVo> getMyShortMessageList(LegacyShortmsgBo bo, PageQuery pageQuery) {

        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        bo.setCreatedBy(loginIdAsLong);

        TableDataInfo<LegacyShortmsgVo> legacyShortmsgVoTableDataInfo = legacyShortMsgService.queryPageList(bo, pageQuery);
        for ( LegacyShortmsgVo vo : legacyShortmsgVoTableDataInfo.getRows()) {
            String images = vo.getImages();
            if (images != null && !images.equals("")) {
                vo.setImgList( Arrays.asList(images.split(",")));
            }
        }
        return legacyShortmsgVoTableDataInfo;
    }

    // 获取短消息详细信息
    @GetMapping("/{id}")
    public R<LegacyShortmsgVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(legacyShortMsgService.queryById(id));
    }

    // 新增短消息
//    @Log(title = "短消息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/create")
    public R<Void> add(@RequestBody LegacyShortmsgBo bo) {

        long loginIdAsLong = StpUtil.getLoginIdAsLong();
        bo.setCreatedBy(loginIdAsLong);

        // 把图片数组变成字符串
        if (bo.getImageBoList() != null) {
            String a1 = Joiner.on(",").join(bo.getImageBoList());
            bo.setImages(a1);
        }


        // 这里建议前端直接把userId 放到createdBy里面
        return toAjax(legacyShortMsgService.insertByBo(bo));
    }

    // 上传图片到oss接口
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysOssUploadVo> upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return R.fail("上传文件不能为空");
        }
        SysOssVo oss = ossService.upload(file);
        SysOssUploadVo uploadVo = new SysOssUploadVo();
        uploadVo.setUrl(oss.getUrl());
        uploadVo.setFileName(oss.getOriginalName());
        uploadVo.setOssId(oss.getOssId().toString());
        return R.ok(uploadVo);
    }

    // 根据id获取图片接口
    @GetMapping("/listByIds/{ossIds}")
    public R<List<SysOssVo>> listByIds(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ossIds) {
        List<SysOssVo> list = ossService.listByIds(Arrays.asList(ossIds));
        return R.ok(list);
    }

    // 修改短消息
//    @Log(title = "短消息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LegacyShortmsgBo bo) {
        return toAjax(legacyShortMsgService.updateByBo(bo));
    }

    // 删除短消息
//    @Log(title = "短消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(legacyShortMsgService.deleteWithValidByIds(List.of(ids), true));
    }
}
