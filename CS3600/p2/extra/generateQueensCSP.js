boardLength = 8;
map = {}
function indexLetter(i){
	if (map[i]){
		return map[i]
	} else {
		map[i] = String.fromCharCode(97 + i);
		return map[i]
	}
}
values = []
constraints = [];
for (var i = 0; i < boardLength; i++){
	for (var j = 0; j < boardLength; j++){
		curPos = indexLetter(i) + indexLetter(j)
			// horizontal
		for (var k = 1; k < (boardLength - j); k++){
			pos = indexLetter(i) + indexLetter(j+k)
			constraints.push('CantAttackConstraint ' + curPos + ' ' + pos)
		}
			// vertical
		for (var k = 1; k < (boardLength - i); k++){
			pos = indexLetter(i+k) + indexLetter(j)
			constraints.push('CantAttackConstraint ' + curPos + ' ' + pos)
		}
			// diagonal
		for (var k = 1; k < (boardLength - j) && k < (boardLength - i); k++){
			pos = indexLetter(i+k) + indexLetter(j+k)
			constraints.push('CantAttackConstraint ' + curPos + ' ' + pos)
		}
		values.push(curPos + ' 0 q')
	}
}
function logArr(arr){
	for (var i = 0; i < arr.length; i++){
		console.log(arr[i])
	}
}
logArr(values)
console.log(0)
logArr(constraints)