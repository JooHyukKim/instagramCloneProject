package instagram.client;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.a.TracingPacketReader;

import instagram.client.Protocol;
import instagram.config.ServerInfo;
import instagram.vo.Comment;
import instagram.vo.Post;
import instagram.vo.User;

public class Test {
	private static int mode;
	private static int chooseNum;
	private static String loginUserId;
	
	private static final int LOGIN_MODE = 1;
	private static final int SING_UP_MODE = 2;
	private static final int HOME_MODE = 3;
	private static final int SEARCH_MODE = 4;
	//private static final int MY_PROFILE = 4;
	private static final int EXIT_MODE = 5;
	private static final int ID_PASS_SERACH_MODE = 6;
	private static final int EXIT = 0;
	
	private static Scanner sc = new Scanner(System.in);
	
	private static Protocol pro;
	
	public Test() { // 생성자 생성
		try	{	    
		    pro =  new Protocol("127.0.0.1");
		    System.out.println("생성자 생성");
			} catch(Exception cnfe) {
				System.out.println("Broker Constructor : " + cnfe);
			}
	}
	
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		
		while(true) {
			System.out.println("=========== Instagram ===========");
			System.out.println("+-------------------------------+");
			System.out.println("| 1. 로그인하기                                     |");
			System.out.println("| 2. 회원가입하기                                  |");
			System.out.println("| 3. 회원정보찾기                                  |");
			System.out.println("| 0. 종료하기                                        |");
			System.out.println("+-------------------------------+");
			sc = new Scanner(System.in);

			System.out.print("입력 : ");
			mode = sc.nextInt();
			
			if(mode != 1 && mode != 2 && mode !=3 && mode != 0  ) {
				System.out.println("=========== Instagram ===========");
				System.out.println("");
				System.out.println("======== System Message =========");
				System.out.println("+-------------------------------+");
				System.out.println("| 다시 입력해주시길 바랍니다.          |");
				System.out.println("+-------------------------------+");
			}else break;
		}
		
