package instagram.client;


import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

import instagram.exception.DuplicateRercordException;
import instagram.exception.RecordNotFoundException;
import instagram.vo.Comment;
import instagram.vo.Hashtag;
import instagram.vo.Post;
import instagram.vo.User;
/*
 없어진 기능들...
 18 = 15와 동일
 27 = 24와동일
 28 = 25와 동일 "클릭한모든 유저가 아니라 클릭은 한사람 밖에 못함"
 
  
 */
public class App {
	private static Scanner sc = new Scanner(System.in);
	private static Protocol pro =  new Protocol("127.0.0.1");
	
	public static boolean login = false;
	public static String currentUserId = ""; 
	public static int option = -1;
	
	public static void menu() {System.out.println("=========== Instagram ===========");System.out.println("+-------------------------------+");System.out.println("| 0. 로그인하기--------------------|");System.out.println("| 1. 회원가입하기-------------------|");System.out.println("| 6. 회원정보찾기id----------------|");System.out.println("| 2. 회원정보찾기email--------------|");System.out.println("| 3. 회원정보변경-------------------|");System.out.println("| 4. 회원탈퇴----------------------|");System.out.println("| 5. 종료하기----------------------|");System.out.println("+-------------------------------+");System.out.println("============="+currentUserId+ "==============");System.out.println("+--------------------------------+");System.out.println("| 10. 게시물포스팅------------------|");System.out.println("| 11. 게시물수정하기-----------------|");System.out.println("| 12. 게시물 지우기------------------|");System.out.println("| 13. 게시물 좋아요------------------|");System.out.println("| 15. 해쉬태그로 게시물 검색------------|");System.out.println("| 16. 클릭한 유저의 모든 게시물들 검색-----|");System.out.println("| 17. 해당 게시물의 해시태그 목록---------|");System.out.println("| 19. 댓글달기----------------------|");System.out.println("| 20. 댓글수정하기-------------------|");System.out.println("| 21. 댓글지우기---------------------|");System.out.println("| 22. 게시물의 댓글조회----------------|");System.out.println("| 222. 댓글좋아요----------------|");System.out.println("| 23. 나의 댓글조회-------------------|");System.out.println("| 24. 해당 게시물에 태그된 유저들 검색------|");System.out.println("| 25. 클릭한 유저 검색-----------------|");System.out.println("| 26. 내 페이지로 이동-----------------|");System.out.println("| 29. 클릭한 유저가 팔로잉하는 이웃목록-----|");System.out.println("| 30. 클릭한 유저의 팔로워 목록리턴--------|");System.out.println("| 31. @유저태그 또는 #해시태그로 검색------|");System.out.println("+-------------------------------+");}
	
