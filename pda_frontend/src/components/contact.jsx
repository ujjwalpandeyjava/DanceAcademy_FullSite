import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './contact.module.css';

function Contact() {

	function submitQuery() {
		console.log("Submit Query");
	}
	return (
		<div>
			<NavBar />
			<h1 className={sheet.h1}>Contact</h1>
			<div id={sheet.contactBox}>
				<div className={sheet.formGroup}><label htmlFor="name">Name: </label><input id="name" type="text" name="name" placeholder="Enter your name" /></div>
				<div className={sheet.formGroup}><label htmlFor="e-main-id">E-mail: </label><input id="e-main-id" type="email" name="e-main-id" placeholder="Enter your e-mail id" /></div>
				<div className={sheet.formGroup}><label htmlFor="e-main-id">Phone no: </label><input id="tele-no" type="tel" name="phn_no" placeholder="Enter contact number" /></div>
				<div className={sheet.formGroup}><label htmlFor="e-main-id">Message: </label><textarea id="message" name="concern" rows="7" cols="20" placeholder="Enter some message"></textarea></div>
				<button id={sheet.submitButton}onClick={submitQuery}>Submit</button>
			</div>
			<FootBar />
		</div>
	)
}

export default Contact