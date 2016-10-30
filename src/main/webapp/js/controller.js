var app = angular.module("userApp",[]);

app.controller("gameController",function($scope,$http,$interval){
    $scope.map = [];
    $scope.player = {};
    $scope.lines = [];
    var log="";
    $http.get("api/self").success(mapResponseSuccess);
    function mapResponseSuccess(data){
        $scope.map = data.map;
        $scope.player = data.player;
        updateFlash();
    }

    function playerResponseSuccess(playerResponse){
        $scope.player = playerResponse;
        updateFlash();
    }
    
    function updateFlash(){
        $http.get('api/flash').success(flashResponseSuccess);
    }
    
    function updateSelf(){
        $http.get("api/self").success(mapResponseSuccess);
    }
    
    function flashResponseSuccess(flashResponse){
        log+=flashResponse;

        $scope.lines = log.split("\n");
        if($scope.lines.length>10){
            for(var i=0;i<$scope.lines.length-10;i++)
                $scope.lines.shift();
            log = $scope.lines.join("\n");            
        }
    }

    $scope.selectEntity = function(entity){
        if(!(entity==null || entity==undefined)){
            $http.post("api/select/entity",{x:entity.x,y:entity.y}).success(playerResponseSuccess);
        }
    };
    
    $scope.performActionOnSelected = function(action){
        $http.post("api/performAction/selected",{action:action}).success(function(){updateSelf()});
    };
    
    $scope.selectInventoryItem =function(index){
        $http.post('api/select/item',{index:index}).success(playerResponseSuccess);
    };



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