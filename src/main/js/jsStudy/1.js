function isEmpty(obj) {
  var counter = 0;
  for (var key in obj) {
    counter++
  }
  return counter > 0;
}

var schedule = {};

schedule["8:30"] = "подъём";

"use strict";

var salaries = {
    "Вася": 100,
    "Петя": 300,
    "Даша": 250
};


function sum(obj) {
    var counter = 0;

    for (var key in obj) {
        counter += obj[key]
    }
    return counter;
}


//alert(sum(salaries));

var mass = [2,24,2,4,1];

//noinspection JSUnresolvedVariable
var newm = mass.sort(function (a,b) {
    if (a>b) return 1;
    else return -1;

});


//alert(newm);
var count = 0;
function dfsfcounter() {

    return count++
}


//alert(counter());
//alert(counter());


function plus() {
    var inc = 0;
    return {
      counter: function () {
         return inc++
      }
    };
}

var plusadd = plus();


alert(plusadd());
alert(plusadd());