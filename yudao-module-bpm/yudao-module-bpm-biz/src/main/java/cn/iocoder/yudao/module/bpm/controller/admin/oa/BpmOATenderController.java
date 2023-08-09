package cn.iocoder.yudao.module.bpm.controller.admin.oa;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderPageReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderRespVO;
import cn.iocoder.yudao.module.bpm.convert.oa.BpmOATenderConvert;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOATenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * OA 投标申请 Controller
 *
 */
@Tag(name = "管理后台 - OA 投标申请")
@RestController
@RequestMapping("/bpm/oa/tender")
@Validated
public class BpmOATenderController {

    @Resource
    private BpmOATenderService tenderService;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('bpm:oa-tender:create')")
    @Operation(summary = "创建请求申请")
    public CommonResult<Long> createTender(@Valid @RequestBody BpmOATenderCreateReqVO createReqVO) {
        return success(tenderService.createTender(getLoginUserId(), createReqVO));
    }

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('bpm:oa-tender:query')")
    @Operation(summary = "获得投标申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<BpmOATenderRespVO> getTender(@RequestParam("id") Long id) {
        BpmOATenderDO tender = tenderService.getTender(id);
        return success(BpmOATenderConvert.INSTANCE.convert(tender));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('bpm:oa-tender:query')")
    @Operation(summary = "获得投标申请分页")
    public CommonResult<PageResult<BpmOATenderRespVO>> getTenderPage(@Valid BpmOATenderPageReqVO pageVO) {
        PageResult<BpmOATenderDO> pageResult = tenderService.getTenderPage(getLoginUserId(), pageVO);
        return success(BpmOATenderConvert.INSTANCE.convertPage(pageResult));
    }

}
