<template>
    <div class="page-exam">
        <header-nav title="模拟考试" :backshow="backshow"></header-nav>
        <no-message tip="暂无试卷" v-show="hasExam"></no-message>
        <div class="gopast" @click="goLink()">考试记录</div>
        <div class="exam-main">
            <ul>
                <li v-for="item in successData" @click="goDetail(item.id)">
                    <span class="main-item">{{item.paperName}}</span>
                    <!-- <span class="main-item2" v-if="item.generationType == 'all'">{{item.paperName}}</span>
           <span class="main-item" v-if="item.generationType == 'chapter'">{{item.paperName}}</span> -->
                </li>
                <!-- <li>
           <span class="main-item2">随机模拟试题一</span>
         </li> -->
            </ul>
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
import noMessage from '../common/noMessage.vue'
export default {
    name: 'examIndex',
    components: {
        Dialog,
        loading,
        toast,
        headerNav,
        noMessage
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
            hasExam: false,
            successData: [],
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {
            this.loading = true;
            apiService.paperList({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                sysCode:window.localStorage.getItem("sysCode"),
                specialty:window.localStorage.getItem("specialty"),
                // sysCode: "94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
                // specialty: "建（构）筑物消防员（初级）",
            }).then((data) => {
                this.loading = false;
                if (data.data.code == 200) {
                    this.successData = data.data.data;
                    if (this.successData.length < 1) {
                        this.hasExam = true;
                    }
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
        goLink: function() {
            this.$router.go('/xTestrecord');
        },
        goDetail: function(tid) {
            this.$router.go({
                name: 'examDetail',
                params: {
                    id: tid
                }
            })
        },
    },
}
</script>
<style lang="scss">
.page-exam {
    width: 100%;
    height: auto;
    .gopast {
        width: 4.2rem;
        height: 2.3rem;
        line-height: 2.3rem;
        position: absolute;
        top: 0;
        right: 0;
        z-index: 100;
        font-size: .8rem;
        text-align: center;
        color: #fff;
    }
    .exam-main {
        width: 100%;
        height: auto;
        background: #fff;
        padding: 0 .5rem;
        ul {
            width: 100%;
            height: auto;
            li {
                list-style: none;
                width: 100%;
                height: 3.2rem;
                line-height: 3.2rem;
                font-size: .85rem;
                border-bottom: 1px solid #ddd;
                background: url("../assets/images/icon_arrow.png") right center no-repeat;
                background-size: .5rem;
                .main-item {
                    width: 100%;
                    height: 100%;
                    display: inline-block;
                    background: url("../assets/images/icon_exam.png") left center no-repeat;
                    background-size: 1.2rem;
                    padding-left: 1.65rem;
                }
                .main-item2 {
                    width: 100%;
                    height: 100%;
                    display: inline-block;
                    background: url("../assets/images/icon_random.png") left center no-repeat;
                    background-size: 1.2rem;
                    padding-left: 1.65rem;
                }
            }
            li:last-child {
                border: 0;
            }
        }
    }
}
</style>
