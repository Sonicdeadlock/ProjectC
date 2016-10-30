var app = angular.module("userApp",[]);

app.controller("gameController",function($scope,$http,$interval){
    $scope.map = [];
    $scope.player = {};
    $http.get("api/self").success(mapResponseSuccess);
    function mapResponseSuccess(data){
            $scope.map = data.map;
            $scope.player = data.player;
    }
    document.addEventListener("keydown",function(event){
       var keyCode = event.keyCode;
        switch (keyCode){
            case 38:
                $http.get("api/move/up").success(mapResponseSuccess);
                break;
            case 40:
                $http.get("api/move/down").success(mapResponseSuccess);
                break;
            case 37:
                $http.get("api/move/left").success(mapResponseSuccess);
                break;
            case 39:
                $http.get("api/move/right").success(mapResponseSuccess);
                break;
        }
    });
});