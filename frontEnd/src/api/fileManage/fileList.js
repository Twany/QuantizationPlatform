import request from '@/utils/request'

// 查询所有文件
export function list(parentId) {
  return request({
    url: '/fileManage/fileList/list/' + parentId,
    method: 'get'
  })
}



