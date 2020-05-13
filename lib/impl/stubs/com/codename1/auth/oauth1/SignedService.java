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
 */
public interface SignedService {

	/**
	 *  Implementer should pass all header and body parameters to the target
	 *  to be used in the signing process. 
	 *  
	 *  @param target
	 */
	public void applyParameters(java.util.Hashtable target);

	/**
	 *  Get the service endpoint URL for this service.
	 *  
	 *  @return service endpoint URL.
	 */
	public String getUrl();

	/**
	 *  Determine if request is a POST or a GET request. 
	 *  
	 *  @return true of a POST request, otherwise false.
	 */
	public boolean isPost();

	/**
	 *  Add a header name/value to the service request.
	 *  
	 *  @param name name of header value.
	 *  @param value value of header value.
	 */
	public void addRequestHeader(String name, String value);
}
