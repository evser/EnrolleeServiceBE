package by.bsu.rikz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import by.bsu.rikz.entity.enums.DocumentTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Document extends AbstractEntity {

	@Lob
	@Column(name = "document_binary")
	private byte[] documentBinary;

	@Enumerated(EnumType.STRING)
	private DocumentTypeEnum type;

	@RestResource(exported = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "enrollee_id", nullable = false)
	private Enrollee enrollee;

}