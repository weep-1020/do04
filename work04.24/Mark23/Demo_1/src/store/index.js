import {
	createStore
} from 'vuex';
export const store = createStore({
	state() {
		return {
			ip: '127.0.0.1:8080',
			pagenum: '',
			user: {
				name: '',
				//用户名
				role: '',
				//职员
				pic: '',
				//图片id
				token: '',
				//验证
				picBody: '',
				//图片本体
			}
		}
	}
})