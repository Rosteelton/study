/**
 * Created by asolovyev on 18.08.16.
 */

function filter(arr,func) {
var newmass = [];
    for (var i = 0; i< arr.length; i++) {
        if (func(arr[i])) newmass.push(arr[i])
    }
    return newmass;
}

var arr = [1,2,3,4,5,6,7];

alert(filter(arr, function(a) {
    return a % 2 == 0
}));

alert( filter(arr, inBetween(3, 6)) );


function inBetween(a,b) {

    return function (c) {
        return (c >= a && c <= b);
    }
}


function makeArmy() {

    var shooter;
    var shooters = [];

    for (var i = 0; i < 10; i++) {
        shooter = function some() {
            alert(some.i); // выводит свой номер
        };
        some.i = i;

        shooters.push(shooter);
    }

    return shooters;
}

var army = makeArmy();

//army[0](); // стрелок выводит 10, а должен 0
//army[5](); // стрелок выводит 10...
// .. все стрелки выводят 10 вместо 0,1,2...9












