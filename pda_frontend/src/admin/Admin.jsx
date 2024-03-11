import { useEffect, useState } from 'react';
import { CgProfile } from "react-icons/cg";
import { GiConcentrationOrb, GiHamburgerMenu } from "react-icons/gi";
import { MdOutlineDashboard, MdOutlineExitToApp, MdOutlineMovieFilter } from "react-icons/md";
import { RiCustomerService2Line } from "react-icons/ri";
import { Link, Outlet } from 'react-router-dom';
import sheet from './admin.module.css';


// https://www.npmjs.com/package/echarts for queries
function Admin() {
	const [smallWidth, setSmallWidth] = useState(false);
	const [name] = useState("Ujjwal Pandey")
	useEffect(() => {
		window.addEventListener('resize', widthChange);
		return () => {
			window.removeEventListener('resize', widthChange);
		};

	}, [])
	function widthChange() {
		if (window.innerWidth < 500)
			setSmallWidth(true);
		// else setSmallWidth(false)
	}

	return (
		<div id={sheet.mainContainer}>
			<div id={sheet.navbar} className={`${smallWidth ? sheet.navIconsOnly : sheet.navFull}`}>
				<div className={sheet.navbarOptions}>
					<div onClick={() => setSmallWidth(!smallWidth)} className={sheet.menuSwitch} >
						<GiHamburgerMenu />
						{/* {smallWidth ? <IoMdClose /> : <GiHamburgerMenu />} */}
					</div>
					<Link to={"./dashboard"} relative='path' className={sheet.eachOption}>
						<MdOutlineDashboard /> {!smallWidth && <>Dashboard</>}
					</Link>
					<Link to={"./admissions"} relative='path' className={sheet.eachOption}>
						<GiConcentrationOrb /> {!smallWidth && <>Admissions Queries</>}
					</Link>
					<Link to={"./classes"} relative='path' className={sheet.eachOption}>
						<MdOutlineMovieFilter /> {!smallWidth && <>Classes</>}
					</Link>
					<Link to={"./queries"} relative='path' className={sheet.eachOption}>
						<RiCustomerService2Line /> {smallWidth ? null : <>Customer Queries</>}
					</Link>
				</div>
				<div className={sheet.profile} >
					<CgProfile size="1.5em" onClick={() => setSmallWidth(!smallWidth)} />
					{!smallWidth && <div>
						{name}
						<p>
							<Link to="../login" relative='route' >
								Logout
								<MdOutlineExitToApp />
							</Link>
						</p>
					</div>}
				</div>
			</div>
			<div id={sheet.content}><Outlet /></div>
		</div>
	)
}

export default Admin