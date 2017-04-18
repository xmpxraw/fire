import {
	INCREMENT
} from '../types'

// 创建一个对象来保存应用启动时的初始状态
const state = {
  // 应用启动时，count 置为0
  count: 0
}

// 创建一个对象存储一系列我们接下来要写的 mutation 函数
const mutations = {
  // mutation 的第一个参数是当前的 state
  // 你可以在函数里修改 state
  INCREMENT (state, amount) {
    state.count = state.count + amount
  }
}
export default {
  state,
  mutations
}