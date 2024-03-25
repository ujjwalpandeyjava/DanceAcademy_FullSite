import moment from "moment";
import { useState } from "react";
import { MdDone, MdOutlineSwapCalls } from "react-icons/md";
import { RiThumbUpLine } from "react-icons/ri";
import { TbPlugConnected } from "react-icons/tb";
import { toast } from "react-toastify";
import apiEndPoints from "../../actions/api";

function EachAdmissionQuery({ query, status }) {
	const [isSolved, setIsSolved] = useState(false);

	const actionType = {
		"New": <button className="delete" onClick={() => resolveQuery(query.id, "Contacted")}>Mark contacted <TbPlugConnected /></button>,
		"Contacted": <button className="delete" onClick={() => resolveQuery(query.id, "Resolved")}>Mark Resolved <RiThumbUpLine /></button>,
		"Resolved": <button className="delete" onClick={() => resolveQuery(query.id, "Contacted")}>Re-Connect <MdOutlineSwapCalls /></button>,
	}
	const actionTypeSolved = {
		"New": <button className="delete"> Contacted < MdDone /></button>,
		"Contacted": <button className="delete"> Resolved < MdDone /></button>,
		"Resolved": <button className="delete"> Re-Connected < MdDone /></button>
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
				console.error("====", error.response.data.Message);
			})
	}

	return (
		<div className="eachAdmissionQuery" >
			<div className="head">
				<strong title={query.name + " - " + query.gender}>{query.name} ({query.gender.substring(0, 1).toUpperCase()})</strong>
				<i className="danceForm" title="Dance form">{query.danceForm}</i>
				<span>{moment(query.createdDate).format("DD-MM-YY, hh:mm a")}</span>
			</div>
			<div className="details">
				<p>Contact: {query.email}, {query.contactNo}{query.guardianContactNo && <>, (Other) {query.guardianContactNo}</>}</p>
				{query.address && <p>address: {query.address}</p>}
				<p>description: {query.description ? query.description : "--"}</p>
				<div className="detailsAction">
					{/* <button className="edit" onClick={() => editQuery(query.id)}>Edit <MdOutlineModeEditOutline /></button> */}
					{isSolved ? actionTypeSolved[status] : actionType[status]}
				</div>
			</div>
		</div>
	)
}

export default EachAdmissionQuery