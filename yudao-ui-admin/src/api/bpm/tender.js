import request from '@/utils/request'

// 创建项目报备申请
export function createTender(data) {
  return request({
    url: '/bpm/oa/tender/create',
    method: 'post',
    data: data
  })
}

// 获得项目报备申请
export function getTender(id) {
  return request({
    url: '/bpm/oa/tender/get?id=' + id,
    method: 'get'
  })
}

// 获得审批人信息
export function getApprovalUser(userId) {
  return request({
    url: '/bpm/oa/tender/approvalUser?userId=' + userId,
    method: 'get'
  })
}


// 获得项目报备申请分页
export function getTenderPage(query) {
  return request({
    url: '/bpm/oa/tender/page',
    method: 'get',
    params: query
  })
}
