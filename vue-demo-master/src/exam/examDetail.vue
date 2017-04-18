<template>
    <div class="page-examDetail">
        <div class="page-header">
            <header><span v-show="backshow" class="back" @click="back()"></span>{{paperData.paperName}}
            </header>
        </div>
        <!-- <header-nav title="全真模拟试卷二" :backshow="backshow"></header-nav> -->
        <!-- 单选 -->
        <template v-if="list&&list[currentIndex].type">
            <div class="exam-main" v-if="list[currentIndex].type == 'singleQuestions'">
                <div class="exam-main-name">{{currentIndex + 1}}.【单选】{{{list[currentIndex].caption}}}</div>
                <img v-show="list[currentIndex].image != ''" class="examImg" :src="list[currentIndex].image">
                <ul>
                    <li>
                        <label class="exam-main-item" for="radioA" @click="doPassed()">
                            A.{{{list[currentIndex].optionA}}}
                            <input class="checkbox" type="radio" name="live" id="radioA" value="optionA" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioA"></label>
                        </label>
                        <label class="exam-main-item" for="radioB" @click="doPassed()">
                            B.{{{list[currentIndex].optionB}}}
                            <input class="checkbox" type="radio" name="live" id="radioB" value="optionB" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioB"></label>
                        </label>
                        <label class="exam-main-item" for="radioC" @click="doPassed()">
                            C.{{{list[currentIndex].optionC}}}
                            <input class="checkbox" type="radio" name="live" id="radioC" value="optionC" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioC"></label>
                        </label>
                        <label class="exam-main-item" for="radioD" @click="doPassed()">
                            D.{{{list[currentIndex].optionD}}}
                            <input class="checkbox" type="radio" name="live" id="radioD" value="optionD" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioD"></label>
                        </label>
                    </li>
                </ul>
            </div>
            <!-- 多选 -->
            <div class="exam-main" v-if="list[currentIndex].type == 'multiQuestions'">
                <div class="exam-main-name">{{currentIndex + 1}}.【多选】{{{list[currentIndex].caption}}}</div>
                <img v-show="list[currentIndex].image != ''" class="examImg" :src="list[currentIndex].image">
                <ul>
                    <li>
                        <label for="checkA" class="exam-main-item" @click="doPassed()">
                            A.{{{list[currentIndex].optionA}}}
                            <input class="checkbox" type="checkbox" id="checkA" value="optionA" v-model="list[currentIndex].answers">
                            <label class="check" for="checkA"></label>
                        </label>
                        <label for="checkB" class="exam-main-item" @click="doPassed()">
                            B.{{{list[currentIndex].optionB}}}
                            <input class="checkbox" type="checkbox" id="checkB" value="optionB" v-model="list[currentIndex].answers">
                            <label class="check" for="checkB"></label>
                        </label>
                        <label for="checkC" class="exam-main-item" @click="doPassed()">
                            C.{{{list[currentIndex].optionC}}}
                            <input class="checkbox" type="checkbox" id="checkC" value="optionC" v-model="list[currentIndex].answers">
                            <label class="check" for="checkC"></label>
                        </label>
                        <label for="checkD" class="exam-main-item" @click="doPassed()">
                            D.{{{list[currentIndex].optionD}}}
                            <input class="checkbox" type="checkbox" id="checkD" value="optionD" v-model="list[currentIndex].answers">
                            <label class="check" for="checkD"></label>
                        </label>
                    </li>
                </ul>
                <!--{{checkedNames | json}}-->
            </div>
            <!-- 判断 -->
            <div class="exam-main" v-if="list[currentIndex].type == 'judeQuestions'">
                <div class="exam-main-name">{{currentIndex + 1}}.【判断】{{{list[currentIndex].caption}}}</div>
                <img v-show="list[currentIndex].image != ''" class="examImg" :src="list[currentIndex].image">
                <ul>
                    <li>
                        <label class="exam-main-item" for="radioA" @click="doPassed()">
                            A.{{{list[currentIndex].optionA}}}
                            <input class="checkbox" type="radio" name="live" id="radioA" value="optionA" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioA"></label>
                        </label>
                        <label class="exam-main-item" for="radioB" @click="doPassed()">
                            B.{{{list[currentIndex].optionB}}}
                            <input class="checkbox" type="radio" name="live" id="radioB" value="optionB" v-model="list[currentIndex].answers">
                            <label class="radio" for="radioB"></label>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="login-next">
                <span @click="nextQuestion()" v-show="isLast">下一题</span>
            </div>
            <div class="videoFooter">
                <span class="left-img" @click="lastQuestion()"></span>
                <span class="footCount">{{currentIndex + 1}}/ {{list.length}}</span>
                <span class="right-img" @click="nextQuestion()"></span>
                <span class="menu" @click="showMenu=true"></span>
                <span class="timeCount">
        <span v-show="singleMNum">0</span>{{minute}}:<span v-show="singleNum">0</span>{{second}}
                </span>
                <span class="submit" @click="sureSubmit()">交卷</span>
            </div>
        </template>
        <div class="mask" v-show="showMenu">
            <div class="problemDetail">
                <div class="mask_top">
                    <p>
                        <span>已答{{alreadyAnswer}}</span>
                        <span>未答{{noAnswer}}</span>
                        <span @click="showMenu=false">取消</span>
                    </p>
                </div>
                <div class="mask_middle">
                    <ul class="mask_middle_ul">
                        <!--  -->
                        <li v-for="item in list" :class="{passActive:item.passed}" @click="jumpTo(item,$index,$event)">{{$index+1}}</li>
                    </ul>
                </div>
                <div class="mask_foot">
                    <span>跳转到</span>
                    <input type="number" v-model="jumpIndex">
                    <span class="sure" @click="jumpToo()">确定</span>
                </div>
            </div>
        </div>
        <dialog :show.sync="showNoScroll" class="dialog-demo" :scroll="false">
            <p class="dialog-title">温馨提示</p>
            <div class="img-box">
                <span>{{dialogWarn}}</span>
            </div>
            <div class="j-operate">
                <span v-show="!timeEnd || giveUp" class="sure-submit" @click="sureDo()">确定</span>
                <span v-show="!timeEnd || giveUp" class="sure-cancle" @click="cancleDo()">取消</span>
            </div>
        </dialog>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Vue from 'vue'
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import clocker from 'vux-components/clocker'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
export default {
    name: 'examDetail',
    components: {
        Dialog,
        loading,
        toast,
        headerNav,
        clocker
    },
    ready() {
        this.renderData();
        this.secondDelay();
    },
    data() {
        return {
            id: this.$route.params.id,
            dialogWarn: "",
            show2: false,
            loading: false,
            backshow: true,
            warntext: "",
            showMenu: false,
            single: false,
            showNoScroll: false,
            second: 60, //秒钟
            minute: 90, //分钟
            singleMNum: false, //时间分钟各位数为0
            singleNum: false, //时间秒钟各位数为0
            sureBack: false, //点击返回按钮确定返回
            timeEnd: false, //倒计时结束
            successData: null,
            alreadyAnswer: 0, //已答
            noAnswer: 100, //未答
            list: null,
            currentIndex: 0, //单选index
            singleCurrent: [], //当前单选选项
            jumpIndex: 1,
            passed: {},
            paperData: [], //试卷详情
            isLast: true,
            giveUp: false,
        }
    },
    destroyed() {
        // window.removeEventListener('onbeforeunload',function(){},false)
        window.onbeforeunload = function() {};
    },
    watch: {
        currentIndex(newval, oldval) {
            if (newval + 1 < this.list.length) {
                this.isLast = true;
            } else {
                this.isLast = false;
            }
        }
    },
    methods: {
        jumpTo: function(item, index, event) {
            this.currentIndex = index;
            this.showMenu = false;
        },
        jumpToo: function() {
            let index = this.jumpIndex - 1;
            let re = /^\d+$/g;
            if (index >= 0 && index < this.list.length && re.test(index)) {
                this.currentIndex = index;
                this.showMenu = false;
            } else {
                this.show2 = true;
                this.warntext = "输入的数字不正确";
            }
        },
        cancle: function() {
            wx.closeWindow();
        },
        cancleDo: function() {
            this.showNoScroll = false;
            this.sureBack = false;
            this.giveUp = false;
        },
        doPassed: function() {
            setTimeout(() => {
                if (this.list[this.currentIndex].type == "singleQuestions" || this.list[this.currentIndex].type == "judeQuestions") {
                    this.passed[this.currentIndex] = true;
                    this.alreadyAnswer = Object.keys(this.passed).length;
                    this.noAnswer = this.list.length - this.alreadyAnswer;
                    this.list[this.currentIndex].passed = true;
                } else {
                    if (this.list[this.currentIndex].answers.length > 0) {
                        this.passed[this.currentIndex] = true;
                        this.list[this.currentIndex].passed = true;
                    } else {
                        this.passed[this.currentIndex] = false;
                        this.list[this.currentIndex].passed = false;
                        delete this.passed[this.currentIndex]
                    }
                    this.alreadyAnswer = Object.keys(this.passed).length;
                    this.noAnswer = this.list.length - this.alreadyAnswer;
                }
            }, 200)
        },
        back: function() {
            window.history.back();
            // this.showNoScroll = true;
            // this.sureBack = true;
            // this.giveUp = true;
            // this.dialogWarn = "确定放弃考试吗?";
        },
        nextQuestion: function() {
            if (this.currentIndex < this.list.length - 1) {
                this.currentIndex = this.currentIndex + 1;
            }
        },
        lastQuestion: function() {
            if (this.currentIndex > 0) {
                this.currentIndex = this.currentIndex - 1;
            }
        },
        sureDo: function() {
            if (this.sureBack) { //返回
                window.history.back();
            } else { //交卷
                this.submitPaper();
            }
        },
        submitPaper: function() {
            var useTime = (this.paperData.duration - this.minute - 1) * 60 + (60 - this.second);
            var submitList = [];
            this.list.forEach((item, index) => {
                if (item.type == "multiQuestions") {
                    submitList.push({
                        questionId: item.id,
                        choiceAnswer: item.answers.join(","),
                    })
                } else {
                    submitList.push({
                        questionId: item.id,
                        choiceAnswer: item.answers,
                    })
                }
            })

            console.log(submitList, "----submitList---");
            this.loading = true;
            apiService.handInPaper({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                paperId: this.paperData.id,
                sysCode: this.paperData.sysCode,
                useTime: useTime,
                answers: submitList,
                // studentNo:"170405002",
                // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
                studentNo: window.localStorage.getItem("studentNo"),
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.show2 = true;
                    this.warntext = "交卷成功！";
                    setTimeout(() => {
                        this.$router.go({
                            name: 'examResult',
                            params: {
                                id: res.data.data.id
                            }
                        });
                    }, 1000)
                } else {
                    this.show2 = true;
                    this.warntext = "交卷失败，请稍后再试！";
                    this.timeEnd = false;
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
                this.timeEnd = false;
            })
        },
        sureSubmit: function() {
            this.showNoScroll = true;
            if (this.noAnswer > 0) {
                this.dialogWarn = "题目未答完，确定交卷么？"
            } else {
                this.dialogWarn = "确定交卷么？";
            }
        },
        secondDelay: function() {
            if (this.second < 11) {
                this.singleNum = true;
            } else {
                this.singleNum = false;
            }
            if (this.second > 1) {
                this.second = this.second - 1;
                setTimeout(() => {
                    this.secondDelay();
                }, 1000)
            } else {
                this.second = 60;
                this.minuteDelay();
            }
        },
        minuteDelay: function() {
            if (this.minute < 11) {
                this.singleMNum = true;
            } else {
                this.singleMNum = false;
            }
            if (this.minute > 0) {
                this.minute = this.minute - 1;
                this.secondDelay();
            } else if (this.minute = 0) {
                this.secondDelay();
            } else {
                // 倒计时完成 do something
                this.second = 0;
                this.timeEnd = true;
                this.showNoScroll = true;
                this.dialogWarn = "时间已到,系统正在自动交卷...";
                this.submitPaper();
                setTimeout(() => {
                    this.showNoScroll = false;
                }, 2000)
            }
        },
        renderData: function() {
            this.loading = true;
            apiService.paperDetail({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                id: this.id,
                sysCode: window.localStorage.getItem("sysCode"),
                // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
            }).then((data) => {
                this.loading = false;
                if (data.data.code == 200) {
                    this.successData = data.data.data;
                    console.log(this.successData, "---");
                    this.paperData = this.successData.paper;
                    this.minute = parseInt(this.paperData.duration) - 1;
                    this.currentIndex = 0;
                    var data = data.data.data;
                    // var imgApi = 'http://yuantai.yt-hr.com';
                    var imgApi = window.location.origin;
                    Object.keys(data).forEach((key) => {
                        if (Array.isArray(data[key])) {
                            data[key].forEach((item, index) => {
                                item.type = key;
                                if (item.image != '') {
                                    item.image = imgApi + item.image;
                                }
                                Vue.set(item, 'passed', false);
                                if (key == "multiQuestions") {
                                    Vue.set(item, 'answers', [])
                                } else {
                                    Vue.set(item, 'answers', "")
                                }
                            })
                        }
                    })
                    this.list = Array.of(...data.judeQuestions, ...data.singleQuestions, ...data.multiQuestions);
                    this.noAnswer = this.list.length;
                    // console.log(this.list)
                } else {
                    this.show2 = true;
                    this.warntext = data.data.msg;
                }

            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },

        // 输出专用

        logJSON: function(data) {
            function innerLog(data) {
                var temp = {};
                for (var p in data) {
                    //console.log(p + "    " + data[p] + "    " + typeof data[p]);
                    if (typeof data[p] == "object") temp[p] = innerLog(data[p]);
                    else temp[p] = data[p];
                }
                return temp;
            }
            // console.log(innerLog(data));
        },

    },

}
</script>
<style lang="scss">
.page-examDetail {
    width: 100%;
    height: auto;
    color: #333;
    padding-bottom: 2.8rem;
    .examImg {
        width: 80%;
        margin: 0 auto;
        display: block;
        margin-bottom: 1rem;
    }
    .exam-main {
        width: 100%;
        height: auto;
        background: #fff;
        padding: 0 .5rem 1rem;
        .exam-main-name {
            width: 100%;
            height: auto;
            padding: 1rem 0;
            font-size: .85rem;
            color: #333;
            line-height: 140%;
        }
        ul {
            width: 100%;
            height: auto;
            li {
                list-style: none;
                width: 100%;
                height: auto;
                .exam-main-item {
                    position: relative;
                    width: 100%;
                    height: auto;
                    padding: .8rem .5rem;
                    border: 1px solid #ddd;
                    border-top: 0;
                    color: #666;
                    font-size: .85rem;
                    line-height: 120%;
                    padding-right: 2rem;
                    display: block;
                    .radio {
                        width: 2.8rem;
                        height: 100%;
                        position: absolute;
                        right: 0;
                        background: url(../assets/images/icon_choose.png) center center no-repeat;
                        background-size: 1rem;
                        top: 0;
                    }
                    input {
                        display: none;
                    }
                    input[type="radio"] + .radio {
                        background: url(../assets/images/icon_choose.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    input[type="radio"]:checked + .radio {
                        background: url(../assets/images/icon_choose_on.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    .check {
                        width: 2.8rem;
                        height: 100%;
                        position: absolute;
                        right: 0;
                        background: url(../assets/images/bg_choose.png) center center no-repeat;
                        background-size: 1rem;
                        top: 0;
                    }
                    input[type="checkbox"] + .check {
                        background: url(../assets/images/bg_choose.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    input[type="checkbox"]:checked + .check {
                        background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                        background-size: 1rem;
                    }
                }
                .exam-main-item:first-child {
                    border-top: 1px solid #ddd;
                }
            }
        }
    }
    .videoFooter {
        width: 100%;
        height: 2.8rem;
        line-height: 2.8rem;
        background: #438ac9;
        position: fixed;
        left: 0;
        bottom: 0;
        .left-img {
            display: inline-block;
            width: 1.9rem;
            height: 100%;
            background: url("../assets/images/btn_left.png") center center no-repeat;
            background-size: .75rem;
        }
        .right-img {
            display: inline-block;
            width: 1.9rem;
            height: 100%;
            background: url("../assets/images/btn_right.png") center center no-repeat;
            background-size: .75rem;
        }
        .footCount {
            display: inline-block;
            width: 3.5rem;
            height: 100%;
            font-size: .75rem;
            text-align: center;
            vertical-align: top;
            color: #fff;
        }
        .menu {
            display: inline-block;
            width: 1.6rem;
            height: 100%;
            background: url("../assets/images/btn_menu.png") center center no-repeat;
            background-size: 1.2rem;
        }
        .timeCount {
            display: inline-block;
            width: 3.5rem;
            height: 100%;
            font-size: .75rem;
            text-align: center;
            vertical-align: top;
            color: #fff;
        }
        .submit {
            display: inline-block;
            height: 100%;
            color: #fff;
            width: 4.25rem;
            text-align: center;
            float: right;
            font-size: .75rem;
            background: #e15147;
        }
    }
    .mask {
        position: fixed;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, .5);
        font-size: .8rem;
        .problemDetail {
            padding: .5rem;
            width: 100%;
            background: #fff;
            height: 20rem;
            position: absolute;
            left: 0;
            bottom: 0;
            .mask_top {
                width: 100%;
                height: 2rem;
                line-height: 2rem;
                span {
                    margin-left: .8rem;
                }
                span:last-child {
                    float: right;
                    color: #4388c9;
                    margin: 0;
                }
            }
            .mask_middle {
                .mask_middle_ul {
                    overflow: auto;
                    height: 14.5rem;
                    border-top: 1px #ddd solid;
                    border-bottom: 1px #ddd solid;
                    li {
                        list-style: none;
                        float: left;
                        width: 2rem;
                        height: 2rem;
                        line-height: 2rem;
                        text-align: center;
                        color: #fff;
                        border-radius: .3rem;
                        background: #ccc;
                        margin-left: .8rem;
                        margin-top: .75rem;
                    }
                    li.passActive {
                        background: #80c26a;
                    }
                    li.notpassActive {
                        background: #e15148;
                    }
                }
            }
            .mask_foot {
                height: 3rem;
                line-height: 3rem;
                font-size: .8rem;
                span: first-child {}
                input {
                    width: 10rem;
                    height: 2rem;
                    border: 1px solid #ddd;
                    border-radius: .3rem;
                    font-size: .8rem;
                    padding-left: .5rem;
                    outline: none;
                }
                .sure {
                    width: 3.75rem;
                    height: 2rem;
                    line-height: 2rem;
                    text-align: center;
                    border: 1px solid #ddd;
                    display: inline-block;
                    border-radius: .3rem;
                    margin-left: .5rem;
                }
            }
        }
    }
}
</style>
