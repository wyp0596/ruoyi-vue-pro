package cn.iocoder.yudao.module.bpm.service.oa;


import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderPageReqVO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;

import javax.validation.Valid;

/**
 * 投标申请 Service 接口
 *
 */
public interface BpmOATenderService {

    /**
     * 创建投标申请
     *
     * @param userId 用户编号
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTender(Long userId, @Valid BpmOATenderCreateReqVO createReqVO);

    /**
     * 更新投标申请的状态
     *
     * @param id 编号
     * @param result 结果
     */
    void updateTenderResult(Long id, Integer result);

    /**
     * 获得投标申请
     *
     * @param id 编号
     * @return 投标申请
     */
    BpmOATenderDO getTender(Long id);

    /**
     * 获得投标申请分页
     *
     * @param userId 用户编号
     * @param pageReqVO 分页查询
     * @return 投标申请分页
     */
    PageResult<BpmOATenderDO> getTenderPage(Long userId, BpmOATenderPageReqVO pageReqVO);

}
