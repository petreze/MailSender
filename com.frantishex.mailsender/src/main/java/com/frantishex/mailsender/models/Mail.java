package com.frantishex.mailsender.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.frantishex.mailsender.enums.MailEnum;

@Entity
@Table(name = "mail")
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String receivingEmail;

	private String subject;

	private String text;

	LocalDate date = LocalDate.now();

	@Enumerated(EnumType.STRING)
	private MailEnum status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceivingEmail() {
		return receivingEmail;
	}

	public void setReceivingEmail(String receivingEmail) {
		this.receivingEmail = receivingEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public MailEnum getStatus() {
		return status;
	}

	public void setStatus(MailEnum status) {
		this.status = status;
	}

}