<template>
    <div class="page-signMessage">
        <header-nav title="我的报名信息" :backshow="backshow"></header-nav>
        <div class="goMy" @click="goMy()"></div>
        <div class="message-main">
            <div class="li-item">
                <span class="li-item-name">姓名</span>
                <span class="li-item-code">{{registeData.student.studnetName}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">身份证号码</span>
                <span class="li-item-code">{{registeData.student.idcard}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">电话号码</span>
                <span class="li-item-code">{{registeData.student.mobile}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">学号</span>
                <span class="li-item-code">{{registeData.student.studentNo}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">性别</span>
                <span class="li-item-code">{{registeData.student.sex}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">区域</span>
                <span class="li-item-code">{{registeData.company.province}}{{registeData.company.city}}{{registeData.company.district}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">单位</span>
                <span class="li-item-code">{{registeData.company.companyName}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">单位地址</span>
                <span class="li-item-code">{{registeData.student.companyAddress}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">培训专业</span>
                <span class="li-item-code">{{registeData.student.specialty}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">培训费</span>
                <span class="li-item-code">{{registeData.student.feesTrain}}元/人</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">国考鉴定费</span>
                <span class="li-item-code">{{registeData.student.feesStudy}}元/人</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">餐费</span>
                <span class="li-item-code">{{registeData.student.feesMeal}}元/人</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">住宿费</span>
                <span class="li-item-code">{{hotel}}元</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">费用总计</span>
                <span class="li-item-code">{{registeData.student.feesTotal}}元</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">报名费用</span>
                <template v-if="registeData.student.payStatus == 1">
                    <span class="li-item-code">已缴费</span>
                </template>
                <template v-else>
                    <span class="li-item-code">未缴费</span>
                </template>
            </div>
            <div class="li-item noborder">
                <span class="li-item-name">服务费用</span>
                <template  v-if="registeData.student.serviceFeesStatus == 1">
                    <span class="li-item-code">已缴费</span>
                </template>
                <template v-else>
                    <span class="li-item-code">未缴费</span>
                </template>
            </div>
        </div>
        <div class="login-next">
            <span @click="back">返回</span>
        </div>
        <toast :show.sync="show2" type="text" :time="500">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
import Cell from 'vux-components/cell'
import Group from 'vux-components/group'
// import { Group, Address, Cell } from 'vux-components/'
export default {
    name: 'signMessage',
    components: {
        Dialog,
        loading,
        toast,
        headerNav,
        Group,
        Cell
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            backshow: true,
            show2: false,
            loading: false,
            warntext: "",
            registeData: [],
            hotel: 0,
        }
    },
    watch: {
        guokao(val) {
            // alert(val);
        },
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        goMy: function() {
            this.$router.go('/person');
        },
        back: function() {
            window.history.back();
        },
        renderData: function() {
            apiService.myRegiste({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                // JSESSIONID: "0C66D5300C688232F819D3AC8BF56DD6",
                // code: "yuantai"
            }).then((data) => {
                console.log(data, "data");
                this.loading = false;
                this.registeData = data.data;
                let studentData = this.registeData.student;
                if (studentData.feesHotel && studentData.feesHotel != "" && studentData.feesHotel > 0) {
                    this.hotel = studentData.feesHotel;
                } else if (studentData.feesDormitory && studentData.feesDormitory != "" && studentData.feesDormitory > 0) {
                    this.hotel = studentData.feesDormitory;
                } else {
                    this.hotel = 0;
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        popState: function() {
            var state = {
                title: "title",
                url: "#"
            };
            window.history.pushState(state, "title", "#");
            window.addEventListener("popstate", function(e) {
                wx.closeWindow();
            }, false);
        },
    },
}
</script>
<style lang="scss">
.page-signMessage {
    position: relative;
    .goMy {
        width: 2.2rem;
        height: 2.3rem;
        position: absolute;
        top: 0;
        right: 0;
        background: url("../assets/images/icon_user.png") center center no-repeat;
        background-size: .95rem;
        z-index: 100;
    }
    .message-main {
        width: 100%;
        height: auto;
        background: #fff;
        padding: 0 .5rem;
        .li-item {
            width: 100%;
            height: auto;
            border-bottom: 1px solid #ddd;
            font-size: .8rem;
            position: relative;
            font-size: 0;
            padding: 1rem 0;
            .li-item-name {
                color: #343434;
                font-size: .8rem;
                width: 33%;
                display: inline-block;
                vertical-align: top;
            }
            .li-item-code {
                display: inline-block;
                width: 66%;
                height: auto;
                font-size: .8rem;
                text-align: right;
                line-height: 130%;
            }
        }
    }
    .noborder {
        border: 0 !important;
    }
}
</style>
