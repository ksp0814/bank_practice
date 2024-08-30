package dev.syntax.main;

import java.util.List;

import java.util.Scanner;

import dev.syntax.data.AccountDAO;
import dev.syntax.data.Account_bank;
import dev.syntax.data.MemberDAO;
import dev.syntax.data.RecordDAO;
import dev.syntax.model.Account;
import dev.syntax.model.Bank;
import dev.syntax.model.Member;
import dev.syntax.model.Record;

public class Login {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("아이디를 입력하시오 :");
		
		String loginId = sc.nextLine();
		
		System.out.println("비밀번호를 입력하시오 :");
		
		String password = sc.nextLine();
		
		MemberDAO memberDAO = new MemberDAO();
		AccountDAO accountDAO = new AccountDAO();
		Account_bank account_bank = new Account_bank();
		RecordDAO recordDAO = new RecordDAO();
		try {
			Member member = memberDAO.findByLoginIdAndPassword(loginId, password);
			
			if ( member != null) {
				System.out.println("로그인 성공");
				System.out.println("회원 ID = "+ member.getId());
				System.out.println("회원 이름 = " + member.getName());
				
				List<Account> accounts = accountDAO.findByMemberId(member.getId());
				
				 System.out.println("내 통장 목록 ");
	                for (Account account : accounts) {
	                    System.out.println(account);
	                }
	                System.out.println("은행 선택해주세요 : (1: 우리은행) (2: 하나은행) (3: 신한은행) (4: 국민은행) (5: 농협은행)");
	                int bank_Select = sc.nextInt();
	                List<Account> bank = account_bank.findByMemberIdAndBankId(member.getId(), bank_Select);
	                List<Record> record = recordDAO.findRecordsByAccountId(member.getId());
	                for (Account bank_list : bank) {
	                	if (bank_list != null) {
	                		System.out.println(bank_list);
	                		
	                		for (Record record_list : record) {
	                			if(record_list != null) {
	                				System.out.println(record_list);
	                			}else {
	                				System.out.println("계좌 내역이 존재하지 않습니다.");
	                			}
	                		}
	                	}else {
	                		System.out.println("해당 은행에 계좌가 없습니다.");
	                	}
	                	
	                }
			}	else {
				System.out.println("로그인 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sc.close();
			memberDAO.close();
		}
		

	}


}
