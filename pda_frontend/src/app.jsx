import { Outlet } from 'react-router-dom'
import apiEndPoints from './actions/api';
import { Fragment } from 'react';
import { ToastContainer } from 'react-toastify';

function App() {
	apiEndPoints.SIMPLE().home()
		.then(response => {
			console.log(response);
			return response.data
		})
		.then(response => {
			// console.log(response);
		})
		.catch(error => {
			console.error(error.message)
		});
	return (
		<Fragment>
			<Outlet />
			<ToastContainer />
		</Fragment>
	)
}

export default App;