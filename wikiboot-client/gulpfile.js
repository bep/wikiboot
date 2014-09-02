var gulpFilter = require('gulp-filter'),
    cram = require('gulp-cram'),
    uglify = require('gulp-uglify'),
    bowerSrc = require('gulp-bower-src'),
    cssMinify = require('gulp-minify-css'),
    imagemin = require('gulp-imagemin'),
    pngcrush = require('imagemin-pngcrush'),
    less = require('gulp-less'),
    gulp = require('gulp'),
    gutil = require('gulp-util'),
    templateCache = require('gulp-angular-templatecache');

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
    destination: './dist',
    thymeleaf: ['../wikiboot-web/src/main/resources/templates/**/*.html'],
    lib: "src/lib"
};



// Optimize application CSS files and copy to "dist" folder
gulp.task('optimize-and-copy-css', function () {

    return gulp.src(paths.css.files)
        .pipe(cssMinify({root: paths.css.root, noRebase: true}))
        .pipe(gulp.dest(paths.destination + '/css'));
});

// Optimize application JavaScript files and copy to "dist" folder
gulp.task('optimize-and-copy-js', function () {

   /* var options = {
        includes: ['curl/loader/legacy'],
        appRoot: "./src"
    };*/
    //return cram(paths.run, options).into('run.js')
        return gulp.src(paths.run)
        .pipe(uglify())
        .pipe(gulp.dest(paths.destination));
});

// Optimize bower-managed JavaScript dependencies and copy to "dist" folder
gulp.task('optimize-and-copy-lib', function () {

    var filter = gulpFilter(["**/*.js", "!**/*.min.js", "!ace-builds/**"]);
    return bowerSrc()
        .pipe(filter)
        .pipe(uglify())
        .pipe(filter.restore())
        .pipe(gulp.dest(paths.destination + '/lib'));
});

gulp.task('copy-images', function () {
    return gulp.src(paths.images)
        .pipe(imagemin({
            progressive: true,
            svgoPlugins: [
                {removeViewBox: false}
            ],
            use: [pngcrush()]
        }))
        .pipe(gulp.dest(paths.destination + '/img'))
});

gulp.task('copy-assets', function () {
    return gulp.src(paths.assets)
        .pipe(gulp.dest(paths.destination))
});

gulp.task('less', function () {
    return gulp.src(paths.less)
        .pipe(less())
        .pipe(cssMinify({noRebase: true}))
        .pipe(gulp.dest(paths.destination + '/css'));
});

/*gulp.task('template-cache', function () {
    var options = {
        //output: 'templates.js',
        module: 'wb.templates',
        root: "template/tabs",
        standalone: true

    };
    gulp.src(paths.lib + '/angular-ui-bootstrap/template/tabs/*.html')
        .pipe(templateCache(options))
        .pipe(gulp.dest(paths.lib + '/dev/js'));
});*/


gulp.task('less-dev', function () {

    return gulp.src(paths.less)
        .pipe(less()).on('error', gutil.log)
        .pipe(cssMinify({noRebase: true}))
        .pipe(gulp.dest(paths.lib + '/dev/css'));
});

var tinylr;
function startLivereload() {

    tinylr = require('tiny-lr')();
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
    startLivereload();
    gulp.watch('src/less/*.less', ['less-dev']);
    gulp.watch(paths.thymeleaf
        .concat(paths.lib + '/dev/css/*')
        .concat('src/**/*.js').concat(paths.app + '/**/*.html'), notifyLiveReload);
});

gulp.task('build', ['optimize-and-copy-css', 'optimize-and-copy-js', 'optimize-and-copy-lib',
    'copy-images', 'less', 'copy-assets'], function () {
});
