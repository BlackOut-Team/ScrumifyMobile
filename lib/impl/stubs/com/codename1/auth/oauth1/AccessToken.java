/**
 * 
 *         <p>
 *             Authentication - OAuth1 related code.
 *         </p>
 *     
 */
package com.codename1.auth.oauth1;


/**
 * 
 *  @author Eric Coolman
 */
public class AccessToken implements Token, com.codename1.io.Externalizable {

	/**
	 *  INTERNAL - DO NOT USE
	 * 
	 *  A default constructor as required by Storable/Externalizable.
	 */
	public AccessToken() {
	}

	/**
	 *  @return the token
	 */
	public String getToken() {
	}

	/**
	 *  @return the Secret
	 */
	public String getSecret() {
	}

	/**
	 *  @return the id
	 */
	public long getId() {
	}

	/**
	 *  @return the screenName
	 */
	public String getScreenName() {
	}

	public void applyParameters(java.util.Hashtable target) {
	}

	public void externalize(java.io.DataOutputStream os) {
	}

	public String getObjectId() {
	}

	public int getVersion() {
	}

	public void internalize(int version, java.io.DataInputStream is) {
	}

	public void read(java.util.Hashtable h) {
	}

	public static void initStorage() {
	}
}
