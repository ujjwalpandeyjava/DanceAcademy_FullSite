import React from 'react';
import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './apply.module.css';

function Apply() {
  return (
    <div>
      <NavBar />
      <h1 className={sheet.h1}>Apply for classes</h1>
      <div id={sheet.contactBox}>
        <form action="/apply" method="post">
          <div className="form-group"><label for="name">Full Name: </label><input id={sheet.name} type="text" name="name" placeholder="Enter your name" /></div>
          <div className="form-group">
            <label for="gender">Gender:</label>
            <select id={sheet.decotare} name="gender">
              <option value="" disabled="" selected="">Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Others</option>
            </select>
          </div>
          <div className="form-group"><label for="danceForm">Choose a Dance Form:</label><select id={sheet.decotare}
            name="danceForm">
            <option value="" disabled="" selected="">Select a Dance form</option>
            <option value="Belly dance">Belly dance</option>
            <option value="Gangnam Style">Gangnam Style</option>
            <option value="Yangko Dance">Yangko Dance</option>
            <option value="Salsa">Salsa</option>
            <option value="Ballet">Ballet</option>
            <option value="Kathak">Kathak</option>
            <option value="B-boying">B-boying</option>
            <option value="Break Dance">Break Dance</option>
            <option value="Line dance">Line dance</option>
          </select></div>
          <div className="form-group"><label for="e-main-id">E-mail: </label><input id="e-main-id" type="email"
            name="e-main-id" placeholder="Enter your e-mail id" /></div>
          <div className="form-group"><label for="phone_no">Phone no: </label><input id="tele-no" type="tel" name="phn_no"
            placeholder="Enter contact number" /></div>
          <div className="form-group"><label for="gardien_phone_no.">Gardien contact no: </label><input id="tele-no"
            type="tel" name="gardien_phone_no" placeholder="Enter Gardien contact number" /></div>
          <div className="form-group"><label for="address">Address: </label><input id="tele-no" type="text" name="address"
            placeholder="Enter address" /></div>
          <div className="form-group"><label for="e-main-id">Some extra details:</label><i>(optional) </i><textarea
            id={sheet.message} name="extra detail" rows="7" cols="20"
            placeholder="Enter something you want to say."></textarea></div><input type="submit" value="Apply"
              id={sheet.submitButton} />
        </form>
      </div>
      <FootBar />
    </div>
  )
}

export default Apply