import { useState } from 'react'
import sheet from './admin.module.css'
import { GiHamburgerMenu } from "react-icons/gi";
import { IoMdClose } from "react-icons/io";
import { MdAdminPanelSettings } from "react-icons/md";
import { Link, Outlet } from 'react-router-dom';
import { MdOutlineDashboard } from "react-icons/md";
import { SiGooglebigquery } from "react-icons/si";
import { GiConcentrationOrb } from "react-icons/gi";
import { RiCustomerService2Line } from "react-icons/ri";
import { CgProfile } from "react-icons/cg";

import { MdOutlineExitToApp } from "react-icons/md";

function Admin() {
	const [smallWidth, setSmallWidth] = useState(false);
	const [name, setName] = useState("Ujjwal Pandey")
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