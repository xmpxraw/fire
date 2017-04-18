<template>
    <div class="school-main">
        <div class="school-header">
            <div class="schoolDescribe">
                <div class="describe"><span>学校简介</span></div>
            </div>
        </div>
        <div class="school-contain">
            <div class="school-contain-item" v-html="html">
            </div>
        </div>
        <loading :show="loading"></loading>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import apiService from '../apiservice.js'
export default {
    name: 'schoolDetail',
    components: {
        Dialog,
        toast,
        loading
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            successData: [],
            html: "",
            loading: true,
            warntext: "",
            show2: false,
        }
    },
    methods: {
        renderData: function() {
            apiService.schoolDetail({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                this.successData = data.data;
                this.htmlspecialchars_decode(this.successData.content);
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        htmlspecialchars_decode: function(str) {
            str = str.replace(/&/g, '&');
            str = str.replace(/</g, '<');
            str = str.replace(/>/g, '>');
            str = str.replace(/"/g, "''");
            str = str.replace(/'/g, "'");
            this.html = str;
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

.school-main {
    background: #ebebeb;
    height: auto;
}

.school-header {
    width: 100%;
    padding: 0 .5rem;
    height: 2.95rem;
    background: #f6f5f5;
    -webkit-box-shadow: 0px 3px 5px #ccc;
    box-shadow: 0px 3px 5px #ccc;
    .schoolDescribe {
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
        background: url(../assets/images/icon_school.png) no-repeat .5rem .95rem;
        background-size: 1.1rem;
        padding-left: 1.9rem;
    }
}

.school-contain {
    width: 100%;
    height: auto;
    .school-contain-item {
        font-size: .75rem;
        padding: .5rem .5rem;
        line-height: 170%;
        text-indent: 2em;
    }
}
</style>
