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
 *  @author Chen
 */
public class PlainText extends SigningImplementation {

	public static final String ID = "PLAINTEXT";

	public PlainText() {
	}

	public String getId() {
	}

	protected String createSignature(String baseString, String consumerSecret, String DELIMITER, Token token) {
	}

	@java.lang.Override
	protected byte[] createSignature(byte[] key, byte[] data) {
	}
}
