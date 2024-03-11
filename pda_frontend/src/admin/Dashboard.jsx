import Head from './miniComp/Head'
import './Admin.scss';
import { Link } from 'react-router-dom';

export default function Dashboard() {
  return (
    <div>
      <Head title={"Dashboard"} />
      <div className="dashboard">
        <section className="blocks" >
          <h2>Admissions</h2>
          Lorem ipsum dolor sit amet consectetur.
          <Link to={"../Admissions"}>See all</Link>
        </section>
        <section className="blocks" >
          <h2>Admissions</h2>
          Lorem ipsum dolor sit amet.
          <Link to={"../Admissions"}>See all</Link>
        </section>
        <section className="blocks" >
          <h2>Queries</h2>
          <p>Lorem ipsum dolor sit.</p>
          <Link to={"../queries"}> See all</Link>
        </section>

        <section className="blocks" >
          <h2>Queries</h2>
<div>Graph </div>          
        </section>
      </div>
    </div>
  )
}