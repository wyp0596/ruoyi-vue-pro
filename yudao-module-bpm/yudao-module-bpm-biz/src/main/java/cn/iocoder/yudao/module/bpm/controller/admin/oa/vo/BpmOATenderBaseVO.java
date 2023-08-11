package cn.iocoder.yudao.module.bpm.controller.admin.oa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 项目报备申请 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class BpmOATenderBaseVO {

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "厦门市图书馆项目")
    @NotNull(message = "项目名称不能为空")
    private String projectName;

    @Schema(description = "预采购产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "防火墙")
    @NotNull(message = "预采购产品不能为空")
    private String projectProduct;

    @Schema(description = "金额（万）", requiredMode = Schema.RequiredMode.REQUIRED, example = "666.66")
    @NotNull(message = "金额不能为空")
    private String projectMoney;

    @Schema(description = "招标日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "招标日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime projectDate;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "厦门市政府")
    @NotNull(message = "客户名称不能为空")
    private String customerName;

    @Schema(description = "联系人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王大力")
    @NotNull(message = "联系人不能为空")
    private String contactName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18025433333")
    @NotNull(message = "手机号不能为空")
    private String contactPhone;

    @Schema(description = "职务", requiredMode = Schema.RequiredMode.REQUIRED, example = "小卡拉米")
    private String contactJob;
}
