import apiEndPoints from "../../actions/api";

export function getToHome() {
	window.location.replace(new URL(window.location.href).origin);
}
export function addJWTTokenInLocalStorage(data) {
	let expireTime = data.expiresIn * (data.unit === "Minutes" ? (60 * 1000) : 1000);
	data["expireTime"] = (new Date().getTime() + expireTime);
	localStorage.setItem("$2a$12$g2wDl3aME82fIdara1XA7uMk7MclPHFk8fCSPxJ5hzj7FUCo2RXay", JSON.stringify(data))
}
export function getJWTTokenFromLocalStorage() {
	let t = localStorage.getItem("$2a$12$g2wDl3aME82fIdara1XA7uMk7MclPHFk8fCSPxJ5hzj7FUCo2RXay");
	if (t) {
		return JSON.parse(t);
	} else return null;
}
export function removeJWTToken() {
	localStorage.removeItem("$2a$12$g2wDl3aME82fIdara1XA7uMk7MclPHFk8fCSPxJ5hzj7FUCo2RXay");
}

export function refreshToken() {
	apiEndPoints.AUTH().refreshToken()
		.then(resp => {
			console.log("Refresh token ", resp);
			if (resp.status === 200) {
				addJWTTokenInLocalStorage(resp.data)
			}
		})
		.catch(error => {

		})
}

export function logoutUser() {
	clearTimeout(window.extraTimeTimeOut);
	apiEndPoints.AUTH().logout()
		.then(resp => {
			if (resp.status === 200) {
				removeJWTToken()
				getToHome();
			}
		})
		.catch(error => {
			console.error(error.message);
		})
}