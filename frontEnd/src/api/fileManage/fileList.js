import request from '@/utils/request'

// 查询所有文件通过parent_id和数据源种类id
export function list(parentId, fileTypeId) {
  return request({
    url: '/fileManage/fileList/' + parentId + '/' + fileTypeId,
    method: 'get'
  })
}

// 上传文件
export function addFile(data) {
  return request({
    url: '/fileInfo/addFile',
    method: 'post',
    data: data
  })
}

// 保存详细信息，插入到 platform_file_detail
export function addPlatformFileDetail(data) {
  return request({
    url: '/fileManage/fileList',
    method: 'post',
    data: data
  })
}