angular.module('bmauth.social.login', [])

.factory('facebook', [ function() {
	
	var facebook = {
	
		init: function() {
			window.fbAsyncInit = function() {
				FB.init({
					appId : '424422617745456',
					xfbml : true,
					version : 'v2.3'
				});
			};

			(function(d, s, id) {
				var js, fjs = d.getElementsByTagName(s)[0];
				if (d.getElementById(id)) {
					return;
				}
				js = d.createElement(s);
				js.id = id;
				js.src = "//connect.facebook.net/en_US/sdk.js";
				fjs.parentNode.insertBefore(js, fjs);
			}(document, 'script', 'facebook-jssdk'));
		}
	}
	
	return facebook;
	
}])

.factory('google', [ function() {
	var google = {
			init: function() {
				(function() {
				    var po = document.createElement('script');
				    po.type = 'text/javascript'; 
				    po.async = true;
				    po.src = 'https://apis.google.com/js/client:plusone.js?onload=bmauth_gprender';
				    po.parsetags = 'explicit';
				    var s = document.getElementsByTagName('script')[0];
				    s.parentNode.insertBefore(po, s);
				  })();
			}
	}
	
	return google;
}]);
