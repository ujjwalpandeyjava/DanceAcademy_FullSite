import { Fragment } from 'react';
import img2 from './assets/pictures/AWS.png';
import img1 from './assets/pictures/FB.png';
import img4 from './assets/pictures/Vic.png';
import img0 from './assets/pictures/amaz.png';
import img3 from './assets/pictures/aws2.png';
import logo1 from "./assets/pictures/logo-1.png";
import logo2 from "./assets/pictures/logo-2.png";
import logo3 from "./assets/pictures/logo-3.png";
import FootBar from './global/footBar';
import NavBar from './global/navBar';
import MyCarousel from './MyCarousel';
import sheet from './home.module.css';

const imageUrls = [
	'https://images.pexels.com/photos/209948/pexels-photo-209948.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
	"https://images.pexels.com/photos/1701202/pexels-photo-1701202.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/2188012/pexels-photo-2188012.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/175659/pexels-photo-175659.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/1918445/pexels-photo-1918445.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/40186/dance-ballet-powder-girl-40186.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/2285932/pexels-photo-2285932.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
	"https://images.pexels.com/photos/1450116/pexels-photo-1450116.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",


];
export default function Home() {
	document.title = "Home";
	return (
		<Fragment>
			<NavBar />
			<section id={sheet.lookImg}>
				Welcome to Pandey Dance Academy.com
				<p>Free feel to dance and dance to feel free.</p>
			</section>
			<div id={sheet.main_heading}>
				<div className={sheet.styleHeading}>Dance Styles</div>
				<section id={sheet.main}>
					<div className={sheet.card}>
						<h2>Hip Hop</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo1} alt="dadf" /></div>
							<div>
								<p>Hip-hop dance, a fusion of street styles, emerged in 1970s New York.</p>
								<p>Influenced by breaking, funk styles, and African-American culture, it’s rhythmic, freestyle, and often seen in battles. 🎶💃</p>
							</div>
						</div>
					</div>
					<div className={sheet.card}>
						<h2>Zumbaa</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo2} alt="" /></div>
							<div>
								<p>Zumba is one of the best-known fitness organizations in the world,</p>
								<p>A rhythmic dance fitness program that blends energetic music with choreographed movements. A joyful way to stay active, improve coordination, and fun! 💃🎶</p>
							</div>
						</div>
					</div>
					<div className={sheet.card}>
						<h2>Free Dancing</h2>
						<div className={sheet.candance}>
							<div className={sheet.imges}><img src={logo3} alt="" /></div>
							<div>
								<p>Freestyle, the heartbeat of hip-hop, unshackles creativity.</p>
								<p>Words flow like graffiti on city walls, rhythm syncs with heartbeat, and dancers paint the air with their souls. 🎤🎶💃</p>
							</div>
						</div>
					</div>
				</section>
			</div>
			<section id={sheet.sponsors}>
				<div className={sheet.styleHeading}>Sponsors</div>
				<div id={sheet.sponsorsCards}>
					<img src={img0} alt="" />
					<img src={img1} alt="" />
					<img src={img2} alt="" />
					<img src={img3} alt="" />
					<img src={img4} alt="" />
				</div>
			</section>
			<MyCarousel images={imageUrls} />
			<FootBar />
		</Fragment>
	)
}