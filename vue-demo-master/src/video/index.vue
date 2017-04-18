<template>
    <div class="page-video">
        <header-nav title="视频教学" :backshow="backshow"></header-nav>
        <div class="read-header">
            <div class="header-item">
                目录
                <select v-model="unit">
                    <option value="">全部</option>
                    <option v-for="i in unitData" value="{{i.id}}">{{i.name}}</option>
                </select>
            </div>
        </div>
        <ul class="read-main">
            <li v-for="item in liData" @click="goDetail(item.id)">
                <div class="li-item-head">
                    <span class="li-item-span">  {{item.name}}</span>
                </div>
            </li>
        </ul>
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
    name: 'videoIndex',
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
            rateNum: 3,
            isActive: false,
            unit: "",
            unitData: [],
            liData: [],
        }
    },
    watch: {
        unit(newval, oldval) {
            this.selectItem(newval);
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        renderData: function() {
            this.loading = true;
            apiService.getVideoDirectory({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                // code:"yuantai",
                // JSESSIONID:"86C2999769D676A7F876AF404583693C",
                dirType: "",
                tags: "教学视频目录",
                specialty: window.localStorage.getItem("specialty"),
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.unitData = res.data.data;
                    this.unit = this.unitData[0].id;
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
        selectItem: function(id) {
            this.loading = true;
            let chapterId = id;
            var dirType = '';
            if (id == '') {
                dirType = 'AllChapter';
            } else {
                dirType = '';
            }
            apiService.getVideoDirectory({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                tags: "教学视频目录",
                parentId: chapterId,
                specialty: window.localStorage.getItem("specialty"),
                dirType:dirType,
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.liData = res.data.data;
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
        goDetail: function(chapterId) {
            this.$router.go({
                name: 'videoDetail',
                params: {
                    chapterId: chapterId
                }
            });
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
.page-video {
    .read-header {
        width: 100%;
        height: 3.4rem;
        line-height: 3.4rem;
        padding: .9rem .5rem;
        .header-item {
            width: 100%;
            height: 1.75rem;
            line-height: 1.75rem;
            font-size: .85rem;
            color: #323232;
            select {
                width: 7.9rem;
                height: 100%;
                background: #fff;
                border-radius: .25rem;
                outline: none;
                color: #999;
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background: #fff url(../assets/images/icon_arrow_m.png) 96% no-repeat;
                background-size: .6rem .6rem;
                padding-left: .2rem;
            }
        }
    }
    .read-main {
        width: 100%;
        height: auto;
        li {
            width: 100%;
            height: auto;
            .li-item-head {
                width: 100%;
                background: #fff;
                height: 2.7rem;
                line-height: 2.7rem;
                padding: 0 .5rem;
                font-size: .9rem;
                color: #323232;
                .li-item-span {
                    display: inline-block;
                    width: 100%;
                    height: 100%;
                    background: url(../assets/images/icon_arrow.png) no-repeat right center;
                    background-size: .4rem;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    display: inline-block;
                }
            }
        }
    }
}
</style>
