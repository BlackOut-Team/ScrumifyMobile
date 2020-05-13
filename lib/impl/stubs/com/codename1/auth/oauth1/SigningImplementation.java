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
public abstract class SigningImplementation {

	public SigningImplementation() {
	}

	public abstract String getId() {
	}

	protected abstract String createSignature(String baseString, String consumerSecret, String DELIMITER, Token token) {
	}

	protected abstract byte[] createSignature(byte[] key, byte[] data) {
	}
}
