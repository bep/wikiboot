var gulpFilter = require('gulp-filter'),
    cram = require('gulp-cram'),
    uglify = require('gulp-uglify'),
    bowerSrc = require('gulp-bower-src'),
    sourcemaps = require('gulp-sourcemaps'),
    cssmin = require('gulp-minify-css'),
    less = require('gulp-less'),
    gulp = require('gulp'),
    gutil = require('gulp-util'),
    filelog = require("gulp-filelog"),
    ngAnnotate = require('gulp-ng-annotate'),
    tinylr = require('tiny-lr')();

var paths = {
    run: 'src/run.js',
    css: {
        files: ['src/css/*.css'],
        root: 'src/css'
    },
    app: 'src/app',
    less: ['src/less/s*'],
    assets: ["src/cache.manifest"],
    images: ["src/img/*"],
    angularTemplates: ["src/app/**/*.html"],
    destination: './dist',
    thymeleaf: ['../wikiboot-web/src/main/resources/templates/**/*.html'],
    lib: "src/lib"
};


//---------------------------------------
// Development related tasks
//---------------------------------------

gulp.task('less-dev', function () {

    return gulp.src(paths.less)
        .pipe(less()).on('error', gutil.log)
        .pipe(cssmin({noRebase: true}))
        .pipe(gulp.dest(paths.lib + '/dev/css'));
});

gulp.task('angular-annotate', function () {

    var ngAnnotateOpts = {
        remove: false,
        add: true,
        single_quotes: true,
        stats: true
    };

    // replacing files - this is run manually when in trouble
    return gulp.src(paths.app + '/**/*.js')
        .pipe(filelog('Ng-annotate'))
        .pipe(ngAnnotate(ngAnnotateOpts))
        .pipe(gulp.dest(paths.app));
});


function startLiveReload() {
    tinylr.listen(35729);
}

function notifyLiveReload(event) {
    var fileName = require('path').relative(__dirname, event.path);

    tinylr.changed({
        body: {
            files: [fileName]
        }
    });
}

// Watch Files For Changes - for use in dev
gulp.task('watch', function () {
    startLiveReload();
    gulp.watch('src/less/*.less', ['less-dev']);
    gulp.watch(paths.thymeleaf
        .concat(paths.lib + '/dev/css/*')
        .concat('src/**/*.js').concat(paths.app + '/**/*.html'), notifyLiveReload);
});


//---------------------------------------
// Build app
//---------------------------------------

// build CSS
gulp.task('build-css', function () {
    return gulp.src(paths.less)
        .pipe(less()).on('error', gutil.log)
        .pipe(cssmin({noRebase: true}))
        .pipe(gulp.dest(paths.destination + '/css'));
});


// cram and uglify JavaScript source files
gulp.task('build-modules', function () {

    var cramOpts = {
        includes: [ 'curl/loader/legacy', 'curl/loader/cjsm11'],
        excludes: ['gmaps', 'crel', 'ace', 'android'],
        appRoot: "./src"
    };

    return cram(paths.run, cramOpts)
        .into('run.js')
        .pipe(filelog('crammed'))
        .pipe(sourcemaps.init())
        .pipe(uglify({ mangle: false }))
        .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(paths.destination));
});

// copy main bower files (see bower.json)
gulp.task('bower-files', function () {
    var filter = gulpFilter(["**/*.js", "!**/src-min-noconflict/*.js", "!**/*.min.js"]);
    return bowerSrc()
        .pipe(sourcemaps.init())
        .pipe(filter)
        .pipe(uglify()).on('error', gutil.log)
        .pipe(filter.restore())
         .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(paths.destination + '/lib'));
});

// copy the rest ...
gulp.task('copy-assets', function () {
    return gulp.src(paths.assets)
        .pipe(gulp.dest(paths.destination));
});

gulp.task('copy-images', function () {
    return gulp.src(paths.images)
        .pipe(gulp.dest(paths.destination + "/img"));
});

// todo add these to angular cache
gulp.task('copy-angular-templates', function () {
    return gulp.src(paths.angularTemplates)
        .pipe(gulp.dest(paths.destination + "/app"));
});

// figure a way to include the less-dev only in dev (it is in the watch task, but)
gulp.task('build', ['less-dev', 'build-css', 'build-modules', 'copy-assets', 'copy-images', 'copy-angular-templates', 'bower-files'], function () {
});
