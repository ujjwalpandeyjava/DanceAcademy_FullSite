import { useRef } from 'react';
import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './apply.module.css';
import apiEndPoints from '../actions/api';

function Apply() {
  const name = useRef();
  const gender = useRef();
  const danceForm = useRef(null);
  const email = useRef();
  const contactNo = useRef();
  const guardianContactNo = useRef();
  const address = useRef();
  const description = useRef();

  function submitForm() {
    let admissionQuery = {
      name: name.current.value,
      gender: gender.current.value,
      danceForm: danceForm.current.value,
      email: email.current.value,
      contactNo: contactNo.current.value,
      guardianContactNo: guardianContactNo.current.value,
      address: address.current.value,
      description: description.current.value,
      createdDate: new Date(),
      status: "New"
    }
    console.log(admissionQuery);
    apiEndPoints.ADMISSION_QUERY_V1().save(admissionQuery)
      .then(async response => {
        if (response.status === 200) {
          response = await response.data;
          alert(response.Message)
          resetForm()
        }
      })
      .catch(error => {
        if (error.response.status === 400) {
          console.log(error.response.data);
          let m = "";
          for (const key in error.response.data) {
            if (error.response.data.hasOwnProperty(key))
              m += `${error.response.data[key]}\n`
          }
          alert(m)
        }
      });
  }

  function resetForm() {
    name.current.value = null;
    danceForm.current.value = gender.current.value = email.current.value = contactNo.current.value = guardianContactNo.current.value = address.current.value = description.current.value = null;
  }
  return (
    <div>
      <NavBar />
      <h1 className={sheet.h1}>Apply For classes</h1>
      <div id={sheet.contactBox}>
        <div className={sheet.formGroup}>
          <label htmlFor="name">Full Name: </label>
          <input id={sheet.name} type="text" name="name" placeholder="First + Last Name" ref={name} />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="gender">Gender:</label>
          <select id={sheet.decotare} name="gender" ref={gender} defaultValue="">
            <option value="" disabled>Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Others</option>
          </select>
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="danceForm">Choose a Dance Form:</label>
          <select id={sheet.decotare} name="danceForm" ref={danceForm} defaultValue="">
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
            <option value="Other">Other</option>
          </select>
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="e-main-id">E-mail: </label>
          <input id="e-main-id" type="email" name="e-main-id" ref={email} placeholder="To update" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="phone_no">Phone no: </label>
          <input id="tele-no" type="tel" name="phn_no" ref={contactNo} placeholder="Student contact number" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="gardien_phone_no.">Gardien contact no: </label>
          <input id="tele-no" type="tel" name="gardien_phone_no" ref={guardianContactNo} placeholder="Guardian/Secondary contact number" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="address">Address: </label>
          <input id="tele-no" type="text" name="address" ref={address} placeholder="Full Address" />
        </div>
        <div className={sheet.formGroup}>
          <label htmlFor="e-main-id">Description:</label>
          <i>(optional) </i><textarea id={sheet.message} name="extra detail" rows="7" cols="20" ref={description} placeholder="Description for better understanding, like motivations."></textarea>
        </div>
        <button id={sheet.submitButton} onClick={submitForm}>Apply</button>
      </div>
      <FootBar />
    </div>
  )
}

export default Apply