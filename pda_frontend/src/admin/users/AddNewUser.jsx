import { useRef, useState } from "react";
import { SiBytedance } from "react-icons/si";
import sheet from '../LoginForm.module.css';
import Head from "../miniComp/Head";
import apiEndPoints from "../../actions/api";
import { toast } from "react-toastify";
function AddNewUser() {
  const username = useRef();
  const password = useRef();
  const password2 = useRef();
  const [errorMessages, setErrorMessages] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const validateForm = () => {
    const errors = {};
    if (!username?.current?.value)
      errors.name = 'Username is required';
    if (!password?.current?.value)
      errors.pass = 'Password is required';
    if (!password2.current.value || password?.current?.value !== password2.current.value)
      errors.pass2 = 'Confirm Password must match password';
    setErrorMessages(errors);
    return Object.keys(errors).length === 0;
  };
  const resetForm = () => {
    username.current.value = password.current.value = password2.current.value = "";
  }
  const handleSubmit = (event) => {
    event.preventDefault();
    if (validateForm()) {
      setIsLoading(true)
      apiEndPoints.AUTH().registerNewUser({
        emailID: username.current.value,
        defaultPassword: password.current.value
      })
        .then(e => {
          if (e.status === 202) {
            resetForm();
            return e.data;
          }
          else if (e.status === 208)
            toast.error(`User: '${username.current.value}' already exists`);

          console.log(e)
        })
        .then(resp => {
          if (!resp)
            return;

          console.log(resp);
          toast.success(resp.Message + " - " + resp.Action);
          // resp.UserID
        })
        .catch(e => console.error(e.message))
        .finally(a => {
          setIsLoading(false);
        });
    }
  };

  return (
    <div>
      <Head title={"Add New USer"} />
      <div className={sheet.loginForm}>
        <form onSubmit={console.log("--")}>
          <SiBytedance className={sheet.heading} />
          <div className={sheet.inputContainer}>
            <label className="requiredStar">Email ID:</label>
            <input type="email" name="uname" ref={username} />
            {errorMessages.name && <div className={sheet.error}>{errorMessages.name}</div>}
          </div>
          <div className={sheet.inputContainer}>
            <label className="requiredStar">Default Password</label>
            <input type="password" name="pass" ref={password} />
            {errorMessages.pass && <div className={sheet.error}>{errorMessages.pass}</div>}
          </div>
          <div className={sheet.inputContainer}>
            <label className="requiredStar">Confirm Password</label>
            <input type="password" name="pass" ref={password2} />
            {errorMessages.pass2 && <div className={sheet.error}>{errorMessages.pass2}</div>}
          </div>
          <button disabled={isLoading} type="submit" className={sheet.submitBtn} value="Register" onClick={handleSubmit}>Register</button>
        </form>
      </div>

    </div>
  )
}

export default AddNewUser