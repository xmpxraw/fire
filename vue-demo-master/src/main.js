import FastClick from 'fastclick'
import Vue from 'vue'
import Router from 'vue-router'
//配置路由规则
import routerMap from './routers'
//加载数据请求组件
import VueResource from 'vue-resource'

import App from './App.vue'
import store from './store.js'
Vue.use(Router)
Vue.use(VueResource)

const router = new Router({});
routerMap(router);
const commit = store.commit || store.dispatch;
//消除click延迟
if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}
router.redirect({
    '*': '/'
})

window.routeList = [];

const namesToPerson = ['point', 'successDetail', 'pointMessage'] //拦截路由，返回到个人中心

router.beforeEach(function({ to, from, next, abort }) {
    if (from.name == 'readDetail') {
        commit('UPDATE_ISNIGHT', false); //物理键返回消除夜间模式
    }
    if (from.name == 'examResult' && to.name == 'examDetail') {
        window.sessionStorage.setItem("giveUp", 1); //判断
        next()
        router.replace('/examIndex');
        return
    }

    if (from.name == 'examDetail' && to.name == 'examIndex') {
        if (window.sessionStorage.getItem("giveUp") && window.sessionStorage.getItem("giveUp") == 1) {
            window.sessionStorage.setItem("giveUp", 2);
        } else {
            window.sessionStorage.setItem("giveUp", 2);
            var isGivup = confirm("确定放弃考试吗？");
            if (isGivup == true) {
                next()
                return
            } else {
                var historyUrl = window.location.href.substring(0, window.location.href.lastIndexOf('/') + 1)
                window.history.pushState(null, '', historyUrl + 'examIndex')
                abort()
                return
            }
        }

    }
    // if(from.name == 'pointMessage' && to.name != 'login'){
    //        next()
    //        router.replace('/person');
    //        return
    // }
    if (routeList.length > 1 && to.name == routeList[routeList.length - 2]['name']) {
        router.app.effect = 'back';
        routeList.splice(routeList.length - 1, 1);
    } else {
        router.app.effect = 'fade';
        routeList.push({
            name: to.name,
            path: to.path,
            query: to.query,
            params: to.params,
            timer: +new Date
        });
    }
    //拦截路由，返回到个人中心
    if (namesToPerson.indexOf(from.name) > -1 && to.name != 'login') {
        setTimeout(function() {
            next()
            router.replace('/person')
        }, 50)
    } else {
        setTimeout(next, 50)
    }

})

router.afterEach(function(transition) {
    // console.log(routeList,'---');  //所有路由信息
    for (var i = 0; i < routeList.length; i++) {};
});

router.start(App, '#app')
