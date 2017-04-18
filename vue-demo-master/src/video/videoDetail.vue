<template>
    <div class="page-video">
        <header-nav title="视频教学" :backshow="backshow"></header-nav>
        <div v-show="!showNoMessage" class="goPratice" @click="goxPractice(successData.directory)">去做练习</div>
        <no-message tip="暂无数据" v-if="showNoMessage"></no-message>
        <template v-if="!showNoMessage">
            <video id="video" :src="videoUrl" controls="controls" style="width: 100%;height: 9.4rem;z-index: 0;" poster="../assets/images/banner.png"></video>
            <div class="video-main" id="mainDetail">
                <div class="video-item-title">{{successData.chapter}}</div>
                <div class="video-item-name">本视频知识点提纲</div>
                <ul>
                    <li>{{{successData.videoDesc}}}</li>
                    <!-- <li>2.知识点提纲</li>
         <li>3.知识点提纲</li> -->
                </ul>
            </div>
        </template>
        <div class="videoFooter">
            <span class="left-img" v-show="hasPrevious" @click="goUpdate(successData.previous)"></span>
            <!-- <span class="footCount">章节</span> -->
            <span class="downLoad" v-if="!showNoMessage && noIphone" @click="goDownLoad()">下载到本地</span>
            <span class="right-img" v-show="hasNext" @click="goUpdate(successData.next)"></span>
            <!-- <span class="downLoad" v-if="!showNoMessage"><a :href="videoUrl" download="{{successData.videoName}}">下载到本地</a></span> -->
        </div>
        <popup :show.sync="showNoScroll">
            <p class="dialog-title">温馨提示</p>
            <div class="img-box">
                <div class="successNote">
                    <p>请手动复制以下链接到浏览器上打开即可下载,或者点击确定后点击微信右上角从浏览器打开</p>
                    <textarea id="textarea" readonly="readonly">{{videoUrl}}</textarea>
                </div>
            </div>
            <div class="j-operate">
                <span class="sure-submit" @click="goCopy()">确定</span>
                <span class="sure-submit" @click="showNoScroll=false">取消</span>
            </div>
        </popup>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import popup from 'vux-components/popup'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
