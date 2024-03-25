import { Fragment, useEffect, useState } from "react";
import { toast } from "react-toastify";
import apiEndPoints from "../../actions/api";
import Head from "../miniComp/Head";
import EachAdmissionQuery from "./EachAdmissionQuery";

function Admission() {
  const [pageNo, setPageNo] = useState(0);
  const [status, setStatus] = useState("Contacted");
  const [admissions, setAdmissions] = useState(null);

  useEffect(() => {
    const body = {
      "pageNo": pageNo,
      "pageSize": 4,
      "sort": -1,
      "sortByKey": "createdDate",
      "status": status  // New, Contacted, Resolved, Admitted
    }

    apiEndPoints.ADMISSION_QUERY_V1().fetchPaginated(body)
      .then(res => {
        // console.log(res);
        if (res.status === 200)
          return res.data;
        else {
          toast.error(res.data.Message)
        }
        // else {
        // alert(res.data)
        // }
      })
      .then((res) => {
        setAdmissions(res);
      })
      .catch((err) => {
        console.error(err)

      });

    return () => {
      setAdmissions(null);
    }
  }, [pageNo, status])



  return (
    <Fragment>
      <Head title={"Admission"} />
      <div className="admission">
        {!admissions ? (<h5>Loading...</h5>) :



          (<div>
            {admissions.empty === true ?
              <h3>No more queries</h3> :
              (<div>
                <h4>Potential students {`${admissions.numberOfElements}/${admissions.totalElements}`}</h4>
                {admissions.content.map(query => <EachAdmissionQuery status={status} query={query} key={query.id} />)}
              </div>)}
            <div className="actionButtons">
              <button disabled={admissions.first} onClick={() => setPageNo((pre) => pre -= 1)}>Prev</button>
              {<div>
                Page: {pageNo} for: <select value={status} onChange={(event) => setStatus(event.target.value)}>
                  <option value="New">New</option>
                  <option value="Contacted">Contacted</option>
                  <option value="Resolved">Resolved</option>
                </select>
              </div>}
              <button disabled={admissions.last} onClick={() => setPageNo((pre) => pre += 1)}>Next</button>
            </div>
          </div>)}



      </div>

    </Fragment>
  )
}

export default Admission