'use strict'

import classDetail from './classDetail/index.vue'  //报班页面
import schoolDetail from './classDetail/schoolDetail.vue' //学校详情
import successDetail from './classDetail/successDetail.vue' //成功报班页面
import noPower from './noPower/index.vue' //未信息绑定页面
import noClass from './classDetail/noClass.vue' //无报班信息页面
import signUp from './classDetail/signUp.vue' //我要报名页面
import login from './login/login.vue' //验证手机号码页面
import getcode from './login/getcode.vue' //获取验证码页面
import signMessage from './classDetail/signMessage.vue' //我的报名信息页面
import point from './classDetail/point.vue' //预约页面
import person from './person/person.vue' //个人中心页面
import personScore from './person/personScore.vue' //我的成绩页面
import pointMessage from './classDetail/pointMessage.vue' //我要报名页面
import schoolIndex from './school/index.vue' //首页
import readIndex from './read/index.vue' //读书列表页
import readDetail from './read/readDetail.vue' //读书详情页
import videoIndex from './video/index.vue' //视频列表页面
import videoDetail from './video/videoDetail.vue' //视频详情页
import examIndex from './exam/index.vue' //考试列表页
import examDetail from './exam/examDetail.vue' //考试详情页
import examResult from './exam/examResult.vue' //考试结果页
import examView from './exam/examView.vue'//查看试卷页
import xTestrecord from './xTestrecord/xTestrecord.vue' //考试记录
import xTestresult from './xTestrecord/xTestresult.vue'  //考试结果
import xReportrecord from './xTestrecord/xReportrecord.vue' //报班记录
import xBroadcastRecord from './xBroadcast/xBroadcastRecord.vue' //播放记录
import xTestrecordOth from './xBroadcast/xTestrecordOth.vue'  //我也不知道为什么新建多了一个播放记录
import xPractice from './xPractice/xPractice.vue'  //章节练习列表
import xPracticedel from './xPractice/xPracticedel.vue'  //章节练习（每题）
import xprtTj from './xPractice/xprtTj.vue'  //练习统计（每题）
import xWrongTj from './xWrongtopic/xWrongTj.vue'  //错题统计（每题）
import xWrongPrt from './xWrongtopic/xWrongPrt.vue'  //错题练习（每题）
import noPay from './common/noPay.vue'  //没有付费页面




/*
* cx4 router
* */


export default function(router) {
    router.map({
        '/': {
            name: 'schoolIndex',
            component: schoolIndex
        },
        '/login': {
            name: 'login',
            component: login
        },
        '/point': {
            name: 'point',
            component: point
        },
        '/pointMessage': {
            name: 'pointMessage',
            component: pointMessage
        },
        '/signMessage': {
            name: 'signMessage',
            component: signMessage
        },
        '/getcode/:mobile/:idcard/:studentName': {
            name: 'getcode',
            component: getcode
        },
        '/classDetail': {
            name: 'classDetail',
            component: classDetail
        },
        '/schoolDetail': {
            name: 'schoolDetail',
            component: schoolDetail
        },
        '/successDetail': {
            name: 'successDetail',
            component: successDetail
        },
        '/noPower': {
            name: 'noPower',
            component: noPower
        },
        '/noClass': {
            name: 'noClass',
            component: noClass
        },
        '/signUp': {
            name: 'signUp',
            component: signUp
        },
        '/person': {
            name: 'person',
            component: person
        },
        '/personScore': {
            name: 'personScore',
            component: personScore
        },
        '/schoolIndex': {
            name: 'schoolIndex',
            component: schoolIndex
        },
        '/xTestrecord': {
            name: 'xTestrecord',
            component: xTestrecord
        },
        '/xTestresult': {
            name: 'xTestresult',
            component: xTestresult
        },
        '/xBroadcastRecord': {
            name: 'xBroadcastRecord',
            component: xBroadcastRecord
        },

        '/xTestrecordOth': {
            name: 'xTestrecordOth',
            component: xTestrecordOth
        },

        '/xReportrecord': {
            name: 'xReportrecord',
            component: xReportrecord

        },

        '/xWrongPrt': {
            name: 'xWrongPrt',
            component: xWrongPrt

        },
        '/readIndex': {
            name: 'readIndex',
            component: readIndex
        },

        '/readDetail/:chapterId': {
            name: 'readDetail',
            component: readDetail
        },

        '/xPracticedel': {
            name: 'xPracticedel',
            component: xPracticedel
        },

        '/videoIndex': {
            name: 'videoIndex',
            component: videoIndex
        },

        '/videoDetail/:chapterId': {
            name: 'videoDetail',
            component: videoDetail
        },

        '/xPractice': {
            name: 'xPractice',
            component: xPractice
        },

        '/xprtTj': {
            name: 'xprtTj',
            component: xprtTj
        },

        '/xWrongTj': {
            name: 'xWrongTj',
            component: xWrongTj
        },

        '/xPracticedel/:chapterId': {
            name: 'xPracticedel',
            component: xPracticedel
        },

        '/xWrongPrt/:chapterId': {
            name: 'xWrongPrt',
            component: xWrongPrt
        },

        '/examIndex': {
            name: 'examIndex',
            component: examIndex
        },

        '/examDetail/:id': {
            name: 'examDetail',
            component: examDetail
        },

        '/examResult/:id': {
            name: 'examResult',
            component: examResult
        },
        '/examView/:id/:isRight': {
            name: 'examView',
            component: examView
        },
        '/noPay': {
            name: 'noPay',
            component: noPay
        },
    })
}
