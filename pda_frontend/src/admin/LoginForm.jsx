import { useState } from 'react';
import { SiBytedance } from "react-icons/si";
import { Link, useNavigate } from 'react-router-dom';
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
			errors.name = 'Username is required.';
		if (!password)
			errors.pass = 'Password is required.';
		setErrorMessages(errors);
		return Object.keys(errors).length === 0;
	};

	const handleSubmit = (event) => {
		event.preventDefault();
		if (validateForm()) {
			apiEndPoints.SIMPLE().login({
				name: "ujjwal",
				password: "sds"
			})
				.then(e => console.log(e))
				.catch(e => console.error(e.message))
				.finally(a => {
					setTimeout(() => {
						nav("../admin");
					}, 1000);
				});
		}
	};

	return (
		<div className={sheet.loginForm}>
			<form onSubmit={handleSubmit}>
				<SiBytedance className={sheet.heading} />
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
					<input
						type="password"
						name="pass"
						value={password}
						onChange={(e) => setPassword(e.target.value)} />
					{errorMessages.pass && <div className={sheet.error}>{errorMessages.pass}</div>}
				</div>
				<input type="submit" className={sheet.submitBtn} value="Login" />
				<div className={sheet.actions}>
					<Link className={sheet.notHere} to={"../register"} relative='path'>Register here...</Link>
				</div>
			</form>
		</div>
	);
};

export default LoginForm;
