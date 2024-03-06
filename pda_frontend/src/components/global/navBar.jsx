import React from 'react'
import sheet from './navBar.module.css';


export default function NavBar() {
	
	return (
		<div>
			<div id={sheet.heading}>Pandey Dance Academy</div>
			<nav id={sheet.navbar}>
				<ul>
					<li><a href="/">Home</a></li>
					<li><a href="/apply">Apply</a></li>
					<li><a href="/classInfo">Classes</a></li>
					<li><a href="/home#sponsers">Sponsers</a></li>
					<li><a href="/contact">Contact</a></li>
				</ul>
			</nav>
		</div>
	)
}