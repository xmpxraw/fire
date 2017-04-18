<template>
    <div class="page-pointMessage">
        <header-nav title="我的预约" :backshow="backshow"></header-nav>
        <div>
            <div class="point-main">
                <div class="li-item">
                    <span class="li-item-name">预约期数：</span>
                    <span class="li-item-code">{{successData.term}}</span>
                </div>
                <div class="li-item">
                    <span class="li-item-name">开班时间：</span>
                    <span class="li-item-code">{{successData.beginTime}}</span>
                </div>
                <div class="li-item noborder">
                    <span class="li-item-name">期数编码：</span>
                    <span class="li-item-code">{{successData.termCode}}</span>
                </div>
            </div>
            <div class="li-code">您已成功预约！</div>
            <div class="login-next">
                <span @click="goPerson()">点击到个人中心</span>
            </div>
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
// import { Group, Address, Cell } from 'vux-components/'
export default {
    name: 'pointMessage',
    components: {
        Dialog,
        XButton,
        loading,
        toast,
        headerNav
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            backshow: true,
            successData: [],
            show2: false,
            loading: false,
            pointState: false,
            warntext: "",
            opentime: "TODAY",
            weeksList: ['日', '一', '二', '三', '四', '五', '六'],
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
        renderData: function() {
            apiService.myReserve({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.pointState = false;
                    this.successData = data.data.term;
                } else if (data.data.result == 0) {
                    this.show2 = true;
                    window.localStorage.setItem("menuStatus", "pointMessage");
                    this.warntext = "您尚未登录，请先登录";
                    setTimeout(() => {
                        this.$router.go('/login');
                    }, 1000)
                } else {
                    this.show2 = true;
                    this.warntext = "您尚未预约，请先预约";
                    setTimeout(() => {
                        this.$router.go('/point');
                    }, 1000)
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        back: function() {
            window.history.back();
        },
        goPoint: function() {
            this.$router.go('/point');
        },
        goPerson: function() {
            this.$router.go('/person');
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
.page-pointMessage {
    .noborder {
        border: 0;
    }
    .point-main-item {
        width: 100%;
        padding: .9rem .75rem;
        font-size: .85rem;
        color: #666;
        line-height: 1.3rem;
    }
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
        }
    }
    .li-code {
        height: 2.9rem;
        line-height: 2.9rem;
        font-size: .9rem;
        padding: 0 .5rem;
    }
    .weui_cell {
        font-size: .8rem;
        padding: 1rem 0;
    }
}
</style>
