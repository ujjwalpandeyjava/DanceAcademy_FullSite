
function EachAdmissionQuery({ details }) {
	console.warn(details);
	return (

		// { "id": "65ed67e75475311e499a41d3", "name": "name", "gender": "Male", "danceForm": "Yangko Dance",
		//  "email": "jksf", "email": "lkjs", "guardianContactNo": "l0---", "address": "908", "description": "08n", 
		// "createdDate": "2024-03-10T07:57:27.532+00:00", "status": "New" }
		<div key={details.id} className="eachAdmissionQuery">
			<p>name: {details.name}</p>
			<p>gender: {details.gender}</p>
			<p>danceForm: {details.danceForm}</p>
			<p>email: {details.email}</p>
			<p>guardianContactNo: {details.guardianContactNo}</p>
			<p>address: {details.address}</p>
			<p>description: {details.description}</p>
			<p>createdDate: {details.createdDate}</p>
		</div>
	)
}

export default EachAdmissionQuery