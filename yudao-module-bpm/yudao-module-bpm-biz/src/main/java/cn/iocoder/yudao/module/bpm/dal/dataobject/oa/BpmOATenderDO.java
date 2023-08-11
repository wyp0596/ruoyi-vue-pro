package cn.iocoder.yudao.module.bpm.dal.dataobject.oa;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * OA 项目报备申请 DO
 *
 *
 * @author jason
 * @author 芋道源码
 */
@TableName("bpm_oa_tender")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmOATenderDO extends BaseDO {

    /**
     * 项目报备申请表单主键
     */
    @TableId
    private Long id;
    /**
     * 申请人的用户编号
     *
     * 关联 AdminUserDO 的 id 属性
     */
    private Long userId;
    /**
     * 申请人的用户昵称
     * 关联 AdminUserDO 的 nickname 属性
     */
    private String nickname;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 预采购产品
     */
    private String projectProduct;
    /**
     * 金额（万）
     */
    private String projectMoney;
    /**
     * 招标日期
     */
    private LocalDateTime projectDate;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 手机号
     */
    private String contactPhone;
    /**
     * 职务
     */
    private String contactJob;
    /**
     * 审批的结果
     *
     * 枚举 {@link BpmProcessInstanceResultEnum}
     * 考虑到简单，所以直接复用了 BpmProcessInstanceResultEnum 枚举，也可以自己定义一个枚举哈
     */
    private Integer result;

    /**
     * 对应的流程编号
     *
     * 关联 ProcessInstance 的 id 属性
     */
    private String processInstanceId;

}
