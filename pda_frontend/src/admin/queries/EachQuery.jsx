import moment from "moment";
import { useRef } from "react";
import { toast } from "react-toastify";
import apiEndPoints from "../../actions/api";

function EachQuery({ query }) {
	console.log(query);
	const status = useRef();

	function updateData() {
		apiEndPoints.ALL_QUERY().updateUser(query.id, status.current.value)
			.then(resp => {
				console.log(resp);
				if (resp.status === 202)
					return resp.data;
				else
					toast(resp.data.Message);
			})
			.then(resp => {
				toast.success(resp.Message);
			})
			.catch(error => {
				toast.error("Error: " + error.message);
			})
			.finally(() => { })

	};

	console.log(query);
	return (
		<tr key={query.id}>
			<td>{query.name}</td>
			<td>{query.email}</td>
			<td>{query.contact ? query.contact : "--"}</td>
			<td>{query.description}</td>
			<td>
				<select defaultValue={query.status} ref={status} onChange={updateData}>
					<option value={"New"}>New</option>
					<option value={"Contacted"}>Mark Contacted</option>
					<option value={"Resolved"}>Mark Resolved</option>
				</select>
			</td>
			<td>{moment(query.createdDateTime).format("DD-MM-YYYY HH:mm a")}</td>
			<td>{query.solvedDateTime ? moment(query.solvedDateTime).format("DD-MM-YYYY HH:mm a") : "Connect"}</td>
		</tr>)
}

export default EachQuery