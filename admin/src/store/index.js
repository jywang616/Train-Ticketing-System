import { createStore } from 'vuex'

const MEMBER = "MEMBER";
export default createStore({
  state: {
    //有可能是第一次登录-->避免空指针异常
    member:window.SessionStorage.get(MEMBER) || {}
  },
  getters: {
  },
  //功能跟set差不多？
  mutations: {
    setMember(state,_member){
      state.member=_member;
      window.SessionStorage.set(MEMBER,_member);
    }
  },
  //启一个异步任务
  actions: {
  },
  modules: {
  }
})
