/**
 * Created by tanyichao on 16/12/14.
 */
import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource);
Vue.http.options.credentials = true;

// const api = window.location.origin + '/';
// const api ='http://www.yt-hr.com/'; //测试接口地址
// const api ='http://10.108.1.211/school/';  //开发接口地址
// const api ='http://10.108.20.249:8080/';  //后台（振体）接口地址
const api ='http://yuantaiyujie.com/';  //后台接口地址



const customActions = {
  //数据请求封装
     checkLogin:{method: 'POST', url: api+'school/weixin/checkLogin'},//登录接口,手机号码验证
     sendMsg:{method: 'POST', url: api+'school/weixin/sendMsg'},//获取验证码
     weixinLogin:{method: 'POST', url: api+'school/weixin/weixinLogin'},//登录接口
     reserve:{method: 'POST', url: api+'school/weixin/reserve'},//预约
     myExam:{method: 'POST', url: api+'school/weixin/myExam'},//我的成绩列表
     getTerms:{method: 'POST', url: api+'school/weixin/getTerms'},//预约期数
     getRegiste:{method: 'POST', url: api+'school/weixin/getRegiste'},//获取报名参数
     registe:{method: 'POST', url: api+'school/weixin/doRegiste'},//提交报名
     myReserve:{method: 'POST', url: api+'school/weixin/myReserve'},//预约信息
     myCenter:{method: 'POST', url: api+'school/weixin/myCenter'},//个人中心
     myRegiste:{method: 'POST', url: api+'school/weixin/myRegiste'},//个人中心
     viewClass:{method: 'GET', url: api+'school/weixin/class/viewClass'},//近报班页面
     signUp:{method: 'POST', url: api+'school/weixin/class/signUp'},//报班
     signEd:{method: 'GET', url: api+'school/weixin/class/signed'},//报班成功后跳转页面
     schoolDetail:{method: 'GET', url: api+'school/weixin/school/view'},//学校信息
     getCompany:{method: 'POST', url: api+'school/weixin/getCompany'},//学校信息
     loginForard:{method: 'POST', url: api+'school/weixin/loginForard'},//判断是否登录
     getFeesScheme:{method: 'POST', url: api+'school/weixin/getFeesScheme'},//费用联动
     WXIndex:{method: 'POST', url: api+'school/weixin/WXIndex'},//首页学员信息
     getAdvertisement:{method: 'POST', url: api+'school/weixin/getAdvertisement'},//首页轮播图信息
     getDirectory:{method: 'POST', url:  api+'api/textbook/get_top_dir'},//获取读书目录(篇)
     getReadChapter:{method: 'POST', url:  api+'api/textbook/get_chapter_dir'},//根据读书目录(篇)获取章节
     getTextbookByDirectory:{method: 'POST', url: api+'api/textbook/get_chapter_textbook'},//获取章节下的教材内容接口
     getVideoDirectory:{method: 'POST', url: api+'api/video/get_top_dir'},//获取视频目录(篇)和根据视频目录(篇)获取章节
     getVideoByDirectory:{method: 'POST', url: api+'api/video/get_chapter_video'},//获取视频内容
     getVideoSave:{method: 'POST', url: api+'api/video/set_video_record'},//保存视频内容
     getVideo:{method: 'POST', url: api+'api/video/get_video_record'},//播放记录

     chapter:{method: 'POST', url: api+'api/question/get_top_dir'},//章节练习筛选
     getChapterProgress:{method: 'POST', url: api+'api/question/statics_by_chapter'},//练习统计
     getAllQuestion:{method: 'POST', url: api+'api/question/get_chapter_question'},//章节练习筛选
     getAllWrongQuestion:{method: 'POST', url: api+'api/question/get_error_question'},//章节练习筛选
     deleteRecord:{method: 'POST', url: api+'api/question/delete_record_by_id'},//删除章节对应id记录
     getAnswerList:{method: 'POST', url: api+'api/question/get_record_by_chapter'},//答案列表
     submitAnswer:{method: 'POST', url: api+'api/question/save_record'},//提交答案
     paperList:{method: 'POST', url: api+'api/examination/paper_list'},//考试列表
     paperDetail:{method: 'POST', url: api+'api/examination/paper_detail'},//试卷详情
     handInPaper:{method: 'POST', url: api+'api/examination/hand_in_paper'},//交卷
     examResultDetail:{method: 'POST', url: api+'api/examination/exam_result_detail'},//试卷详情
     examResultList:{method: 'POST', url: api+'api/examination/exam_result_list'},//考试记录列表
     examAnswerDetail:{method: 'POST', url: api+'api/examination/exam_answer_detail'},//考试答题结果
}

const resource = Vue.resource('', {}, customActions);

export default resource
