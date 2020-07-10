login_module.controller('LoginController',
    ['$scope', '$window', '$location', '$translate', 'toastr', 'AppUtil','CommonService',
        LoginController]);

function LoginController($scope, $window, $location, $translate, toastr, AppUtil, CommonService) {

    CommonService.getCasLoginUrl().then(function (result) {
        $scope.casLoginUrl = result.url;
    });

    if ($location.$$url) {
        var params = AppUtil.parseParams($location.$$url);
        if (params.error) {
            $translate('Login.UserNameOrPasswordIncorrect').then(function(result)  {
                $scope.info = result;
            });
        }
        if (params.logout) {
            $translate('Login.LogoutSuccessfully').then(function(result)  {
                $scope.info = result;
            });
        }
    }

}
