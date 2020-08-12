import Vue from 'vue'
import Router from 'vue-router'

import constants from '../lib/constants'

// user
import Login from '../page/user/Login.vue'
import Join from '../page/user/Join.vue'
import Info from '../page/user/Info.vue'
import Basket from '../page/user/Basket.vue'
import Pwsearch from '../page/user/Pwsearch.vue'
import Agreement from '../page/user/Agreement.vue'

// error
import NotFound from '../page/error/NotFound.vue'
import Params from '../page/error/Params.vue'
// main
import List from '../page/main/List.vue'

// notice
import NoticeList from '../page/notice/NoticeList.vue'
import NoticeListDetail from '../page/notice/NoticeListDetail.vue'
import NoticeCreate from '../page/notice/NoticeCreate.vue'
import NoticeUpdate from '../page/notice/NoticeUpdate.vue'

// post
import PostList from '../page/post/PostList.vue'
import PostListDetail from '../page/post/PostListDetail.vue'
import PostCreate from '../page/post/PostCreate.vue'
import PostUpdate from '../page/post/PostUpdate.vue'
import PostTemp from '../page/post/PostTemp.vue'
import PostTempDetail from '../page/post/PostTempDetail.vue'


Vue.use(Router)

export default new Router({
    routes: [
        // 로그인/가입
        {
            path: '/user/login',
            name: constants.URL_TYPE.USER.LOGIN,
            component: Login
        },
        {
            path: '/user/join',
            name: constants.URL_TYPE.USER.JOIN,
            component: Join
        },
        //회원정보
        {
            path: '/user/info',
            name: constants.URL_TYPE.USER.INFO,
            component: Info
        },
        {
            path: '/user/basket',
            name: 'Basket',
            component: Basket
        },
        {
            path: '/agreement',
            name: 'Agreement',
            component: Agreement
        },
        {
            path: '/user/pwsearch',
            component: Pwsearch
        },
        // 메인
        {
            path: '/',
            name: constants.URL_TYPE.POST.MAIN,
            component: List,
        },
        {
            path: '*',
            component: NotFound,
        },
        {
            path: '/params',
            component: Params,
            name: 'Params'
        },

        // 공지사항
        {
            path: '/notice',
            component: NoticeList,
            name: 'NoticeList'
        },
        {
            path: '/notice/:ID',
            component: NoticeListDetail,
            name: 'NoticeListDetail'
        },
        {
            path: '/noticecreate',
            component: NoticeCreate,
            name: 'NoticeCreate'
        },
        {
            path: '/notice/:ID/noticeupdate',
            component: NoticeUpdate,
            name: 'NoticeUpdate'
        },



        // 포스트
        {
            path: '/posts',
            name: 'PostList',
            component: PostList,
        },
        {
            path: '/posts/:ID',
            name: 'PostListDetail',
            component: PostListDetail,
        },
        {
            path: '/postcreate',
            name: 'PostCreate',
            component: PostCreate,
        },
        {
            path: '/posts/:ID/postupdate/',
            name: 'PostUpdate',
            component: PostUpdate,
        },
        {
            path: '/posttemp',
            name: 'PostTemp',
            component: PostTemp,
        },
        {
            path: '/posts/:ID/posttempdetail',
            name: 'PostTempDetail',
            component: PostTempDetail,
        },
    ]
})