import noMessage from '../common/noMessage.vue'
export default {
    name: 'videoIndex',
    components: {
        loading,
        toast,
        headerNav,
        noMessage,
        popup
    },
    ready() {
        this.renderData();
        let video = document.getElementById("video");
        let time = 0;
        let self = this;
        video.addEventListener('timeupdate', function() {
            // debugger
            self.currentTime = video.currentTime;
        })
        this.judgePhone();
    },
    data() {
        return {
            show2: false,
            loading: false,
            backshow: true,
            warntext: "",
            videoUrl: "",
            successData: [],
            showNoMessage: false,
            currentTime: 0,
            showNoScroll: false,
            hasPrevious: true,
            hasNext: true,
            chapterId: this.$route.params.chapterId,
            code: window.localStorage.getItem("codeStatus"),
            jessonid: window.localStorage.getItem("JSESSIONID"),
            specialty: window.localStorage.getItem("specialty"),
            noIphone: true,
        }
    },
    destroyed() {
        this.saveVideo();
    },
    methods: {
        judgePhone: function() {
            //判断当前设备是苹果还是安卓
            var ua = navigator.userAgent.toLowerCase();
            if (/iphone|ipad|ipod/.test(ua)) {
                // 当前设备是苹果 
                this.noIphone = false;

            } else if (/android/.test(ua)) {
                // 当前设备是安卓
                this.noIphone = true;
            };
        },
        goxPractice: function(id) {
            this.$router.go({
                name: 'xPracticedel',
                params: {
                    chapterId: id
                }
            });
        },
        cancle: function() {
            wx.closeWindow();
        },
        goUpdate: function(id) {
            this.chapterId = id;
            this.saveVideo();
            this.renderData();
        },
        goDownLoad: function() {
            this.showNoScroll = true;
        },
        goCopy: function() {
            this.showNoScroll = false;
            window.location.href = this.videoUrl;
        },
        saveVideo: function() {
            if (!this.showNoMessage) {
                // alert(this.currentTime);
                apiService.getVideoSave({
                    code: this.code,
                    JSESSIONID: this.jessonid,
                    chapterId: this.chapterId,
                    specialty: this.specialty,
                    chapter: this.successData.chapter,
                    directory: this.successData.directory,
                    videoName: this.successData.videoName,
                    lenght: this.currentTime,
                    videoId: this.successData.id,
                    // sysCode:this.successData.sysCode,
                    studentName: window.localStorage.getItem("studentName"),
                    studentNo: window.localStorage.getItem("studentNo"),
                    sysCode: window.localStorage.getItem("sysCode"),
                    // studentNo:"170401009",
                    // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
                    // studentName:"小玉",
                }).then((res) => {
                    if (res.data.code == 200) {

                    }

                }, (err) => {

                })
            }
        },
        renderData: function() {
            this.loading = true;
            var x = document.body.scrollTop || document.getElementById("mainDetail").scrollTop;
            window.scrollTo(x, 0);
            apiService.getVideoByDirectory({
                code: this.code,
                JSESSIONID: this.jessonid,
                chapterId: this.chapterId,
                specialty: this.specialty,
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    if (res.data.data.length > 0) {
                        this.showNoMessage = false;
                        this.successData = res.data.data[0];
                        this.videoUrl = this.successData.url;
                        if (!this.successData.previous) {
                            this.hasPrevious = false;
                        } else {
                            this.hasPrevious = true;
                        }
                        if (!this.successData.next) {
                            this.hasNext = false;
                        } else {
                            this.hasNext = true;
                        }
                        // this.videoUrl = "http://ok6eiwhi9.bkt.clouddn.com/问世间.mp4"
                    } else {
                        this.showNoMessage = true;
                        this.hasPrevious = false;
                        this.hasNext = false;
                    }
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

    },
}
</script>
<style lang="scss">
.page-video {
    width: 100%;
    height: auto;
    color: #333;
    .goPratice {
        width: 4rem;
        height: 2.3rem;
        line-height: 2.3rem;
        position: absolute;
        top: 0;
        right: 0;
        z-index: 100;
        color: #fff;
        font-size: .8rem;
        text-align: center;
    }
    .weui_mask {
        z-index: 99999;
    }
    .weui_dialog {
        z-index: 100000;
        .img-box p {
            width: 90%;
            font-size: .75rem;
            height: auto;
            line-height: 150%;
        }
    }
    .dialog-title {
        width: 100%;
        text-align: center;
        font-size: .95rem;
        font-weight: bold;
        height: 3rem;
        line-height: 3rem;
        position: relative;
    }
    .successNote {
        p {
            width: 90%;
            font-size: .75rem;
            height: auto;
            line-height: 150%;
        }
    }
    #textarea {
        width: 85%;
        height: auto;
        outline: none;
        font-size: .75rem;
        line-height: 150%;
        border: 0;
        background: none;
        resize: none;
        margin: 0 auto;
        display: block;
    }
    .j-operate {
        text-align: center;
    }
    .video-main {
        background: #fff;
        padding: .5rem;
        margin-bottom: 3rem;
        .video-item-title {
            font-size: .9rem;
            width: 100%;
            height: auto;
            line-height: 150%;
        }
        .video-item-name {
            font-size: .8rem;
            height: 2.05rem;
            line-height: 2.05rem;
        }
        ul {
            width: 100%;
            height: auto;
            li {
                list-style: none;
                width: 100%;
                height: auto;
                line-height: 1.65rem;
                font-size: .75rem;
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
            position: absolute;
            left: 0;
            z-index: 10;
            top: 0;
        }
        .right-img {
            display: inline-block;
            width: 1.9rem;
            height: 100%;
            background: url("../assets/images/btn_right.png") center center no-repeat;
            background-size: .75rem;
            position: absolute;
            right: 0;
            z-index: 10;
            top: 0;
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
        .downLoad {
            display: inline-block;
            height: 100%;
            color: #fff;
            width: 100%;
            text-align: center;
            font-size: .75rem;
            vertical-align: top;
            a {
                color: #fff;
            }
        }
    }
}
</style>
