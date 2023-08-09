package cn.iocoder.yudao.module.bpm.dal.mysql.oa;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderPageReqVO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * 投标申请 Mapper
 *
 */
@Mapper
public interface BpmOATenderMapper extends BaseMapperX<BpmOATenderDO> {

    default PageResult<BpmOATenderDO> selectPage(Collection<Long> userIds, BpmOATenderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmOATenderDO>()
                .inIfPresent(BpmOATenderDO::getUserId, userIds)
                .eqIfPresent(BpmOATenderDO::getResult, reqVO.getResult())
                .betweenIfPresent(BpmOATenderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BpmOATenderDO::getId));
    }

}