		while(true) {
		switch(mode) {
		
		case LOGIN_MODE:
			loginSystem();
			mode = HOME_MODE;
			break;
		case SING_UP_MODE:
			singUpSystem();
			mode = LOGIN_MODE;
		case HOME_MODE:
			homeSystem();
			break;

		}
		}
				
	} // main

	private static void homeSystem() throws Exception {
		while(true) {
		System.out.println(pro.getSomePostsOfFollowingPerson(loginUserId));
		
		System.out.println("=========== Instagram ===========");
		System.out.println("+-------------------------------+");
		System.out.println("| 1. 게시물 좋아요                                 |");
		System.out.println("| 2. 댓글달기                                 	|");
		System.out.println("| 3. 해당 유저페이지로 불러오기                |");
		System.out.println("| 4. 해쉬태그로 검색                               |");
		System.out.println("| 5. 태그된 모든 유저 검색                       |");
		System.out.println("| 6. 클릭한 모든 유저 검색                       |");
		System.out.println("| 7. 홈으로 이동                                    |");
		System.out.println("| 8. 검색 ( @ / # )               |");
		System.out.println("| 9. 내 페이지로 이동                              |");
		System.out.println("| 0. 종료                                             |");
		System.out.println("+-------------------------------+");
		
		sc = new Scanner(System.in);

		System.out.print("입력 : ");
		chooseNum = sc.nextInt();
		if(chooseNum != 1 && chooseNum != 2 && chooseNum !=3 && chooseNum != 4 && chooseNum != 5 && chooseNum != 6
				&& chooseNum != 7 && chooseNum != 8 && chooseNum != 9 && chooseNum != 0) {
			System.out.println("=========== Instagram ===========");
			System.out.println("");
			System.out.println("============ System Message ============");
			System.out.println("+---------------------------------------+");
			System.out.println("| 1~9 사이로 다시 입력해주시길 바랍니다.          |");
			System.out.println("+---------------------------------------+");
		}else break;
		}
		
		switch(chooseNum) {
		case 1:
			System.out.println("+-------------------------------+");
			System.out.println("| 좋아요 누른 게시물 ID를 입력해주세요        |");
			System.out.println("+-------------------------------+");
			System.out.print("입력 : ");
			String postId = sc.next();
			pro.likePost(postId);
			System.out.println("좋아요를 눌렀습니다.");
			break;
		case 2:
			System.out.println("+----------------------------------+");
			System.out.println("|댓글을 달 게시물 ID 를 입력해주세요 (String)|");
			System.out.println("+----------------------------------+");
			System.out.print("입력 : ");
			String postId1 = sc.next();
			System.out.println("+----------------------------------+");
			System.out.println("|    댓글을 입력해주세요 (String)        |");
			System.out.println("+----------------------------------+");
			System.out.print("입력 : ");
			String comment = sc.next();
			pro.addComment(loginUserId, postId1, comment);
			
			System.out.println("댓글이 추가되었습니다.");
			break;
			
		case 3:
			System.out.println("+--------------------------------+");
			System.out.println("| 클릭한 유저ID 입력해주세요. (String) |");
			System.out.println("+--------------------------------+");
			System.out.print("입력 : ");				
			String userId = sc.next();
			pro.getAllPostsOfPerson(userId);
			break;
		case 4:
			System.out.println("+---------------------------------+");
			System.out.println("| 클릭한 해쉬태그를 입력해주세요. (String)|");
			System.out.println("+---------------------------------+");
			System.out.print("입력 : ");			
			//System.out.println(pro.getPostsByHashTag(sc.nextLine()));
			break;
		case 5:
			System.out.println("+--------------------------------+");
			System.out.println("| 클릭하신 PeopleTag의 포스트 ID를 입력   |");
			System.out.println("+--------------------------------+");
			System.out.print("입력 : ");				
			//System.out.println(pro.getPersontagsOnPost(sc.nextLine()));
			break;
		case 6:
			System.out.println("+--------------------------------+");
			System.out.println("| 클릭하신 person의 이름를 입력해주세요     |");
			System.out.println("+--------------------------------+");
			System.out.print("입력 : ");				
			//System.out.println(pro.getUser(sc.nextLine()));
			break;
		case 7:
			System.out.println("+--------------------------------+");
			System.out.println("|     홈버튼 클릭. 홈화면을 로드합니다.  |");
			System.out.println("+--------------------------------+");	
			//System.out.println(mainPage);
			break;
		case 8:
			System.out.println("+--------------------------------+");
			System.out.println("|        검색내용을 입력해주세요.      |");
			System.out.println("+--------------------------------+");
			System.out.print("입력 : ");
			//searchSystem();
			break;
		case 9:
			System.out.println("+------------------------------------+");
			System.out.println("| 내 프로필 클릭.유저본인 상세페이지로 이동합니다.|");
			System.out.println("+------------------------------------+");
			System.out.println();
			break;
		case 0:
			mode = EXIT_MODE;
			//login_flag = false;
			break;
		}
	}

	private static void singUpSystem() {
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
			System.out.println("E-mail 양식을 지켜 입력해주세요");
			continue;
		}
		
		System.out.println("+-------------------------------+");
		System.out.println("| 사용하고 싶은 ID를 입력해주십시오.     |");
		System.out.println("+-------------------------------+");
		System.out.print("ID :");
		String userId = sc.next();
		if(!pro.checkUserId(userId)) {
			System.out.println("이미 같은 ID가 존재합니다.");
			continue;
		}
		
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

		System.out.println("+---------------------------------------+");
		System.out.println("|사용자의 성별을 입력해주십시오.(남자 : M, 여자: W |");
		System.out.println("+---------------------------------------+");
		System.out.print("성별 : ");
		String gender = sc.next();
		if(!gender.equals("M") && !gender.equals("W") ) {
			continue;
		}
		try {
		User user= new User(userId, userName, password, email, gender);
		pro.addUser(user);
		} catch (Exception e) {
		}
		break;
		}
	}


	private static void loginSystem() throws Exception {
		while(true) {
		System.out.println("+-------------------------------+");
		System.out.println("| 사용자의 ID를 입력해주십시오.        |");
		System.out.println("+-------------------------------+");
		System.out.print("입력 : ");
		String userId = sc.next();
		
		System.out.println("+-------------------------------+");
		System.out.println("| 사용자의 password를 입력해주십시오.        |");
		System.out.println("+-------------------------------+");
		System.out.print("입력 : ");
		String password = sc.next();
		
		boolean result = pro.authenticateUser(userId, password);
		System.out.println(result+"==========================================");
		if(result== false) continue;
		loginUserId = userId;
		break;
		}
	}
	
}











