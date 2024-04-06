import React, { Fragment } from 'react';
import ReactDOM from 'react-dom/client';
import Apply from './visitor/apply';
import ClassInfo from './visitor/classInfo';
import Contact from './visitor/contact';
import Home from './visitor/home';
// import { Provider } from 'react-redux';
import { Outlet, Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';
import Admin from './admin/Admin.jsx';
import Dashboard from './admin/Dashboard.jsx';
import LoginForm from './admin/LoginForm.jsx';
import Queries from './admin/queries/Queries.jsx';
import Admission from './admin/admission/Admission.jsx';
import Classes from './admin/classes/Classes.jsx';
import AddNewUser from './admin/users/AddNewUser.jsx';
import Users from './admin/users/Users.jsx';
import App from './app';
import './visitor/global/index.css';

document.title = "Pandey Dance Academy";
const routesWithJSX = createBrowserRouter(
  createRoutesFromElements(
    <Route path="" element={<App />} >
      <Route path="" element={<Home />} />
      <Route path="home" element={<Home />} />
      <Route path="user" element={<Outlet />}>
        <Route path="" element={<LoginForm />} />
        <Route path="login" element={<LoginForm />} />
        <Route path="admin" element={<Admin />}>
          <Route path="" element={<Dashboard />} />
          <Route path="admissions" element={<Admission />} />
          <Route path="classes" element={<Classes />} />
          <Route path="queries" element={<Queries />} />
          <Route path='users' element={<Outlet />}>
            <Route path="" element={<Users />} />
            <Route path="addUser" element={<AddNewUser />} />
          </Route>
          <Route path='*' element={<h2>Page Option Not found</h2>} />
        </Route>
      </Route>
      <Route path="admission" element={<Apply />} />
      <Route path="contact" element={<Contact />} />
      <Route path="classInfo" element={<ClassInfo />} />
      <Route path='*' element={<h2>Page Not found</h2>} />
    </Route>
  ),
);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Fragment>
    {/* <Provider store={reduxStore}> */}
    <RouterProvider router={routesWithJSX} />
    {/* </Provider> */}
  </Fragment>
);