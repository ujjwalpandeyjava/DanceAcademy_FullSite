import { Fragment, useEffect, useState } from "react"
import apiEndPoints from "../../actions/api"
import Head from "../miniComp/Head"
import EachAdmissionQuery from "./EachAdmissionQuery";

function Admission() {
  const [pageNo, setPageNo] = useState(1);
  const [admissions, setAdmissions] = useState(null);

  useEffect(() => {
    const parms = {
      "pageNo": pageNo,
      "pageSize": 6,
      "sort": 1,
      "sortByKey": "createdDate"
    }
   
    apiEndPoints.SIMPLE().home();
    /*apiEndPoints.ADMISSION_QUERY_V1().fetchPaginated(parms)
      .then((res) => {
        // console.log(res);
        if (res.status === 200)
          return res.data;
        else {
          alert(res.data)
        }
      })
      .then((res) => {
        console.log(res);
        setAdmissions(res);
      })
      .catch((err) => {
        console.error(err.message);
      });
    return () => {
      setAdmissions(null);
    }
    */
  }, [pageNo])



  return (
    <Fragment>
      <Head title={"Admission"} />
      <div className="admission">
        {!admissions ? (<h5>Loading...</h5>) : (<div>
          {admissions.empty === false ?
            (<div>
              <h4>Potential students {`${admissions.numberOfElements}/${admissions.totalElements}`}</h4>
              {admissions.content.map((eachCont) => {
                console.log(eachCont);
                return <EachAdmissionQuery details={eachCont} />
              })}
              <div>
                <button disabled={pageNo === 0} onClick={() => setPageNo((pre) => pre -= 1)}>Prev</button>
                {pageNo}
                <button disabled={pageNo === admissions.totalPages - 1} onClick={() => setPageNo((pre) => pre += 1)}>Next</button>
              </div>
            </div>)
            : <h2>No more queries</h2>
          }
        </div>)}
        {/* {JSON.stringify(admissions)} */}
      </div>

    </Fragment>
  )
}

export default Admission