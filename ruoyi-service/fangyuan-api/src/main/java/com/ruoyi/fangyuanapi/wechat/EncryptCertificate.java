package com.ruoyi.fangyuanapi.wechat;

/**
 * 微信返回的加密证书对象
 */
public class EncryptCertificate {


    private String algorithm;
    private String nonce;
    private String associated_data;
    private String ciphertext;
    
	@Override
	public String toString() {
		return "EncryptCertificate [algorithm=" + algorithm + ", nonce=" + nonce + ", associated_data=" + associated_data + ", ciphertext=" + ciphertext + "]";
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getAssociated_data() {
		return associated_data;
	}
	public void setAssociated_data(String associated_data) {
		this.associated_data = associated_data;
	}
	public String getCiphertext() {
		return ciphertext;
	}
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	
}
