import axios from 'axios';

let basezxm = 'http://saleljw.free.idcfengye.com/library-system-reader/librarySystemReader';
export const loginByReader = params => {
    return axios.post(`${basezxm}/readerLogin/login`, params);
};
/*做个验证把，如果这个页面会话域中存在数据则放入数据并发送请求*/
// alert(sessionStorage.getItem('AppointToken'))
export const findBook = (params, configs) => {
    return axios.post(`${basezxm}/bookByReader/findBookByCriteria`, params, configs);
};
export const GetTypeList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBookType`, configs);
};
export const GetCountryList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getAuthorCountry`, configs);
};
export const GetLanguageList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBookLanguage`, configs);
};
export const addToShelf = (params, configs) => {
    return axios.get(`${basezxm}/bookByReader/addBookshelves/` + params, configs);
};
export const findBookShelf = (params, configs) => {
    return axios.post(`${basezxm}/bookShelfServletByReader/queryBookshelvesBooks`, params, configs);
};
export const deleteFromBookShelf = (params, configs) => {
    return axios.post(`${basezxm}/bookShelfServletByReader/deleteBookshelvesBooks`, params, configs);
};
export const bookBorrow = (params, configs) => {
    return axios.get(`${basezxm}/bookBorrowByReader/borrowBook/` + params, configs);
};
export const addBookTag = (bookId, value, configs) => {
    return axios.get(`${basezxm}/bookTapByReader/addBookmark/` + bookId + "?value=" + value, configs);
};
export const deleteBookTag = (bookId, configs) => {
    return axios.delete(`${basezxm}/bookTapByReader/deleteBookTag/` + bookId, configs);
};
export const leaveBookMessage = (params, configs) => {
    return axios.post(`${basezxm}/commentsByReader/bookMessage`, params, configs);
};
export const borrowStatusList = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getBorrowStatus`, configs);
};
export const findBorrow = (params, configs) => {
    return axios.post(`${basezxm}/bookBorrowByReader/findBorrowedBooks`, params, configs);
};
export const returnBook = (params, configs) => {
    return axios.get(`${basezxm}/bookBorrowByReader/returnBooks/` + params, configs);
};
export const deleteBorrow = (params, configs) => {
    return axios.delete(`${basezxm}/bookBorrowByReader/deleteBorrowingRecord/` + params, configs);
};

//座位预约
export const getFloors = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getFloor`, configs);
};
export const findSeat = (params, configs) => {
    return axios.post(`${basezxm}/reserveSeatByReader/initializeFixedSeat`, params, configs);
};
export const bookingSet = (params, configs) => {
    return axios.post(`${basezxm}/reserveSeatByReader/reserveSeat`, params, configs);
};
export const determineReservationConditions = (configs) => {
    return axios.get(`${basezxm}/reserveSeatByReader/determineReservationConditions`, configs);
};
export const historyAppointment = (page, configs) => {
    return axios.get(`${basezxm}/reserveSeatByReader/findAllAppointmentsByUser?page=` + page, configs);
}
export const cancelAppointment = (appointmentId,configs) => {
    return axios.get(`${basezxm}/reserveSeatByReader/cancelAppointment/`+appointmentId, configs);
};
//图书预定
export const bookScheduled = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getPreOrderBookStatus`,configs);
};
// axios.get('/user?ID='+12345)
export const findReservation = (bookName,bookType,configs) => {
    return axios.get(`${basezxm}/bookReservationByReader/findBookReservation?bookName=`+bookName+'&bookType='+bookType,configs);
};
export const addReservation = (bookName,configs) => {
    return axios.get(`${basezxm}/bookReservationByReader/bookReservationByBookName?bookName=`+bookName,configs);
};
export const updateBookReservation = (bookId,bookName,configs) => {
    return axios.get(`${basezxm}/bookReservationByReader/amendBookReservations/`+bookId+ "?bookName=" + bookName, configs);
};

//活动中心
export const findActivity = (params, configs) => {
    return axios.post(`${basezxm}/activityByReader/queryActivity`, params, configs);
};
export const activityRegistration = (activityId,configs) => {
    return axios.get(`${basezxm}/activityByReader/participateInRegistration/`+activityId, configs);
};
export const registrationCanel = (applicationId,configs) => {
    return axios.get(`${basezxm}/activityByReader/cancellationApplication/`+applicationId, configs);
};
export const getActivityType = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getEventCategory`, configs);
};
export const getActivityStatus = (configs) => {
    return axios.get(`${basezxm}/dropdownListData/getActivityStatus`, configs);
};


