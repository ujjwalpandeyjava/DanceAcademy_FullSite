import { Outlet } from 'react-router-dom'
import { Fragment } from 'react';
import { ToastContainer } from 'react-toastify';

function App() {

	return (
		<Fragment>
			<Outlet />
			<ToastContainer />
		</Fragment>
	)
}

export default App;