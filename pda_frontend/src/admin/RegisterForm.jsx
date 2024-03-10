import { useState } from 'react';
import { SiBytedance } from "react-icons/si";
import { Link, useNavigate } from 'react-router-dom';
import apiEndPoints from '../actions/api';
import sheet from './LoginForm.module.css';

const RegisterForm = () => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [password2, setPassword2] = useState('');
	const [errorMessages, setErrorMessages] = useState({});
	const nav = useNavigate();

	const validateForm = () => {
		const errors = {};
		if (!username)
			errors.name = 'Username is required';
		if (!password)
			errors.pass = 'Password is required';
		if (!password2 || password !== password2)
			errors.pass2 = 'Confirm Password must match password';
		setErrorMessages(errors);
		console.log(errors);
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
					console.log("fin");
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
					<label>Email ID:</label>
					<input
						type="email"
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
				<div className={sheet.inputContainer}>
					<label>Confirm Password</label>
					<input
						type="password"
						name="pass"
						value={password2}
						onChange={(e) => setPassword2(e.target.value)} />
					{errorMessages.pass2 && <div className={sheet.error}>{errorMessages.pass2}</div>}
				</div>
				<input type="submit" className={sheet.submitBtn} value="Login" />
				<div className={sheet.actions}>
					<Link className={sheet.notHere} to={"../login"} relative='path'>Existing user?</Link>
				</div>
			</form>
		</div>
	);
};

export default RegisterForm;
