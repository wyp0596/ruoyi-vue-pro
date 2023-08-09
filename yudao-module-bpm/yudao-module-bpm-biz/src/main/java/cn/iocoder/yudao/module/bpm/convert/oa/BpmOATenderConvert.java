package cn.iocoder.yudao.module.bpm.convert.oa;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.oa.vo.BpmOATenderRespVO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOATenderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 投标申请 Convert
 *
 */
@Mapper
public interface BpmOATenderConvert {

    BpmOATenderConvert INSTANCE = Mappers.getMapper(BpmOATenderConvert.class);

    BpmOATenderDO convert(BpmOATenderCreateReqVO bean);

    BpmOATenderRespVO convert(BpmOATenderDO bean);

    List<BpmOATenderRespVO> convertList(List<BpmOATenderDO> list);

    PageResult<BpmOATenderRespVO> convertPage(PageResult<BpmOATenderDO> page);

}
