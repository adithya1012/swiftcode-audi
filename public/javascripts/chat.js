var app = angular.module('chatApp', ['ngMaterial']);
app.controller('chatController', function ($scope) {
    $scope.message = [
        {
            'sender': 'USER',
            'text': 'hello'
    },
        {
            'sender': 'BOT',
            'text': 'wt to do'
    },
        {
            'sender': 'USER',
            'text': 'nothing'
    },
        {
            'sender': 'BOT',
            'text': 'then'
    },
        {
            'sender': 'USER',
            'text': 'sleep'
    }
    ]
});