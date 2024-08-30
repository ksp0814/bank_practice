package dev.syntax;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

import dev.syntax.model.Account;
import dev.syntax.model.Major;
import dev.syntax.model.Member;
import dev.syntax.model.Student;

class MainTest {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("bank");
	EntityManager manager = factory.createEntityManager();
	EntityTransaction transaction = manager.getTransaction();

	@Test
	void 양방향_연관관계_저장() {
		transaction.begin();

		// 학과 생성 및 영속화
		Major kor = Major.builder().name("국문학과").build();
		manager.persist(kor);

		// 학생 생성 및 영속화
		Student yoon = Student.builder().name("성윤").build();
		yoon.setMajor(kor); // 연관관계 설정(student -> major), 엔티티 매니저는 이곳에 입력된 값을 통해 외래키를 관리함
		manager.persist(yoon);
		
		Student kim = Student.builder().name("창영").build();
		kim.setMajor(kor); // 연관관계 설정(student -> major), 엔티티 매니저는 이곳에 입력된 값을 통해 외래키를 관리함
		manager.persist(kim);

		transaction.commit();
		// 양방향 연관관계에서는 연관관계의 주인이 외래 키를 관리하기 때문에(Student.major 필드)
		// 주인이 아닌 방향인 Major.students 필드는 값을 따로 설정하지 않아도 DB에 외래키가 정상적으로 적용됨  
	}

	@Test
	void 양방향_연관관계_외래키_설정_미흡() {
		transaction.begin();

		// 학생 생성 및 영속화
		Student yoon = Student.builder().name("창영").build();
		manager.persist(yoon);

		// 학과 생성 및 영속화
		Major kor = Major.builder().name("영문과").build();
		kor.getStudents().add(yoon); // 주인이 아닌 방향에서 외래키 설정
		// 외래키를 관리하는 연관관계의 주인인 Student.major에 별도의 값을 작성하지 않았기 때문에
		// major_id의 null이 저장되었음
		
		manager.persist(kor);

		transaction.commit();
	}

	@Test
	void 양방향_연관관계_조회() {
		// id가 1인 학과 조회
		Major major = manager.find(Major.class, 3);
		
		// 학과에 속한 모든 학생 목록 조회
		List<Student> students = major.getStudents();
		
		System.out.println(students);
	}

	@Test
	void 양방향_연관관계_중_객체_패러다임에서_연관관계_설정이_미흡한_경우() {
		// 객체 패러다임에서는 연관관계의 주인에'만' 값을 설정하는 방식은 문제가 될 수 있음
		
		Major ko = Major.builder().name("국문학과").build();
		Student yoo = Student.builder().name("YOO").build();
		Student kang = Student.builder().name("KANG").build();
		
		yoo.setMajor(ko); // 학생에게 학과 연관관계 설정(student -> major)
		kang.setMajor(ko);
		
		// 국문학과에 속한 학생 목록 조회
		List<Student> students = ko.getStudents();
		System.out.println(students); // []
		// JPA를 사용하지 않는 객체 지향 패러다임 맥락에서 외래키의 반대 방향에 연관관계를 설정하지 않았기 때문에
		// 학생 목록이 비어있는 배열이 출력됨
	}
	
	@Test
	void 양방향_연관관계_중_객체_패러다임에서_연관관계_설정을_양쪽_모두_적용한_경우() {
		Major ko = Major.builder().name("국문학과").build();
		Student yoo = Student.builder().name("YOO").build();
		Student kang = Student.builder().name("KANG").build();
		
		yoo.setMajor(ko);
		// 학과에서도 해당 학생을 등록
		List<Student> students = ko.getStudents();
		students.add(yoo);
		
		kang.setMajor(ko);
		ko.getStudents().add(kang); // 한 줄로 작성
		
		// 국문학과에 속한 학생 목록 조회
		List<Student> studentsList = ko.getStudents();
		System.out.println(studentsList); // [dev.syntax.model.Student@1320e68a, dev.syntax.model.Student@4b033eac]
		// -> 학생 목록이 정상적으로 조회됨
		// 객체 패러다임에서는 객체까지 고려하기 위해 양쪽 다 관계를 맺어야함
	}

