import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {bookingSet, determineReservationConditions, findSeat, getFloors, historyAppointment} from "@/api/zxmLibrary";
import {ElMessage} from "element-plus";
import {privateKey} from "@/api/keyJSEncrypt";

export default {
    data() {
        return {
            userInformationList: [],
            displayTimeapp: false,
            determineReservationDisplay: false,
            floorDisable: true,
            startTime: "8:00",
            endTime: "16:00",
            appTime: '',
            seatShow: false,
            userImage: undefined,
            userName: undefined,
            userId: '',
            floorId: '',
            areaId: '',
            SeatNumber: '',
            displaySeat: false,
            xmax: 10,
            ymax: 5,
            seatNumber: '',
            isSole: '',
            dataLists: [],
            sureChoose: false,
            seatArry: [],
            oldPageNumber: '',
            choose: {floor: '', appTime: ''},
            floors: [{
                value: undefined,
                coding: undefined,
                timeStart: undefined,
                timeEnd: undefined,
                timeRang: undefined,
            }],
            floor: undefined,
            configs: {
                headers: {
                    token: sessionStorage.getItem('AppointToken')
                }
            },
            areaList: [
                {
                    value: 1,
                    areaId: 'A'
                },
                {
                    value: 2,
                    areaId: 'B'
                },
                {
                    value: 3,
                    areaId: 'C'
                },
                {
                    value: 4,
                    areaId: 'D'
                },
            ],
            page:1,
            historyList:[{

            }],
            floorName:'',
            appointmentsStatus:'',
            appointmentTime:'',
            appointmentsCreateTime:'',
        }
    },
    methods: {
        choose1(data) {
            if (!this.displaySeat) {
                let datas = {
                    oldSeatNumber: this.oldPageNumber,
                    newSeatNumber: this.floorId + '-' + this.areaId + '-' + data.data,
                    userInformation: {},
                };
                this.oldPageNumber = this.floorId + '-' + this.areaId + '-' + data.data;
                this.stompClients.send(
                    "/app/reserveSeat/modifyDataFormByUser",
                    {
                        token: sessionStorage.getItem('AppointToken')
                    },
                    JSON.stringify(datas)
                );
            }
            if (data.isSole == 0) {
                data.isSole = 1;
                this.SeatNumber = data.data;
                this.dataLists.splice(data.data, 1, data)
                this.seatNumber = this.areaId + '-' + data.data;
                this.displaySeat = true;
            } else if (data.isSole == 1) {
                data.isSole = 0;
                this.SeatNumber = '';
                this.dataLists.splice(data.data, 1, data)
                this.displaySeat = false;
            }
        },
        seatStatus(message) {
            let seatInformation = JSON.parse(message.body);
            if (seatInformation.length != undefined) {
                //数组类型,进行操作
                //5-E-23
                if (this.userInformationList.length != 0) {
                    let a = 0;
                    let b = 0;
                    let c=this.userInformationList.length;
                    let d=seatInformation.length
                    // eslint-disable-next-line no-constant-condition
                    while (true) {
                        //a--this.userIn  b--seatInf
                        if((a>=c && b==d-1 )|| b>=d){
                            a=b=0;
                            break;
                        }
                        if(seatInformation[b].userInformation.id==this.userInformationList[a].id){
                            b++;
                            a=0;
                        }else{
                            a++;
                        }if(a==c){
                            //说明到头
                            this.userInformationList.push(seatInformation[b].userInformation)
                        }
                    }
                }else{
                    for (let i = 0; i < seatInformation.length; i++) {
                        this.userInformationList.push(seatInformation[i].userInformation);
                    }
                }
                for (let i = 0; i < seatInformation.length; i++) {
                    if (seatInformation[i].userInformation.id == this.userId) {
                        i++;
                        if (i == seatInformation.length) {
                            break;
                        }
                    }
                    let oldSeatNumber = seatInformation[i].oldSeatNumber;
                    let newSeatNumber = seatInformation[i].newSeatNumber;
                    if (oldSeatNumber != null && oldSeatNumber != '') {
                        let strings = oldSeatNumber.split('-');
                        if (strings[0] == this.floorId) {
                            if (strings[1] == this.areaId) {
                                let dataNumberOld = strings[2];
                                let dataList = this.dataLists[dataNumberOld];
                                dataList.isSole = 0;
                                this.dataLists.splice(parseInt(dataNumberOld), 1, dataList);
                            }
                        }
                    }
                    if (newSeatNumber != null && newSeatNumber != '') {
                        let strings = newSeatNumber.split('-');
                        if (strings[0] == this.floorId) {
                            if (strings[1] == this.areaId) {
                                let dataNumberNew = strings[2];
                                let dataList = this.dataLists[dataNumberNew];
                                dataList.userInformation = seatInformation[i].userInformation;
                                dataList.isSole = 4;
                                this.dataLists.splice(parseInt(dataNumberNew), 1, dataList);
                            }
                        }
                    }
                }
            } else {
                if (seatInformation.appointmentSuccessful) {
                    //当前为预约完成的座位发送
                    let strings = seatInformation.newSeatNumber.split("-");
                    if (strings[0] == this.floorId) {
                        if (strings[1] == this.areaId) {
                            //当前占用的座位,将数据进行修改
                            let dataList = this.dataLists[parseInt(strings[2])];
                            dataList.isSole = 2;
                            this.dataLists.splice(parseInt(strings[2]), 1, dataList);
                        }
                    }
                    return;
                }
                let userInformationSame = false;
                for (let i = 0; i < this.userInformationList.length; i++) {
                    if (this.userInformationList[i].id == seatInformation.userInformation.id) {
                        userInformationSame = true;
                        i++;
                        if (i == this.userInformationList.length) {
                            break;
                        }
                    }
                }
                if (!userInformationSame) {
                    this.userInformationList.push(seatInformation.userInformation)
                }
                if (seatInformation.userInformation.id == this.userId) {
                    return;
                }
                //对象类型操作
                let oldSeatNumber = seatInformation.oldSeatNumber;
                let newSeatNumber = seatInformation.newSeatNumber;
                if (oldSeatNumber != null && oldSeatNumber != '') {
                    let strings = oldSeatNumber.split('-');
                    if (strings[0] == this.floorId) {
                        if (strings[1] == this.areaId) {
                            let dataNumberOld = strings[2];
                            let dataList = this.dataLists[dataNumberOld];
                            dataList.isSole = 0;
                            this.dataLists.splice(parseInt(dataNumberOld), 1, dataList)
                        }
                    }
                }
                if (newSeatNumber != null && newSeatNumber != '') {
                    let strings = newSeatNumber.split('-');
                    if (strings[0] == this.floorId) {
                        if (strings[1] == this.areaId) {
                            let dataNumberNew = strings[2];
                            let dataList = this.dataLists[dataNumberNew];
                            dataList.userInformation = seatInformation.userInformation;
                            dataList.isSole = 4;
                            this.dataLists.splice(parseInt(dataNumberNew), 1, dataList)
                        }
                    }
                }
            }
        },
        seatInitialization() {
            //所在的位置0、0（必须的）
            let xTest = 0;
            let yTest = 0;
            let dataList = [];
            for (let i = 0; i < 10000; i++) {
                const dataTemple = {
                    data: i,
                    isSole: "0",
                    isSure: false,
                    userInformation: {},
                    x: xTest,
                    y: yTest
                }
                dataList.push(dataTemple)
                if ((dataTemple.x === this.xmax) && (dataTemple.y === this.ymax)) {
                    this.dataLists = dataList
                    return
                }
                xTest++;
                if (dataTemple.x === (this.xmax)) {
                    yTest++;
                    xTest = 0;
                }
            }
        },
        webSocketInitialization() {
            let that = this;
            const socket = new SockJS(
                "http://saleljw.free.idcfengye.com/library-system-reader/librarySystemReader/librarySystemReader-websocket"
            );
            var stompClient = Stomp.over(socket);
            this.stompClients = stompClient; //用stom进行包装，规范协议
            stompClient.connect(
                {
                    token:
                        sessionStorage.getItem('AppointToken')
                },
                //测试9
                function () {
                    //订阅在线消息
                    stompClient.subscribe(
                        "/reserve/modifyForm",
                        function (message) {
                            console.log(message.body)
                            that.seatStatus(message);
                        },
                        {
                            token: sessionStorage.getItem('AppointToken')
                        }
                    );
                    //发送初始化界面消息
                    stompClient.send(
                        "/app/reserveSeat/modifyDataFormByUserInitialization",
                        {
                            token: sessionStorage.getItem('AppointToken')
                        }
                    );
                }
            );
        },
        //获取楼层下拉
        floorList() {
            getFloors(this.configs).then((res) => {
                if (res.data.statusCode === 'C200') {
                    let str = ''
                    for (let i = 0; i < res.data.result.length; i++) {
                        str = res.data.result[i].timeStart + '~' + res.data.result[i].timeEnd;
                        res.data.result[i].timeRang = str
                    }
                    this.floors = res.data.result;
                } else {
                    ElMessage.error(res.data.message);
                }
            })
        },
        //座位初始化
        getSeat() {
            let para = {
                floorId: this.choose.floor,
                appointmentTime: new Date(new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() + " " + this.choose.appTime)
            }
            findSeat(para, this.configs).then((res) => {
                if (res.data.statusCode === 'C200') {
                    this.displayTimeapp = true;
                    let datas = res.data.result
                    this.floorId = this.choose.floor;
                    this.areaId = this.choose.areaId;
                    this.webSocketInitialization();
                    this.seatInitialization();
                    this.changeSeatName(datas);
                } else {
                    ElMessage.error(res.data.message);
                }
            })
        },
        changeSeatName(datas) {
            for (let i = 0; i < datas.length; i++) {
                let strings = datas[i].seatName.split("-");
                if (strings[0] == this.areaId) {
                    //当前占用的座位
                    let dataList = this.dataLists[parseInt(strings[1])]
                    dataList.isSole = 2
                    this.dataLists.splice(parseInt(strings[1]), 1, dataList)
                }
            }
            //延迟加载
            this.seatShow = true;
        },
        //座位预约
        seatBooking() {
            let para = {
                floorId: this.choose.floor,
                reserveTime: new Date(new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + "-" + new Date().getDate() + " " + this.choose.appTime),
                seatNumber: this.seatNumber
            }
            bookingSet(para, this.configs).then((res) => {
                if (res.data.statusCode === 'C200') {
                    this.seatArry = res.data.result;
                    this.determineReservationDisplay=false;
                } else {
                    ElMessage.error(res.data.message);
                }
            })
        },
        determineReservationCondition() {
            determineReservationConditions(this.configs).then((res) => {
                if (res.data.statusCode == 'C404') {
                    ElMessage.error(res.data.message);
                } else if (res.data.statusCode == 'C200') {
                    this.determineReservationDisplay = true;
                    ElMessage.success(res.data.message);
                }
            })
        },
        //预约历史记录
        apponitmentHistory(){
            alert(this.page)
            historyAppointment(this.page,this.configs).then((res) => {
                if (res.data.statusCode == 'C404') {
                    ElMessage.error(res.data.message);
                } else if (res.data.statusCode == 'C200') {
                    this.historyList = res.data.result.list;
                    ElMessage.success(res.data.message);
                }
            })
        }

    },
    mounted() {
        //对个人信息解密
        this.userId=JSON.parse(privateKey(localStorage.getItem("userInformation"))).id;
        this.determineReservationCondition();
        this.floorList();
        this.apponitmentHistory();
    },
}
