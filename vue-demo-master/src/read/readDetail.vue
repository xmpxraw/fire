<template>
    <div class="page-readDetail">
        <header-nav title="读书详情" :backshow="backshow" v-bind:class="[isNight ? 'night1' : 'light1']"></header-nav>
        <no-message tip="暂无数据" v-show="showNoMessage" v-bind:class="[isNight ? 'night' : 'light']"></no-message>
        <div id="mainDetail" class="detail-main" v-bind:class="[isNight ? 'night' : 'light']" v-show="!showNoMessage">
            <p class="readTitle"> {{successData.chapter}}</p>
            {{{successData.content}}}
        </div>
        <div class="readFoot" v-bind:class="[isNight ? 'night2' : 'light2']">
            <span class="last" v-show="hasPrevious" @click="goUpdate(successData.previous)">上一节</span>
            <span class="next" v-show="hasNext" @click="goUpdate(successData.next)">下一节</span>
        </div>
        <toast :show.sync="show2" type="text" :time="500">{{warntext}}</toast>
        <loading :show="loading"></loading>
        <div class="readStyle" @click="changeStyle()" v-bind:class="[isNight ? 'night3' : 'light3']"></div>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
import noMessage from '../common/noMessage.vue'
import store from '../store.js'
const commit = store.commit || store.dispatch;
export default {
    name: 'readDetail',
    store: store,
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
            successData: [],
            loading: false,
            backshow: true,
            warntext: "",
            isNight: false,
            showNoMessage: false,
            hasPrevious: true,
            hasNext: true,
            chapterId: this.$route.params.chapterId,
            code: window.localStorage.getItem("codeStatus"),
            jessonid: window.localStorage.getItem("JSESSIONID"),
            specialty: window.localStorage.getItem("specialty"),
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        goUpdate: function(id) {
            this.chapterId = id;
            this.renderData();
        },
        renderData: function() {
            this.loading = true;
            var x = document.body.scrollTop || document.getElementById("mainDetail").scrollTop;
            window.scrollTo(x, 0);
            // document.getElementById("mainDetail").scrollTo(0);
            apiService.getTextbookByDirectory({
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
                    } else {
                        this.showNoMessage = true;
                        this.hasPrevious = false;
                        this.hasNext = false;
                    }
                } else {
                    this.show2 = true;
                    this.warntext = res.data.msg;
                    this.hasPrevious = false;
                    this.hasNext = false;
                }

            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })

        },
        changeStyle: function() {
            this.isNight = !this.isNight;
            commit('UPDATE_ISNIGHT', this.isNight)
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
.page-readDetail {
    .night1 header {
        background: #262629;
    }
    .light {
        background: #fff !important;
    }
    .night {
        background: #343434 !important;
        color: #a7a589 !important;
        p,
        div,
        hr,
        span,
        h1,
        h2,
        h3,
        h4,
        h5,
        a {
            background: #343434 !important;
            color: #a7a589 !important;
            background-color: #343434 !important;
        }
        .noMessage {
            color: #a7a589 !important;
        }
    }
    .detail-main {
        width: 100%;
        height: auto;
        padding: .5rem;
        font-size: .85rem;
        margin-bottom: 2.3rem;
        overflow-x: hidden;
        .readTitle {
            width: 100%;
            font-weight: 600;
            line-height: 150%;
            margin-bottom: .5rem;
        }
    }
    .readStyle {
        width: 2.5rem;
        height: 2.5rem;
        border-radius: 50%;
        position: fixed;
        bottom: 2.8rem;
        right: .55rem;
    }
    .light3 {
        background: url(../assets/images/btn_moon.png) no-repeat right center;
        background-size: contain;
    }
    .night3 {
        background: url(../assets/images/btn_sun.png) no-repeat right center;
        background-size: contain;
    }
    .readFoot {
        width: 100%;
        height: 2.5rem;
        line-height: 2.5rem;
        position: fixed;
        left: 0;
        bottom: 0;
        font-size: 0;
        padding: 0 .5rem;
        span {
            display: inline-block;
            width: 50%;
            font-size: .8rem;
            color: #fff;
        }
        .last {
            background: url(../assets/images/icon_back.png) no-repeat left center;
            background-size: .575rem .95rem;
            padding-left: 1rem;
            position: absolute;
            top: 0;
            left: .5rem;
        }
        .next {
            background: url(../assets/images/icon_go.png) no-repeat right center;
            background-size: .575rem .95rem;
            padding-right: 1rem;
            text-align: right;
            position: absolute;
            top: 0;
            right: .5rem;
        }
    }
    .light2 {
        background: #438ac9;
    }
    .night2 {
        background: #262629;
    }
}
</style>
