module.exports = function(config){
  config.set({

    basePath : '../',

    files : [
      'classes/static/js/app.js',
      'classes/static/js/test.js',
      'test-classes/unit/**/*.js'
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-jasmine'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }

  });
};