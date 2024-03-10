import { Link } from 'react-router-dom';
import sheet from './navBar.module.css';

export default function NavBar() {

	return (
		<div id={sheet.navbarCont}>
			<div id={sheet.heading}><Link relative='route' to="/">Pandey Dance Academy</Link>
				<nav id={sheet.navbar}>
					<Link relative='route' to="/home">Home</Link>
					<Link relative='route' to="/admission">Apply</Link>
					<Link relative='route' to="/classInfo">Classes</Link>
					<Link relative='route' to="/contact">Contact</Link>
				</nav>
			</div>
		</div>
	)
}