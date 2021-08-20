function intializeAttributes(classname){
	var attributes = document.getElementsByClassName(classname);
	for(var i = 0; i<attributes.length; i++){
		attributes.item(i).value = 8;
	}
}

function increment(id){
	if((document.getElementById(id).value < 13 && getCounterValue() > 0) || (document.getElementById(id).value < 17 && getCounterValue() > 1) || (document.getElementById(id).value < 18 && getCounterValue() > 2))  {
		document.getElementById(id).value = parseInt(document.getElementById(id).value) + 1;
		if(document.getElementById(id).value < 14){
			setCounterValue(getCounterValue()-1);
			return;
		}
		if(document.getElementById(id).value < 18){
			setCounterValue(getCounterValue()-2);
			return;
		}
		setCounterValue(getCounterValue()-3);
	}
}

function decrement(id){
	if(document.getElementById(id).value > 8)  {
		document.getElementById(id).value = parseInt(document.getElementById(id).value) - 1;
		if(document.getElementById(id).value == 17){
			setCounterValue(getCounterValue()+3);
			return;
		}
		if(document.getElementById(id).value > 12){
			setCounterValue(getCounterValue()+2);
			return;
		}
		setCounterValue(getCounterValue()+1);
	}
}

function getCounterValue(){
	return parseInt(document.getElementById("counter").innerHTML.match(/\d+/g)[0]);
}

function setCounterValue(int){
	document.getElementById("counter").innerHTML = document.getElementById("counter").innerHTML.slice(0, document.getElementById("counter").innerHTML.search(/\d/)) + int;
}