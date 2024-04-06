import { useRef } from "react"
import apiEndPoints from "../../actions/api"
import { toast } from 'react-toastify';

function NewClass() {
  const dateTime = useRef(null);
  const duration = useRef(null);
  const note = useRef(null);

  function reset() {
    dateTime.current.value = null;
    duration.current.value = 45;
    note.current.value = null;
  }
  function saveNew() {
    apiEndPoints.CLASSES().addNewClass({
      body: {
        batchStartDateTime: dateTime.current.value,
        durationInMin: duration.current.value,
        note: note.current.value,
      }
    })
      .then(response => {
        if (response.status === 201) {
          toast.success("Save successfully!")
          reset();
        }
      })
      .catch(error => {
        if (error.response.status === 400) {
          if (error.response.status === 400) {
            for (const key in error.response.data)
              if (error.response.data.hasOwnProperty(key))
                toast.warn(`${key}: ${error.response.data[key]}`);
          }
        }

      })
  }
  return (
    <div style={{margin: "auto", display: "block", width: "600px"}}>
      <h2>Add New Class</h2>
      <div className="addClassForm">
        <div>
          <label htmlFor="datetime" className={"requiredStar"}>Start Date Time</label>
          <input type="datetime-local" name="dateTime" id="dateTime" ref={dateTime} />
        </div>
        <div>
          <label htmlFor="duration" className={"requiredStar"}>Duration</label>
          <input type="number" name="duration" id="duration" ref={duration} defaultValue={45} />
        </div>
        <div>
          <label htmlFor="note" className={"requiredStar"}>Note for viewers</label>
          <input type="text" name="note" id="note" ref={note} placeholder="Contact to check for changes" />
        </div>
        <button onClick={saveNew}>Save</button>
      </div>
    </div>
  )
}

export default NewClass