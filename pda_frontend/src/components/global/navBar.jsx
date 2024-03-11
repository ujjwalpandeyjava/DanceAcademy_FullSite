import { Link } from 'react-router-dom';
import sheet from './navBar.module.css';
import { GiGloop } from "react-icons/gi";import { TbCurlyLoop } from "react-icons/tb";
import logo from '../assets/pictures/logo__.png'


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