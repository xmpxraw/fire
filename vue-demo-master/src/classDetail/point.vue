<template>
    <div class="page-point">
        <header-nav :backAddress="backAddress" title="我要预约" :backshow="backshow"></header-nav>
        <div class="point-main">
            <div class="li-item">
                <!-- <span class="li-item-name">预约期数</span>
           <input class="li-item-val" type="text"  placeholder="未填写"> -->
                预约期数
                <select v-model="classSelect" class="li-select">
                    <option v-for="i in terms" value="{{$index}}">{{i.term}}</option>
                </select>
            </div>
            <div class="li-item">
                <span class="li-item-name">开班时间</span>
                <span class="li-item-code">{{currentItem.beginTime}}</span>
            </div>
            <div class="li-item">
                <span class="li-item-name">期数编码</span>
                <span class="li-item-code">{{currentItem.termCode}}</span>
            </div>
            <!-- <calendar :value.sync="opentime" title="开班时间" disable-past></calendar> -->
        </div>
        <div class="point-main-item">
            您尚未预约，请选择预约的期数，点击“确定预约”，完成预约！
        </div>
        <div class="login-next">
            <span @click="surePoint()">确定预约</span>
        </div>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import XButton from 'vux-components/x-button'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
import Cell from 'vux-components/cell'
import Group from 'vux-components/group'
import calendar from 'vux-components/calendar'
// import { Group, Address, Cell } from 'vux-components/'
export default {
    name: 'point',
    components: {
        Dialog,
        XButton,
        loading,
        toast,
        headerNav,
        Group,
        Cell,
        calendar
    },
    ready() {
        this.renderData();
        // this.popState();
    },
    data() {
        return {
            backshow: true,
            show2: false,
            loading: false,
            backAddress: "person",
            warntext: "",
            classSelect: "",
            opentime: "TODAY",
            currentItem: [],
            terms: [],
            weeksList: ['日', '一', '二', '三', '四', '五', '六'],
            list: [{
                key: 'gd',
                value: '广东'
            }, {
                key: 'gx',
                value: '广西'
            }],
        }
    },
    watch: {
        classSelect(val) {
            this.currentItem = this.terms[val];
        },
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        onChange: function() {

        },
        surePoint: function() {
            // this.loading = true;
            apiService.reserve({
                term: this.currentItem.term,
                termCode: this.currentItem.termCode,
                beginTime: this.currentItem.beginTime,
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.show2 = true;
                    this.warntext = "预约成功";
                    setTimeout(() => {
                        this.$router.go('/pointMessage');
                    }, 1000)
                } else {
                    this.show2 = true;
                    this.warntext = "网络不给力,请稍后再试...";
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        back: function() {
            // this.$router.go('/person');
            window.history.back();
        },
        renderData: function() {
            // this.loading = true;
            apiService.getTerms({
                code: window.localStorage.getItem("codeStatus")
            }).then((data) => {
                this.loading = false;
                this.terms = data.data.terms;
                this.currentItem = this.terms[0];
                this.classSelect = 0;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        popState: function() {
            let _this = this;
            // var state = { 
            // title: "title", 
            // url: "#"
            // }; 
            // window.history.pushState(state, "title", "#"); 
            window.addEventListener("popstate", function(e) {
                // _this.back(); 
            }, false);
        },

    },
}
</script>
<style lang="scss">
.page-point {
    .point-main {
        width: 100%;
        height: auto;
        background: #fff;
        padding: 0 .5rem;
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
            }
            .li-item-name {
                color: #343434;
                font-size: .8rem;
            }
            .li-item-code {
                float: right;
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
        }
        .noborder {
            border: 0;
        }
    }
    .point-main-item {
        width: 100%;
        padding: .9rem .5rem 0;
        font-size: .85rem;
        color: #666;
        line-height: 1.3rem;
    }
    .weui_cell {
        font-size: .8rem;
        padding: 1rem 0;
    }
}
</style>
