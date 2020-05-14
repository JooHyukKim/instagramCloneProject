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

#사용자가 자신의 댓글 수정  -> 게시물에 있는 댓글 조회 후 id입력
-- update comment set comment = ?
-- where commentid = ?;

#사용자가 자신의 댓글 삭제
-- delete from comment
-- where commentid = ?;

#게시물에 있는 댓글들 조회
-- select comment from comment
-- where postid = ?;

#댓글 id로 댓글 조회
-- select comment, likeNum from comment
-- where commendId = ?;

#자신이 쓴 모든 댓글들 조회 --skip
-- select commentId, comment, likeNum, postId from comment
-- where userId = ?;

#사용자가 게시물 작성

#사용자가 특정 게시물 검색
select po.postID, po.caption, po.imageSrc, po.likeNum, po.date, pe.userId 
from post po left join persontag pe
on po.postId = pe.postId
where userId = 'id01';

#사용자가 작성한 게시물들 검색
-- select postId, postOwner from persontag
-- where userId = "id01" and postOwner = "Y";

#사용자를 팔로우하는 사용자들의 게시물 검색(개수 제한)
-- select result.userId, result.postId, p.caption, p.imageSrc, p.likeNum
-- from (select followingPerson.userId, postOfOwner.postId  
-- 	  from (select * from persontag
-- 			where postOwner = "Y") as postOfOwner
-- 			join 
-- 			(select userId from follow
-- 			where followingId = "id05") as followingPerson
-- 			on postOfOwner.userId = followingPerson.userId) as result, post as p
-- where result.postId = p.postId;

#사용자가 자신의 게시물 수정
update post set caption = ?
where postId = ?;

update post set imageSrc = ?
where postId = ?;

UPDATE post SET caption = ?, imageSrc= ? 
where postId = ?;


 
#사용자가 자신의 특정 게시물 삭제


delete from post 
where postId = ?




#게시물에 있는 사용자태그 검색
-- select userId from personTag
-- where postId = ?;

# 특정 사용자 태그가 들어있는 게시물들 검색
-- select postId, postOwner from persontag
-- where userId = ?;

# 특정 사용자 태그가 들어있는 게시물들 중에서 다른 사람꺼..
-- select postId, postOwner from persontag
-- where userId = "id01" and postOwner = "N";

#사용자 게시물에 있는 해시태그 검색
-- select hashtagId from hashgroup
-- where postId = ?;

#해시태그로 게시물들 검색
-- select postId from hashgroup
-- where hashtagId = ?;