	@Test
	void 양방향_연관관계_중_JPA_관계형_패러다임과_객체_패러다임에서_연관관계_설정을_양쪽_모두_적용한_경우() {
		transaction.begin();
		
		// 학과 데이터 생성 및 저장
		Major en = Major.builder().name("영문학과").build();
		manager.persist(en);
		
		// 학생1 생성 및 저장
		Student yoo = Student.builder().name("YOO").build();
		yoo.setMajor(en); // 외래키 설정(관계형 패러다임)
		en.getStudents().add(yoo);
		manager.persist(yoo);// 객체 패러다임에서 필요하기 때문에 설정
		
		// 학생 2 생성 및 저장
		Student kang = Student.builder().name("KANG").build();
		kang.setMajor(en);
		en.getStudents().add(kang); // 학과에 학생 등록, 연관관계의 주인이 아니기 때문에 데이터 저장 시 사용되진 않음, 객체 패러다임에서 사용하기 위해 저장
		manager.persist(kang);	
		// 영문학과에 속한 학생 목록 조회
		Major major = manager.find(Major.class, en.getId());
		System.out.println(major.getStudents());
		transaction.commit();
	}
	
	@Test
	void 연관관계_매핑용_편의_메서드를_활용하여_리팩토링() {
		transaction.begin();
		
		// 학과 데이터 생성 및 저장
		Major en = Major.builder().name("영문학과").build();
		manager.persist(en);
		
		// 학생1 생성 및 저장
		Student yoo = Student.builder().name("YOO").build();
		yoo.setMajor(en); // 외래키 설정(관계형 패러다임)
		manager.persist(yoo);// 객체 패러다임에서 필요하기 때문에 설정
		
		// 학생 2 생성 및 저장
		Student kang = Student.builder().name("KANG").build();
		kang.setMajor(en);
		manager.persist(kang);	
		// 영문학과에 속한 학생 목록 조회
		Major major = manager.find(Major.class, en.getId());
		System.out.println(major.getStudents());
		transaction.commit();
	}
	
	@Test
	void 학생의_학과_변경_테스트_에러_케이스() {
		Major cs = Major.builder().name("컴퓨터 공학").build();
		Major ko = Major.builder().name("국어 국문").build();
		
		Student kim = Student.builder().name("KIM").build();
		
		kim.setMajor(cs); //kim 학생을 컴공과로 지정
		
		// 전공 변경
		kim.setMajor(ko);
		List<Student> students = cs.getStudents();
		System.out.println(students.get(0).getName());
	}
	
	@Test
	void 학생의_학과_변경_테스트_해결() {
		Major cs = Major.builder().name("컴퓨터 공학").build();
		Major ko = Major.builder().name("국어 국문").build();
		
		Student kim = Student.builder().name("KIM").build();
		
		kim.setMajor(cs); //kim 학생을 컴공과로 지정
		
		// 전공 변경
		kim.setMajor(ko);
		List<Student> cs_students = cs.getStudents();
		System.out.println(cs_students); // [] , 컴공과 학생이 없이 때문에 빈 배열 추력
		
		List<Student> ko_students = ko.getStudents();
		System.out.println(ko_students.get(0).getName()); // kim이 국문과에 등록되었다.
	}
	
	@Test
	void 뱅크_해당_멤버_조회 () {
		Member member1 = manager.find(Member.class, 1);
		System.out.println(member1.getName());
	}
	
	@Test
	void 뱅크_해당_멤버_계좌_조회 () {
		int id = 2;
		Account account1 = manager.find(Account.class,id);
		System.out.println(account1);
		
	}
	
}

//TypedQuery typedQuery = em.createQuery("select p from Person p where p.department.name = :name", Person.class);






