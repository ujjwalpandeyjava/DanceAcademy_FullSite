import { Suspense, lazy, useState } from "react";
import Head from "../miniComp/Head"
import { FaTruckLoading } from "react-icons/fa";

const classesCategoryConst = {
	past: "past",
	future: "future",
	new: "New"
}
const Component = {
	[classesCategoryConst.past]: lazy(() => import("./PastClasses")),
	[classesCategoryConst.future]: lazy(() => import("./FutureClasses")),
	[classesCategoryConst.new]: lazy(() => import("./NewClass"))
}

function Classes() {
	const [renderComp, setRenderComp] = useState(classesCategoryConst.new);
	const MyLazyComp = Component[renderComp];
	return (
		<div>
			<Head title={"Classes"} />
			<div className="classes">
				<div className="categorySelection">
					<button type="button" onClick={() => setRenderComp(classesCategoryConst.new)}
						className={renderComp === classesCategoryConst.new ? "active" : ""}>Add New</button>
					<button type="button" onClick={() => setRenderComp(classesCategoryConst.future)}
						className={renderComp === classesCategoryConst.future ? "active" : ""}>Future</button>
					<button onClick={() => setRenderComp(classesCategoryConst.past)}
						className={renderComp === classesCategoryConst.past ? "active" : ""}>Past</button>
				</div>
				<Suspense fallback={<FaTruckLoading />}>
					<MyLazyComp />
				</Suspense>
			</div>
		</div>
	)
}

export default Classes