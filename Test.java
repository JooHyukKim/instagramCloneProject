package test;

import java.util.InputMismatchException;
import java.util.Scanner;

import instagram.client.Protocol;
import instagram.server.Database;

public class Test {
	
	private static int mode = 0;
	private static final int LOGIN_MODE = 0;
	private static final int SING_UP_MODE = 1;
	private static final int HOME_MODE = 2;
	private static final int SEARCH_MODE = 3;
	private static final int MY_PROFILE = 4;
	private static final int EXIT_MODE = 5;
	private static final int ID_PASS_SERACH_MODE = 6;
	private static final int EXIT = 666;
	
	private static Scanner sc = new Scanner(System.in);
	
	Protocol pro;
	
	public static void loginSystem() {
		boolean login_flag = true;
		boolean choose_flag = true;
		boolean login_success = true;
		String userID = null;
		String password = null;
		int choose_num = 0;
		
		while(login_flag) {			
			while(choose_flag) {
				System.out.println("=========== Instagram ===========");
				System.out.println("+-------------------------------+");
				System.out.println("| 1. 로그인하기                                     |");
				System.out.println("| 2. 회원가입하기                                  |");
				System.out.println("| 3. 회원정보찾기                                  |");
				System.out.println("| 0. 종료하기                                        |");
				System.out.println("+-------------------------------+");
				System.out.print("입력 : ");
				try {
					choose_num = sc.nextInt();
					sc.nextLine();
					choose_flag = false;
				} catch (InputMismatchException e) {
				
					sc = new Scanner(System.in);
				
					System.out.println("=========== Instagram ===========");
					System.out.println("");
					System.out.println("======== System Message =========");
					System.out.println("+-------------------------------+");
					System.out.println("| 다시 입력해주시길 바랍니다.          |");
					System.out.println("+-------------------------------+");
				} // try
			} // while
			switch(choose_num) {
			case 1:
				while(login_success) {
					System.out.println("=========== Instagram ===========");
					System.out.println("+-------------------------------+");
					System.out.print("ID :");
		
					userID = sc.nextLine();
					System.out.print("PW : ");
					password = sc.nextLine();
					//login_success = authenticateUser(userID, password);		
				} // while
				mode = HOME_MODE;
				login_flag = false;
				break;
			case 2:
				mode = SING_UP_MODE;
				login_flag = false;
				break;
			case 3:
				mode = SEARCH_MODE;
				login_flag = false;
				break;
			case 0:
				System.out.println("=========== Instagram ===========");
				System.out.println("");
				System.out.println("======== System Message =========");
				System.out.println("+-------------------------------+");
				System.out.println("| 프로그램을 종료하겠습니다.           |");
				System.out.println("+-------------------------------+");
				mode = EXIT_MODE;
				login_flag = false;
				break;
			default :
				System.out.println("=========== Instagram ===========");
				System.out.println("");
				System.out.println("======== System Message =========");
				System.out.println("+-------------------------------+");
				System.out.println("| 다시 입력해주시길 바랍니다.          |");
				System.out.println("+-------------------------------+");
				break;
			} // switch
		} // while
	} // method
	
	public static void singUpSystem() {
		boolean flag = true;
		
		while(flag) {
			
		}
	}
	
	public static void searchSystem() {
		
	}

	public static void main(String[] args) {
		
		mode = LOGIN_MODE;
		
		while(true) {
			
			switch(mode) {
			case LOGIN_MODE:
				loginSystem();
				break;
			case SING_UP_MODE:
				singUpSystem();
				break;
			case HOME_MODE:
				break;
			case SEARCH_MODE:
				searchSystem();
				break;
			case MY_PROFILE:
				break;
			case ID_PASS_SERACH_MODE:
				break;
			case EXIT_MODE:
				break;
			default:
					break;
			} // switch
			
		} // while
		
	} // main
} // class
