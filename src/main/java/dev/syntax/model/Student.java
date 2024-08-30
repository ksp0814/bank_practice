package dev.syntax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@ToString
@Builder
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@ManyToOne // 학생(Many)들은 하나의 전공(One)에 속함
	private Major major;// Student는 Major를 포함(Composition)하고 있다.
	
	public void setMajor(Major major) {
		// 기존 학과와의 관계 제거
		if (this.major != null) {
			this.major.getStudents().remove(this);
		}
		this.major = major;
		
		// 객체 패러다임에서 필요하기 때문에 설정
		// 연관관계 반대편 매핑용 코드
		this.major.getStudents().add(this);
	}
}









