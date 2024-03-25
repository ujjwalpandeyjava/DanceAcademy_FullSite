import { Fragment, useEffect, useState } from "react";
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
      console.log(response);
      if (response.status === 200) {
        return response.data;
      } else {
        console.warn(response);
      }
    }).then(response => {
      console.log(response);
      setUsers(response);
    }).catch(error => {
      console.error(error, error.message);
    })
      .finally(() =>
        setIsLoading(false))
    return () => {
      setUsers(null);
    }
  }, [])

  return (
    <div>
      <Head title={"Users"} />
      <div className="users">
        {users === null || isLoading ? fallingLines() :
          (<Fragment>
            <AddNewAction />
            <ListOfUsers users={users.users} />
          </Fragment>)}
      </div>
    </div>
  )
}