/**
 * 
 *         <p>
 *             Authentication - OAuth1 related code.
 *         </p>
 *     
 */
package com.codename1.auth.oauth1;


/**
 *  The OAuth1 class implements the OAuth flow, as documented here, for example:
 *  
 *  <pre>
 *  https://dev.twitter.com/docs/auth/implementing-sign-twitter
 *  </pre>
 *  
 *  @author Eric Coolman
 *  
 */
public class OAuth1 {

	public OAuth1(String callback) {
	}

	/**
	 *  Return the ID of the authenticated user, or 0L if not authenticated.
	 *  
	 *  @return user ID, or 0L.
	 */
	public long getUserId() {
	}

	/**
	 *  Return the screen name of the authenticated user, or null if not
	 *  authenticated.
	 *  
	 *  @return users screen name, or null.
	 */
	public String getScreenName() {
	}

	public void signRequest(SignedService request) {
	}

	public static void register(String callback, ServiceProvider provider, String consumerKey, String consumerSecret) {
	}

	public void authenticate() {
	}

	public void authenticate(com.codename1.ui.Form currentForm) {
	}

	/**
	 *  Handle placement of the web browser, ie. in a form or dialog.
	 *  
	 *  @param webBrowser
	 */
	public void onDisplayLogin(com.codename1.ui.Form backForm, com.codename1.components.WebBrowser webBrowser) {
	}

	/**
	 *  Handle disposal of the web browser after auth complete
	 *  
	 *  @param webBrowser
	 */
	public void onDisposeLogin(com.codename1.ui.Form backForm, com.codename1.components.WebBrowser webBrowser) {
	}

	/**
	 *  Handle a newly received access token, use this to handle one-time
	 *  operations, not including persistence (see onSaveAccessToken()).
	 *  
	 *  @param token
	 */
	public void onReceiveAccessToken(AccessToken token) {
	}

	/**
	 *  Handle a newly received access token, use this to handle one-time
	 *  operations such as persistence, etc.
	 *  
	 *  @param token
	 */
	public void onSaveAccessToken(AccessToken token) {
	}

	/**
	 *  Handle a request made to retrieve a previously stored access token. On
	 *  successful retrieval, implementors of this method should chain to
	 *  onAccessToken() to elevate privileges.
	 *  
	 *  @param token
	 */
	public boolean onLoadAccessToken() {
	}

	/**
	 *  Handle elevating access privileges.
	 *  
	 *  @param token
	 */
	public void onAuthenticated() {
	}

	/**
	 *  User denied, continue with limited access.
	 */
	public void onDeniedAccess() {
	}

	/**
	 *  Retrieve the current authenticated access token. This method should not
	 *  be called unless you previously handle onAuthenticated().
	 *  
	 *  @return
	 */
	public AccessToken getAccessToken() {
	}

	public void setErrorListener(com.codename1.ui.events.ActionListener errorListener) {
	}
}
