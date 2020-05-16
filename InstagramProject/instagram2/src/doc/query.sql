#~~~~~~~~~~~~~~~~~~~~~~~~~~~회원관련~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

#회원탈퇴 // 선행조건 : 이 회원과의 팔로우 관계도 없어짐.
-- delete from follow where userId = ?;
-- delete from follow where followingId = ?;
-- delete from user where userId = ?;

#특정사용자 팔로우하기
-- insert into follow(userId, followingId) values(?, ?);

#특정사용자 팔로우취소하기
-- delete from follow where userId = ? and followingId = ?;

#자신이 팔로우하는 사용자들 검색
select f.followingId, u.followerNum, u.followingNum, u.postNum, 
u.email from user u left join follow f on u.userId = f.userId 
where f.userId='id01';

#자신을 팔로우하는 사용자들 검색
select f.userId, u.followerNum, u.followingNum, u.postNum, 
u.email from user u left join follow f on u.userId = f.userId 
where f.followingId ='id01';

select * from follow;
#~~~~~~~~~~~~~~~~~~~~~~~~~~~게시물관련~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  검색할 때 해시태그랑 댓글도 같이..
#사용자가 게시물 작성
-- INSERT INTO post(postId, caption, imageSrc, likeNum) values(?, ?, ?, 0);
-- INSERT INTO persontag(userId, postid, postOwner) values(?, ?, "Y"); -- 사용자 본인 소유 확인
-- 	-- 다른 사용자를 태그 했을 경우 선행조건 : 게시글에 다른 사용자 태그가 있어야함.
--  -- 만약 태그를 했다면....
-- 	INSERT INTO persontag(userId, postid, postOwner) values(?, ?, "N"); 
--  -- 해시태그를 작성했을 경우. 선행조건 : 해시태그 테이블에 중복되는지 검사해야함. 
--  해시태그 중복이 안된다면
--  INSERT INTO hashtag(hashtagId) values(?);
--  포스트와의 관계 설정.
--  insert into hashgroup(postId, hashtagId) values(?, ?);

#사용자가 자신이 작성한(자기자신이 태그된) 게시물 검색
-- select po.postId, po.caption, po.imageSrc, po.likeNum, po.date, pe.userId 
-- from post po left join persontag pe
-- on po.postId = pe.postId
-- where pe.userId = ? and pe.postOwner ='Y';

#사용자가 다른 사람이 작성한(자신이 태그가 안된) 게시물 검색
-- select po.postId, po.caption, po.imageSrc, po.likeNum, po.date, pe.userId 
-- from post po left join persontag pe
-- on po.postId = pe.postId
-- where pe.userId = ? and pe.postOwner ='N';

#사용자를 팔로우하는 사용자들의 게시물 검색
-- select result.userId, result.postId, p.caption, p.imageSrc, p.likeNum
-- from (select followingPerson.userId, postOfOwner.postId  
-- 	  from (select * from persontag
-- 			where postOwner = "Y") as postOfOwner
-- 			join 
-- 			(select userId from follow
-- 			where followingId = ?) as followingPerson
-- 			on postOfOwner.userId = followingPerson.userId) as result, post as p
-- where result.postId = p.postId;

#사용자가 자신의 게시물 수정  검사사항 : 해시태그 삭제와 사용자 태그 삭제
-- UPDATE post SET caption = 'test', imageSrc='src\images\test.jpg' where postId ='post04'; 
-- 다른 사용자 태그를 삭제했다면
	-- persontag 테이블에서 다른 사용자태그와 테이블 연결 삭제
    -- delete from persontag where userId = ? and postId = ? and postOwner = "N";
-- 해시 태그를 삭제했다면
	-- hashgroup에서 게시물과 해당 해시태그 연결 삭제
    -- delete from hashgroup where postId = ? and hashtagId = ?;
    -- hashgroup에서 해당 해시태그와 연결된 포스트가 있는지 검색 
    -- 만약 없다면 hashtag 테이블에서 해당 해시태그 삭제
    -- delete from hashtag where tashtagId = ?;

#사용자가 자신의 특정 게시물 삭제 //선행조건 : hashgroup에서 해당 포스트 관련 행 삭제 -> PersonTag에서 해당 포스트 관련 행 삭제 -> comment에서 해당 포스트 관련 행 삭제
-- delete from hashgroup where postId = ?;
-- delete from persontag where postId = ?;
-- delete from comment where postId = ?;
-- delete from post where postId = ?

#사용자가 게시물에 댓글 작성
-- insert into comment(commentId, comment, likeNum, userid, postid)
-- values(?, ?,?, ?);

#사용자가 자신의 댓글 수정  선행조건: 게시물에 있는 자신의 댓글 조회
-- select commentId from comment where userId = ? and postId = ?;
-- 원하는 commentId 삭제 진행.
-- update comment set comment = ?
-- where commentId = ?;
select * from comment;
#사용자가 자신의 댓글 삭제 선행조건: 게시물에 있는 자신의 댓글 조회
-- select commentId from comment where userId = ? and postId = ?;
-- 원하는 댓글 삭제 진행
-- delete from comment 
-- where commentId = ?;

#게시물에 있는 댓글들 조회
-- select commentId, comment, likeNum 
-- from comment
-- where postId = ?;

#자신이 쓴 모든 댓글들 조회 --skip
-- select commentId, comment, likeNum, postId from comment
-- where userId = ?;

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