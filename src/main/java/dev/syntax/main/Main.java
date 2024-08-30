package dev.syntax.main;

import dev.syntax.data.AccountDAO;
import dev.syntax.model.Account;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("조회할 회원의 ID를 입력해 주세요 :");
    	int memberId =sc.nextInt();
    	// 1. AccountDAO 인스턴스 생성
        AccountDAO accountDAO = new AccountDAO();
        
        
        // 2. 특정 회원의 모든 계좌 조회
        List<Account> accounts = accountDAO.findByMemberId(memberId);

        // . 조회된 계좌 정보 출력
        if (accounts.isEmpty()) {
            System.out.println("해당 회원의 계좌가 없습니다.");
        } else {
            for (Account account : accounts) {
                System.out.println("계좌 ID : " + account.getId());
                System.out.println("계좌번호 : " + account.getNumber());
                System.out.println("계좌 타입 : " + account.getType());
                System.out.println("은행 ID : " + account.getBank().getId());
                System.out.println("잔액 : " + account.getTotal_money());
                System.out.println("---------------------------");
            }
        }
        
        // 5. DAO 자원 해제
        accountDAO.close();
    }
}
