package me.pepe.Twittir.Database.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserProfileImageData")
public class UserProfileImageData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	@Lob
	/*
	 * 1. TINYBLOB: Puede almacenar hasta 255 bytes.
	 * 2. BLOB: Puede almacenar hasta 64 kilobytes (KB).
	 * 3. MEDIUMBLOB: Puede almacenar hasta 16 megabytes (MB).
	 * 4. LONGBLOB: Puede almacenar hasta 4 gigabytes (GB). ('max_allowed_packet')
	 */
	@Column(name = "imageData", columnDefinition = "LONGBLOB")
	private byte[] imageData;
	public UserProfileImageData() {}
	public long getID() {
		return id;
	}
	public byte[] getImageData() {
		return imageData;
	}
}