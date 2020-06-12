appService.service('CommonService', ['$resource', '$q', 'AppUtil',
                                       function ($resource, $q, AppUtil) {
    var resource = $resource('', {}, {
        page_setting: {
            method: 'GET',
            isArray: false,
            url: AppUtil.prefixPath() + '/page-settings'
        },
        cas_login_url: {
            method: 'GET',
            isArray: false,
            url: AppUtil.prefixPath() + '/utils/getCasLoginUrl'
        }
    });

                                           
    return {
        getPageSetting: function () {
            return AppUtil.ajax(resource.page_setting, {});
        },
        getCasLoginUrl: function () {
            return AppUtil.ajax(resource.cas_login_url, {});
        }
    }
}]);
