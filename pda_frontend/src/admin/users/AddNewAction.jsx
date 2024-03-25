import { Link } from "react-router-dom"

function AddNewAction() {
  return (
    <div className="AddNewAction">
      <h3>Control Users</h3>
      <Link to={"./addUser"} relative="path" className="addNewUserBtn">Add New User</Link>
    </div>
  )
}

export default AddNewAction