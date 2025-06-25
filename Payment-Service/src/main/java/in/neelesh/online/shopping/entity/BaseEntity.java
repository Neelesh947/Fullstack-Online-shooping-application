package in.neelesh.online.shopping.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	public static final String ENTITY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	@Id
	private String id;

	@Column(name = "create_date_time", updatable = false)
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ENTITY_DATE_FORMAT)
	private Timestamp createDateTime;

	@Column(name = "update_date_time")
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ENTITY_DATE_FORMAT)
	private Timestamp updateDateTime;
}