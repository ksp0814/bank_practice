package dev.syntax.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private Date date;
    private int money;

    @ManyToOne
    @JoinColumn(name = "bank_id")  // bank_id 컬럼을 사용해 Bank 엔티티와 연결
    private Bank bank;

    @Column(name = "target_account_id")
    private int targetAccountId;

    @ManyToOne
    @JoinColumn(name = "account_id")  // account_id 컬럼을 사용해 Account 엔티티와 연결
    private Account account;

    private String memo;

	@Override
	public String toString() {
		return "Record [id=" + id + ", type=" + type + ", date=" + date + ", money=" + money + ", bank=" + bank
				+ ", targetAccountId=" + targetAccountId + ", account=" + account + ", memo=" + memo + "]";
	}
    
    

    // getters, setters, constructors...
}