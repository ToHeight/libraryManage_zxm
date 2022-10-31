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