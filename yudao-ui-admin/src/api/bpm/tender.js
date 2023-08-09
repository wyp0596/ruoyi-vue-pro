import request from '@/utils/request'

// 创建投标申请
export function createTender(data) {
  return request({
    url: '/bpm/oa/tender/create',
    method: 'post',
    data: data
  })
}

// 获得投标申请
export function getTender(id) {
  return request({
    url: '/bpm/oa/tender/get?id=' + id,
    method: 'get'
  })
}

// 获得投标申请分页
export function getTenderPage(query) {
  return request({
    url: '/bpm/oa/tender/page',
    method: 'get',
    params: query
  })
}
