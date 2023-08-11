package cn.iocoder.yudao.module.bpm.service.oa;


import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.ApprovalUserRespVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderPageReqVO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;

import javax.validation.Valid;

/**
 * 项目报备申请 Service 接口
 *
 */
public interface BpmOATenderService {

    /**
     * 创建项目报备申请
     *
     * @param userId 用户编号
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTender(Long userId, @Valid BpmOATenderCreateReqVO createReqVO);

    /**
     * 更新项目报备申请的状态
     *
     * @param id 编号
     * @param result 结果
     */
    void updateTenderResult(Long id, Integer result);

    /**
     * 获得项目报备申请
     *
     * @param id 编号
     * @return 项目报备申请
     */
    BpmOATenderDO getTender(Long id);

    /**
     * 获得项目报备申请分页
     *
     * @param userId 用户编号
     * @param pageReqVO 分页查询
     * @return 项目报备申请分页
     */
    PageResult<BpmOATenderDO> getTenderPage(Long userId, BpmOATenderPageReqVO pageReqVO);

    /**
     * 获得审批人信息
     *
     * @param userId 发起人用户id
     * @return 审批人信息
     */
    ApprovalUserRespVO getApprovalUser(Long userId);
}
