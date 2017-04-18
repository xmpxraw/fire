<template>
    <div class="class-main">
        <header-nav title="我要报班" :backshow="backshow"></header-nav>
        <div v-show="pointState">
            <div class="point-main-item">
                <span v-show="nopoint">您尚未做上课预约，请先预约期数后再进行报班操作！
                </span>
                <span v-show="!nopoint">
                请先到学校找管理员报到
                </span>
            </div>
            <div class="login-next">
                <span @click="goPoint()" v-show="nopoint">马上预约</span>
                <span @click="cancle()" v-show="!nopoint">关闭页面</span>
            </div>
        </div>
        <div v-show="!pointState">
            <div class="header">
                <div class="logo">
                    <img src="../assets/images/logo.png">
                </div>
                <div class="schoolName" @click='goSchoolDetail'>{{successData.schoolName}}</div>
            </div>
            <div class="contain">
                <ul>
                    <li>
                        <span class="name-item">班级号</span>
                        <select v-model="classCoded" class="li-select">
                            <option v-for="i in dataList" value="{{$index}}">{{i.classCode}}</option>
                        </select>
                    </li>
                    <!-- <li>
                        <span class="name-item">班级名称</span>
                        <span class="result-item">{{successData.classCode}}</span>
                    </li> -->
                    <li>
                        <span class="name-item">班级类型</span>
                        <span class="result-item">{{successData.classTypeName}}</span>
                    </li>
                    <li>
                        <span class="name-item">专业</span>
                        <span class="result-item">{{successData.specialty}}</span>
                    </li>
                    <li>
                        <span class="name-item">开班时间</span>
                        <span class="result-item">{{successData.beginTime}}</span>
                    </li>
                    <li>
                        <span class="name-item">学习天数</span>
                        <span class="result-item">{{successData.period}}天</span>
                    </li>
                    <li>
                        <span class="name-item">招生人数</span>
                        <span class="result-item">{{successData.planCount}}人</span>
                    </li>
                    <li>
                        <span class="name-item">剩余名额</span>
                        <span class="result-item">{{successData.delayCount}}人</span>
                    </li>
                </ul>
            </div>
            <div class="j-submit" @click='submit'>确定报班</div>
        </div>
        <!-- <div class="shawdom"></div> -->
        <div style="height: 100%">
            <dialog :show.sync="showNoScroll" class="dialog-demo" :scroll="false">
                <p class="dialog-title">温馨提示:<img src="../assets/images/icon_close.png" @click="showNoScroll=false"></p>
                <template v-if="showitem">
                    <div class="img-box">
                        <p>您选择的班级是{{successData.classCode}}</p>
                        <p>开班时间是{{successData.beginTime}}</p>
                        <p>您确定要报班吗？</p>
                    </div>
                    <div class="j-operate">
                        <span class="sure-submit" @click="sureSubmit">确定</span>
                        <span class="cancle" @click="showNoScroll=false">取消</span>
                    </div>
                </template>
                <template v-if="!showitem">
                    <div class="img-box">
                        <p>名额已满，不能报班!</p>
                    </div>
                    <div class="j-operate">
                        <span class="sure-submit" @click="canSure">确定</span>
                        <span class="cancle" @click="canSure">取消</span>
                    </div>
                </template>
            </dialog>
        </div>
        <loading :show="loading"></loading>
        <toast :show.sync="show2" type='text' :time="1000">{{warntext}}</toast>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import XButton from 'vux-components/x-button'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import headerNav from '../common/header.vue'
