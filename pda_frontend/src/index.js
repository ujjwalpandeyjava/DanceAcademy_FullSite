import React, { Fragment } from 'react';
import ReactDOM from 'react-dom/client';
import Home from './components/home';
import Apply from './components/apply';
import Contact from './components/contact';
import ClassInfo from './components/classInfo';
// import { Provider } from 'react-redux';
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';
import App from './app';
import './components/global/index.css';


document.title = "Health Companion";
const routesWithJSX = createBrowserRouter(
  createRoutesFromElements(
    <Route path="" element={<App />} >
      <Route path="" element={<Home />} />
      <Route path="home" element={<Home />} />
      <Route path="apply" element={<Apply />} />
      <Route path="contact" element={<Contact />} />
      <Route path="classInfo" element={<ClassInfo />} />
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