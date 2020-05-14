insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
values("id01", "Lee", "1234", 2, 1, 0, "aaa@test.com", "M");
insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
values("id02", "Kim", "1234", 4, 3, 0, "abc@test.com", "W");
insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
values("id03", "Kang", "1234", 3, 3, 0, "ave@test.com", "M");
insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
values("id04", "Song", "1234", 1, 3, 0, "age@test.com", "W");
insert into user(userId, userName, password, followerNum, followingNum, postNum, email, gender)
values("id05", "Park", "1234", 3, 3, 0, "ajw@test.com", "M");
insert into follow(userId, followingId) values("id02", "id01");
insert into follow(userId, followingId) values("id03", "id01");
insert into follow(userId, followingId) values("id02", "id02");
insert into follow(userId, followingId) values("id03", "id02");
insert into follow(userId, followingId) values("id04", "id02");
insert into follow(userId, followingId) values("id05", "id02");
insert into follow(userId, followingId) values("id04", "id03");
insert into follow(userId, followingId) values("id05", "id03");
insert into follow(userId, followingId) values("id02", "id03");
insert into follow(userId, followingId) values("id05", "id04");
insert into follow(userId, followingId) values("id02", "id05");
insert into follow(userId, followingId) values("id03", "id05");
insert into follow(userId, followingId) values("id04", "id05");

insert into post(postId, caption, imageSrc, likeNum) values("post01", "The sea", "src\\images\\sea.jpg", 3);
insert into post(postId, caption, imageSrc, likeNum) values("post02","The sun", "src\\images\\sun.jpg", 3);
 insert into post(postId, caption, imageSrc, likeNum) values("post03","The star", "src\\images\\star.jpg", 2);
 insert into post(postId, caption, imageSrc, likeNum) values("post04","The mt", "src\\images\\mt.jpg", 1);
 insert into post(postId, caption, imageSrc, likeNum) values("post05","The computer", "src\\images\\computer.jpg", 4);
 insert into post(postId, caption, imageSrc, likeNum) values("post06","The python3", "src\\images\\python3.jpg", 1);
 insert into post(postId, caption, imageSrc, likeNum) values("post07","The thread", "src\\images\\thread.jpg", 0);
 insert into post(postId, caption, imageSrc, likeNum) values("post08","The server", "src\\images\\server.jpg", 0);
 insert into post(postId, caption, imageSrc, likeNum) values("post09","The for", "src\\images\\for.jpg", 1);
 insert into post(postId, caption, imageSrc, likeNum) values("post10","The continue", "src\\images\\continue.jpg", 2);
 insert into post(postId, caption, imageSrc, likeNum) values("post11","The break", "src\\mages\\break.jpg", 2);
 insert into post(postId, caption, imageSrc, likeNum) values("post12","The stone", "src\\images\\stone.jpg", 2);

insert into persontag(userId, postId, postOwner) values("id01", "post01", "Y");
insert into persontag(userId, postId, postOwner) values("id02", "post01", "N");
insert into persontag(userId, postId, postOwner) values("id04", "post01", "N");

insert into persontag(userId, postId, postOwner) values("id01", "post02", "Y");
insert into persontag(userId, postId, postOwner) values("id04", "post02", "N");
insert into persontag(userId, postId, postOwner) values("id05", "post02", "N");

insert into persontag(userId, postId, postOwner) values("id02", "post03", "Y");
insert into persontag(userId, postId, postOwner) values("id01", "post03", "N");
insert into persontag(userId, postId, postOwner) values("id03", "post03", "N");
insert into persontag(userId, postId, postOwner) values("id04", "post03", "N");

insert into persontag(userId, postId, postOwner) values("id02", "post04", "Y");
insert into persontag(userId, postId, postOwner) values("id01", "post04", "N");
insert into persontag(userId, postId, postOwner) values("id03", "post04", "N");

insert into persontag(userId, postId, postOwner) values("id02", "post05", "Y");
insert into persontag(userId, postId, postOwner) values("id04", "post05", "N");

