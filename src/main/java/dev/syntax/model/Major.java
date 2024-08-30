package dev.syntax.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder
@Entity
public class Major {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	// Student(N쪽)에서 외래키를 가지고 있기 때문에 Student.major 필드가 연관관계의 주인
	// JPA에서는 외래 키를 관리할 때 연관관계의 주인만 사용함
	// 주인이 아닌 Major.students 필드는 조회를 위한 목적으로 JPQL이나 객체 그래프 탐색할 때 사용
	@OneToMany(mappedBy = "major") // 반대쪽 매핑(Student.major)의 필드 이름(major)을 지정
	@Builder.Default
	private List<Student> students = new ArrayList<>();
}





