
var app = angular.module("annotation",['ngRoute']);

if(typeof(String.prototype.trim) === "undefined")
{
    String.prototype.trim = function()
    {
        return String(this).replace(/^\s+|\s+$/g, '');
    };
}

app.run(function($rootScope,$http,$window) {
    $rootScope.working = false;
    $rootScope.done = false;

    $rootScope.count = 0;
    $http.get("/auth/status").then(function(response) {
        if (response.status !== 200) {
            $window.location.href= "/login"
        }
    });

    $http.get("/task/count").then(function(response) {
        $rootScope.count=response.data.count;
    });

});



app.factory('annotationService', ['$rootScope', function ($rootScope) {

    function AnnotationServiceImpl() {

    }


    AnnotationServiceImpl.prototype.putAnnotation = function(http, id, label, evi, partial_evi,cb) {

        getEvi = function(e) {
            ret = [];
            angular.forEach(e, function(value,key){
                if(value) {
                    this.push(parseInt(key))
                }
            }, ret);
            return ret;
        };

        if ($rootScope.working || $rootScope.done) {
            return;
        }
        $rootScope.working = true;




        http.put('/task/'+id, {"label":label, individualEvidence:getEvi(evi),partialEvidence:getEvi(partial_evi)}).then(function successCallback(response) {
            $rootScope.working = false;
            $rootScope.done = true;

            cb();


        }, function errorCallback(response) {
            $rootScope.working = false;
            alert("A network error occurred\n" + response.statusText + "\n\nPlease report this and retry submitting")
        });


    };


    AnnotationServiceImpl.prototype.skipAnnotation = function(http, id,cb) {

        getEvi = function(e) {
            ret = [];
            angular.forEach(e, function(value,key){
                if(value) {
                    this.push(parseInt(key))
                }
            }, ret);
            return ret;
        };

        if ($rootScope.working || $rootScope.done) {
            return;
        }
        $rootScope.working = true;




        http.put('/task/skip/'+id).then(function successCallback(response) {
            $rootScope.working = false;
            $rootScope.done = true;

            cb();


        }, function errorCallback(response) {
            $rootScope.working = false;
            alert("A network error occurred\n" + response.statusText + "\n\nPlease report this and retry submitting")
        });


    };



    AnnotationServiceImpl.prototype.getNextSentence = function(http,callback) {

        getTask = function(http,callback,data) {
            http.get("/task/"+data.taskId).then(function(response) {
                callback(data.taskId,response.data);
            });
        };


        http.get("/task/next").then(function(response) {
            getTask(http,callback,response.data);
        });
    };


    return new AnnotationServiceImpl();

}]);

app.controller("WelcomeController",function($scope,$location) {

    $scope.annotate = function() {
        $location.path("/annotate");
    };

});


app.controller("AnnotateController",function($rootScope,$route, $scope,$http,annotationService) {

    $scope.claimText = "";

    $scope.hide_q1 = false;
    $scope.hide_q2 = false;
    $scope.hide_q3 = false;


    $scope.noa = false;
    $scope.partial_noa = false;
    $scope.evi = {};
    $scope.partial_evi = {};
    $scope.q3_finish = false;
    $scope.originalPage = ""


    $scope.evi_change = function() {
        $scope.q2_finish = Object.values($scope.evi).every(function(x) { return x }) && !$scope.noa;
        $scope.q3_continue = !$scope.q2_finish && (Object.values($scope.evi).some(function(x) { return x }) || $scope.noa);
        $scope.finish = ($scope.q3_finish && $scope.q3_continue) || $scope.q2_finish;
    };

    $scope.partial_evi_change = function() {
        $scope.q3_finish = $scope.q3_continue && ($scope.partial_noa || Object.values($scope.partial_evi).some(function(x) { return x }));
        $scope.finish = $scope.q3_finish;

    };


    $scope.answer_q1 = function(answer) { 
        $scope.hide_q1 = true;
        $scope.label = answer;
        if(answer == 2) {
            $scope.q1_continue = true;
        } else if(answer==1) {
            $scope.q1_continue = true;
        } else {
            $scope.finish = true;
        }
    };


    $scope.answer_q2 = function(answer) { 
        $scope.hide_q2 = true;
        if(answer == 2) {
            $scope.q2_continue = true;
        } else if(answer==1) {
            $scope.q2_continue = true;
        } else {
            $scope.finish = true;
        }
    };

    $scope.answer_q3 = function(answer) { 
        $scope.hide_q3 = true;
        if(answer) {
            $scope.finish = true;
        } else {
            $scope.q3_continue = true;
        }
    };


    $scope.update = function () {

        annotationService.getNextSentence($http,function (taskId,task) {

            $rootScope.done=false;

            $scope.taskId = taskId;

            $scope.claimId = task.claimId;
            $scope.claimText = task.claimText;
            $scope.evidence = task.evidence;
            $scope.originalPage = task.originalPage;

            angular.forEach($scope.evidence, function(value, key) {
                $scope.evi[value.id.toString()] = false;
                $scope.partial_evi[value.id.toString()] = false;
            });


            $http.get("/task/count").then(function(response) {
                $rootScope.count=response.data.count;
            });
        });


    };

    $scope.save = function() {

        annotationService.putAnnotation($http, $scope.taskId, $scope.label, $scope.evi, $scope.partial_evi, function() {
            $route.reload();
        })

    };

    $scope.skip = function() {
        annotationService.skipAnnotation($http, $scope.taskId, function() {
            $route.reload();
        })

    };

    $scope.update();

});


app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'views/welcome.html',
            controller: 'WelcomeController'
        }).
        when("/annotate", {
            templateUrl: 'views/annotate.html',
            controller: 'AnnotateController'
        }).
        otherwise({
            redirectTo: '/'
        });


}]);