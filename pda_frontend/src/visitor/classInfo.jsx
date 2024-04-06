import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './classInfo.module.css'
import { Link } from 'react-router-dom';
import apiEndPoints from '../actions/api';
import { useEffect, useState } from 'react';
import moment from 'moment';

function ClassInfo() {
  const [classData, setClassDate] = useState(null);

  useEffect(() => {
    classes();
    return () => { }
  }, [])

  function classes() {
    apiEndPoints.CLASSES().getClasses()
      .then(response => {
        if (response.status === 200) {
          return response.data;
        } else alert(JSON.stringify(response.data))
      })
      .then(resp => {
        console.log(resp);
        setClassDate(resp);
      })
      .catch(error => {
        console.error(error)
      })
  }

  return (
    <div>
      <NavBar />
      {classData ?
        <>
          {classData?.onGoing?.length > 0 &&
            <div className={sheet.latestClasses}>
              <h1 className={sheet.h1}>On-Going Batches</h1>
              <table>
                <thead>
                  <tr>
                    <th>So.</th>
                    <th>Starting from</th>
                    <th>Estimated class Start-time: </th>
                    <th>Duration</th>
                    <th>Note</th>
                  </tr>
                </thead>
                <tbody>
                  {classData.onGoing.map((eachClass, index) =>
                    <tr key={eachClass.id} >
                      <td>{index + 1}</td>
                      <td>{moment(eachClass.batchStartDateTime).format("dddd, DD/MM/YYYY")}</td>
                      <td>{moment(eachClass.batchStartDateTime).format("hh:mm a")}</td>
                      <td>{eachClass.durationInMin} mins</td>
                      <td className={sheet.notes} >{eachClass.note}</td>
                      {/* <p><button onClick={() => delete (eachClass.id)}>Delete</button></p> */}
                    </tr>
                  )}
                </tbody>
              </table>
            </div>}
          {classData?.upComing?.length > 0 &&
            <div className={sheet.latestClasses}>
              <h1 className={sheet.h1}>Up-Coming new Batches</h1>
              <table>
                <thead>
                  <tr>
                    <th>So.</th>
                    <th>Starting from</th>
                    <th>Estimated class Start-time: </th>
                    <th>Duration</th>
                  </tr>
                </thead>
                <tbody>
                  {classData.upComing.map((eachClass, index) =>
                    <tr key={eachClass.id} title={eachClass.noe}>
                      <td>{index + 1}</td>
                      <td>{moment(eachClass.batchStartDateTime).format("dddd, DD/MM/YYYY")}</td>
                      <td>{moment(eachClass.batchStartDateTime).format("hh:mm a")}</td>
                      <td>{eachClass.durationInMin} mins</td>
                      <td className={sheet.notes} >{eachClass.note}</td>
                      {/* <p><button onClick={() => delete (eachClass.id)}>Delete</button></p> */}
                    </tr>
                  )}
                </tbody>
              </table>
            </div>}
        </> : <h1>Loading...</h1>
      }
      <Link to="../contact" path='route' id={sheet.newBatchSoon} style={{ fontSize: '23px', fontFamily: 'Yeon Sung', margin: "2rem" }}>Connect for personal class. </Link>
      <FootBar />
    </div>
  )
}

export default ClassInfo