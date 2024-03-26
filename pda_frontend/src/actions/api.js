import axios from "axios";
import { toast } from "react-toastify";
import { addJWTTokenInLocalStorage, getJWTTokenFromLocalStorage, getToHome, logoutUser, refreshToken } from "../components/global/Utlity";

window.reLoginTime = null;
window.extraTimeTimeOut = null;

const ax = axios.create({
	baseURL: `http://localhost:8882`,
	timeout: 30000
});
const apiEndPoints = {
	SIMPLE(url = '') {
		return {
			home: () => ax.get(url),
		}
	},
	AUTH(url = "/api/v1/auth") {
		return {
			login: (payload) => ax.post(url + "/getToken", payload.body, { params: { ...payload.params } }),
			refreshToken: () => ax.post(url + "/refreshToken"),
			logout: () => ax.delete(url + "/revokeToken"),
			registerNewUser: (body) => {
				const headers = { "Content-Type": "application/json", "accept": "application/json" };
				return ax.post(url + "/addNewUser", { ...body }, {
					headers: { ...headers }
				})
			}
		}
	},
	ADMISSION_QUERY_V1(url = '/api/v1/admissionQuery') {
		return {
			save: (formData) => {
				return ax.post(url + "/save", formData, {
					headers: { "Content-Type": "multipart/form-data" },
					params: {}
				})
			},
			fetchPaginated: (paginateData) => {
				const params = { ...paginateData };
				const headers = { "Content-Type": "application/json", "accept": "application/json" };
				return ax.post(url + "/paginated", params, { headers, params })
			},
			update: (id, status) => ax.patch(url + `/${id}`, { status: status }, {
				params: { status: status }
			}),
			fetchAll: () => ax.get(url),
			fetchById: (id) => ax.get(url + id),
			delete: (id, updatedRecord) => ax.put(url + id, updatedRecord),
		}
	},
	ALL_QUERY(url = "/api/v1/query") {
		return (
			{
				save: (formData) => ax.post(url + "/save", formData, {
					headers: { 'Content-Type': 'application/json' }
				}),
				getAll: () => ax.get(url),
				updateUser: (id, newStatus) => ax.patch(url + "/" + id, null, {
					params: {
						newStatus: newStatus
					}
				})
			}
		)
	},
	USERS(url = "api/v1/users") {
		return {
			// login: (payload) => ax.post(url + "/getToken", payload.body, { params: { ...payload.params } }),
			getAll: (params) => ax.get(url, { params: { ...params } }),
			updateUser: (id, payload) => ax.patch(url + "/update/" + id, payload.params, {
				params: {},
				headers: {
					"Content-Type": "application/json"
				}
			})
		}
	}
}

ax.interceptors.request.use(config => {
	// console.log("from interceptor", { "Req params": config.params, "Req headers": config.headers });
	// Set the header in their own api calls..
	// config.headers['Content-Type'] = 'application/json';
	// config.headers['Accept'] = 'application/json';

	if (!config.url.endsWith('v1/auth/getToken')) {
		let tokenData = getJWTTokenFromLocalStorage();
		if (tokenData) {
			config.headers['Authorization'] = `Bearer ${tokenData.token}`;
			var extraTime = 6000;
			clearTimeout(window.reLoginTime);
			window.reLoginTime = setTimeout(() => {
				toast(
					<p className="logoutWarning">
						<p>you are about to logout!</p>
						<p className="logoutWarningActions">
							<button onClick={refreshToken}>Stay logged-in</button>
							<button onClick={logoutUser}>Logout</button>
						</p>
					</p>
				);
				clearTimeout(window.extraTimeTimeOut)
				window.extraTimeTimeOut = setTimeout(() => {
					logoutUser();
				}, extraTime);
			}, Math.max(1, (tokenData.expireTime - extraTime - new Date().getTime())));
			console.log(Math.max(1, (tokenData.expireTime - extraTime - new Date().getTime())));
		}
	}
	return config;
}, (error) => {
	console.error("Error in request interceptor: ", error.message);
	return Promise.reject(error);
});


ax.interceptors.response.use(response => {
	//	console.log("response data: " , response, response.data || response.json);
	if (response.request.responseURL.endsWith("api/v1/auth/getToken")) {
		addJWTTokenInLocalStorage(response.data);
	}
	return response;
}, function (error) {
	// console.error("Error in response interceptor: ", error);
	if (error?.response?.status === 401) {
		toast.error("Logged out!")
		getToHome();
	}
	return Promise.reject(error);
});


export default apiEndPoints;