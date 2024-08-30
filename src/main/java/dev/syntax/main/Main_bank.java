package dev.syntax.main;

import java.util.List;
import java.util.Scanner;

import dev.syntax.data.Account_bank;
import dev.syntax.model.Account;

public class Main_bank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
    	
    	System.out.println("조회할 회원의 ID를 입력해 주세요 :");
    	int memberId =sc.nextInt();
    	
    	System.out.println("조회할 은행 ID를 입력해 주세요 :");
    	int bankId = sc.nextInt();
    	
    	Account_bank account_bank = new Account_bank();
    	
    	List<Account> accountList = account_bank.findByMemberIdAndBankId(memberId, bankId);
    	
    	if (accountList.isEmpty()) {
            System.out.println("해당 회원의 계좌가 없습니다.");
        } else {
            for (Account account : accountList) {
                System.out.println("계좌 ID : " + account.getId());
                System.out.println("계좌번호 : " + account.getNumber());
                System.out.println("계좌 타입 : " + account.getType());
                System.out.println("은행 ID : " + account.getBank().getId());
                System.out.println("잔액 : " + account.getTotal_money());
                System.out.println("---------------------------");
            }
        }
    	
    	 account_bank.close();
	}
}
