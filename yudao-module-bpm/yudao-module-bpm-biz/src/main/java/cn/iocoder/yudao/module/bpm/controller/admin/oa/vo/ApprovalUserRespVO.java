package cn.iocoder.yudao.module.bpm.controller.admin.oa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 项目报备审批人 Response VO")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class ApprovalUserRespVO {

    @Schema(description = "一级代理名字")
    private String firstName;

    @Schema(description = "区域厂家负责人审批名字")
    private String secondName;

}
