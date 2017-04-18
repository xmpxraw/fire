<template>
    <div class="success-main">
        <header-nav title="报班成功" :backshow="backshow"></header-nav>
        <!-- <div class="success-header">
      <div class="successDescribe">
         <div class="describe"><span>温馨提示</span></div>
      </div>
   </div> -->
        <div class="success-contain">
            <div class="tip-success">您已成功报班!</div>
            <div class="success-detail">
                <div class="massage-sign">报班消息</div>
                <ul>
                    <li>
                        <p>学校：{{successData.schoolName}}</p>
                        <p>班级：{{successData.classCode}}</p>
                    </li>
                    <li>
                        <p>班级类型：{{successData.classTypeName}}</p>
                        <p>培训时间：{{successData.beginTime}}</p>
                        <p>学习时间：{{successData.duration}}天</p>
                    </li>
                    <li>
                        <p>招生人数：{{successData.planCount}}人</p>
                        <p>剩余名额：{{successData.laset}}人</p>
                    </li>
                </ul>
            </div>
            <div class="tip-item">提示:请于当日上午9:00整,准时到学校报到!</div>
            <div class="j-cancle" @click='back()'>回到个人中心</div>
        </div>
        <toast :show.sync="show2" type="success" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
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
    name: 'successDetail',
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
            title: '班级',
            showNoScroll: false,
            showitem: true,
            show2: false,
            successData: [],
            loading: true,
            backshow: true,
            warntext: "",
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {
            window.sessionStorage.setItem("isSuccess", 1);
            apiService.signEd({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                if (data.data.classType == 1) {
                    data.data.classTypeName = "学习班";
                } else {
                    data.data.classTypeName = "突击班";
                }
                this.successData = data.data;
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        back: function() {
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
div,
ul,
li,
p,
span,
a {
    padding: 0;
    margin: 0 auto;
}

.success-main {
    background: #ebebeb;
    height: auto;
    padding-bottom: 2.3rem;
}

.success-header {
    width: 100%;
    padding: 0 .5rem;
    height: 2.95rem;
    background: #f6f5f5;
    -webkit-box-shadow: 0px 3px 5px #ccc;
    box-shadow: 0px 3px 5px #ccc;
    .successDescribe {
        width: 100%;
        height: 100%;
        line-height: 2.95rem;
        background: url(../assets/images/line.png) repeat-x center;
    }
    .describe {
        width: 6rem;
        height: 100%;
        line-height: 2.95rem;
        background: #f6f5f5;
        font-size: .9rem;
        color: #438ac9;
        font-weight: bold;
    }
    .describe span {
        display: inline-block;
        width: 100%;
        height: 2.95rem;
        line-height: 2.95rem;
        background: url(../assets/images/icon_face.png) no-repeat .5rem .85rem;
        background-size: 1.1rem;
        padding-left: 1.9rem;
    }
}

.success-contain {
    width: 100%;
    height: auto;
    padding: 0 .5rem;
    .tip-success {
        width: 100%;
        height: 3.6rem;
        line-height: 3.6rem;
        font-size: .9rem;
        font-weight: bold;
        color: #438ccc;
    }
    .success-detail {
        width: 100%;
        height: auto;
        background: #ffffff;
        .massage-sign {
            width: 100%;
            height: 2.25rem;
            line-height: 2.25rem;
            background: #8bc1f2;
            color: #fff;
            text-align: center;
        }
        ul {
            width: 100%;
            height: auto;
            li {
                width: 100%;
                height: auto;
                padding: .7rem .75rem;
                border-top: 1px solid #dddddd;
                font-size: .75rem;
                p {
                    width: 100%;
                    height: 1.35rem;
                    line-height: 1.35rem;
                }
            }
            li:first-child {
                border-top: 0px;
            }
        }
    }
    .tip-item {
        width: 100%;
        height: 2.4rem;
        line-height: 2.4rem;
        font-size: .75rem;
        color: #666;
        padding-left: .2rem;
    }
    .j-cancle {
        width: 100%;
        height: 2.5rem;
        line-height: 2.5rem;
        text-align: center;
        color: #fff;
        font-size: .9rem;
        background: #4389ca;
        border-radius: .25rem;
        margin: .7rem 0;
    }
}
</style>
