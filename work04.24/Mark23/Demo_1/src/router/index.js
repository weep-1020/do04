import {
	createRouter,
	createWebHashHistory
} from "vue-router"
import Login from "../views/Login.vue"

import Stu from "../views/student/Stu.vue"
import Stu_FindClass from "../views/student/FindClass.vue"
import Stu_FindMark from "../views/student/FindMark.vue"
import Stu_UpDatePwd from "../views/student/UpDatePwd.vue"
import Stu_TeaMark from "../views/student/TeaMark.vue"

import Tea from "../views/teacher/Tea.vue"
import Tea_Go2Mark from "../views/teacher/Go2Mark.vue"
import Tea_AddMark from "../views/teacher/AddMark.vue"
import Tea_FindTask from "../views/teacher/FindTask.vue"
import Tea_UpDatePwd from "../views/teacher/UpDatePwd.vue"

import Adm from "../views/admin/Adm.vue"
import Adm_AboutClz from "../views/admin/AboutClz.vue"
import Adm_AboutCourse from "../views/admin/AboutCourse.vue"
import Adm_AboutTask from "../views/admin/AboutTask.vue"
import Adm_StuAdd from "../views/admin/stu/Add.vue"
import Adm_StuShow from "../views/admin/stu/Show.vue"
import Adm_TeaAdd from "../views/admin/tea/Add.vue"
import Adm_TeaShow from "../views/admin/tea/Show.vue"



export const routes = [{
		path: '/login',
		component: Login
	},
	{
		path: '/stu',
		component: Stu,
		children: [{
				path: 'findClass',
				component: Stu_FindClass
			},
			{
				path: 'findMark',
				component: Stu_FindMark
			},
			{
				path: 'upDatePwd',
				component: Stu_UpDatePwd
			},
			{
				path: 'teaMark',
				component: Stu_TeaMark
			}
		]
	},
	{
		path: '/tea',
		component: Tea,
		children: [{
				path: 'go2Mark',
				component: Tea_Go2Mark
			},
			{
				path: 'addMark',
				component: Tea_AddMark
			},
			{
				path: 'findTask',
				component: Tea_FindTask
			},
			{
				path: 'upDatePwd',
				component: Tea_UpDatePwd
			}
		]
	},
	{
		path: '/adm',
		component: Adm,
		children: [{
				path: 'Clz',
				component: Adm_AboutClz
			},
			{
				path: 'Course',
				component: Adm_AboutCourse
			},
			{
				path: 'Task',
				component: Adm_AboutTask
			},
			{
				path: 'Stuadd',
				component: Adm_StuAdd
			},
			{
				path: 'StuShow',
				component: Adm_StuShow
			},
			{
				path: 'TeaAdd',
				component: Adm_TeaAdd
			},
			{
				path: 'teashow',
				component: Adm_TeaShow
			}
		]
	}
];

// 注册路由表 告诉Vue-router 路由数组
const router = createRouter({
	history: createWebHashHistory(import.meta.env.BASE_URL),
	routes
});
// 全局前置守卫
router.beforeEach((to, from, next) => {
	const publicPages = ['/login'];
	const authRequired = !publicPages.includes(to.path);
	const loggedIn = !!window.localStorage.getItem('token');

	if (authRequired && !loggedIn) {
		return next('/login');
	} else {
		next();
	}
});

// export  暴露出来 为import 做准备
export default router;