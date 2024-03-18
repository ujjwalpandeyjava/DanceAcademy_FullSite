import axios from "axios";

const ax = axios.create({
	baseURL: `http://localhost:8882`,
	timeout: 30000,
});
const apiEndPoints = {
	SIMPLE(url = '') {
		return {
			home: () => ax.get(url),
			login: (payload) => ax.get(url, { params: { ...payload } })
		}
	},

	ADMISSION_QUERY_V1(url = '/api/v1/admissionQuery') {
		return {
			save: (newRecord) => ax.post(url, newRecord),
			fetchPaginated: (paginateData) => {
				const params = { ...paginateData };
				const headers = {};
				return ax.get(url, { headers, params })
			},
			fetchAll: () => ax.get(url),
			fetchById: (id) => ax.get(url + id),
			update: (id, updatedRecord) => ax.put(url + id, updatedRecord),
			delete: (id) => ax.delete(url + id),
		}
	}
}

// Axios Interceptors
ax.interceptors.request.use(config => {
	// console.log("Req params" +config.params);
	return config;
},
	(error) => {
		// console.error("Error in request interceptor: ", error.message);
		return Promise.reject(error);
	});
ax.interceptors.response.use(response => {
	console.log("response data: " + response.data || response.json);
	return response;
}, function (error) {
	// console.error("Error in response interceptor: ", error);
	if (error?.response?.status === 400) {
		// alert("==="+ error?.response?.data?.Message)
	}
	return Promise.reject(error);
});
export default apiEndPoints;