import React, { Fragment } from 'react';
import NavBar from './global/navBar';
import FootBar from './global/footBar';
import sheet from './home.module.css';



import img0 from  './assets/pictures/amaz.png'
import img1 from  './assets/pictures/FB.png'
import img2 from  './assets/pictures/AWS.png'
import img3 from  './assets/pictures/aws2.png'
import img4 from  './assets/pictures/Vic.png'
import logo1 from "./assets/pictures/logo-1.png"
import logo2 from "./assets/pictures/logo-2.png"
import logo3 from "./assets/pictures/logo-3.png"


export default function Home() {
	document.title = "Home";
	return (
		<Fragment>
			<NavBar />
			<section id={sheet.lookImg}>
				<div> Welcome to Pandey Dance Academy.com
					<p>Free feel to dance and dance to feel free.</p>
				</div>
			</section>
			<div id={sheet.main_heading}>Types of Dance Styles
				<section id={sheet.main}>
					<div className={sheet.card}>
						<h2>Hip Hop</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo1} alt="dadf" /></div>
							<div>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
							</div>
						</div>
					</div>
					<div className={sheet.card}>
						<h2>Zumbaa</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo2} alt="" srcset="" /></div>
							<div>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
							</div>
						</div>
					</div>
					<div className={sheet.card}>
						<h2>Free Dancing</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo3} alt="" srcset="" /></div>
							<div>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
								<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
							</div>
						</div>
					</div>
				</section>
			</div>
			<section id={sheet.sponsers}>
				<h2>Sponsers</h2>
				<div>
					<div><img src={img0} alt="" srcset="" /></div>
					<div><img src={img1} alt="" srcset="" /></div>
					<div><img src={img2} alt="" srcset="" /></div>
					<div><img src={img3} alt="" srcset="" /></div>
					<div><img src={img4} alt="" srcset="" /></div>
				</div>
			</section>
			<section id={sheet.about}>
				<div> </div>
				<p> <b>Address: </b><span>Building no-43, near mate park, Saket, new Delhi-110002</span></p>
				<p> <b>Date of Stablished:</b><span>15 January 1998</span></p>
				<p> <b>Owner: </b><i>Ujjwal Pandey</i></p>
				<p> <b>Mail: </b><span>PandeyDanceAcademy@gmail.com</span></p>
				<p> <b>Contact no: </b><span>001-987-543 , +91-987654321</span></p>
			</section>
			<FootBar/>
		</Fragment>
	)
}