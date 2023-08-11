package cn.iocoder.yudao.module.bpm.service.oa.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOATenderService;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOATenderServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 项目报备单的结果的监听器实现类
 *
 */
@Component
public class BpmOATenderResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private BpmOATenderService tenderService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOATenderServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        tenderService.updateTenderResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
