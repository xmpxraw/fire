<template>
    <div class="page-personScore">
        <header-nav title="我的成绩" :backshow="backshow"></header-nav>
        <div class="noExam" v-show="noShow">
            您暂时没有成绩！
        </div>
        <div class="contain" v-show="!noShow">
            <ul>
                <li v-for="item in items">
                    <dl>
                        <dt><span class="totalScore">总分: {{item.scoreTotal}}</span><span class="pass" v-if="(item.pass == '是')">通过</span><span class="pass nopass" v-if="(item.pass == '否')">不通过</span></dt>
                        <dd class="firstDl">
                            <p>理论成绩: {{item.scoreLl}}</p>
                            <p>水系统成绩: {{item.scoreSxt}}</p>
                            <p>控制室成绩: {{item.scoreKzs}}</p>
                            <p>防火巡查成绩: {{item.scoreFhxc}}</p>
                            <p>气体灭火成绩: {{item.scoreQtmh}}</p>
                        </dd>
                        <dd><span class="special total-time">时间: {{item.examTime}}</span></dd>
                        <dd><span class="special total-address">考试地点：{{item.examLocation}}</span></dd>
                    </dl>
                </li>
            </ul>
        </div>
        <loading :show="loading"></loading>
        <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
    </div>
</template>
<script>
import headerNav from '../common/header.vue'
import loading from 'vux-components/loading'
import apiService from '../apiservice.js'
import toast from 'vux-components/toast'
export default {
    name: 'personScore',
    components: {
        headerNav,
        loading,
        toast
    },
    ready() {
        this.renderData();
    },
    data() {
        return {
            backshow: true,
            noShow: false,
            loading: false, //控制是否出现loading
            show2: false, //控制toast的出现
            warntext: "",
            items: [],
            // items:[{
            //     scoreTotal:210,
            //     pass:"通过",
            //     scoreLl:51,//理论成绩
            //     scoreSxt:51,//水系统成绩
            //     scoreKzs:51,//控制室成绩
            //     scoreFhxc:51,//防火巡查成绩
            //     scoreQtmh:51,//气体灭火成绩
            //     examTime:"2016年11月28日",
            //     examLocation:"深圳远泰消防学校1"
            // },{
            //    scoreTotal:200,
            //     pass:"未通过",
            //     scoreLl:50,//理论成绩
            //     scoreSxt:50,//水系统成绩
            //     scoreKzs:50,//控制室成绩
            //     scoreFhxc:50,//防火巡查成绩
            //     scoreQtmh:50,//气体灭火成绩
            //     examTime:"2016年12月28日",
            //     examLocation:"深圳远泰消防学校"
            // }
            // ]
        }
    },
    methods: {

        back: function() {
            window.history.back();
        },
        renderData: function() {
            // this.loading = true;
            apiService.myExam({
                code: window.localStorage.getItem("codeStatus"),
                JSESSIONID: window.localStorage.getItem("JSESSIONID")
            }).then((data) => {
                this.loading = false;
                this.items = data.data.exams;
                if (data.data.exams.length == 0) {
                    this.noShow = true;
                }
            }, (err) => {
                this.loading = false;
                this.show2 = true;
                this.warntext = "网络不给力,请稍后再试...";
                console.log(err);
            })
        },
        popState: function() {
            var state = {
                title: "title",
                url: "#"
            };
            window.history.pushState(state, "title", "#");

        },
    },

}
</script>
<style lang="scss">
.page-personScore {
    .noExam {
        height: 5rem;
        line-height: 5rem;
        text-align: center;
        font-size: .9rem;
    }
    .contain {
        width: 100%;
        height: auto;
        background: #fff;
        ul {
            width: 100%;
            height: auto;
            li {
                width: 100%;
                border-top: 1px solid #dddddd;
                list-style: none;
                padding-bottom: 1.2rem;
                dl {
                    overflow: hidden;
                    margin: 0;
                    dt {
                        overflow: hidden;
                        height: 2rem;
                        line-height: 2rem;
                        margin-bottom: .6rem;
                        .totalScore {
                            float: right;
                            background: #f5f3f4;
                            padding: .2rem .5rem;
                            margin: .2rem .5rem 0 0;
                            line-height: 1rem;
                        }
                        .pass {
                            color: #fff;
                            width: 50%;
                            background: url(../assets/images/image_pass.png) left no-repeat;
                            background-size: 4.35rem;
                            padding: 0 .5rem;
                            display: inline-block;
                            height: 2rem;
                            line-height: 2rem;
                            position: relative;
                        }
                        .nopass {
                            width: 50%;
                            background: url(../assets/images/image_no_pass.png) left no-repeat;
                            background-size: 5.5rem;
                        }
                    }
                    dd {
                        text-align: left;
                        overflow: hidden;
                        margin: 0;
                        padding: 0 .4rem;
                        box-sizing: border-box;
                        p {
                            float: left;
                            width: 50%;
                            font-size: .9rem;
                            line-height: 1.2rem;
                        }
                        .special {
                            color: #666;
                            font-size: .7rem;
                            line-height: 1.2rem;
                        }
                    }
                    .firstDl {
                        margin-bottom: .5rem;
                    }
                }
            }
        }
    }
}
</style>
