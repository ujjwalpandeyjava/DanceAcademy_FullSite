import React, { useState } from 'react';
import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './apply.module.css';

function Apply() {
  const [genderValue, setGenderValue] = useState("");
  const [danceForm, setDanceForm] = useState("");

  function submitForm() {
    console.log({ genderValue, danceForm });
  }
  return (
    <div>
      <NavBar />
      <h1 className={sheet.h1}>Apply For classes</h1>
      <div id={sheet.contactBox}>
        <div className={sheet.formGroup}>
          <label htmlFor="name">Full Name: </label>
          <input id={sheet.name} type="text" name="name" placeholder="Enter your name" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="gender">Gender:</label>
          <select id={sheet.decotare} name="gender" defaultValue={genderValue} onChange={(e) => setGenderValue(e.target.value)}>
            <option value="" disabled>Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Others</option>
          </select>
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="danceForm">Choose a Dance Form:</label>
          <select id={sheet.decotare} name="danceForm" defaultValue={danceForm} onChange={(e) => setDanceForm(e.target.value)}>
            <option value="" disabled >Select a Dance form</option>
            <option value="Belly dance">Belly dance</option>
            <option value="Gangnam Style">Gangnam Style</option>
            <option value="Yangko Dance">Yangko Dance</option>
            <option value="Salsa">Salsa</option>
            <option value="Ballet">Ballet</option>
            <option value="Kathak">Kathak</option>
            <option value="B-boying">B-boying</option>
            <option value="Break Dance">Break Dance</option>
            <option value="Line dance">Line dance</option>
          </select>
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="e-main-id">E-mail: </label>
          <input id="e-main-id" type="email" name="e-main-id" placeholder="Enter your e-mail id" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="phone_no">Phone no: </label>
          <input id="tele-no" type="tel" name="phn_no" placeholder="Enter contact number" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="gardien_phone_no.">Gardien contact no: </label>
          <input id="tele-no" type="tel" name="gardien_phone_no" placeholder="Enter Gardien contact number" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="address">Address: </label>
          <input id="tele-no" type="text" name="address" placeholder="Enter address" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="e-main-id">Some extra details:</label>
          <i>(optional) </i><textarea id={sheet.message} name="extra detail" rows="7" cols="20" placeholder="Enter something you want to say."></textarea>
        </div>
        <button id={sheet.submitButton} onClick={submitForm}>Apply</button>
      </div>
      <FootBar />
    </div>
  )
}

export default Apply