import apiService from '../apiservice.js'
export default {
    name: 'classDetail',
    components: {
        Dialog,
        XButton,
        loading,
        toast,
        headerNav
    },
    ready() {
        this.loadPermissions();
    },
    data() {
        return {
            backshow: true,
            title: '班级',
            showNoScroll: false, //控制弹出现，true为出现
            showitem: true, //控制是不是已经报满了
            dataList: [],
            successData: [],
            loading: true, //控制是否出现loading
            show2: false, //控制toast的出现
            pointState: false,
            warntext: "",
            nopoint: true,
            classCoded: "",
        }
    },
    watch: {
        classCoded(newval, oldval) {
            this.successData = this.dataList[newval];
            this.successData.delayCount = parseInt(this.successData.planCount) - parseInt(this.successData.registryCount);
            if (this.successData.classType == 1) {
                this.successData.classTypeName = "学习班";
            } else {
                this.successData.classTypeName = "突击班";
            }
        }
    },
    methods: {
        submit: function() {
            this.showNoScroll = true;
        },
        cancle: function() {
            wx.closeWindow();
        },
        goSchoolDetail: function() {
            this.$router.go('/schoolDetail');
        },
        canSure: function() {
            this.showNoScroll = false;
            this.loadPermissions();
        },
        back: function() {
            window.history.back();
        },
        sureSubmit: function() {
            this.loading = true;
            apiService.signUp({
                classCode: this.successData.classCode,
                classType: this.successData.classType,
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                // success callback
                this.loading = false;
                if (data.data.code == 200) {
                    // this.showitem = true;
                    this.$router.go('/successDetail');
                } else if (data.data.code == -1) {
                    this.showitem = false;
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })

        },
        goPoint: function() {
            this.$router.go('/point');
        },
        loadPermissions: function() {
            apiService.viewClass({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                /*JSESSIONID: "6974591272DC5AB3667E541645829DDF",
                code: "yuantai"*/
            }).then((data) => {
                this.loading = false;
                console.log(data, "------------")
                    // success callback result是0没有绑定 1已经绑定 2已经报班 3为未开班 0未登录 4未预约 5未到管理员报到
                if (data.data.result != "undefined") {
                    if (data.data.result == 0) {
                        window.localStorage.setItem("menuStatus", "classDetail");
                        this.show2 = true;
                        this.warntext = "您尚未登录，请先登录";
                        setTimeout(() => {
                            this.$router.go('/login');
                        }, 1000)
                        return;
                    }

                    if (data.data.result == 4) {
                        // this.$router.go('/noPower');
                        this.pointState = true;
                        this.nopoint = true;
                        return;
                    }
                    if (data.data.result == 5) {
                        this.pointState = true;
                        this.nopoint = false;
                        return;
                    }
                    if (data.data.result == 2) {
                        if (window.sessionStorage.getItem("isSuccess") && window.sessionStorage.getItem("isSuccess") == 1) {
                            //不需跳转
                            window.sessionStorage.setItem("isSuccess", 2);
                        } else {
                            window.sessionStorage.setItem("isSuccess", 2);
                            this.$router.go('/successDetail');
                        }
                        return;
                    }
                    if (data.data.result == 3) {
                        this.$router.go('/noClass');
                        return;
                    }
                }
                this.dataList = data.data.classes;
                this.classCoded = 0;

            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
    },
}
</script>
<style lang="scss">
.class-main {
    .li-select {
        width: 60%;
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
    .point-main-item {
        width: 100%;
        padding: .9rem .75rem;
        font-size: .85rem;
        color: #666;
        line-height: 1.3rem;
    }
    .notscoll {
        overflow: hidden;
    }
    .main {
        padding-bottom: 2.3rem;
    }
    .header {
        width: 100%;
        padding: 0 .5rem;
        background: #fff;
        height: auto;
        display: none;
        .logo {
            height: auto;
            padding-top: 1rem;
            padding-bottom: .9rem;
            img {
                height: 5.45rem;
                width: 5.7rem;
                margin: 0 auto;
                display: block;
            }
        }
        .schoolName {
            width: 100%;
            height: 2.875rem;
            line-height: 2.875rem;
            border-top: 1px solid #dddddd;
            color: #333333;
            font-size: .75rem;
            background: url(../assets/images/icon_arrow.png) no-repeat right center;
            background-size: .5rem;
        }
    }
    .contain {
        width: 100%;
        height: auto;
        padding: 0 .5rem;
        background: #fff;
        margin-top: .5rem;
        ul {
            width: 100%;
            height: auto;
            li {
                width: 100%;
                height: 2.85rem;
                line-height: 2.85rem;
                border-top: 1px solid #dddddd;
                list-style: none;
                span {
                    font-size: .75rem;
                    display: inline-block;
                }
                .name-item {
                    text-align: left;
                    width: 33%;
                    color: #333333;
                }
                .result-item {
                    text-align: right;
                    width: 63%;
                    color: #999999;
                }
            }
            li:first-child {
                border-top: 0px;
            }
        }
    }
    .j-submit {
        width: 94.9%;
        height: 2.5rem;
        line-height: 2.5rem;
        text-align: center;
        color: #fff;
        background: #438ac9;
        border-radius: .25rem;
        margin-top: 1.15rem;
        font-size: .9rem;
    }
    .shawdom {
        width: 100%;
        height: 100%;
        background: #000;
        opacity: .5;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 99;
    }
}

.dialog-title {
    font-size: .95rem;
    font-weight: bold;
    height: 3rem;
    line-height: 3rem;
    position: relative;
}

.dialog-title img {
    width: .8rem;
    height: .75rem;
    position: absolute;
    top: 1rem;
    right: .6rem;
    color: #333333;
}

.weui_dialog {
    width: 94.6%;
    color: #000;
}

.weui_dialog .img-box p {
    width: 100%;
    height: 1.15rem;
    line-height: 1.15rem;
    font-size: .75rem;
}

.j-operate {
    width: 100%;
    padding: 1.35rem 0;
}

.j-operate span {
    width: 42%;
    height: 2.375rem;
    line-height: 2.375rem;
    text-align: center;
    display: inline-block;
    color: #fff;
    border-radius: .25rem;
    font-size: .9rem;
}

.j-operate .sure-submit {
    background: #438ac9;
}

.j-operate .sure-cancle {
    background: #999;
}

.j-operate .cancle {
    background: #a9a9a9;
}
</style>
