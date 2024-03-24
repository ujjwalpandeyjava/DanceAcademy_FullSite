import { FaArrowRight, FaChevronRight, FaRegHandPointRight } from "react-icons/fa";
import { Link } from 'react-router-dom';
import Head from './miniComp/Head';

export default function Dashboard() {
  return (
    <div>
      <Head title={"Dashboard"} />
      <div className="dashboard">

        <section className="blocks" >
          <div className="details">
            <h2>Admissions</h2>
            <div>
              <p>- Online applications</p>
              <p>- Existing students</p>
            </div>
          </div>
          <Link to={"./admissions"}><button className="button-48">
            <span className="text">Check out <FaArrowRight /></span>
          </button></Link>
        </section>

        <section className="blocks" >
          <div className="details">
            <h2>Classes</h2>
            <div>
              <p>- Existing classes</p>
              <p>- Add new upcoming classes</p>
            </div>
          </div>
          <Link to={"./classes"}>
            <button className="button-48">
              <span className="text">update <FaChevronRight /></span>
            </button>
          </Link>
        </section>

        <section className="blocks" >
          <div className="details">
            <h2>Queries</h2>
            <div>
              <p>- Online Queries</p>
              <p>- Connect with the users</p>
              <p>- Solve queries</p>
            </div>
          </div>
          <Link to={"./queries"}>
            <button className="button-48">
              <span className="text">More <FaRegHandPointRight /></span>
            </button>
          </Link>
        </section>

        {/* <section className="blocks" >
          <div className="details">
            <h2>Queries</h2>
            <div>Graph - O </div>
          </div>
        </section> */}
      </div>
    </div>
  )
}