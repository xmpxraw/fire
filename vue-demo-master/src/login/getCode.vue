<template>
    <div class="page-getcode">
        <header-nav title="登录" :backshow="backshow"></header-nav>
        <ul class="getcode-item">
            <li>学员：{{studentName}}</li>
            <li>手机号：{{mobile}}</li>
            <li>身份证号码：{{idcard}}</li>
        </ul>
        <div class="getcode-item2">
            <input type="text" v-model="messageCode" placeholder="输入验证码">
            <span v-show="!showMin" class="massagecode" @click="getCode()">获取验证码</span>
            <span v-show="showMin" class="massagecode code2">{{time}}s后才能重发</span>
        </div>
        <div class="login-next">
            <span @click="login()">登录</span>
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
export default {
    name: 'getcode',
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
            backshow: false,
            mobile: this.$route.params.mobile,
            studentName: this.$route.params.studentName,
            idcard: this.$route.params.idcard,
            messageCode: "",
            show2: false,
            warntext: "",
            showMin: false,
            time: 60,
            loading: false,
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {
            this.loading = true;
            apiService.loginForard({
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                code: window.localStorage.getItem("codeStatus")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.show2 = true;
                    this.warntext = "已经登录，即将跳转到个人中心";
                    setTimeout(() => {
                        this.$router.go('/person');
                    }, 1000)
                } else {

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
        login: function() {
            if (this.messageCode == "") {
                this.show2 = true;
                this.warntext = "验证码不能为空";
                return;
            }
            this.loading = true;
            //删掉
            // window.localStorage.setItem("loginStatus", 1);
            // if(window.localStorage.getItem("routeStatus")){
            //     this.$router.go('/' + window.localStorage.getItem("routeStatus"));
            //   }
            //end
            apiService.weixinLogin({
                mobile: this.mobile,
                PIN: this.messageCode,
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                code: window.localStorage.getItem("codeStatus")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    if (window.localStorage.getItem("menuStatus")) {
                        // this.$router.go('/' + window.localStorage.getItem("menuStatus"));
                        this.$router.go('/schoolIndex');
                    }
                } else {
                    this.show2 = true;
                    this.warntext = "登录失败，请确认验证码是否正确";
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        finish: function() {
            this.time = this.time - 1;
            if (this.time > 0) {
                setTimeout(() => {
                    this.finish();
                }, 1000)
            } else {
                this.showMin = false;
                this.time = 60;
            }
        },
        getCode: function() {
            this.loading = true;
            apiService.sendMsg({
                mobile: this.mobile,
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                code: window.localStorage.getItem("codeStatus")
            }).then((data) => {
                this.loading = false;
                if (data.data.result == 1) {
                    this.show2 = true;
                    this.warntext = "验证码发送成功";
                    this.showMin = true;
                    this.finish();
                } else {
                    this.show2 = true;
                    this.warntext = "验证码发送失败，请稍后再试";
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
.page-getcode {
    .getcode-item {
        width: 100%;
        height: auto;
        background: #fff;
        padding: .5rem .95rem;
        li {
            list-style: none;
            height: 1.65rem;
            line-height: 1.65rem;
            font-size: .9rem;
            color: #333;
        }
    }
    .getcode-item2 {
        width: 100%;
        height: 3.15rem;
        line-height: 3.15rem;
        background: #fff;
        margin-top: .6rem;
        padding: .75rem;
        position: relative;
        input {
            color: #333;
            height: 100%;
            width: 100%;
            border: 0;
            display: inherit;
            font-size: .85rem;
            padding-left: 1.35rem;
            background: url(../assets/images/icon_code.png) left center no-repeat;
            background-size: .85rem 1.25rem;
            outline: none;
        }
        .massagecode {
            display: inline-block;
            height: 1.85rem;
            line-height: 1.85rem;
            font-size: .85rem;
            text-align: center;
            color: #666;
            width: 5.5rem;
            border: 1px solid #999;
            border-radius: .25rem;
            position: absolute;
            top: .6rem;
            right: .75rem;
        }
        .code2 {
            font-size: .7rem;
        }
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
</style>
