package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import instagram.vo.User;

public class test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("=========== Instagram ===========");
			System.out.println("");
			System.out.println("");
			System.out.println("+-------------------------------+");
			System.out.println("| 사용자의 E-mail을 입력해주십시오.    |");
			System.out.println("+-------------------------------+");
			System.out.print("E-mail : ");
			String email = sc.next();
			if(!email.contains("@")) {
				continue;
			}
			
			System.out.println("+-------------------------------+");
			System.out.println("| 사용하고 싶은 ID를 입력해주십시오.     |");
			System.out.println("+-------------------------------+");
			System.out.print("ID :");
			String userId = sc.next();
			
			System.out.println("+-------------------------------+");
			System.out.println("|사용하고 싶은 Password를 입력해주십시오.|");
			System.out.println("+-------------------------------+");
			System.out.print("Password : ");
			String password = sc.next();
			
			System.out.println("+-------------------------------+");
			System.out.println("|사용자의 이름을 입력해주십시오.        |");
			System.out.println("+-------------------------------+");
			System.out.print("이름 : ");
			String userName = sc.next();

			System.out.println("+-------------------------------+");
			System.out.println("|사용자의 성별을 입력해주십시오.        |");
			System.out.println("+-------------------------------+");
			System.out.print("성별 : ");
			String gender = sc.next();
			

			}
		}
}