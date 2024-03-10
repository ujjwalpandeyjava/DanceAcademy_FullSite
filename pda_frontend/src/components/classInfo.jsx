import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './classInfo.module.css'
import { Link } from 'react-router-dom';

function ClassInfo() {
  return (
    <div>
      <NavBar />
      <div>
        <h1 className={sheet.h1}>Ongoing Class</h1>
        <div id={sheet.classesTiming}>
          <div>
            <h2>Morning classes</h2>
            <div className={sheet.eachTiming}>
              <div>10:00 am -- 11:00 pm</div>
              <div>11:30 am -- 01:00 pm</div>
              <div>01:30 pm -- 3:00 pm <i>(new batch)</i></div>
            </div>
          </div>
          <div>
            <h2>Evening classes</h2>
            <div className={sheet.eachTiming}>
              <div>3:30 pm -- 5:00 pm</div>
              <div>05:30 pm -- 07:00 pm</div>
              <div>7:30 pm -- 9:00 pm</div>
            </div>
          </div>
        </div>
      </div>
      <div>
        <h1 className={sheet.h1}>Up-coming Class</h1>
        <div id={sheet.classesTiming}>
          <div>
            <h2>Morning classes</h2>
            <div className={sheet.eachTiming}>
              <div>10:00 am -- 11:00 pm</div>
              <div>11:30 am -- 01:00 pm</div>
              <div>01:30 pm -- 3:00 pm <i>(new batch)</i></div>
            </div>
          </div>
          <div>
            <h2>Evening classes</h2>
            <div className={sheet.eachTiming}>
              <div>3:30 pm -- 5:00 pm</div>
              <div>05:30 pm -- 07:00 pm</div>
              <div>7:30 pm -- 9:00 pm</div>
            </div>
          </div>
        </div>
      </div>
      <Link to="../contact" path='route' id={sheet.newBatchSoon} style={{ fontSize: '23px', fontFamily: 'Yeon Sung' }}>Connect for personal class. </Link>
      <FootBar />
    </div>
  )
}

export default ClassInfo