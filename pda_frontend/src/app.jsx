import { Outlet } from 'react-router-dom'
import apiEndPoints from './actions/api';

function App() {
	apiEndPoints.SIMPLE().home()
		.then(response => {
			console.log(response);
			return response.data
		})
		.then(response => {
			console.log(response);
		})
		.catch(error => {
			console.error(error.message)
		});
	return (
		<Outlet />
	)
}

export default App;