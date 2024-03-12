import { Link } from 'react-router-dom';
import logo from '../assets/pictures/logo__.png';
import sheet from './navBar.module.css';


export default function NavBar() {

	return (
		<div id={sheet.navbarCont}>
			<div id={sheet.heading}><Link relative='route' to="/"><img src={logo} alt='logo'/> Hollo-Loop</Link>
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