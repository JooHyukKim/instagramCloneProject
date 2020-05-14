package instagram.client;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import instagram.client.Protocol;
import instagram.vo.Post;
import instagram.vo.User;

public class Test {
	
	private static int mode = 0;
	private static final int LOGIN_MODE = 0;
	private static final int SING_UP_MODE = 1;
	private static final int HOME_MODE = 2;
	private static final int SEARCH_MODE = 3;
	//private static final int MY_PROFILE = 4;
	private static final int EXIT_MODE = 5;
	private static final int ID_PASS_SERACH_MODE = 6;
	private static final int EXIT = 666;
	
	private static String current_userID = null;
	
	private static Scanner sc = new Scanner(System.in);
	
	private static Protocol pro;
	
	public static void loginSystem() throws Exception {
		boolean login_flag = true;
		boolean choose_flag = true;
		boolean login_success = true;
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
					System.out.print("ID : ");
		
					current_userID = sc.nextLine();
					System.out.print("PW : ");
					password = sc.nextLine();
					try {
						login_success = pro.authenticateUser(current_userID, password);
					} catch (NullPointerException e) {}
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

	public static void singUpSystem() throws Exception {
		boolean flag = true;
		User newUser = new User();
		String userId = null;
		String userPassword = null;
		String userName = null;
		String userEmail = null;
		String userGender = null;
		
		while(flag) {
			System.out.println("=========== Instagram ===========");
			System.out.println("");
			System.out.println("+-------------------------------+");
			System.out.println("| 사용자의 E-mail을 입력해주십시오.    |");
			System.out.println("+-------------------------------+");
			System.out.print("E-mail : ");
			userEmail = sc.nextLine();
			newUser.setEmail(userEmail);
			
			System.out.println("+-------------------------------+");
			System.out.println("| 사용하고 싶은 ID를 입력해주십시오.     |");
			System.out.println("+-------------------------------+");
			System.out.print("ID :");
			userId = sc.nextLine();
			newUser.setUserId(userId);
			
			System.out.println("+-------------------------------+");
			System.out.println("|사용하고 싶은 Password를 입력해주십시오.|");
			System.out.println("+-------------------------------+");
			System.out.print("Password : ");
			userPassword = sc.nextLine();
			newUser.setPassword(userPassword);
			
			System.out.println("+-------------------------------+");
			System.out.println("|사용자의 이름을 입력해주십시오.        |");
			System.out.println("+-------------------------------+");
			System.out.print("이름 : ");
			userName = sc.nextLine();
			newUser.setUserName(userName);
		
			System.out.println("+-------------------------------+");
			System.out.println("|사용자의 성별을 입력해주십시오.        |");
			System.out.println("+-------------------------------+");
			System.out.print("성별 : ");
			userGender = sc.nextLine();
			newUser.setGender(userGender);
			
			flag = pro.addUser(newUser);
		}
	}
	
	public static void iD_PASS_searchSystem() throws Exception {
		int choose_num = 0;
		String email = null;
		String id = null;
		boolean choose_flag = true;
		boolean flag = true;
		boolean result = true;
		
		while(flag) {			
			while(choose_flag) {
				System.out.println("=========== Instagram ===========");
				System.out.println("");
				System.out.println("+-------------------------------+");
				System.out.println("| 1. E-mail로 찾기                              |");
				System.out.println("| 2. ID로 찾기                                      |");
				System.out.println("| 3. 뒤로가기                                        |");
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
				System.out.println("+-------------------------------+");
				System.out.println("| 사용자의 E-mail을 입력해주십시오.    |");
				System.out.println("+-------------------------------+");
				System.out.print("입력 : ");
				email = sc.nextLine();
				result = pro.getUserByEmail(email);
				
				if(result)
					flag = false;
				
				break;
			case 2:
				System.out.println("+-------------------------------+");
				System.out.println("| 사용자의 ID를 입력해주십시오.        |");
				System.out.println("+-------------------------------+");
				System.out.print("입력 : ");
				id = sc.nextLine();
				result = pro.getUser(id);
				break;
			case 3:
				mode = LOGIN_MODE;
				flag = false;
				break;
			default :
				choose_flag = true;
				break;
			}
		} // while
	}
	
	public static void homeMenu() {
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
	}
	
	public static void homeSystem() throws Exception {
		boolean login_flag = true;
		// MainPage접속
		while (login_flag) {
			int choose_num = 0;
			boolean choose_flag = true;
			boolean login_success = true;
			String userID = null;
			String password = null;
			//ArrayList<Post> mainPage = pro.getAllPostsOfWithLimit();
			while(choose_flag) {
				//System.out.println(mainPage); //리턴타입 ArrayList<Post>
				homeMenu();
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
				} // try}
			}
			
			switch(choose_num) {
			case 1:
				System.out.println("+-------------------------------+");
				System.out.println("| 좋아요 누른 게시물 ID를 입력해주세요        |");
				System.out.println("+-------------------------------+");
				System.out.print("입력 : ");
				//pro.likePost(sc.nextLine());
				System.out.println("좋아요를 눌렀습니다.");
				break;
			case 2:
				System.out.println("+----------------------------------+");
				System.out.println("|댓글을 달 게시물 ID 를 입력해주세요 (String)|");
				System.out.println("+----------------------------------+");
				System.out.print("입력 : ");
				String postId = sc.nextLine();
				System.out.println("+----------------------------------+");
				System.out.println("|    댓글을 입력해주세요 (String)        |");
				System.out.println("+----------------------------------+");
				System.out.print("입력 : ");
				String comment = sc.nextLine();
				//pro.addComment(userID, postId, comment);
				System.out.println("댓글이 추가되었습니다.");
				break;
				
			case 3:
				System.out.println("+--------------------------------+");
				System.out.println("| 클릭한 유저ID 입력해주세요. (String) |");
				System.out.println("+--------------------------------+");
				System.out.print("입력 : ");				
				//User selectedUser = pro.getUser(sc.nextLine());
				//System.out.println(selectedUser);
				//System.out.println(pro.getAllPostsOfPerson(selectedUser.getUserId()));
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
				System.out.println(pro.getUser(sc.nextLine()));
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
				searchSystem();
				break;
			case 9:
				System.out.println("+------------------------------------+");
				System.out.println("| 내 프로필 클릭.유저본인 상세페이지로 이동합니다.|");
				System.out.println("+------------------------------------+");
				System.out.println();
				break;
			case 0:
				mode = EXIT_MODE;
				login_flag = false;
				break;
				
				default:
					break;
			} // switch		
		} // while
	}// class
 // homesystem()
	
