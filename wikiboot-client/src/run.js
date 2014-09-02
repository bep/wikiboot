'use strict';

var baseUrl;
(function () {

    var cjsConfig = {
        loader: 'curl/loader/cjsm11'
    };

    var angularModuleConfig = {
        loader: 'curl/loader/cjsm11',
        requires: [ 'angular' ]
    };

    curl.config({
        baseUrl: baseUrl || '',
        packages: {
            app: { location: 'app', main: 'app' },
            utils: { location: 'app/common/utils', main: 'utils' },
            //feature: { location: 'feature' },
            //component: { location: 'component', config: cjsConfig },
            //platform: { location: 'platform', config: cjsConfig },
            curl: { location: 'lib/curl/src/curl/' }
            //when: { location: 'lib/when', main: 'when' },
            //most: { location: 'lib/most', main: 'most', config: cjsConfig },
            //poly: { location: 'lib/poly' }
        },
        paths: {
            jquery: 'lib/jquery/jquery.min',
            angular: {
                location: 'lib/angular/angular',
                config: {
                    loader: 'curl/loader/legacy',
                    exports: 'angular'
                }
            },
            lodash: {
                location: '/lib/lodash/dist/lodash.min'
            },
            bootstrap: {
                location: '/lib/bootstrap/dist/js/bootstrap.min',
                config: {
                    loader: 'curl/loader/cjsm11'
                }
            },
            angularsanitize: {
                location: '/lib/angular-sanitize/angular-sanitize.min',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular' ]
                }
            },
            restangular: {
                location: '/lib/restangular/src/restangular',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular', 'lodash' ]
                }
            },
            crel: {
                location: '/lib/crel/crel.min',
                config: {
                    loader: 'curl/loader/cjsm11'
                }
            },
            jsonhuman: {
                location: '/lib/json-human/src/json.human',
                requires: [ 'crel' ]
            },
            angular_animate: {
                location: '/lib/angular-animate/angular-animate.min',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular' ]
                }
            },
            angular_loading_bar: {
                location: '/lib/angular-loading-bar/build/loading-bar.min',
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
                location: 'lib/angular-ui-ace/ui-ace.min',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular', 'ace' ]
                }
            },
            angular_bootstrap: {
                location: 'lib/angular-bootstrap/ui-bootstrap-tpls.min',
                config: {
                    loader: 'curl/loader/cjsm11',
                    requires: [ 'angular']
                }
            }
        }
    });

    curl(['app/main' ]).then(start, fail);


    function start(main) {
        // tell the jquery migrate plugin to be quiet
        //$.migrateMute = true;
    }

    function fail(ex) {
        throw ex;
    }

}());
