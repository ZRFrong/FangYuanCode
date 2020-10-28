package com.ruoyi.fangyuanapi.wechat;

/**
 * 微信证书请求接口参数
 */
public class Certificate {

	private String serial_no;
	private String effective_time;
	private String expire_time;
	private EncryptCertificate encrypt_certificate;
	
	@Override
	public String toString() {
		return "Certificate [serial_no=" + serial_no + ", effective_time=" + effective_time + ", expire_time=" + expire_time + ", encrypt_certificate=" + encrypt_certificate + "]";
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getEffective_time() {
		return effective_time;
	}
	public void setEffective_time(String effective_time) {
		this.effective_time = effective_time;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	public EncryptCertificate getEncrypt_certificate() {
		return encrypt_certificate;
	}
	public void setEncrypt_certificate(EncryptCertificate encrypt_certificate) {
		this.encrypt_certificate = encrypt_certificate;
	}

}
