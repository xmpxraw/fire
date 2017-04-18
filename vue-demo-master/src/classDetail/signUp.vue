<template>
    <div class="page-signup">
        <header-nav title="我要报名" :backshow="backshow"></header-nav>
        <div class="singupMain">
            <!-- <div class="li-item">
            <cell title="Notifications" value="Enabled"></cell>
        </div> -->
            <div class="li-item">
                <span class="li-item-name">姓名</span>
                <input class="li-item-val" v-model="studentName" type="text" placeholder="必填" maxlength='15'>
            </div>
            <div class="li-item">
                <span class="li-item-name">身份证号码</span>
                <input class="li-item-val" id="idCard" v-model="idCard" type="text" placeholder="必填" v-on:blur="checkidCard()">
            </div>
            <div class="li-item">
                <span class="li-item-name">手机号码</span>
                <input class="li-item-val" id="phoneNum" v-model="phoneNum" type="text" placeholder="必填" v-on:blur="checkPhone()">
            </div>
            <div class="li-item">
                性别
                <select v-model="sex" class="li-select">
                    <option value="男" selected="selected">男</option>
                    <option value="女">女</option>
                </select>
            </div>
            <div class="li-item">
                <address title="区域" :value.sync="value2" raw-value :list="addressData"></address>
            </div>
            <div class="li-item">
                单位
                <select v-model="unit" class="li-select">
                    <option v-for="i in companyData" value="{{$index}}">{{i.companyName}}</option>
                    <option value="暂无">暂无</option>
                </select>
            </div>
            <div class="li-item">
                <span class="li-item-name">单位地址</span>
                <input class="li-item-val" v-model="companyAddress" type="text" placeholder="选填">
            </div>
            <div class="li-item">
                培训专业
                <select v-model="major" class="li-select">
                    <option v-for="j in registeData.specialtys" value="{{j.value}}">{{j.value}}</option>
                </select>
            </div>
            <div class="li-item">
                <span class="li-item-name2">培训费</span>{{feesTrain}}元/人
            </div>
            <div class="li-item">
                <span class="li-item-name2">国考鉴定费</span>{{registeData.feesStudy}}元/人
                <input class="checkbox" type="checkbox" id="guokao" v-model="guokao">
                <label class="check" for="guokao"></label>
            </div>
            <div class="li-item">
                <span class="li-item-name2">餐费</span>{{registeData.feesMeal}}元/人
                <input class="checkbox" type="checkbox" id="food" v-model="food">
                <label class="check" for="food"></label>
            </div>
            <div class="li-item borderNone">
                <span class="li-item-name2">住宿费</span>
                <input class="checkbox" type="checkbox" id="liveFree" v-model="liveFree">
                <label class="check" for="liveFree"></label>
            </div>
            <div class="li-item li-item2" v-show="liveFree">
                宿舍:{{registeData.feesDormitory}}元/人
                <input class="checkbox" type="radio" name="live" id="live2" value="{{registeData.feesDormitory}}" v-model="live">
                <label class="check" for="live2"></label>
            </div>
            <div class="li-item li-item2" v-show="liveFree">
                酒店:{{registeData.feesHotel}}元/人
                <input class="checkbox" type="radio" name="live" id="live1" value="{{registeData.feesHotel}}" v-model="live">
                <label class="check" for="live1"></label>
            </div>
            <div class="li-item borderNone">
                <span class="li-item-name">费用总数</span>
                <input class="li-item-val" v-model="feesTotel" type="text" disabled="disabled">
            </div>
        </div>
        <div class="login-next">
            <span @click="submit()">提交</span>
        </div>
        <dialog :show.sync="showNoScroll" class="dialog-demo" :scroll="false">
            <p class="dialog-title">温馨提示</p>
            <div class="img-box">
                <div class="successNote" v-show="signSuccess">
                    <span class="note-item">{{studentName}}</span>您已成功报名，马上去登录吧！
                    <p>手机号码：{{phoneNum}}</p>
                    <p>身份证号码：{{idCard}}</p>
                </div>
                <span v-show="!signSuccess">报名未成功！{{errmsg}}</span>
            </div>
            <div class="j-operate">
                <span v-show="!signSuccess" class="sure-submit" @click="goMessege()">确定</span>
                <span v-show="signSuccess" class="sure-submit" @click="goMessege()">马上登录</span>
                <span v-show="signSuccess" class="sure-submit" @click="cancle()">关闭页面</span>
            </div>
        </dialog>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
