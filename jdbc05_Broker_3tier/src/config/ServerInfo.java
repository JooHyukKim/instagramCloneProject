package config;
/*
 * DB서버 정보의 상수값으로 구성된 인터페이스
 * Driver FQCN
 * DBServer URL
 * DBServer USER
 * DBServer PASSWORD
 */
public interface ServerInfo {
	String DRIVE_NAME = "com.mysql.cj.jdbc.Driver"; //public static final
	String URL = "jdbc:mysql://127.0.0.1:3306/instaclone?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	String USER = "root";
	String PASSWORD = "1234";
	
}
