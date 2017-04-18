import Vue from 'vue'
import Vuex from 'vuex'
import increment from './modules/increment'

Vue.use(Vuex)
const debug = process.env.NODE_ENV !== 'production'
Vue.config.debug = debug
Vue.config.warnExpressionErrors = false

export default new Vuex.Store({
  modules: {
    increment
  },
  strict: debug,
})
