package me.pepe.Twittir.Database.Tweet;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface TweetRepository extends Repository<TweetData, Integer> {    
	@Query("SELECT td FROM TweetData td WHERE td.id = :id")
	TweetData find(@Param("id") long id);

	@Query("SELECT td FROM TweetData td INNER JOIN UserData ud ON userID = ud.id WHERE ud.verified ORDER BY postTime")
	List<TweetData> findRecomendeds();

	@Query("SELECT td FROM TweetData td INNER JOIN UserData ud ON userID = ud.id WHERE ud.verified OR ud.id = :userID ORDER BY postTime")
	List<TweetData> findRecomendedsAndMyTweets(@Param("userID") long userID);

	@Query("SELECT td FROM TweetData td WHERE td.userID = :userID ORDER BY postTime")
	List<TweetData> findOfUser(@Param("userID") long userID);

	@Query("SELECT td FROM TweetData td WHERE td.content LIKE %:content% ORDER BY postTime")
	List<TweetData> findByContent(@Param("content") String content);

    @Modifying
    @Query(value = "INSERT INTO TweetData (userID, content, postTime) values(:userID, :content, :postTime);", nativeQuery = true)
    @Transactional
    void tweet(@Param("userID") long userID, @Param("content") String content, @Param("postTime") long postTime);
    
	@Query("SELECT itd FROM InteractionTweetData itd WHERE itd.tweetID = :tweetID AND itd.userID = :userID")
	InteractionTweetData getInteraction(@Param("tweetID") long tweetID, @Param("userID") long userID);

    @Modifying
	@Query(value = "INSERT INTO InteractionTweetData (tweetID, userID, liked, retweeted) values(:tweetID, :userID, false, false)", nativeQuery = true)
    @Transactional
	int createInteraction(@Param("tweetID") long tweetID, @Param("userID") long userID);
	
	@Query("SELECT EXISTS(SELECT itd FROM InteractionTweetData itd WHERE itd.tweetID = :tweetID AND itd.userID = :userID AND itd.liked = true)")
	boolean isLiked(@Param("tweetID") long tweetID, @Param("userID") long userID);

    @Modifying
	@Query(value = "UPDATE InteractionTweetData SET liked = :like WHERE tweetID = :tweetID AND userID = :userID", nativeQuery = true)
    @Transactional
    void setLike(@Param("tweetID") long tweetID, @Param("userID") long userID, @Param("like") boolean like);
	
	@Query("SELECT COUNT(id) FROM InteractionTweetData itd WHERE itd.tweetID = :tweetID AND itd.liked = true")
	int countLikes(@Param("tweetID") long tweetID);
    
	@Query("SELECT EXISTS(SELECT itd FROM InteractionTweetData itd WHERE itd.tweetID = :tweetID AND itd.userID = :userID AND retweeted = true)")
	int isRetweeted(@Param("tweetID") long tweetID, @Param("userID") long userID);

    @Modifying
	@Query(value = "UPDATE InteractionTweetData SET retweeted = :retweeted WHERE tweetID = :tweetID AND userID = :userID", nativeQuery = true)
    @Transactional
	void setRetweeted(@Param("tweetID") long tweetID, @Param("userID") long userID, @Param("retweeted") boolean retweeted);
	
	@Query("SELECT COUNT(id) FROM InteractionTweetData itd WHERE itd.tweetID = :tweetID AND itd.retweeted = true")
	int countRetweets(@Param("tweetID") long tweetID);
}