import Address from 'vux-components/address'
import Cell from 'vux-components/cell'
import Group from 'vux-components/group'
import AddressChinaData from '../address/list.json'
import value2name from '../filters/value2name'
// import { Group, Address, Cell } from 'vux-components/'
export default {
    name: 'signUp',
    components: {
        Dialog,
        loading,
        toast,
        headerNav,
        Group,
        Address,
        Cell,
        AddressChinaData
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            registeData: [],
            feesTrain: 0,
            studentName: "", //学生名字
            idCard: "", //身份证
            phoneNum: "", //手机号码
            unit: "0", //单位
            major: "", //专业 
            companyAddress: "", //单位地址
            showNoScroll: false, //提示报名成功
            guokao: false, //国考费
            food: false, //餐费
            liveFree: false, //住宿费
            live: 0, //住宿费
            address: "", //地区
            backshow: false,
            show2: false,
            warntext: "",
            loading: false,
            value2: ['广东省', '深圳市', '南山区'], //地区
            addressData: AddressChinaData,
            signSuccess: true,
            feesTotel: 0, //总数
            oldVal: 0,
            newVal: 0,
            companyname: "",
            companycode: "",
            errmsg: "",
            companyData: [],
            idCardPass: true,
            sex: "",
        }
    },
    watch: {

        /*idCard(val) { //验证身份证
            // var reg=/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
            var reg = /^[0-9a-zA-Z]*$/g;
            if (reg.test(val)) {
                // console.log("ok");
                this.idCardPass = true;
            } else {
                this.show2 = true;
                this.warntext = "只能输入数字或字母";
                this.idCardPass = false;
            }
        },*/
        major(val) {
            this.changeFees(val);
        },
        feesTrain(newVal, oldVal) {
            this.total();
        },
        unit(val) {
            if (val == "暂无") {
                this.companyname = null;
                this.companycode = null;
            } else {
                this.companyname = this.registeData.companys[val].companyName;
                this.companycode = this.registeData.companys[val].id;
            }
        },
        value2(val) {
            this.address = value2name(val, AddressChinaData); //把值转为文字
            this.getCompany(this.address);
        },
        guokao(val) {
            this.total();
        },
        food(val) {
            this.total();
        },
        live(newVal, oldVal) {
            this.total();
        },
        liveFree(val) {
            this.total();
        }
    },
    methods: {
        total: function() {
            this.feesTotel = parseFloat(this.feesTrain);
            if (this.guokao) {
                this.feesTotel = this.feesTotel + parseFloat(this.registeData.feesStudy);
            }
            if (this.food) {
                this.feesTotel = this.feesTotel + parseFloat(this.registeData.feesMeal);
            }
            if (this.liveFree) {
                this.feesTotel = this.feesTotel + parseFloat(this.live);
            }
            this.feesTotel = this.feesTotel.toFixed(2);
        },
        changeFees: function(val) {
            this.loading = true;
            apiService.getFeesScheme({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                specialty: val,
                // JSESSIONID: "2FEA264F4222FC80E5F42BB4739AF5C5",
                // code: "yuantai"
            }).then((data) => {
                this.loading = false;
                var data = data.data.feesScheme;
                this.registeData.feesDormitory = data.feesDormitory;
                this.registeData.feesHotel = data.feesHotel;
                this.registeData.feesMeal = data.feesMeal;
                this.registeData.feesStudy = data.feesStudy;
                this.live = data.feesDormitory;
                // this.feesTrain = data.feesTrain;
                this.feesTrain = data.feesTrain;
                console.log(data, "---");
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        getCompany: function(val) {
            apiService.getCompany({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                address: val,
            }).then((data) => {
                this.loading = false;
                this.companyData = data.data.companys;
                this.companyname = this.companyData[0].companyName;
                this.companycode = this.companyData[0].id;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        cancle: function() {
            wx.closeWindow();
        },
        back: function() {
            window.history.back();
        },
        renderData: function() {
            apiService.getRegiste({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                // JSESSIONID: "2FEA264F4222FC80E5F42BB4739AF5C5",
                // code: "yuantai"
            }).then((data) => {
                this.loading = false;
                this.registeData = data.data;
                this.feesTotel = this.feesTotel + this.feesTrain;
                this.live = this.registeData.feesDormitory;
                this.oldVal = this.registeData.feesHotel;
                //下拉选项默认选中第一项
                // this.companyname = this.registeData.companys[0].companyName;
                // this.companycode = this.registeData.companys[0].id;
                this.major = this.registeData.specialtys[0].value;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })

        },
        checkPhone:function(){
            if (!(/^((13[0-9])|(15[0-9])|(18[0-9])|(17[1-9])|(147))\d{8}$/.test(this.phoneNum))) {
                this.show2 = true;
                this.warntext = "请输入正确的手机号码";
                document.getElementById('phoneNum').focus();
            }
        },
        checkidCard:function(){
             if (!(/^(\d{15}[0-9a-zA-Z])|(\d{17}[0-9a-zA-Z])/.test(this.idCard))) {
                this.show2 = true;
                this.warntext = "请输入正确的身份证号码";
                document.getElementById('idCard').focus();
            }
        },
        submit: function() {
            if (this.studentName == "" || this.idCard == "" || this.unit == "" || this.major == "" || this.phoneNum == "") {
                this.show2 = true;
                this.warntext = "您的资料没有填写完整";
                return;
            }
            // if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(this.phoneNum))) {
            //     this.show2 = true;
            //     this.warntext = "请输入正确的手机号码";
            //     document.getElementById('phoneNum').focus();
            //     return;
            // }
            // if (this.idCardPass == false) {
            //     this.show2 = true;
            //     this.warntext = "请填写正确的身份证号码";
            //     document.getElementById('idCard').focus();
            //     return;
            // }
            let selectHotel = 0;
            let selectDormitory = 0;
            let feesTotel = this.feesTrain;
            //判断住宿类型
            if (this.liveFree) {
                if (this.live == "") {
                    this.show2 = true;
                    this.warntext = "请选择您要的住宿种类";
                    return;
                }
                if (this.live == this.registeData.feesHotel) {
                    selectHotel = this.registeData.feesHotel;
                    selectDormitory = 0;
                    // feesTotel = feesTotel + this.registeData.feesHotel;
                } else if (this.live == this.registeData.feesDormitory) {
                    selectHotel = 0;
                    selectDormitory = this.registeData.feesDormitory;
                    // feesTotel = feesTotel + this.registeData.feesDormitory;
                }
            } else {
                selectHotel = 0;
                selectDormitory = 0;
            }

            let feesMeal = 0;
            let feesStudy = 0;
            if (this.food) {
                feesMeal = this.registeData.feesMeal;
            } else {
                feesMeal = 0;
            }
            if (this.guokao) {
                feesStudy = this.registeData.feesStudy;
            } else {
                feesStudy = 0;
            }
            this.loading = true;
            apiService.registe({
                studnetName: this.studentName,
                mobile: this.phoneNum,
                idcard: this.idCard,
                address: this.address,
                company: this.companyname,
                companyCode: this.companycode,
                companyAddress: this.companyAddress,
                specialty: this.major,
                feesStudy: feesStudy,
                feesMeal: feesMeal,
                feesHotel: selectHotel,
                feesDormitory: selectDormitory,
                feesTrain: this.feesTrain,
                sex: this.sex,
                code: window.localStorage.getItem("codeStatus"),
                feesTotel: this.feesTotel,
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.showNoScroll = true;
                    this.signSuccess = true;
                } else {
                    this.showNoScroll = true;
                    this.signSuccess = false;
                    this.errmsg = data.data.msg;
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })

        },
        goMessege: function() {
            if (this.signSuccess) {
                this.$router.go('/login');
            } else {
                this.showNoScroll = false;
            }
        },

        // kk:function(){
        // alert(value2name(this.value2, AddressChinaData));//把值转为文字
        // },
        popState: function() {
            // var state = { 
            // title: "title", 
            // url: "#"
            // }; 
            // window.history.pushState(state, "title", "#"); 
            window.addEventListener("popstate", function(e) {
                this.back();
            }, false);
        },
    },
}
</script>
<style lang="scss">
.page-signup {
    .weui_cell_bd p {
        position: absolute;
        top: 0;
    }
    .vux-popup-picker-value {
        font-size: .75rem;
    }
    .weui_select {
        direction: rtl;
    }
    input:disabled {
        background: #fff;
        padding-right: 1rem;
    }
    .borderTop {
        border-bottom: 0;
        border-top: 1px solid #ddd;
    }
    .singupMain {
        width: 100%;
        height: auto;
        background: #fff;
        padding: 0 .5rem 1rem;
        .li-item {
            width: 100%;
            height: 2.9rem;
            line-height: 2.9rem;
            border-bottom: 1px solid #ddd;
            font-size: .8rem;
            position: relative;
            .li-select {
                width: 70%;
                float: right;
                height: 100%;
                border: 0;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                direction: rtl;
                outline: none;
                background: #fff;
                font-size: .8rem;
                background: url(../assets/images/icon_arrow.png) no-repeat right center;
                background-size: .4rem;
                padding-right: 1rem;
                color: #999;
            }
            .li-item-name {
                color: #343434;
                font-size: .8rem;
            }
            .li-item-val {
                height: 100%;
                width: 60%;
                float: right;
                border: 0;
                text-align: right;
                color: #999;
                font-size: .8rem;
                outline: none;
            }
            .weui_cell {
                padding: 0;
                font-size: .8rem;
            }
            .li-item-name2 {
                width: 6.1rem;
                display: inline-block;
            }
            .checkbox {
                display: none;
            }
            .check {
                width: 2.8rem;
                height: 100%;
                position: absolute;
                right: 0;
                background: url(../assets/images/bg_choose.png) center center no-repeat;
                background-size: .95rem .95rem;
            }
            input[type="checkbox"]:checked + label {
                background: url(../assets/images/chose.png) center .725rem no-repeat;
                background-size: 1.5rem 1.2rem;
            }
            input[type="radio"] + label {
                background: url(../assets/images/icon_choose.png) center .35rem no-repeat;
                background-size: 1rem;
            }
            input[type="radio"]:checked + label {
                background: url(../assets/images/icon_choose_on.png) center .35rem no-repeat;
                background-size: 1rem;
            }
        }
        .borderNone {
            border: 0;
            margin-bottom: -0.5rem;
        }
        .li-item2 {
            line-height: 2rem;
            height: 2rem;
            padding-left: 1rem;
            border: 0;
        }
    }
    .successNote {
        text-align: left;
        padding-left: .5rem;
        font-size: .85rem !important;
        .note-item {
            color: red;
        }
        p {
            margin-top: .5rem;
        }
    }
}
</style>
