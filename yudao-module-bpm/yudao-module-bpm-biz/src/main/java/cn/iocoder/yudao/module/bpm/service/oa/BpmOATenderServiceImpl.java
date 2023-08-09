package cn.iocoder.yudao.module.bpm.service.oa;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderPageReqVO;
import cn.iocoder.yudao.module.bpm.convert.oa.BpmOATenderConvert;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;
import cn.iocoder.yudao.module.bpm.dal.mysql.oa.BpmOATenderMapper;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.OA_TENDER_NOT_EXISTS;

/**
 * OA 投标申请 Service 实现类
 *
 */
@Service
@Validated
public class BpmOATenderServiceImpl implements BpmOATenderService {

    /**
     * OA 投标对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "oa_tender";

    @Resource
    private BpmOATenderMapper tenderMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private DeptApi deptApi;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTender(Long userId, BpmOATenderCreateReqVO createReqVO) {
        // 插入 OA 投标单
        // TODO 检查项目名称是否已存在
        BpmOATenderDO tender = BpmOATenderConvert.INSTANCE.convert(createReqVO)
                .setUserId(userId)
                .setNickname(adminUserApi.getUser(userId).getNickname())
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        tenderMapper.insert(tender);

        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(tender.getId())));

        // 将工作流的编号，更新到 OA 投标单中
        tenderMapper.updateById(new BpmOATenderDO().setId(tender.getId()).setProcessInstanceId(processInstanceId));
        return tender.getId();
    }

    @Override
    public void updateTenderResult(Long id, Integer result) {
        validateTenderExists(id);
        tenderMapper.updateById(new BpmOATenderDO().setId(id).setResult(result));
    }

    private void validateTenderExists(Long id) {
        if (tenderMapper.selectById(id) == null) {
            throw exception(OA_TENDER_NOT_EXISTS);
        }
    }

    @Override
    public BpmOATenderDO getTender(Long id) {
        return tenderMapper.selectById(id);
    }

    @Override
    public PageResult<BpmOATenderDO> getTenderPage(Long userId, BpmOATenderPageReqVO pageReqVO) {
        List<Long> userIds = Collections.singletonList(userId);
        AdminUserRespDTO user = adminUserApi.getUser(userId);
        DeptRespDTO dept = deptApi.getDept(user.getDeptId());
        // 是部门管理者
        if (Objects.equals(dept.getLeaderUserId(), userId)) {
            Set<Long> deptIdSet = deptApi.getChildDeptIdSet(dept.getId());
            deptIdSet.add(dept.getId());
            userIds = adminUserApi.getUserListByDeptIds(deptIdSet)
                    .stream()
                    .map(AdminUserRespDTO::getId)
                    .collect(Collectors.toList());
        }
        return tenderMapper.selectPage(userIds, pageReqVO);
    }
}
