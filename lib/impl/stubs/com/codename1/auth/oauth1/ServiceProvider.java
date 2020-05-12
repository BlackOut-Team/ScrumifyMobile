/**
 * 
 *         <p>
 *             Authentication - OAuth1 related code.
 *         </p>
 *     
 */
package com.codename1.auth.oauth1;


/**
 *  @author Eric Coolman
 *  
 */
public class ServiceProvider {

	/**
	 *  @param id
	 *  @param requestTokenUrl
	 *  @param accessTokenUrl
	 *  @param authenticateUrl
	 *  @param signer
	 */
	public ServiceProvider(String id, String requestTokenUrl, String accessTokenUrl, String authenticateUrl, String authorizeUrl, SigningImplementation signer) {
	}

	/**
	 *  @return the id
	 */
	public String getId() {
	}

	/**
	 *  @return the requestTokenUrl
	 */
	public String getRequestTokenUrl() {
	}

	/**
	 *  @return the accessTokenUrl
	 */
	public String getAccessTokenUrl() {
	}

	/**
	 *  @return the authenticateUrl
	 */
	public String getAuthenticateUrl(RequestToken requestToken) {
	}

	/**
	 *  @return the authenticateUrl
	 */
	public String getAuthorizeUrl(RequestToken requestToken) {
	}

	/**
	 *  @return the signer
	 */
	public SigningImplementation getSigner() {
	}
}
