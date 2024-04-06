import moment from "moment";
import { useRef, useState } from "react";
import { MdDone, MdOutlineSwapCalls } from "react-icons/md";
import { RiThumbUpLine } from "react-icons/ri";
import { TbPlugConnected } from "react-icons/tb";
import { toast } from "react-toastify";
import apiEndPoints from "../../actions/api";
import { BsReply } from "react-icons/bs";

function EachAdmissionQuery({ query, status }) {
	const [isSolved, setIsSolved] = useState(false);
	const [mailModal, setMailModal] = useState(false);
	const [actionDisabled, setActionDisabled] = useState(false);

	const mailBody = useRef();
	const mailSubject = useRef();
	const actionType = {
		"New": <button className="delete" onClick={() => resolveQuery(query.id, "Contacted")}>Mark contacted <TbPlugConnected /></button>,
		"Contacted": <>
			<button className="delete" onClick={() => resolveQuery(query.id, "Resolved")}>Mark Not-Interested <RiThumbUpLine /></button>
			<button className="delete" onClick={() => resolveQuery(query.id, "Admitted")}>Mark Admitted <RiThumbUpLine /></button>
		</>,
		"Resolved": <button className="delete" onClick={() => resolveQuery(query.id, "Contacted")}>Re-Connect <MdOutlineSwapCalls /></button>,
		"Admitted": "Now a student",
	}
	const actionTypeSolved = {
		"New": <button className="delete"> Contacted < MdDone /></button>,
		"Contacted": "Check respective section",
		"Resolved": <button className="delete">Re-Connected < MdDone /></button>,
		"Admitted": ""
	}

	function resolveQuery(id, status) {
		apiEndPoints.ADMISSION_QUERY_V1().update(id, status)
			.then(r => {
				if (r.status === 202)
					return r.data
			})
			.then(r => {
				toast.success(r.Message);
				setIsSolved(true)
			})
			.catch(error => {
				console.error("==== ", error.response.data.Message);
			})
	}

	function sendMail() {
		setActionDisabled(true);
		apiEndPoints.EMAIL().simpleMail({
			body: {
				"recipientEmail": query.email,
				"subject": mailSubject.current.value,
				"msgBody": mailBody?.current?.value,
				"attachment": null,
				"referenceID": query.id
			}
		})
			.then(response => {
				console.log(response);
				if (response.status === 200) {
					toast.success(response.data.Message);
					resetForm();
				}
			})
			.catch(error => {
				console.error(error);
				if (error.response.status === 400) {
					for (const key in error.response.data)
						if (error.response.data.hasOwnProperty(key))
							toast.warn(`${key}: ${error.response.data[key]}`);
				}
			})
			.finally(() => {
				setActionDisabled(false)
			})
	}
	function resetForm() {
		mailSubject.current.value = null;
		mailBody.current.value = null;
		setTimeout(() => {
			setMailModal(false)
		}, 300);
	}
	return (
		<div className="eachAdmissionQuery" >
			<div className="head">
				<strong title={query.name + " - " + query.gender}>{query.name} ({query.gender.substring(0, 1).toUpperCase()})</strong>
				<i className="danceForm" title="Dance form">{query.danceForm}</i>
				<span>{moment(query.createdDate).format("DD-MM-YY, hh:mm a")}</span>
			</div>
			<div className="details">
				<p className="contactReply">
					Contact: {query.email}, {query.contactNo}{query.guardianContactNo && <>, (Other) {query.guardianContactNo}</>}
					<button className="reply" title="Via email" onClick={() => setMailModal(true)}>Reply <BsReply /></button>
				</p>
				{query.address && <p>address: {query.address}</p>}
				<p>description: {query.description ? query.description : "--"}</p>
				<div className="detailsAction">
					{/* <button className="edit" onClick={() => editQuery(query.id)}>Edit <MdOutlineModeEditOutline /></button> */}
					{isSolved ? actionTypeSolved[status] : actionType[status]}
				</div>
			</div>
			{mailModal && <div className="emailReply">
				<h2 className="heading">Send a Respond Via E-Mail</h2>
				<div className="to">
					<label htmlFor="mailTo" className="requiredStar">Sending to:</label><br />
					<input type="text" name="mailTo" id="mailTo" value={query.email} disabled={true} />
				</div>
				<div className="subject">
					<label htmlFor="mailSubject" className="requiredStar">Subject for e-Mail</label><br />
					<input type="text" name="mailSubject" id="mailSubject" ref={mailSubject} placeholder="Dance academy response" />
				</div>
				<div className="body">
					<label htmlFor="mailBody" className="requiredStar">e-Mail Body</label><br />
					<textarea name="mailBody" id="mailBody" ref={mailBody} cols="30" rows="5" defaultValue={`Hi, ${query.name}!\n\n`} autoFocus></textarea>
				</div>
				<div className="action">
					{actionDisabled === true ? <h3>Sending...</h3> : <>
						<button onClick={() => sendMail(false)}>Send</button>
						<button onClick={() => { setActionDisabled(false); setMailModal(false); }} className="close">Close</button>
					</>}
				</div>
			</div>}
		</div>
	)
}

export default EachAdmissionQuery