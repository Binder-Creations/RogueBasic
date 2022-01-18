function intializeAttributes(classname){
	let attributes = document.getElementsByClassName(classname);
	for(let i = 0; i<attributes.length; i++){
		attributes.item(i).value = 8;
	}
}

function increment(id){
	let value = getElementValue(id);
	let counterValue = getCounterValue();
	
	if(value < 15 && counterValue >= 1) {
		value = value*1 + 1*1;
		setCounterValue(counterValue-1);
	} else if(value >= 15 && value < 18 && counterValue > 1) {
		value = value*1 + 1*1;
		setCounterValue(counterValue-2);
	}
	
	setElementValue(id, value);
}

function decrement(id){
	let value = getElementValue(id);
	let counterValue = getCounterValue();
	
	if(value >= 16) {
		value -= 1;
		setCounterValue(counterValue*1+2*1);
	} else if(value < 16 && value > 8) {
		value -= 1;
		setCounterValue(counterValue*1+1*1);
	}
	
	setElementValue(id, value);
}

function getElementValue(id){
	return document.getElementById(id).value;
}

function setElementValue(id, value){
	document.getElementById(id).value = value;
}

function getCounterValue(){
	return parseInt(document.getElementById("counter").innerHTML.match(/\d+/g)[0]);
}

function setCounterValue(int){
	document.getElementById("counter").innerHTML = document.getElementById("counter").innerHTML.slice(0, document.getElementById("counter").innerHTML.search(/\d/)) + int;
}

function disable(){
	document.getElementById("submit").innerHTML = "Generating";
	setTimeout(function(){document.getElementById("submit").innerHTML = "Generating."}, 400);
	setTimeout(function(){document.getElementById("submit").innerHTML = "Generating.."}, 800);
	setTimeout(function(){document.getElementById("submit").innerHTML = "Generating..."}, 1200);
	setTimeout(function(){document.getElementById("submit").innerHTML = "Generating...."}, 1600);
	setTimeout(function(){document.getElementById("submit").innerHTML = "Generating....."}, 2000);
}