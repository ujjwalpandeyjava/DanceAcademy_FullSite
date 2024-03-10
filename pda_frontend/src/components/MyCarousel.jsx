import { useState } from 'react';
import sheet from './MyCarousel.module.css'
import { MdNavigateBefore, MdNavigateNext } from "react-icons/md";
import { useSwipeable } from 'react-swipeable';

export default function MyCarousel({ images }) {
	const [currentIndex, setCurrentIndex] = useState(0);

	const nextSlide = () => {
		setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
	};

	const prevSlide = () => {
		setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
	};
	const handlers = useSwipeable({
		onSwiped: (eventData) => console.log("User Swiped!", eventData),
		onSwipedLeft: (e) => console.log(nextSlide(), e),
		onSwipedRight: (e) => console.log(prevSlide(), e),
		onTap: (e) => console.log(" onTap", e),
	});
	return (
		<div className={sheet.myCarousel} {...handlers}>
			<img src={images[currentIndex]} alt={`Slide ${currentIndex}`} />
			<div className={sheet.actionBtn}>
				<button onClick={prevSlide}><MdNavigateBefore /></button>
				{currentIndex + 1}
				<button onClick={nextSlide}><MdNavigateNext /></button>
			</div>
		</div>
	);
};