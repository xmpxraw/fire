<template>
    <div id="app" v-bind:class="[isNight ? 'yes' : 'no']">
        <router-view class="view">
        </router-view>
    </div>
</template>
<script>
import store from './store.js'
import apiService from './apiservice.js'
export default {
    name: 'app',
    components: {

    },
    store: store,
    vuex: {
        getters: {
            isNight: (state) => state.isNight,
        }
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            // night:false,
        }
    },
    watch: {
        isNight(newValue, oldValue) {

        }
    },
    methods: {
        renderData: function() {
            //删掉
            window.localStorage.setItem("sysCode", "94a1533f-85a2-4c6a-8850-74a39b2b5ba9");
            window.localStorage.setItem("specialty", "建（构）筑物消防员（初级）");
            window.localStorage.setItem("studentNo", "170401009");
            window.localStorage.setItem("code", "yuantai");
            window.localStorage.setItem("JSESSIONID", "A4EDCCEB52A6F34EB1F68DA74325A0DE");
            //end
            let menu = this.getParams("menu");
            let code = this.getParams("code");
            let JSESSIONID = this.getParams("JSESSIONID");
            JSESSIONID = JSESSIONID.substring(0, JSESSIONID.indexOf("#"));
            window.localStorage.setItem("menuStatus", menu);
            window.localStorage.setItem("codeStatus", code);
            window.localStorage.setItem("JSESSIONID", JSESSIONID);
            if (menu == 'readIndex' || menu == 'videoIndex' || menu == 'examIndex' || menu == 'xPractice') { //判断是否付费了
                apiService.WXIndex({
                    code: code,
                    JSESSIONID: JSESSIONID
                }).then((data) => {
                    if (data.data.result == 0) {
                        this.$router.go('/login');
                        return
                    }
                    if (data.data.student) {
                        window.localStorage.setItem("studentNo", data.data.student.id);
                        window.localStorage.setItem("studentName", data.data.student.studnetName);
                        window.localStorage.setItem("sysCode", data.data.student.sysCode);
                        window.localStorage.setItem("specialty", data.data.student.specialty);
                        if (data.data.student.serviceFeesStatus == 1) {
                            this.$router.go('/' + menu);
                            return
                        } else {
                            this.$router.go('/noPay');
                            return
                        }
                    } else {
                        this.$router.go('/noPay');
                        return
                    }
                }, (err) => {
                    this.$router.go('/schoolIndex');
                    return
                })
            } else {
                // this.$router.go('/' + menu);
            }
        },
        getParams: function(paras) {
            let url = decodeURI(location.href);
            // let url = "http://yuantai.yt-hr.com/school/weixin/front?code=yuantai&menu=readIndex&JSESSIONID=D095CE34FFEAAAD376A6B5786E821199#!/";
            let paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
            let returnValue;

            for (let i = 0; i < paraString.length; i++) {
                let tempParas = paraString[i].split('=')[0];
                let parasValue = paraString[i].split('=')[1];
                if (tempParas === paras)
                    returnValue = parasValue;
            }
            if (typeof(returnValue) == "undefined") {
                return "";
            } else {
                return returnValue;
            }
        },

    },
}
</script>
<style lang="scss">
@import './assets/css/common.css';
@import './public/css/1px.scss';
.yes {
    min-height: 100%;
    background: #343434;
}
</style>