	private static void searchSystem() throws Exception {
		ArrayList<User>	users = null;
		String search_way = null;
		String name = null;
		String tag = null;
		boolean flag = true;
		
		while(flag) {
			System.out.println("=========== Instagram ===========");
			System.out.println("+-------------------------------+");
			System.out.println("| 검색할 방법을 선택해 주세요.          |");
			System.out.println("| 1.(@) 사용자 / 2.(#) 해쉬태그          |");
			System.out.println("+-------------------------------+");
			System.out.println("입력 : ");
			search_way = sc.nextLine();
			
			if(search_way.equals("@")) {
				System.out.println("=========== Instagram ===========");
				System.out.println("+-------------------------------+");
				System.out.println("| 검색할 사용자 이름을 입력해주세요.     |");
				System.out.println("+-------------------------------+");
				System.out.println("입력 : ");
				name = sc.nextLine();
				users = pro.getUserByName(name);	
				System.out.println(users);
			}
			else if(search_way.equals("#")) {
				System.out.println("=========== Instagram ===========");
				System.out.println("+-------------------------------+");
				System.out.println("| 검색할 해쉬태크를 입력해주세요.       |");
				System.out.println("+-------------------------------+");
				System.out.println("입력 : ");
				tag = sc.nextLine();
				users = pro.getUserByTag(tag);
				System.out.println(users);
			}
			else {
				System.out.println("=========== Instagram ===========");
				System.out.println("");
				System.out.println("======== System Message =========");
				System.out.println("+-------------------------------+");
				System.out.println("| 다시 입력해주시길 바랍니다.          |");
				System.out.println("+-------------------------------+");;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		boolean main_while_flag = true;
		mode = HOME_MODE;
		
		while(main_while_flag) {
			
			switch(mode) {
			case LOGIN_MODE:
				loginSystem();
				break;
			case SING_UP_MODE:
				singUpSystem();
				break;
			case HOME_MODE:
				homeSystem();
				break;
			case ID_PASS_SERACH_MODE:
				iD_PASS_searchSystem();
				break;
			case EXIT_MODE:
				main_while_flag = false;
				break;
			default:
					break;
			} // switch
			
		} // while
		
	} // main

} // class