	public static void main(String[] args) throws Exception {
		while (true) {
			menu();
			System.out.print("기능 선택 : ");
//			sc.nextLine();
			option = sc.nextInt();
			switch (option) {
			case 0: // 0. 로그인하기. id02로 로그인하기 로그인 성공시에 static userid가 입력되어 로그인시 사용가능한 기능들을 ACCESS할수있음
				System.out.print("아이디 입력 : ");sc.nextLine();
				String id = sc.nextLine();
				System.out.print("비밀번호 입력 : ");
				String password = sc.nextLine();
				try {
					pro.authenticateUser(id, password);
					currentUserId = id;
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 1: // 1. 새로운 유저 추가.
				try {
					pro.addUser(new User("id09", "Real", "1234", "beanskobe@gmail.com", "M")); // new
					pro.addUser(new User("id08", "Kim", "1234", "beanskobe@gmail.com", "M")); // exist
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 2: // 비밀번호 전송하는.
				try {
					pro.getUserByEmail("aaa@test.com"); //존재하는 
					pro.getUserByEmail("dsafdsaf@ghdlkags.com");//존재하지않는 >>
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 6: // 21. 아이디를 들고가서 비밀번호를 반환한다. 이론상으로는 이메일로 전송됨.
				try {
					pro.getUserById("id02"); //존재하는아이디
					pro.getUserById("id0251"); //존재하지않는 >>에러
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 3: // 3. 회원정보변경.... USER생성자를 들고가서 ID/비번이 맞으면 바꿔주고 아니면 에러
				try {
					pro.updateUser(new User("id01", "afdsafa", "1234", "beanskobe@sayclub.com", "M")); // 존재하는 유저 팔로잉은 못바꿈.
					pro.updateUser(new User("id02", "Ree", "zzzz", "beanskobe@gmail.com", "F"));//존재하지않는 >>에러
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 4: // 4. 회원탈퇴. 3번과 비슷하다..... User.Vo의 (id,pass) 생성자를 들고가서 ID/비번이 맞으면 삭제 아니면 에러
				try {
					pro.deleteUser(new User("id07", "1234"));//삭제됨
					pro.deleteUser(new User("id9999", "1234")); // 존재하지않는 유저 에러.
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 5: // 낫띵
				System.out.println("하하하하");
				break;
			
			case 10: // 10(로그인전용). 포스팅하기
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.addPost(currentUserId, new Post("post33", "대애박", "local:C//휴지통"));
				} 
				catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 11: // 11(로그인전용). id02로 로그인하기 게시물수정.... userId와 Post.VO를 들고가서 본인의 게시물이 아니면 에러
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.updatePost(currentUserId, new Post("post01", "굿굿", "local:C//휴지통"));
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 12: // 12(로그인전용)id02기준 게시물지우기. 4번과 비슷하다..... postId의 게시물이 currentUserId를 가지고 있으면 삭제. 아니면 패스.
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.deletePost(currentUserId, new Post("post11"));//삭제됨
					pro.deletePost(currentUserId, new Post("post16")); // 다른게정포스트
					
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 13: // 13(로그인전용) 좋아요누르면 로그인된 아이디로 like가 추가된다.
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.likePost(currentUserId,"post05");
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
				
			case 15: // 15 해당 해쉬태그를 가지고 있는 게시물들을 찾아서 가지고온다.
				try {
					Iterator<Post> posts = pro.getPostsByHashTag(new Hashtag("algorithm")).iterator();
					while (posts.hasNext()) System.out.println((Post) posts.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 16: // 16 해당 userName를 클릭하면 해당 유저의 모든 게시물들을 반환한다.
				try {
					Iterator<Post> posts = pro.getAllPostsOfPerson("id02").iterator();
					while (posts.hasNext()) System.out.println((Post) posts.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 17: // 17 해당 게시물의 해시태그 목록
				try {
					Iterator<Hashtag> hashtags = pro.getHashtagsOnPost("post02").iterator();
					while (hashtags.hasNext()) System.out.println((Hashtag) hashtags.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
				
			case 19: // 19(로그인전용) 댓글달기
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.addComment(currentUserId, "post05","주혁이 화이팅.");
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
			
			case 20: // 20(로그인전용). 댓글수정... id05로 로그인하기 
				// 로그인된 회원이 특정게시물에 등록한 댓글을 수정. required :: loggedin 
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.updateComment(currentUserId, 29 ,"post20", "만세.....");
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 21: // 21(로그인전용) id02로 로그인하기 
				/// 로그인된 유저 자신의 댓글만 삭제가능
				// 게시물에 있는 댓글들중 자신의 댓글에는 삭제가 가능하게 되어있음
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.deleteComment(currentUserId, "post02", 23);
					pro.deleteComment(currentUserId, "post03", 24);
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 22: // 게시물의 모든 댓글 조회. // 댓글삭제기능은 로그인 상태서만 나타남.
				try {
					Iterator<Comment> comments = pro.getCommentsOnPost("post20").iterator();
					while (comments.hasNext()) System.out.println((Comment) comments.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
				
			case 222: //댓글좋아요 누르기.. 뭐없음.
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					pro.likeComment(currentUserId, 27);
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
				
			case 23: //(로그인필요) 나의 모든 댓글조회// id05로 로그인할것... 로그인된 유저의 활동기록을 나타내는것....
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					Iterator<Comment> comments = pro.getAllCommentsOfPerson(currentUserId).iterator();
					while (comments.hasNext()) System.out.println((Comment) comments.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
				
			case 24: //게시물id를 들고가서 태그된 유저들을 검색
				try {
					Iterator<User> personTags = pro.getPersontagsOnPost("post05").iterator();
					while (personTags.hasNext()) System.out.println((User) personTags.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
				
			case 25: //클릭한 유저 상세 페이지로 이동..... 유저정보와+유저의 포스트 가지고와야함...
				//password는 null값...비밀. 왜냐하면 password가 없는 생성자로 리턴받았으니깬~
				try {
					String userOnClick = "id05";
					System.out.println(pro.getUser(userOnClick));
					Iterator<Post> posts = pro.getAllPostsOfPerson(userOnClick).iterator();
					while (posts.hasNext()) System.out.println((Post) posts.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 26: //(로그인필요) 25와 동일하지만 이번에는 로그인된 사람의 상세 페이지로 이동..... 
				// 유저정보와+유저의 포스트 가지고와야함...
				//password는 null값...비밀. 왜냐하면 password가 없는 생성자로 리턴받았으니깬~
				try {
					if (currentUserId=="") {System.out.println("로그인을 먼저해주세유");break;}
					System.out.println(pro.getUser(currentUserId));
					Iterator<Post> posts = pro.getAllPostsOfPerson(currentUserId).iterator();
					while (posts.hasNext()) System.out.println((Post) posts.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;		
			
			case 29:
				try {
					Iterator<User> personTags = pro.getFollowerUsers("id01").iterator();
					while (personTags.hasNext()) System.out.println((User) personTags.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;	
			
			case 30:
				try {
					Iterator<User> personTags = pro.getFollowingUsers("id02").iterator();
					while (personTags.hasNext()) System.out.println((User) personTags.next());
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;		
			
			case 31: // @Ree 또는 #algorithm 
				System.out.print("검색단어 입력 ㄱㄱ : ");sc.nextLine();
				String keyword = sc.nextLine();
				try {
					if (keyword.charAt(0)=='@') {
						User user =pro.getUser(new User(keyword.substring(1)));
						System.out.println(user);
						Iterator<Post> posts = pro.getAllPostsOfPerson(user.getUserId()).iterator();
						while (posts.hasNext()) System.out.println((Post) posts.next());} 
					else if (keyword.charAt(0)=='#') {
						System.out.println(keyword.substring(1));
						Iterator<Post> posts = pro.getPostsByHashTag(new Hashtag(keyword.substring(1))).iterator();
						while (posts.hasNext()) System.out.println((Post) posts.next());} 
					else break;
				} catch (DuplicateRercordException e) { System.out.println("이미 존재하는 정보 입니다.");}
				catch (RecordNotFoundException e) { System.out.println("정보를 찾을수 없습니다.");}
				catch (SQLException e) {System.out.println("서버오류");}
				break;
				
			default:
				break;	
				
				
				
			}

		}

	}

}


