<template>
    <div class="page-person">
        <header-nav title="个人中心" :backshow="backshow"></header-nav>
        <div class="goMy" @click="goSchoolIndex()"></div>
        <div class="header">
            <dl>
                <dt>
                    <!-- <img src="../assets/images/logo.png"> -->
                    <span>{{studentData.studnetName}}</span>
                </dt>
                <dd><a title="完善资料" class="finishmt" @click="goMessage()">查看资料</a></dd>
            </dl>
        </div>
        <div class="contain">
            <ul>
                <li>
                    <a title="我的预约" class="signLink" @click="goPoint()"><span class="name-item">我的预约<i v-show="ispoint">(未预约)</i></span></a>
                </li>
                <li>
                    <a title="班级信息" class="signLink" @click="goClass()"><span class="name-item">班级信息<i v-show="isClass">(未报班)</i></span></a>
                </li>
                <li>
                    <a title="我的成绩" class="signLink" @click="goScore()"><span class="name-item">我的成绩</span></a>
                </li>
                <li>
                    <a title="模拟考试记录" class="signLink" @click="goxTestrecord()"><span class="name-item">模拟考试记录</span></a>
                </li>
                <li>
                    <a title="播放记录" class="signLink" @click="goxTestrecordOth()"><span class="name-item">播放记录</span></a>
                </li>
            </ul>
        </div>
        <loading :show="loading"></loading>
        <toast :show.sync="show2" type='text' :time="1000">{{warntext}}</toast>
    </div>
</template>
<script>
import headerNav from '../common/header.vue'
import loading from 'vux-components/loading'
import apiService from '../apiservice.js'
import toast from 'vux-components/toast'
export default {
    name: 'person',
    components: {
        headerNav,
        loading,
        toast
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            backshow: false,
            ispoint: false,
            isClass: false,
            loading: false, //控制是否出现loading
            show2: false, //控制toast的出现
            studentData: [],
            warntext: "",
        }
    },
    methods: {

        back: function() {
            // window.history.back();
            this.$router.go('/personScore');
        },
        goxTestrecord: function() {
            this.$router.go('/xTestrecord');
        },
        goxTestrecordOth: function() {
            this.$router.go('/xTestrecordOth');
        },
        goPoint: function() {
            if (this.ispoint) {
                this.$router.go('/point');
            } else {
                this.$router.go('/pointMessage');
            }
        },
        goScore: function() {
            this.$router.go('/personScore');
        },
        goClass: function() {
            // if (this.isClass) {
            this.$router.go('/classDetail');
            // } else {
            //     this.$router.go('/successDetail');
            // }
        },
        goMessage: function() {
            this.$router.go('/signMessage');
        },
        goSchoolIndex: function() {
            this.$router.go('/schoolIndex');
        },
        renderData: function() {
            apiService.myCenter({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                if (data.data.result == 0) {
                    window.localStorage.setItem("menuStatus", "person");
                    this.show2 = true;
                    this.warntext = "您尚未登录，请先登录";
                    setTimeout(() => {
                        this.$router.go('/login');
                    }, 1000)
                    return;
                }
                this.loading = false;
                this.studentData = data.data.student;
                window.localStorage.setItem("studentNo", data.data.student.id);
                window.localStorage.setItem("studentName", data.data.student.studnetName);
                window.localStorage.setItem("sysCode", data.data.student.sysCode);
                window.localStorage.setItem("specialty", data.data.student.specialty);
                if (!this.studentData.termCode || this.studentData.termCode == "") {
                    this.ispoint = true;
                }
                if (!this.studentData.classCode || this.studentData.classCode == "") {
                    this.isClass = true;
                }
                window.localStorage.setItem("studentId", this.studentData.id);
                window.localStorage.setItem("sysCode", this.studentData.sysCode);
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

        },
    },

}
</script>
<style lang="scss">
.page-person {
    .notscoll {
        overflow: hidden;
    }
    .goMy {
        width: 2.2rem;
        height: 2.3rem;
        position: absolute;
        top: 0;
        right: 0;
        background: url("../assets/images/icon_home.png") center center no-repeat;
        background-size: .95rem;
        z-index: 100;
    }
    .main {
        padding-bottom: 2.3rem;
    }
    .header {
        width: 100%;
        padding: 0 .5rem;
        background: #fff;
        height: auto;
        dl {
            overflow: hidden;
            height: 4.6rem;
            margin: 0;
            dt {
                width: 62%;
                float: left;
                overflow: hidden;
                padding: .5rem;
                img {
                    width: 100%;
                    border-radius: 50%;
                }
                span {
                    margin-top: 1.2rem;
                    display: inline-block;
                }
            }
            dd {
                /*display: inline-block;*/
                font-size: .9rem;
                padding: 0;
                text-align: left;
                line-height: 4rem;
                a.finishmt {
                    float: right;
                    padding: .4rem .6rem;
                    background: #f39900;
                    color: #fff;
                    font-size: .9rem;
                    line-height: 1rem;
                    border-radius: .25rem;
                    margin-top: 1.2rem;
                }
            }
        }
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
                .signLink {
                    width: 100%;
                    height: 2.875rem;
                    line-height: 2.875rem;
                    display: block;
                    border-top: 1px solid #dddddd;
                    color: #333333;
                    font-size: .75rem;
                    background: url(../assets/images/icon_arrow.png) no-repeat right center;
                    background-size: .5rem;
                }
                span {
                    font-size: .75rem;
                    display: inline-block;
                    background: url(../assets/images/icon_message.png) no-repeat 0.5rem 0.95rem;
                    background-size: 1.1rem;
                    padding-left: 1.9rem;
                    i {
                        font-style: inherit;
                    }
                }
            }
            li:first-child {
                border-top: 0px;
                span {
                    background: url(../assets/images/icon_clock.png) no-repeat 0.5rem 0.95rem;
                    background-size: 1.1rem;
                }
            }
            li:nth-child(3) {
                span {
                    background: url(../assets/images/icon_score.png) no-repeat 0.5rem 0.95rem;
                    background-size: 1.1rem;
                }
            }
            li:nth-child(4) {
                span {
                    background: url(../assets/images/icon_pen.png) no-repeat 0.5rem 0.95rem;
                    background-size: 1.1rem;
                }
            }
            li:nth-child(5) {
                span {
                    background: url(../assets/images/icon_play02.png) no-repeat 0.5rem 0.95rem;
                    background-size: 1.1rem;
                }
            }
        }
    }
}
</style>
