import { useEffect, useState } from "react";
import apiEndPoints from "../../actions/api";
import { fallingLines } from "../../components/global/Utlity";
import Head from "../miniComp/Head";
import AddNewAction from './AddNewAction';
import ListOfUsers from './ListOfUsers';

export default function Users() {
  const [users, setUsers] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    apiEndPoints.USERS().getAll({
      "abd": "ddfd"
    }).then(response => {
      // console.log(response);
      if (response.status === 200) {
        return response.data;
      } else {
        console.warn(response);
      }
    }).then(response => {
      // console.log(response, response.users);
      setUsers(response,);
    }).catch(error => {
      console.error(error, error.message);
    })
      .finally(() =>
        setIsLoading(false))
    return () => {
      // setUsers(null);
    }
  }, [])

  return (
    <div>
      <Head title={"Users"} />
      <div className="users">
        <AddNewAction />
        {users === null || isLoading ? fallingLines() : <div className="usersList">
          {users?.users?.length < 1 ?
            <h2>No user found!</h2> :
            <table>
              <thead>
                <tr>
                  <th>E-Mail</th>
                  <th>account Non-Expired</th>
                  <th>account Non-Locked</th>
                  <th>credentials Non-Expired</th>
                  <th>enabled</th>
                  <th>created Date</th>
                  <th>Authorities</th>
                </tr>
              </thead>
              <tbody>
                {users.users.map((userData, index) => <ListOfUsers key={userData.id} userData={userData} />)}
              </tbody>
            </table>
          }
        </div>}
      </div>
    </div>
  )
}