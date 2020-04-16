package com.storyart.commentservice.repository;

import com.storyart.commentservice.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c FROM Comment c LEFT JOIN Reaction r ON c.id = r.commentId " +
            "where c.storyId = ?1 and c.active=true and c.disableByAdmin= false GROUP BY c.id " +
            "order by count(r.commentId) desc")
    Page<Comment> findAllByStoryIdAndOrderByReactions(int storyId, Pageable pageable);

    @Query("select c from Comment c where c.storyId = ?1 and c.active=true " +
            "and c.disableByAdmin = false order by c.createdAt desc")//createdAt nha, thong nhat la createdAt updatedAt ok e
    Page<Comment> findAllByStoryIdAndOrderBycreatedAt(int storyId, Pageable pageable); //cho nay anh ko nen fix cung isActive=true
    ///anh ne truyen param vao, de sau nay minh dung cho nhieu truong hop, ok

    @Query("select c from  Comment c where c.userId =?1 and c.active=true and c.disableByAdmin = false order by c.createdAt desc")
    Page<Comment> findAllByUserId(int userId, Pageable pageable);

    @Query("select c from Comment c where c.id in (:commentIds)")
    List<Comment> findAllByCommentIds(List<Integer> commentIds);

    List<Comment> findAllByStoryIdInAndActiveAndDisableByAdminAndCreatedAtBetweenOrderByCreatedAtDesc(List<Integer> storyIds,boolean isActive,boolean isDisableByAdmin, Date startDate, Date endDate);

    @Query("select c.id from Comment c where c.userId in ?1")
    List<Integer> findAllCommentIdByUserIds(List<Integer> userIds);

    @Query("select c FROM Comment c inner join Report r ON c.id = r.commentId " +
            "where c.id in ?1 and r.isHandled = ?2 GROUP BY c.id " +
            "order by count(r.commentId) desc")
    Page<Comment> getCommentReports(List<Integer> commentIds,boolean isHandled, Pageable pageable);
}
