/**
 * Created by tanyichao on 16/11/9.
 */
import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource);
Vue.http.options.credentials = true;
// Vue.http.options.crossOrigin = true;
const api ='http://www.yt-hr.com/school/weixin/'; //测试接口地址
// const api ='http://10.108.1.211:8088/schoolManage/school/weixin/';  //开发接口地址
// const api ='http://10.108.1.211/school/weixin/';  //开发接口地址
const client = {
  apiUrl:{
            viewClass:api+'class/viewClass', //近报班页面
            signUp:api+'class/signUp',//报班
            signEd:api+'class/signed',//报班成功后跳转页面
            schoolDetail:api+'school/view',//学校信息
         },
  doGetData (url, data, sucess, error) {
    if(data){
      var params = '?';
      for(const k in data){
        params += params + "'" + k + "'" + k + '&';
      }
      params.substring(0,params.length - 1);
    }
    Vue.http.get(url + params).then( (response) => {
      sucess(response)
    }, (response) => {
      error(response)
    })
  },
  doGet (url, sucess, error) {
    Vue.http.get(url).then( (response) => {
      sucess(response)
    }, (response) => {
      error(response)
    })
  },
  doPost (url, data, sucess, error) {
    Vue.http.post(url, data).then( (response) => {
      sucess(response)
    }, (response) => {
      error(response)
    })
  }
}
export default client
