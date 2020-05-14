#회원가입
-- insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
-- values("?", "?", "?", 0, 0, 0, "?", "?");

#회원 정보 검색
-- select userId, userName, followerNum, followingNum, postNum, email 
-- from user
-- where userId = "?";

#회원 정보 변경 (비밀번호)
-- update user
-- set password = ?
-- where userId = ?;

#회원 정보 변경(follower)
-- UPDATE user
-- set followerNum = ?
-- where userId = ?;

#회원 정보 변경(following)
-- UPDATE user
-- set followingNum = ?
-- where userId = ?;

#회원 정보 변경(postNum)
-- UPDATE user
-- set postNum = ?
-- where userId = ?;

#회원 정보 변경(email)
-- UPDATE user
-- set email = ?
-- where userId = ?;

#회원 정보 변경(gender)
-- UPDATE user
-- set gender = ?
-- where userId = ?;

#회원탈퇴
-- delete from user where userId = ?;

#특정사용자 팔로우하기
-- insert into follow(userId, followingId) values(?, ?);

#특정사용자 팔로우취소하기
-- delete from follow where userId = ? and followingId = ?;

#자신이 팔로우하는 사용자들 검색
-- select followingId from follow
-- where userId = "?";

#자신을 팔로우하는 사용자들 검색
-- select userId from follow
-- where followingId = "?";

#사용자가 게시물에 댓글 작성
-- insert into comment(commendId, comment, likeNum, userid, postid)
-- values(?, ?, 0, ?, ?);

#사용자가 자신의 댓글 수정

#사용자가 자신의 댓글 삭제

#게시물에 있는 댓글들 조회




#자신이 쓴 모든 댓글들 조회
#사용자가 게시물 작성
#사용자가 특정 게시물 검색
#사용자가 작성한 게시물들 검색
#사용자를 팔로우하는 사용자들의 게시물 검색(개수 제한)
#사용자가 자신의 게시물 수정
#사용자가 특정 게시물 삭제
#게시물에 사용자태그되어 있는 사용자들 검색
#게시물에 있는 사용자태그 검색
#사용자 태그로 사용자 검색???
#사용자 게시물에 있는 해시태그 검색
#해시태그로 게시물들 검색