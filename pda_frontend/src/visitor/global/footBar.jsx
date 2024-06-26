import { FaMapLocationDot } from 'react-icons/fa6'
import { SiCloudfoundry } from 'react-icons/si'
import { Link } from 'react-router-dom'
import sheet from './footBar.module.css'

function FootBar() {
	return (
		<div>
			<section id={sheet.about}>
				<div className={sheet.connect}>
					<p>support@pandeyDanceAcademy.com</p>
					<p>110-987-543 <i>(toll-free)</i></p>
					<p>+91-9876-543-210</p>
				</div>
				<div className={sheet.whose}>
					<p> <b>Establishment On: </b><SiCloudfoundry size={"1.2em"} />15 January 1998</p>
					<p> <b>Owner: </b><i>Ujjwal Pandey</i></p>
					<p>pandey@pandeyDanceAcademy.com</p>
				</div >
				<div className={sheet.address}>
					<div><FaMapLocationDot size={"1.2em"} /> Building no-43, near mate park, Saket, new Delhi-110002</div>
					<Link to="../user/login" relative='route'>Login</Link>
				</div>
			</section>
			<footer id={sheet.footer}>
				<p>Copyright @2020 | Hollo-Loop.com | All right reserved</p>
			</footer>
		</div>
	)
}

export default FootBar