import { useEffect, useState } from 'react';
import { GiHamburgerMenu } from "react-icons/gi";
import { Link } from 'react-router-dom';
import logo from '../assets/pictures/logo__.png';
import sheet from './navBar.module.css';


export default function NavBar() {
	const [isOpen, setIsOpen] = useState(false);
	const [smallWidth, setSmallWidth] = useState(false)

	useEffect(() => {
		widthChange();
		window.addEventListener('resize', widthChange);
		return () => {
			window.removeEventListener('resize', widthChange);
		};
	}, [])

	function widthChange() {
		if (window.innerWidth < 650)
			setSmallWidth(true);
		else
			setSmallWidth(false)
	}
	return (
		<div id={sheet.heading}>
			<Link relative='route' to="/"><img src={logo} alt='logo' /> Hollo-Loop</Link>
			{smallWidth === true ?
				<nav className={sheet.navBurgerSection} onClick={() => setIsOpen(!isOpen)}>
					<GiHamburgerMenu className={sheet.navBurger} />
					<nav className={`${sheet.navBurgerLinks} ${isOpen ? sheet.navBurgerLinksActive : ""}`}>
						<Link relative='route' to="/home">Home</Link>
						<Link relative='route' to="/admission">Apply</Link>
						<Link relative='route' to="/classInfo">Classes</Link>
						<Link relative='route' to="/contact">Contact</Link>
					</nav>
				</nav> :
				<nav className={sheet.nav}>
					<Link relative='route' to="/home">Home</Link>
					<Link relative='route' to="/admission">Apply</Link>
					<Link relative='route' to="/classInfo">Classes</Link>
					<Link relative='route' to="/contact">Contact</Link>
				</nav>}
		</div>
	)
}