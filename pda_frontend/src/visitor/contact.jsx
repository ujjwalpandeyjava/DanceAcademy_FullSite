import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './contact.module.css';
import { useRef, useState } from 'react';
import { toast } from 'react-toastify';
import apiEndPoints from '../actions/api';

function Contact() {
	const name = useRef();
	const email = useRef();
	const contactNo = useRef();
	const description = useRef();
	const [submitState, setSubmitState] = useState(false);

	function submitForm() {
		if (!name.current.value || !email.current.value || !description.current.value) {
			toast.warn("Please fill all required fields");
			return;
		}
		setSubmitState(true)
		const formData = new FormData();
		formData.append("name", name.current.value);
		formData.append("email", email.current.value);
		formData.append("contactNo", contactNo.current.value);
		formData.append("description", description.current.value);
		formData.append("createdDateTime", new Date());
		formData.append("status", "New");
		formData.append("solvedDateTime", "");

		apiEndPoints.ALL_QUERY().save(formData)
			.then(response => {
				console.log("====", response);
				if (response.status === 201) {
					toast.success("Your query has been submitted successfully, we will contact you soon!");
					resetForm();
				}
			})
			.catch(error => {
				if (error?.response?.status === 400) {
					console.log(error.response.data);
					let m = "";
					for (const key in error.response.data) {
						if (error.response.data.hasOwnProperty(key))
							m += `${error.response.data[key]}\n`
					}
					console.error(m);
				}
			})
			.finally(() => {
				setSubmitState(false)
			});
	}

	function resetForm() {
		name.current.value = email.current.value = contactNo.current.value = description.current.value = "";
	}

	return (
		<div>
			<NavBar />
			<h1 className={sheet.h1}>Contact</h1>
			<div id={sheet.contactBox}>
				<div className={sheet.formGroup}>
					<label htmlFor="name" className='requiredStar'>Name: </label><input id="name" type="text" name="name" ref={name} placeholder="Enter your name" />
				</div>
				<div className={sheet.formGroup}>
					<label htmlFor="e-main-id" className='requiredStar'>E-mail: </label><input id="e-main-id" type="email" name="e-main-id" ref={email} placeholder="Enter your e-mail id" />
				</div>
				<div className={sheet.formGroup}>
					<label htmlFor="e-main-id">Phone no: </label><input id="tele-no" type="tel" name="phn_no" ref={contactNo} placeholder="Enter contact number" />
				</div>
				<div className={sheet.formGroup}>
					<label htmlFor="e-main-id" className='requiredStar'>Message: </label><textarea id="message" name="concern" rows="7" cols="20" ref={description} placeholder="Enter some message"></textarea>
				</div>
				<button disabled={submitState} id={sheet.submitButton} onClick={submitForm}>Submit</button>
			</div>
			<FootBar />
		</div>
	)
}

export default Contact