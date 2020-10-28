package com.ruoyi.fangyuanapi.wechat;

public class NotifyResource {
	private String original_type;
	private String algorithm;
	private String ciphertext;
	private String associated_data;
	private String nonce;
	
	@Override
	public String toString() {
		return "NotifyResource [original_type=" + original_type + ", algorithm=" + algorithm + ", ciphertext=" + ciphertext + ", associated_data=" + associated_data + ", nonce=" + nonce + "]";
	}
	public String getOriginal_type() {
		return original_type;
	}
	public void setOriginal_type(String original_type) {
		this.original_type = original_type;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getCiphertext() {
		return ciphertext;
	}
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
	public String getAssociated_data() {
		return associated_data;
	}
	public void setAssociated_data(String associated_data) {
		this.associated_data = associated_data;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
}
