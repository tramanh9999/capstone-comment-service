package com.storyart.commentservice.repository;

import com.storyart.commentservice.model.Comment;
import com.storyart.commentservice.model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface StoryRepository extends JpaRepository<Story, Integer> {
    @Query("select s.id from Story s where s.userId = ?1 and s.active = true and s.deactiveByAdmin=false")
    List<Integer> getAllStoryIdByUserId(int userId);

    @Query("select s.id from Story s where s.userId in ?1")
    List<Integer> findAllStoryIdByUserIds(List<Integer> userIds);

    @Query("select s FROM Story s inner join Report r ON s.id = r.storyId " +
            "where s.id in ?1 and r.isHandled = ?2 GROUP BY s.id " +
            "order by count(r.storyId) desc")
    Page<Story> getStoryReports(List<Integer> storyIds, boolean isHandled, Pageable pageable);
}
