<template>
    <div class="page-read">
        <header-nav title="在线读书" :backshow="backshow"></header-nav>
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
            <li v-for="item in liData">
                <!-- <div class="li-item-head" @click="item.isActive=!item.isActive"> -->
                <div class="li-item-head" @click="openChild(item)">
                    <span v-bind:class="[item.isActive ? 'activeClass' : 'noActclass', 'li-item-span']">  {{item.name}}</span>
                </div>
                <div class="li-item-code" v-show="item.isActive">
                    <div class="li-item-item" v-for="j in item.children" @click.stop.prevent="goRead(j.id)">
                        <span class="spanfont">{{j.name}}</span>
                        <rater :value.sync="j.significance" slot="value" active-color="#e15147" disabled :font-size="20"></rater>
                    </div>
                </div>
            </li>
        </ul>
        <toast :show.sync="show2" type="text" :time="500">{{warntext}}</toast>
        <loading :show="loading"></loading>
    </div>
</template>
<script>
import Vue from 'vue'
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import rater from 'vux-components/rater'
import apiService from '../apiservice.js'
import headerNav from '../common/header.vue'
export default {
    name: 'readIndex',
    components: {
        Dialog,
        loading,
        toast,
        rater,
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
            // if (newval == '') {
            //     this.selectAllItem(newval);
            // } else {
                this.selectItem(newval);
            // }

        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        selectItem: function(id) {
            this.loading = true;
            let chapterId = id;
            apiService.getReadChapter({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                parentId: chapterId,
                specialty: window.localStorage.getItem("specialty"),
                dirType: "",
                tags: "在线读书目录",
                sysCode: window.localStorage.getItem("sysCode"),
                studentNo: window.localStorage.getItem("studentNo"),
            }).then((res) => {
                this.loading = false;
                if (res.data.code == 200) {
                    this.liData = res.data.data;
                    //添加isActive用来控制下拉
                    this.liData.forEach(function(item) {
                        Vue.set(item, 'isActive', false);
                    })
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
        
        renderData: function() {
            this.loading = true;
            apiService.getDirectory({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID"),
                // code:"yuantai",
                // JSESSIONID:"86C2999769D676A7F876AF404583693C",
                dirType: "",
                tags: "在线读书目录",
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
        openChild: function(item) {
            if (item.isActive) {
                item.isActive = !item.isActive;
            } else {
                item.isActive = true;
            }
        },
        goRead: function(chapterId) {
            this.$router.go({
                name: 'readDetail',
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
.page-read {
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
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    display: inline-block;
                }
                .noActclass {
                    background: url(../assets/images/icon_arrow_down.png) no-repeat right center;
                    background-size: .75rem;
                }
                .activeClass {
                    background: url(../assets/images/icon_arrow_up.png) no-repeat right center;
                    background-size: .75rem;
                }
            }
            .li-item-code {
                padding: 0 .5rem;
                background: #f5f5f5;
                position: relative;
                .li-item-item {
                    width: 100%;
                    padding-left: .5rem;
                    font-size: .8rem;
                    height: 2.8rem;
                    line-height: 2.8rem;
                    border-bottom: 1px solid #ddd;
                    color: #333;
                    position: relative;
                    .spanfont {
                        overflow: hidden;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                        display: inline-block;
                        width: 66%;
                    }
                    .vux-rater {
                        height: 100%;
                        line-height: 2.8rem;
                        position: absolute;
                        right: .5rem;
                    }
                }
                .li-item-item:last-child {
                    border: 0;
                }
            }
        }
    }
}
</style>
