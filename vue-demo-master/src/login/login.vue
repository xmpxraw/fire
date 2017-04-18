<template>
    <div class="page-login">
        <header-nav title="登录" :backshow="backshow"></header-nav>
        <div class="login-item">
            <input type="text" placeholder="输入手机号" v-model="phoneNum">
        </div>
        <div class="login-next">
            <span @click="gonext()">下一步</span>
        </div>
        <div style="height: 100%">
            <dialog :show.sync="showNoScroll" class="dialog-demo" :scroll="false">
                <p class="dialog-title">温馨提示</p>
                <!-- <template v-if="showitem"> -->
                <div class="img-box">
                    未能查找到您的报名信息,您可以返回公众号点击“我要报名”,完成报名操作。
                </div>
                <div class="j-operate">
                    <span class="sure-submit" @click="showNoScroll = false">确定</span>
                </div>
                <!-- </template> -->
            </dialog>
        </div>
        <toast :show.sync="show2" type="text" :time="500">{{warntext}}</toast>
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
export default {
    name: 'login',
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
            showNoScroll: false,
            showitem: true,
            show2: false,
            successData: [],
            loading: false,
            backshow: false,
            phoneNum: "",
            warntext: "",
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {

        },
        gonext: function() {
            // this.showNoScroll = true;
            // this.$router.go('/getcode');
            // this.loading = true;
            if (this.phoneNum == "") {
                this.show2 = true;
                this.warntext = "电话号码不能为空";
                return;
            }
            apiService.checkLogin({
                mobile: this.phoneNum,
                schoolCode: "",
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.successData = data.data;
                    this.$router.go({
                        name: 'getcode',
                        params: {
                            mobile: this.successData.mobile,
                            idcard: this.successData.idcard,
                            studentName: this.successData.studentName
                        }
                    });
                } else {
                    this.showNoScroll = true;
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
.page-login {
    .login-item {
        width: 100%;
        height: 3.1rem;
        line-height: 3.1rem;
        background: #fff;
        padding-left: .85rem;
        input {
            font-size: .85rem;
            width: 100%;
            height: 100%;
            border: 0;
            outline: none;
            display: inherit;
            padding-left: 1.25rem;
            background: url(../assets/images/icon_phone.png) left center no-repeat;
            background-size: .85rem 1.25rem;
        }
    }
    .login-next {
        padding: .75rem .5rem;
        span {
            display: block;
            width: 100%;
            height: 2.5rem;
            line-height: 2.5rem;
            text-align: center;
            color: #fff;
            font-size: .85rem;
            background: #f39900;
            border-radius: .25rem;
        }
    }
    .img-box {
        font-size: .85rem;
        line-height: 1.5rem;
        padding: 0 .5rem;
    }
}
</style>