insert into persontag(userId, postId, postOwner) values("id03", "post06", "Y");
insert into persontag(userId, postId, postOwner) values("id04", "post06", "N");
insert into persontag(userId, postId, postOwner) values("id05", "post06", "N");

insert into persontag(userId, postId, postOwner) values("id03", "post07", "Y");
insert into persontag(userId, postId, postOwner) values("id01", "post07", "N");
insert into persontag(userId, postId, postOwner) values("id02", "post07", "N");
insert into persontag(userId, postId, postOwner) values("id05", "post07", "N");

insert into persontag(userId, postId, postOwner) values("id04", "post08", "Y");
insert into persontag(userId, postId, postOwner) values("id02", "post08", "N");
insert into persontag(userId, postId, postOwner) values("id03", "post08", "N");

insert into persontag(userId, postId, postOwner) values("id04", "post09", "Y");
insert into persontag(userId, postId, postOwner) values("id01", "post09", "N");
insert into persontag(userId, postId, postOwner) values("id02", "post09", "N");
insert into persontag(userId, postId, postOwner) values("id03", "post09", "N");

insert into persontag(userId, postId, postOwner) values("id05", "post10", "Y");
insert into persontag(userId, postId, postOwner) values("id04", "post10", "N");

insert into persontag(userId, postId, postOwner) values("id05", "post11", "Y");
insert into persontag(userId, postId, postOwner) values("id02", "post11", "N");

insert into persontag(userId, postId, postOwner) values("id05", "post12", "Y");
insert into persontag(userId, postId, postOwner) values("id01", "post12", "N");
insert into persontag(userId, postId, postOwner) values("id03", "post12", "N");


insert into comment(commentId, comment, likeNum, userId, postId) values("comm01", "good", 0, "id01", "post07");
insert into comment(commentId, comment, likeNum, userId, postId) values("comm02", "like", 1, "id02", "post04");
insert into comment(commentId, comment, likeNum, userId, postId) values("comm03", "good", 2, "id03", "post05");
insert into comment(commentId, comment, likeNum, userId, postId) values("comm04", "great", 1, "id04", "post03");
insert into comment(commentId, comment, likeNum, userId, postId) values("comm05", "like", 2, "id05", "post02");
insert into comment(commentId, comment, likeNum, userId, postId) values("comm06", "great", 1, "id02", "post10");

insert into hashtag(hashtagId) values("travel");
insert into hashtag(hashtagId) values("nature");
insert into hashtag(hashtagId) values("computer");
insert into hashtag(hashtagId) values("trip");
insert into hashtag(hashtagId) values("vacation");
insert into hashtag(hashtagId) values("algorithm");
insert into hashtag(hashtagId) values("programming");

insert into hashgroup(postId, hashtagId) values("post01", "travel");
insert into hashgroup(postId, hashtagId) values("post01", "nature");
insert into hashgroup(postId, hashtagId) values("post02", "travel");
insert into hashgroup(postId, hashtagId) values("post02", "nature");
insert into hashgroup(postId, hashtagId) values("post02", "vacation");
insert into hashgroup(postId, hashtagId) values("post02", "trip");
insert into hashgroup(postId, hashtagId) values("post03", "travel");
insert into hashgroup(postId, hashtagId) values("post03", "nature");
insert into hashgroup(postId, hashtagId) values("post03", "trip");
insert into hashgroup(postId, hashtagId) values("post04", "nature");
insert into hashgroup(postId, hashtagId) values("post04", "vacation");
insert into hashgroup(postId, hashtagId) values("post05", "computer");
insert into hashgroup(postId, hashtagId) values("post05", "algorithm");
insert into hashgroup(postId, hashtagId) values("post05", "programming");
insert into hashgroup(postId, hashtagId) values("post06", "computer");
insert into hashgroup(postId, hashtagId) values("post06", "programming");
insert into hashgroup(postId, hashtagId) values("post07", "programming");
insert into hashgroup(postId, hashtagId) values("post08", "computer");
insert into hashgroup(postId, hashtagId) values("post09", "programming");
insert into hashgroup(postId, hashtagId) values("post10", "computer");
insert into hashgroup(postId, hashtagId) values("post11", "algorithm");




