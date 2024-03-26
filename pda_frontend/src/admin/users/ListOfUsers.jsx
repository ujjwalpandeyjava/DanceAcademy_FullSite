import moment from "moment";
import { useRef } from "react";
import { toast } from "react-toastify";
import apiEndPoints from '../../actions/api';

function ListOfUsers({ userData }) {
  const accountNonExpired = useRef();
  const accountNonLocked = useRef();
  const credentialsNonExpired = useRef();
  const enabled = useRef();

  function updateData() {
    apiEndPoints.USERS().updateUser(userData.id, {
      params: {
        accountNonExpired: accountNonExpired.current.value,
        accountNonLocked: accountNonLocked.current.value,
        credentialsNonExpired: credentialsNonExpired.current.value,
        enabled: enabled.current.value
      }
    })
      .then(resp => {
        console.log(resp);
        if (resp.status === 202)
          return resp.data;
        else
          toast(resp.data.Message)
      })
      .then(resp => {
        toast.success(resp.Message)
      })
      .catch(error => {
        console.error("=== ", error);
        toast.error("Error: " + error.message)
      })
      .finally(() => { })

  };

  return (
    <tr key={userData.id}>
      <td>{userData.username}</td>
      <td>
        <select defaultValue={userData.accountNonExpired} ref={accountNonExpired} onChange={updateData}>
          <option value={true}>Non-Expired</option>
          <option value={false}>Expire Account</option>
        </select>
      </td>
      <td>
        <select defaultValue={userData.accountNonLocked} ref={accountNonLocked} onChange={updateData}>
          <option value={true}>Account in Working</option>
          <option value={false}>Account Locked</option>
        </select>
      </td>
      <td>
        <select defaultValue={userData.credentialsNonExpired} ref={credentialsNonExpired} onChange={updateData}>
          <option value={true}>Credentials workable</option>
          <option value={false}>Expire Credentials</option>
        </select>
      </td>
      <td>
        <select defaultValue={userData.enabled} ref={enabled} onChange={updateData}>
          <option value={true}>Enable User</option>
          <option value={false}>Disable User</option>
        </select>
      </td>
      <td>{moment(userData.createdDateTime).format("DD-MM-YYYY")}</td>
      <td>{userData.authorities.map(a => a.authority).join(', ')}</td>
    </tr>
  );
};

export default ListOfUsers