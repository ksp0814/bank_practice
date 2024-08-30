package dev.syntax;

import dev.syntax.model.Major;
import dev.syntax.model.Student;

public class Main {

	public static void main(String[] args) {
        // 일반적인 객체 지향 문법으로 관계 작성
        Student sy = Student.builder().id(1).name("성윤").build();
        
        Major cs = Major.builder().name("디미과").build(); // build() 메서드를 호출해야 합니다.
        
        sy.setMajor(cs);
        
        Major major = sy.getMajor();
        System.out.println(major);
    }

}
