import { Fragment, useEffect, useState } from "react";
import apiEndPoints from "../../actions/api";
import moment from "moment";

export default function FutureClasses() {
	const [data, setDate] = useState(null);
	useEffect(() => {
		classList();
	}, []);

	function classList() {
		apiEndPoints.CLASSES().getFutureClasses({})
			.then(response => {
				if (response.status === 200) {
					return response.data;
				} else alert(JSON.stringify(response.data))
			})
			.then(resp => {
				setDate(resp);
			})
			.catch(error => {
				console.error(error)
			})
	}
	function deleteClass(id) {
		apiEndPoints.CLASSES().deleteClass(id)
			.then(response => {
				if (response.status === 200) {
					alert(response.data.Message);
					classList();
				} else alert(JSON.stringify(response.data))
			})
			.catch(error => {
				console.error(error.message)
			})
	}
	return (<>
		{!data ? <div>Loading...</div> :
			<div>
				<table>
					<thead>
						<tr>
							{/* <th>So.</th> */}
							<th>Starting from</th>
							<th>Estimated class Start-time</th>
							<th>Duration</th>
							<th>Note</th>
							<th>Delete</th>
						</tr>
					</thead>
					<tbody>
						{data.map((eachClass, index) => (<tr key={eachClass.id}>
							{/* <td>{index + 1}</td> */}
							<td>{moment(eachClass.batchStartDateTime).format("dddd, DD/MM/YYYY")}</td>
							<td>{moment(eachClass.batchStartDateTime).format("hh:mm a")}</td>
							<td>{eachClass.durationInMin} mins</td>
							<td className="notes">{eachClass.note}</td>
							<td><button onClick={() => deleteClass(eachClass.id)}>Delete</button></td>
							{/* {eachClass} */}
							{/* {JSON.stringify(eachClass + index)} */}
						</tr>)
						)}
					</tbody>
				</table>
			</div>}
	</>);
}