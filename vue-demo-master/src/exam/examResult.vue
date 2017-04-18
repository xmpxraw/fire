<template>
    <div class="page-result">
        <header-nav title="考试结果" :backshow="backshow"></header-nav>
        <div class="result-main">
            <template v-if="successData.judeResult || successData.singleResult || successData.multiResult">
                <div class="result-head">{{successData.examinationPaperName}}</div>
                <div class="result-head">您本次考试得分：</div>
                <div class="result-score">{{successData.score}}</div>
                <p class="result-time"><span class="result-time-total">耗时:{{successData.useTime}}</span></p>
                <p class="result-time"><span class="result-time-end">交卷时间:{{successData.createTime}}</span></p>
                <ul>
                    <li>
                        <span class="result-item-name">判断题</span>
                        <span class="result-item-code">答对{{successData.judeResult.rightCount}}个，得{{successData.judeResult.score}}分</span>
                    </li>
                    <li>
                        <span class="result-item-name">单选题</span>
                        <span class="result-item-code">答对{{successData.singleResult.rightCount}}个，得{{successData.singleResult.score}}分</span>
                    </li>
                    <li>
                        <span class="result-item-name">多选题</span>
                        <span class="result-item-code">答对{{successData.multiResult.rightCount}}个，得{{successData.multiResult.score}}分</span>
                    </li>
                </ul>
            </template>
        </div>
        <div class="result-foot">
            <div class="result-foot-item">
                <span class="dataView" @click="goExamView(true)">查看试卷</span>
            </div>
            <div class="result-foot-item">
                <span class="errorView" @click="goExamView(false)">只看错题</span>
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
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
export default {
    name: 'examResult',
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
            show2: false,
            loading: false,
            backshow: true,
            warntext: "",
            id: this.$route.params.id,
            successData: [],
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        goExamView: function(val) {
            this.$router.go({
                name: 'examView',
                params: {
                    id: this.successData.id,
                    isRight: val
                }
            });
        },
        renderData: function() {
            this.loading = true;
            apiService.examResultDetail({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                examinationId: this.id,
                sysCode: window.localStorage.getItem("sysCode"),
                // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.successData = res.data.data;
                    this.successData.useTime = this.formatSeconds(this.successData.useTime);
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
        formatSeconds: function(value) {
            var theTime = parseInt(value); // 秒
            var theTime1 = 0; // 分
            var theTime2 = 0; // 小时
            if (theTime > 60) {
                theTime1 = parseInt(theTime / 60);
                theTime = parseInt(theTime % 60);
                if (theTime1 > 60) {
                    theTime2 = parseInt(theTime1 / 60);
                    theTime1 = parseInt(theTime1 % 60);
                }
            }
            var result = "" + parseInt(theTime) + "秒";
            if (theTime1 > 0) {
                result = "" + parseInt(theTime1) + "分" + result;
            }
            if (theTime2 > 0) {
                result = "" + parseInt(theTime2) + "小时" + result;
            }
            return result;
        },

    },
}
</script>
<style lang="scss">
.page-result {
    .result-main {
        width: 100%;
        height: auto;
        padding: .5rem;
        background: #fff;
        .result-head {
            width: 100%;
            height: 2rem;
            line-height: 2rem;
            font-size: .9rem;
            color: #333;
        }
        .result-score {
            width: 4.1rem;
            line-height: 2.95rem;
            height: 2.95rem;
            border-radius: .25rem;
            background: #53b7ee;
            color: #fff;
            text-align: center;
            font-size: 1.95rem;
            margin: .6rem auto;
        }
        .result-time {
            height: 1.75rem;
            line-height: 1.75rem;
            color: #666;
            font-size: .85rem;
            text-align: center;
            .result-time-total {
                height: 100%;
                display: inline-block;
                padding-left: 1.1rem;
                background: url(../assets/images/icon_time01.png) left center no-repeat;
                background-size: 1rem;
            }
            .result-time-end {
                height: 100%;
                display: inline-block;
                padding-left: 1.1rem;
                background: url(../assets/images/icon_time02.png) left center no-repeat;
                background-size: 1rem;
            }
        }
        ul {
            width: 78.7%;
            height: auto;
            border: 1px solid #ccc;
            border-radius: .25rem;
            margin: .9rem auto;
            li {
                list-style: none;
                height: 1.9rem;
                line-height: 1.9rem;
                font-size: .75rem;
                text-align: center;
                border-bottom: 1px solid #ccc;
                .result-item-name {
                    width: 23%;
                    display: inline-block;
                    height: 100%;
                    border-right: 1px solid #ccc;
                }
                .result-item-code {
                    width: 67%;
                    display: inline-block;
                    height: 100%;
                }
            }
            li:last-child {
                border: 0;
            }
        }
    }
    .result-foot {
        width: 100%;
        height: auto;
        padding: .75rem 0;
        margin-top: .35rem;
        background: #fff;
        font-size: 0;
        .result-foot-item {
            display: inline-block;
            width: 50%;
            font-size: .85rem;
            text-align: center;
            height: 2rem;
            line-height: 2rem;
            .dataView {
                display: inline-block;
                height: 100%;
                padding-left: 1.55rem;
                background: url(../assets/images/icon_book.png) left center no-repeat;
                background-size: 1.1rem;
            }
            .errorView {
                display: inline-block;
                height: 100%;
                padding-left: 1.45rem;
                background: url(../assets/images/icon_wrong.png) left center no-repeat;
                background-size: 1rem;
            }
        }
        .result-foot-item:first-child {
            border-right: 1px solid #ccc;
        }
    }
}
</style>
