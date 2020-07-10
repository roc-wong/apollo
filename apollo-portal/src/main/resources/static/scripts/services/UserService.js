appService.service('UserService', ['$resource', '$q', 'AppUtil', function ($resource, $q, AppUtil) {
    var user_resource = $resource('', {}, {
        load_user: {
            method: 'GET',
            url: AppUtil.prefixPath() + '/user'
        },
        find_users: {
            method: 'GET',
            url: AppUtil.prefixPath() + '/users'
        },
        create_or_update_user: {
            method: 'POST',
            url: AppUtil.prefixPath() + '/users'
        },
        authorize: {
            method: 'POST',
            url: AppUtil.prefixPath() + '/oaUsers/authorize'
        },
        cancelAuthorization: {
            method: 'POST',
            url: AppUtil.prefixPath() + '/oaUsers/cancelAuthorization'
        }
    });
    return {
        load_user: function () {
            var finished = false;
            var d = $q.defer();
            user_resource.load_user({},
                                    function (result) {
                                        finished = true;
                                        d.resolve(result);
                                    },
                                    function (result) {
                                        finished = true;
                                        d.reject(result);
                                    });
            return d.promise;
        },
        find_users: function (keyword) {
            var d = $q.defer();
            user_resource.find_users({
                                         keyword: keyword
                                     },
                                     function (result) {
                                         d.resolve(result);
                                     },
                                     function (result) {
                                         d.reject(result);
                                     });
            return d.promise;
        },
        createOrUpdateUser: function (user) {
            var d = $q.defer();
            user_resource.create_or_update_user({}, user,
                                     function (result) {
                                         d.resolve(result);
                                     },
                                     function (result) {
                                         d.reject(result);
                                     });
            return d.promise;   
        },
        authorize: function (user) {
            var d = $q.defer();
            user_resource.authorize({}, user,
                function (result) {
                    d.resolve(result);
                },
                function (result) {
                    d.reject(result);
                });
            return d.promise;
        },
        cancelAuthorization: function (user) {
            var d = $q.defer();
            user_resource.cancelAuthorization({}, user,
                function (result) {
                    d.resolve(result);
                },
                function (result) {
                    d.reject(result);
                });
            return d.promise;
        }

    }
}]);
