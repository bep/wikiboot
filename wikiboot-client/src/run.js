'use strict';

var baseUrl;
(function () {

    var cjsConfig = {
        loader: 'curl/loader/cjsm11'
    };


    curl.config({
        baseUrl: baseUrl || '',
        packages: {
            app: { location: 'app', main: 'app' },
            utils: { location: 'app/common/utils', main: 'utils' },
            curl: { location: 'lib/curl/src/curl/' }
        },
        paths: {
            angular: {
                location: 'lib/angular/angular',
                config: {
                    loader: 'curl/loader/legacy',
                    exports: 'angular'
                }
            },
            lodash: {
                location: 'lib/lodash/dist/lodash'
            },
            bootstrap: {
                location: 'lib/bootstrap/dist/js/bootstrap',
                config: {
                    loader: 'curl/loader/cjsm11'
                }
            },
            angularsanitize: {
                location: 'lib/angular-sanitize/angular-sanitize',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular' ]
                }
            },
            restangular: {
                location: 'lib/restangular/dist/restangular',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular', 'lodash' ]
                }
            },
            crel: {
                location: 'lib/crel/crel',
                config: {
                    loader: 'curl/loader/cjsm11'
                }
            },
            jsonhuman: {
                location: 'lib/json-human/src/json.human',
                requires: [ 'crel' ]
            },
            angular_animate: {
                location: 'lib/angular-animate/angular-animate',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular' ]
                }
            },
            angular_loading_bar: {
                location: 'lib/angular-loading-bar/build/loading-bar',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular', 'angular_animate' ]
                }
            },
            ace: {
                location: 'lib/ace-builds/src-min-noconflict/ace',
                config: {
                    loader: 'curl/loader/legacy',
                    exports: 'ace'
                }
            },
            angular_ui_ace: {
                location: 'lib/angular-ui-ace/ui-ace',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular', 'ace' ]
                }
            },
            angular_bootstrap: {
                location: 'lib/angular-bootstrap/ui-bootstrap-tpls',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular']
                }
            }
        }
    });

    curl(['app/main' ]).then(start, fail);


    function start(main) {

    }

    function fail(ex) {
        throw ex;
    }

}());
