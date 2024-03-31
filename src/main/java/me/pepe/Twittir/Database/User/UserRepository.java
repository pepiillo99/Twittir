package me.pepe.Twittir.Database.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
	
public interface UserRepository extends Repository<UserData, Integer> {
	@Query("SELECT u FROM UserData u WHERE u.id = :id")
	UserData find(@Param("id") long id);
	
	@Query("SELECT u FROM UserAboutData u WHERE u.id = :id")
	UserAboutData findAboutData(@Param("id") long id);
	
	@Query("SELECT u FROM UserLoginData u WHERE u.id = :id")
	UserLoginData findLoginData(@Param("id") long id);
	
	@Query("SELECT u FROM UserData u WHERE u.userName = :username")
	Optional<UserData> findByUserName(@Param("username") String username);

	@Query("SELECT u FROM UserProfileImageData u WHERE u.id = :id")
	UserProfileImageData getUserImageProfile(@Param("id") long id);

    @Modifying
    @Query(value = "INSERT INTO UserProfileImageData (id, imageData) values(:id, :data);", nativeQuery = true)
    @Transactional
	int uploadUserImageProfile(@Param("id") long id, byte[] data);

	@Query("SELECT followID FROM UserFollowData where userID = :userid")
	List<Integer> getFollowing(@Param("userid") int userid);

	@Query("SELECT userID FROM UserFollowData where followID = :userid")
	List<Integer> getFollowers(@Param("userid") int userid);

	@Query("SELECT u FROM UserData u WHERE u.name = :name")
	List<UserData> findByUser(@Param("name") String name);

	@Query("SELECT u FROM UserData u WHERE u.name LIKE %:content%")
	List<UserData> findByUserContains(@Param("content") String content);
	
	@Modifying
	@Query(value = "INSERT INTO UserData (userName, name, verified) VALUES (:userName, :name, :verified);", nativeQuery = true)
	@Transactional
	int newUser(@Param("userName") String userName, @Param("name") String name, @Param("verified") boolean verified);
	
	@Modifying
	@Query(value = "INSERT INTO UserAboutData (userID, description, location, birthdate, createdTime, work, email, numberPrefix, phoneNumber) VALUES (:userID, :description, :location, :birthdate, :createdTime, :work, :email, :numberPrefix, :phoneNumber);", nativeQuery = true)
	@Transactional
	int newUserAbout(@Param("userID") long userID, @Param("description") String description, @Param("location") String location, long birthdate, @Param("createdTime") long createdTime, @Param("work") String work, @Param("email") String email, @Param("numberPrefix") int numberPrefix, @Param("phoneNumber") int phoneNumber);
	
	@Modifying
	@Query(value = "INSERT INTO UserLoginData (userID, password) VALUES (:userID, :password);", nativeQuery = true)
	@Transactional
	int newUserLogin(@Param("userID") long userID, @Param("password") String password);

	@Query(value = "SELECT LAST_INSERT_ID() from UserData", nativeQuery = true)
	Long getLastUserRegistered();

	@Query("SELECT COUNT(uf) FROM UserFollowData uf WHERE uf.userID = :userID")
	int countFollowing(@Param("userID") long userID);

	@Query("SELECT COUNT(uf) FROM UserFollowData uf WHERE uf.followID = :userID")
	int countFollowers(@Param("userID") long userID);

	@Query("SELECT CASE WHEN COUNT(uf) > 0 THEN true ELSE false END FROM UserFollowData uf WHERE uf.followID = :followID AND uf.userID = :userID")
	boolean isFollowing(@Param("userID") long userID, @Param("followID") long followID);

    @Modifying
    @Query(value = "INSERT INTO UserFollowData (userID, followID) values(:userID, :followID);", nativeQuery = true)
    @Transactional
	int followUser(@Param("userID") long userID, @Param("followID") long followID);

    @Modifying
    @Query(value = "DELETE FROM UserFollowData WHERE userID = :userID AND followID = :followID", nativeQuery = true)
    @Transactional
	int unfollowUser(@Param("userID") long userID, @Param("followID") long followID);
    
    @Modifying
    @Query(value = "UPDATE UserAboutData set birthdate = :birthdate, description = :description, email = :email, location = :location, numberPrefix = :numberPrefix, phoneNumber = :phoneNumber, work = :work where userID = :userID", nativeQuery = true)
    @Transactional
	int updateUserAboutData(@Param("userID") long userID, @Param("birthdate") long birthdate, @Param("description") String description, @Param("location") String location, @Param("numberPrefix") int numberPrefix, @Param("phoneNumber") int phoneNumber, @Param("work") String work, @Param("email") String email);
    
    @Modifying
    @Query(value = "UPDATE UserData set name = :name where id = :userID", nativeQuery = true)
    @Transactional
	int updateName(@Param("userID") long userID, @Param("name") String name);
}