import store from './store'
import * as types from './types'

export const increment = ({dispatch, state}) => {
  dispatch(types.INCREMENT, 1)
}

export const reduction = ({dispatch, state}) => {
  dispatch(types.INCREMENT, -1)
}