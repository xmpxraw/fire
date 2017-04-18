<template>
    <div class="page-examView">
        <header-nav :title="title" :backshow="backshow"></header-nav>
        <div class="exam-main" v-for="item in list">
            <div class="exam-main-name">{{$index + 1}}.【{{item.questionType}}】{{{item.caption}}}</div>
            <img v-show="item.image != ''" class="examImg" :src="item.image">
            <ul>
                <li>
                    <div class="exam-main-item">
                        A.{{{item.optionA}}}
                        <label class="check {{item.classA}}" for="check1"></label>
                    </div>
                    <div class="exam-main-item">
                        B.{{{item.optionB}}}
                        <label class="check {{item.classB}}" for="check2"></label>
                    </div>
                    <div class="exam-main-item" v-if="item.type!='judeAnswer'">
                        C.{{{item.optionC}}}
                        <label class="check {{item.classC}}" for="check2"></label>
                    </div>
                    <div class="exam-main-item" v-if="item.type!='judeAnswer'">
                        D.{{{item.optionD}}}
                        <label class="check {{item.classD}}" for="check2"></label>
                    </div>
                </li>
            </ul>
            <div class="exam-answer">
                <p>正确答案：{{item.rightSelect}}</p>
                <p>你选择了：{{item.choiceSelect}}</p>
                <div class="answer-main">
                    答案解析：{{{item.analysis}}}
                </div>
            </div>
        </div>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Vue from 'vue'
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
export default {
    name: 'examView',
    components: {
        Dialog,
        loading,
        toast,
        headerNav
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            title: "查看试卷",
            show2: false,
            loading: false,
            backshow: true,
            warntext: "",
            live: "1",
            single: false,
            checkedNames: [],
            id: this.$route.params.id,
            isRight: this.$route.params.isRight,
            kk: [{}, {}],
            successData: [],
            list: [],
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {
            this.loading = true;
            let isRight = "";
            if (this.isRight == "false") {
                isRight = false;
                this.title = '只看错题';
            } else {
                isRight = "";
                this.title = '查看试卷';
            }
            apiService.examAnswerDetail({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                examinationId: this.id,
                isRight: isRight,
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.successData = res.data.data;
                    var data = res.data.data;
                    var temp = ["A", "B", "C", "D"];
                    Object.keys(data).forEach((key) => {
                            if (Array.isArray(data[key])) {
                                data[key].forEach((item, index) => {
                                    item.type = key;
                                    item.classA = "";
                                    item.classB = "";
                                    item.classC = "";
                                    item.classD = "";
                                    item.rightSelect = "";
                                    item.choiceSelect = "";
                                    for (var i = 0; i < 4; i++) {
                                        var optionName = "option" + temp[i];
                                        var className = "class" + temp[i];
                                        if (item[optionName] != "") { //判断该题目中是否有这个选项
                                            var selectList = item.optionChoice; //已选答案
                                            var rightList = item.rightAnswer; //正确答案
                                            if (selectList.indexOf(optionName) != -1) { //选择的答案
                                                item.choiceSelect = item.choiceSelect + temp[i];
                                            }
                                            if (rightList.indexOf(optionName) != -1) {
                                                item.rightSelect = item.rightSelect + temp[i]; //正确的答案
                                                // if(item.type == "multiAnswer"){//多选的处理
                                                if (selectList.indexOf(optionName) != -1) {
                                                    item[className] = "rightCheck";
                                                } else {
                                                    item[className] = "wrongCheck";
                                                }
                                                // }else{//单选的处理
                                                //   item[className] = "rightCheck";
                                                //   }
                                            } else {
                                                if (selectList.indexOf(optionName) != -1) {
                                                    item[className] = "errCheck";
                                                }
                                            }


                                        }
                                    }


                                })
                            }
                        })
                        // this.list = Array.of(...data.singleAnswer,...data.multiAnswer ,...data.judeAnswer);
                    this.list = Array.of(...data.judeAnswer, ...data.singleAnswer, ...data.multiAnswer);
                    // console.log(this.list,"this.list");
                    this.logJSON(this.list);
                } else {
                    this.show2 = true;
                    this.warntext = res.data.msg;
                }

            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
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
            console.log(innerLog(data), "-----");
        },

    },
}
</script>
<style lang="scss">
.page-examView {
    width: 100%;
    height: auto;
    background: #fff;
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
        padding: 0 .5rem;
        .exam-main-name {
            width: 100%;
            height: auto;
            padding: .75rem 0;
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
                    padding: .5rem;
                    border: 1px solid #ddd;
                    border-top: 0;
                    color: #666;
                    font-size: .85rem;
                    line-height: 120%;
                    padding-right: 2rem;
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
                    input[type="radio"] + label {
                        background: url(../assets/images/icon_choose.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    input[type="radio"]:checked + label {
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
                    .rightCheck {
                        background: url(../assets/images/icon_answer_right.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    .wrongCheck {
                        background: url(../assets/images/icon_answer_choose.png) center center no-repeat;
                        background-size: 1rem;
                    }
                    .errCheck {
                        background: url(../assets/images/icon_answer_wrong.png) center center no-repeat;
                        background-size: 1rem;
                    }
                }
                .exam-main-item:first-child {
                    border-top: 1px solid #ddd;
                }
            }
        }
        .exam-answer {
            width: 100%;
            height: auto;
            padding: .5rem 0;
            p {
                font-size: .75rem;
                color: #333;
                line-height: 1.2rem;
            }
            .answer-main {
                padding: .5rem 0;
                font-size: .75rem;
                color: #999;
                line-height: 140%;
                border-bottom: 1px solid #f2f2f2;
            }
        }
    }
}
</style>
