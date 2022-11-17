import axios from 'axios';

let basezxm = 'http://saleljw.5gzvip.91tunnel.com/library-system-admin/librarySystemAdmin';
export const loginByAdmin = params => {
    return axios.post(`${basezxm}/loginAdmin/login`, params);
};

////图书信息部分
//图书种类查找
export const findBookType = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBookType`, configs);
};
//作者国家查找
export const findAuthorCountry = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getAuthorCountry`, configs);
};
//图书语言获取
export const findBookLanguage = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBookLanguage`, configs);
};
//图书状态获取
export const findBookStatus = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBookStatus`, configs);
};
//图书查询获取
export const findBookList = (params,configs) => {
    return axios.post(`${basezxm}/bookByAdmin/queryAllBooks`,params,configs);
};
//图书添加
export const addBook = (params,configs) => {
    return axios.post(`${basezxm}/bookByAdmin/addBook`,params,configs);
};
//图书封面上传
export const uploadBookImg = (editImage) => {
    return axios.post(`${basezxm}/bookByAdmin/uploadCoverAddress`, editImage);
};
//图书编辑
export const editBook = (params,configs) => {
    return axios.post(`${basezxm}/bookByAdmin/editBookInformation`,params,configs);
};
//图书删除
export const deleteBook = (bookId,configs) => {
    return axios.delete(`${basezxm}/bookByAdmin/deleteBookById/`+bookId,configs);
};

//图书标签部分
//书签查询
export const findTagList = (params,configs) => {
    return axios.post(`${basezxm}/bookTagByAdmin/findAllBookTag`,params,configs);
};
//书签修改
export const editTag = (params,configs) => {
    return axios.post(`${basezxm}/bookTagByAdmin/editBookTag`,params,configs);
};
//远程搜索
export const selectBook = (bookName,configs) => {
    return axios.post(`${basezxm}/bookByAdmin/searchBookTitlesRemotely`,bookName,configs);
};
//书签添加
export const addTag = (params,configs) => {
    return axios.post(`${basezxm}/bookTagByAdmin/addBookTag`,params,configs);
};
//书签删除
export const deleteTag = (tagId,configs) => {
    return axios.delete(`${basezxm}/bookTagByAdmin/deleteTagById/`+tagId,configs);
};

//图书留言部分
export const findMessageList = (params,configs) => {
    return axios.post(`${basezxm}/commentsByAdmin/queryAllMessages`,params,configs);
};
//留言删除
export const deleteMessage = (params,configs) => {
    return axios.post(`${basezxm}/commentsByAdmin/deleteComments`,params,configs);
};

//图书预定部分
//获取图书预定状态
export const findScheduledStatus = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getPreOrderBookStatus`, configs);
};
//获取图书预定
export const findScheduledInfo = (params,configs) => {
    return axios.post(`${basezxm}/scheduledByAdmin/getAllPreOrderedBook`,params,configs);
};
//修改图书预定状态
export const scheduledStatusUpdate = (params,configs) => {
    return axios.post(`${basezxm}/scheduledByAdmin/modifyReservationStatus`,params,configs);
};

//借阅管理部分
//获取借阅状态
export const findBorrowStatus = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBorrowStatus`, configs);
};
//获取借阅信息
export const findBorrowInfo = (params,configs) => {
    return axios.post(`${basezxm}/borrowByAdmin/queryAllBorrowingInformation`,params,configs);
};
//归还确认
export const returnCheck = (borrowId,configs) => {
    return axios.get(`${basezxm}/borrowByAdmin/confirmReturnBooks/`+borrowId,configs);
};

//活动部分
//获取活动类别
export const findActivityType = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getEventCategory`, configs);
};
//获取活动状态
export const findActivityStatus = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getActivityStatus`, configs);
};
//获取所有活动
export const findActivityInfo = (params,configs) => {
    return axios.post(`${basezxm}/activityByAdmin/findAllActivity`,params,configs);
};
//新增活动
export const addActivity = (params,configs) => {
    return axios.post(`${basezxm}/activityByAdmin/addActivity`,params,configs);
};
//删除活动
export const deleteActivity = (activityId,configs) => {
    return axios.get(`${basezxm}/activityByAdmin/deleteActivity/`+activityId,configs);
};
//修改活动
export const updateActivity = (params,configs) => {
    return axios.post(`${basezxm}/activityByAdmin/modifyActivity`,params,configs);
};

