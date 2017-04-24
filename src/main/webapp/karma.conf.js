// Karma configuration
// ref: https://scotch.io/tutorials/testing-angularjs-with-jasmine-and-karma-part-1
module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      //include libraries first - load the libraries in order
      'js/jquery.js',
      'js/angular.min.js',
      'js/angular-ui-router.min.js',
      'js/angular-cookies.js',
      'js/dirPagination.js',
      'js/ngStorage.js',
      'js/angular-mocks.js',

      //include project files here
       //keep app module and states before other js files
      // 'js/controllers/welcomeController.js',
      // 'app/runBlock.js',
      // 'app/constants.js',

      //controllers
      'js/controllers/*.js',

      //services
      'js/services/*.js',

      //directives
      'js/directives/*.js',


      //keep test files here
      'js/spec.js',
      'js/app.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['spec'], //progress by default


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
};
