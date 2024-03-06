import React from 'react';
import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './classInfo.module.css'

function ClassInfo() {
  return (
    <div>
      <NavBar />
      <h1 class={sheet.h1}>Class timings </h1>
      <div id={sheet.classesTiming}>
        <div>
          <h2>Morning classes</h2>
          <div class={sheet.eachTiming}>
            <div>10:00 am -- 11:00 pm</div>
            <div>11:30 am -- 01:00 pm</div>
            <div>01:30 pm -- 3:00 pm <i>(new batch)</i></div>
          </div>
        </div>
        <div>
          <h2>Evening classes</h2>
          <div class={sheet.eachTiming}>
            <div>3:30 pm -- 5:00 pm</div>
            <div>05:30 pm -- 07:00 pm</div>
            <div>7:30 pm -- 9:00 pm</div>
          </div>
        </div>
      </div>
      <div id={sheet.newBatchSoon} style={{ fontSize: '23px', fontFamily: 'Yeon Sung' }}>New batches soon</div>
      <FootBar />
    </div>
  )
}

export default ClassInfo