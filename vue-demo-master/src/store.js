/**
 * Created by tanyichao on 16/12/29.
 */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
    isNight: false,
}
export default new Vuex.Store({
    state,
    mutations: {
        UPDATE_ISNIGHT (state, status) {
            state.isNight = status
        },
    }
})