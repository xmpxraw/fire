<template>
    <div class="page-schoolIndex">
        <header-nav title="在线学习平台" :backshow="backshow"></header-nav>
        <div class="goMy" @click="goMy()"></div>
        <swiper :list="list" height="9.4rem" auto></swiper>
        <div class="page-main">
            <ul>
                <li v-for="item in liList" @click="goLink(item.url)">
                    <!-- <img :src="item.img"> -->
                    <i class="icon {{item.className}}"></i>
                    <span>{{item.text}}</span>
                </li>
            </ul>
        </div>
        <div class="schoolIndex-main">
            <div class="school-header">
                <div class="schoolDescribe">
                    <div class="describe"><span>学校简介</span></div>
                </div>
            </div>
            <div class="school-contain">
                <div class="school-contain-item">
                    {{{successData.content}}}
                </div>
            </div>
        </div>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
// import swiper from 'vux-components/swiper'
import swiper from '../common/swiper/index.vue'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
export default {
    name: 'schoolIndex',
    components: {
        Dialog,
        loading,
        toast,
        headerNav,
        swiper
    },
    ready() {
        this.getSchoolDetail();
        this.renderData();
        this.getAdvertisement();
    },
    data() {
        return {
            showitem: true,
            show2: false,
            successData: [],
            indexData: [],
            loading: false,
            backshow: false,
            phoneNum: "",
            warntext: "",
            list: [{
                url: 'http://baidu.com',
                img: '../../assets/images/ad01.png',
            }, {
                url: 'http://baidu.com',
                img: '../../assets/images/ad01.png',
            }, {
                url: 'http://baidu.com',
                img: '../../assets/images/ad01.png',
            }],
            liList: [{
                className: 'icon_p1',
                text: '章节练习',
                url: "xPractice"
            }, {
                className: 'icon_p2',
                text: '错题练习',
                url: "xWrongTj"
            }, {
                className: 'icon_p3',
                text: '模拟考试',
                url: "examIndex"
            }, {
                className: 'icon_p4',
                text: '在线读书',
                url: "readIndex"
            }, {
                className: 'icon_p5',
                text: '视频教学',
                url: "videoIndex"
            }, {
                className: 'icon_p6',
                text: '我要报班',
                url: "classDetail"
            }],
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        goMy: function() {
            this.$router.go('/person');
        },
        getAdvertisement: function() {
            this.loading = true;
            apiService.getAdvertisement({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
                    // code:"yuantai",
                    // JSESSIONID:"86C2999769D676A7F876AF404583693C"
            }).then((data) => {
                this.loading = false;
                this.list = data.data.advertisement;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        renderData: function() {
            this.loading = true;
            apiService.WXIndex({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
                    // code:"yuantai",
                    // JSESSIONID:"86C2999769D676A7F876AF404583693C"
            }).then((data) => {
                this.loading = false;
                this.indexData = data.data;
                if (data.data.student) {
                    window.localStorage.setItem("studentNo", data.data.student.id);
                    window.localStorage.setItem("studentName", data.data.student.studnetName);
                    window.localStorage.setItem("sysCode", data.data.student.sysCode);
                    window.localStorage.setItem("specialty", data.data.student.specialty);
                }

            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        getSchoolDetail: function() {
            this.loading = true;
            apiService.schoolDetail({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
                    // code:"yuantai",
                    // JSESSIONID:"86C2999769D676A7F876AF404583693C"
            }).then((data) => {
                this.loading = false;
                this.successData = data.data;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        goLink: function(url) {
            if (this.indexData.result == 0) {
                this.$router.go("/login");
                return
            }
            if (url == 'classDetail') {
                this.$router.go("/" + url)
            } else {
                if (this.indexData.student && this.indexData.student.serviceFeesStatus == 1) {
                    this.$router.go("/" + url)
                } else {
                    this.show2 = true;
                    this.warntext = "您未缴费,无权限使用此模块...";
                }
            }
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
.page-schoolIndex {
    position: relative;
    .vux-swiper-desc {
        height: auto !important;
    }
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
    .page-main {
        width: 100%;
        height: 10.8rem;
        background: #fff;
        padding-bottom: .8rem;
        ul {
            width: 100%;
            height: auto;
            font-size: 0;
            li {
                width: 33.3%;
                height: 5rem;
                display: inline-block;
                font-size: .75rem;
                color: #333;
                text-align: center;
                .icon {
                    width: 2.3rem;
                    height: 2.3rem;
                    margin: 0 auto;
                    display: block;
                    margin-top: 1rem;
                    display: block;
                }
                span {
                    height: 1.75rem;
                    line-height: 1.75rem;
                    width: 100%;
                    display: block;
                }
                .icon_p1 {
                    background-image: url(../assets/images/icon_p1.png);
                    background-size: contain;
                }
                .icon_p2 {
                    background-image: url(../assets/images/icon_p2.png);
                    background-size: contain;
                }
                .icon_p3 {
                    background-image: url(../assets/images/icon_p3.png);
                    background-size: contain;
                }
                .icon_p4 {
                    background-image: url(../assets/images/icon_p4.png);
                    background-size: contain;
                }
                .icon_p5 {
                    background-image: url(../assets/images/icon_p5.png);
                    background-size: contain;
                }
                .icon_p6 {
                    background-image: url(../assets/images/icon_p6.png);
                    background-size: contain;
                }
            }
        }
    }
    .schoolIndex-main {
        width: 100%;
        background: #fff;
        height: auto;
        margin-top: .5rem;
        .school-header {
            width: 100%;
            height: 2.95rem;
            .schoolDescribe {
                width: 100%;
                height: 100%;
                line-height: 2.95rem;
            }
            .describe {
                width: 6rem;
                height: 100%;
                line-height: 2.95rem;
                font-size: .9rem;
                color: #438ac9;
                font-weight: bold;
            }
            .describe span {
                display: inline-block;
                width: 100%;
                height: 2.95rem;
                line-height: 2.95rem;
                background: url(../assets/images/icon_school.png) no-repeat .5rem .95rem;
                background-size: 1.1rem;
                padding-left: 1.9rem;
            }
        }
        .school-contain {
            width: 100%;
            height: auto;
            .school-contain-item {
                font-size: .75rem;
                padding: .5rem .5rem;
                line-height: 170%;
                text-indent: 2em;
            }
        }
    }
}
</style>
