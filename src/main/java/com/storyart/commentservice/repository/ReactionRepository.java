package com.storyart.commentservice.repository;

import com.storyart.commentservice.model.Comment;
import com.storyart.commentservice.model.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    @Query("SELECT r FROM Reaction r WHERE r.commentId = ?1 AND r.userId = ?2 and r.isActive=true")
    Optional<Reaction> findReactionByCommentIdAndUserId(int commentId, int userId);
    //phai lay r.type nua, moi map dc, umk v lay 2 cái, ok
    //cho na anh fix sau nhe ok e
    @Query("SELECT r from Reaction r where r.commentId in (:commentIds) and r.isActive = true") //cho nay anh chi can lay userid thoi nha, ko dc mlay may thon tin khac
    List<Reaction> findListUserId(@Param("commentIds") List<Integer> commentIds);
    //@Query("delete FROM Reaction r WHERE r.comment.id = ?1 AND r.userId = ?2 ")
    //void deleteByCommentIdAndUserId(int commentId, int userId);
    @Query("select r from Reaction r where  r.userId =?1 and r.isActive = true order by r.createdAt desc")
    Page<Reaction> getReactionsHistoryByUserId(int userId, Pageable pageable);
}
