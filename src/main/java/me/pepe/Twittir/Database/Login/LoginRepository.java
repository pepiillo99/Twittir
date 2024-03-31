package me.pepe.Twittir.Database.Login;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;


public interface LoginRepository extends Repository<LoginTokenData, Integer> {
	@Query("SELECT l FROM LoginTokenData l WHERE l.id = :id")
	LoginTokenData find(@Param("id") long id);
	
	@Query("SELECT l FROM LoginTokenData l WHERE l.loginKey = :loginKey")
	Optional<LoginTokenData> findByLoginKey(@Param("loginKey") String loginKey);

    @Modifying
    @Query(value = "INSERT INTO LoginTokenData (userID, loginKey, created, expire, agent) values(:userID, :loginKey, :created, :expire, :agent);", nativeQuery = true)
    @Transactional
    int newLoginToken(@Param("userID") long userID, @Param("loginKey") String loginKey, @Param("created") long created, @Param("expire") long expire, @Param("agent") String userAgent);
    
    @Modifying
    @Query(value = "DELETE FROM LoginTokenData WHERE userID = :userID", nativeQuery = true)
    @Transactional
    int delete(@Param("userID") long userID);
}
