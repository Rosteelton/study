/**
 * Created by asolovyev on 19.08.16.
 */
function sum(arr) {
    return arr.reduce(function(a, b) {
        return a + b;
    });
}

function sumArgs() {
return [].reduce.call(arguments,function (a, b) {
    return a+b;
})
}

function applyAll(func) {
return func.apply(null, [].slice.call(arguments,1))
}

// alert( applyAll(Math.max, 2, -2, 3) );

"use strict";

function ask(question, answer, ok, fail) {
    var result = prompt(question, '');
    if (result.toLowerCase() == answer.toLowerCase()) ok();
    else fail();
}

var user = {
    login: 'Василий',
    password: '12345',

    // метод для вызова из ask
    loginDone: function(result) {
        alert( this.login + (result ? ' вошёл в сайт' : ' ошибка входа') );
    },

    checkPassword: function() {
        ask("Ваш пароль?", this.password, this.loginDone.bind(this,true),
            (function() {
                this.loginDone(false);
            }).bind(this)
        );
    }
};

var vasya = user;
user = null;
vasya.checkPassword();