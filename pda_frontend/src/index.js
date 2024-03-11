import React, { Fragment } from 'react';
import ReactDOM from 'react-dom/client';
import Apply from './components/apply';
import ClassInfo from './components/classInfo';
import Contact from './components/contact';
import Home from './components/home';
// import { Provider } from 'react-redux';
import { Outlet, Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';
import Admin from './admin/Admin.jsx';
import Admission from './admin/admission/Admission.jsx';
import Classes from './admin/Classes.jsx';
import Dashboard from './admin/Dashboard.jsx';
import LoginForm from './admin/LoginForm.jsx';
import Queries from './admin/Queries.jsx';
import RegisterForm from './admin/RegisterForm.jsx';
import App from './app';
import './components/global/index.css';


document.title = "Pandey Dance Academy";
const routesWithJSX = createBrowserRouter(
  createRoutesFromElements(
    <Route path="" element={<App />} >
      <Route path="user" element={<Outlet />}>
        <Route path="" element={<LoginForm />} />
        <Route path="login" element={<LoginForm />} />
        <Route path="register" element={<RegisterForm />} />
        <Route path="admin" element={<Admin />}>
          <Route path="" element={<Dashboard />} />
          <Route path="admissions" element={<Admission />} />
          <Route path="classes" element={<Classes />} />
          <Route path="queries" element={<Queries />} />
          <Route path='*' element={<h2>Page Option Not found</h2>} />
        </Route>
      </Route>
      <Route path="" element={<Home />} />
      <Route path="home" element={<Home />} />
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
    {/* <React.StrictMode> */}
    {/* <Provider store={reduxStore}> */}
    <RouterProvider router={routesWithJSX} />
    {/* </Provider> */}
    {/* </React.StrictMode> */}
  </Fragment>
);