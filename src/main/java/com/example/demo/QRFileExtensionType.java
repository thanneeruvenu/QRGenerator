/**
 * 
 */
package com.example.demo;

/**
 * @author venut
 *
 */
public enum QRFileExtensionType {

	JPEG("image/jpeg", "jpeg"), JPG("image/jpg", "jpg"), GIF("image/gif", "gif"), PNG("image/png", "png"),
	SVG("image/svg+xml", "svg");

	private String key;
	private String value;

	QRFileExtensionType(String key, String value) {
		this.setKey(key);
		this.setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	public static QRFileExtensionType fetchByKey(String key) {
		QRFileExtensionType type = JPEG;
		for(QRFileExtensionType t : QRFileExtensionType.values()) {
			if(t.key.equalsIgnoreCase(key)){
				type = t;
				break;
			}
		}
		return type;
	}
	

}
