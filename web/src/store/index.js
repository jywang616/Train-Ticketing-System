import { createStore } from 'vuex'

export default createStore({
  state: {
    member:{}
  },
  getters: {
  },
  //功能跟set差不多？
  mutations: {
    setMember(state,_member){
      state.member=_member;
    }
  },
  //启一个异步任务
  actions: {
  },
  modules: {
  }
})