//编码部分
//获取编码
export const findAllCode = (params,configs) => {
    return axios.post(`${basezxm}/constantByAdmin/findAllConstant`,params,configs);
};
//修改编码数值
export const updateCodeInfo = (params,configs) => {
    return axios.post(`${basezxm}/constantByAdmin/editConstant`,params,configs);
};

//报名信息
export const findApplicationInfo = (params,configs) => {
    return axios.post(`${basezxm}/applicationByAdmin/findAllApplication`,params,configs);
};
//报名成功
export const successApplication = (applicationId,configs) => {
    return axios.get(`${basezxm}/applicationByAdmin/signUpSuccessfully/`+applicationId,configs);
};
//取消报名
export const canelApplication = (applicationId,configs) => {
    return axios.get(`${basezxm}/applicationByAdmin/cancelRegistration/`+applicationId,configs);
};

//楼层开放信息
//获取楼层
export const findFloor = (page,configs) => {
    return axios.get(`${basezxm}/floorByAdmin/findAllFloor/`+page,configs);
};
//获取时间列表
export const findTimeList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/queryTimeList`, configs);
};
//获取楼层状态
export const findStatusList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/findFloorStatus`, configs);
};
//修改楼层信息
export const updatefloorInfo = (params,configs) => {
    return axios.post(`${basezxm}/floorByAdmin/modifyFloorInformation`,params,configs);
};

//座位预约部分
//座位预约查询
export const findSeatInfo = (params,configs) => {
    return axios.post(`${basezxm}/userAppointmentsByAdmin/findAllAppointment`,params,configs);
};
//撤销座位预约
export const canelSeatAppointment = (appointmentId,configs) => {
    return axios.get(`${basezxm}/userAppointmentsByAdmin/cancelAppointment/`+appointmentId,configs);
};

//权限部分
//权限获取
export const findAllRole = (configs) => {
    return axios.get(`${basezxm}/roleByAdmin/findAllRoleInfo`,configs);
};
//模块下拉列表
export const findAllModule = (configs) => {
    return axios.get(`${basezxm}/roleByAdmin/findAllModule`,configs);
};
//增加权限
export const addRoleInfo = (params,configs) => {
    return axios.post(`${basezxm}/roleByAdmin/addRole`,params,configs);
};
//修改权限
export const updateRoleInfo = (params,configs) => {
    return axios.post(`${basezxm}/roleByAdmin/editPermission`,params,configs);
};

//用户信息部分
export const findAllUser = (params,configs) => {
    return axios.post(`${basezxm}/userByAdmin/findAllUser`,params,configs);
};
//获取性别
export const findAllGender = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getGender`,configs);
};

//用户修改
export const updateUserInfo = (params,configs) => {
    return axios.post(`${basezxm}/dropdownListData/editUser`,params,configs);
};
//一键激活
export const activeUser = (userId,configs) => {
    return axios.get(`${basezxm}/dropdownListData/activateAccount/`+userId,configs);
};

//管理员信息部分
//管理员信息获取
export const findAllAdmin = (params,configs) => {
    return axios.post(`${basezxm}/managerByAdmin/findAllManager`,params,configs);
};
//权限下拉列表
export const findRoleList = (configs) => {
    return axios.get(`${basezxm}/roleByAdmin/getRoleInfoList`,configs);
};
//添加账号
export const addAdmin = (params,configs) => {
    return axios.post(`${basezxm}/managerByAdmin/addManager`,params,configs);
};
//修改信息
export const updateAdmin = (params,configs) => {
    return axios.post(`${basezxm}/managerByAdmin/editManagerInformation`,params,configs);
};
//管理员修改自己密码
export const updateAdminPassword = (params,configs) => {
    return axios.post(`${basezxm}/managerByAdmin/editPassword`,params,configs);
};