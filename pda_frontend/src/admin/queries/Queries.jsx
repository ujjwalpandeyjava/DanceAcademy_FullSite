import { useEffect, useState } from "react";
import Head from "../miniComp/Head";
import apiEndPoints from "../../actions/api";
import { fallingLines } from "../../visitor/global/Utlity";
import EachQuery from "./EachQuery";


export default function Queries() {
  const [queries, setQueries] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    apiEndPoints.ALL_QUERY().getAll()
      .then(response => {
        // console.log(response);
        if (response.status === 200) {
          return response.data;
        } else
          console.warn(response);
      })
      .then(response => {
        console.log(response);
        setQueries(response);
      })
      .catch(error => {
        console.error(error, error.message);
      })
      .finally(() =>
        setIsLoading(false))
  }, [])



  return (
    <div>
      <Head title={"Queries"} />
      <div className="queries">
        {queries === null || isLoading ? fallingLines() : <div className="queryList">
          {queries?.queries?.length < 1  || queries?.length < 1?
            <h2>No query found!</h2> :
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>E - mail</th>
                  <th>Contact</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>Asked on at</th>
                  <th>Solved on</th>
                </tr>
              </thead>
              <tbody>
                {queries.map((query, index) => <EachQuery key={query.id} query={query} />)}
              </tbody>
            </table>
          }
        </div>}
      </div>
    </div>
  )
}