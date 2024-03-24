import { Fragment } from 'react';
import { Outlet } from 'react-router-dom';
import { Slide, ToastContainer } from 'react-toastify';

function App() {

	return (
		<Fragment>
			<Outlet />
			<ToastContainer
				position="bottom-right"
				autoClose={7003}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick={true}
				rtl={false}
				pauseOnFocusLoss
				draggable={true}
				pauseOnHover={true}
				theme="light"
				transition={Slide}
			/>
		</Fragment>
	)
}

export default App;