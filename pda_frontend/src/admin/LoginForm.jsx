import { useState } from 'react';
import { SiBytedance } from "react-icons/si";
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import apiEndPoints from '../actions/api';
import sheet from './LoginForm.module.css';

const LoginForm = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [errorMessages, setErrorMessages] = useState({});
	const nav = useNavigate();
	const validateForm = () => {
		const errors = {};
		if (!username)
			errors.name = 'Username required.';
		if (!password)
			errors.pass = 'Password required.';
		setErrorMessages(errors);
		return Object.keys(errors).length === 0;
	};

	const handleSubmit = (event) => {
		event.preventDefault();
		if (validateForm()) {
			apiEndPoints.AUTH().login({
				body: {
					userName: username,
					password: password
				},
				params: {
				}
			})
				.then(e => {
					console.log(e)
					if (e.status === 200) {
						switch (e.data.user.authorities[0].authority) {
							case "ADMIN":
								nav("../admin");
								break;
							case "FACULTY":
								nav("../faculty")
								break;
							case "USER":
								nav("../user")
								break;
							default:
								toast.error("Un-Handled User Type")
								break;
						}
					}
				})
				.catch(e => {
					console.log(e);
					if (e.response.status === 400) {
						let text = '';
						for (let key in e.response.data) {
							if (e.response.data.hasOwnProperty(key)) {
								text += `${key} - ${e.response.data[key]}\n`;
							}
						}
						toast.warning(text);
					}
				})
				.finally(a => {
				});
		}
	};

	return (
		<div className={sheet.loginForm}>
			<form onSubmit={handleSubmit}>
				<Link to={"../../"} relative='path'>
				<SiBytedance className={sheet.heading} />
				</Link>
				<div className={sheet.inputContainer}>
					<label>Username/Email</label>
					<input
						type="text"
						name="uname"
						value={username}
						onChange={(e) => setUsername(e.target.value)} />
					{errorMessages.name && <div className={sheet.error}>{errorMessages.name}</div>}
				</div>
				<div className={sheet.inputContainer}>
					<label>Password</label>
					<input type="password" name="pass"
						value={password} onChange={(e) => setPassword(e.target.value)} />
					{errorMessages.pass && <div className={sheet.error}>{errorMessages.pass}</div>}
				</div>
				<input type="submit" className={sheet.submitBtn} value="Login" />
				<div className={sheet.actions}>
					{/* <Link className={sheet.notHere} to={"../register"} relative='path'>Register here...</Link> */}
					<Link className={sheet.notHere} to={"../../contact"} relative='path'>New User?... Contact Admin</Link>
				</div>
			</form>
		</div>
	);
};

export default LoginForm;
