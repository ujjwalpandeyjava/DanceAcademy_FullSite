import sheet from './Head.module.css'


function Head({ title }) {
	return (
		<div className={sheet.head}>
			<h1>{title}</h1>
		</div>
	)
}

export default Head