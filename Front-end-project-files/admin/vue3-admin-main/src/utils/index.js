export function localGet (key) {
  const value = window.localStorage.getItem(key)
  try {
    return JSON.parse(window.localStorage.getItem(key))
  } catch (error) {
    return value
  }
}

export function localSet (key, value) {
  window.localStorage.setItem(key, JSON.stringify(value))
}

export function localRemove (key) {
  window.localStorage.removeItem(key)
}

// 判断内容是否含有表情字符，现有数据库不支持。
export function hasEmoji (str = '') {
  const reg = /[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f\u2026\u2022\u20ac\r\n]/g;
  return str.match(reg) && str.match(reg).length
}

// 单张图片上传
export const uploadImgServer = 'http://backend-api-02.newbee.ltd/manage-api/v1/upload/file'
// 多张图片上传
export const uploadImgsServer = 'http://backend-api-02.newbee.ltd/manage-api/v1/upload/files'

export const pathMap = {
  login: '登录',
  introduce: '系统介绍',
  dashboard: '大盘数据',
  userManage: '用户信息管理',
  bookManage: '图书信息管理',
  bookTagManage: '图书书签管理',
  bookMessageManage: '图书留言管理',
  bookScheduledManage: '图书预定管理',
  bookBorrowManage: '图书借阅管理',
  activityManage: '活动信息管理',
  codeManage: '编码管理',
  applicationManage: '报名管理',
  floorManage: '楼层管理',
  appointmentManage: '预约管理'
}