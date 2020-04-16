package com.storyart.commentservice.repository;

import com.storyart.commentservice.dto.report.ReportCommentResponseDTO;
import com.storyart.commentservice.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("select r from Report r where r.userId = ?1 and r.commentId = ?2 and r.isHandled=false ")
    Optional<Report> findReportByUserIdAndCommentId(int userId, int commentId);

    @Query("select r from Report r where r.userId = ?1 and r.storyId = ?2 and r.isHandled=false ")
    Optional<Report> findReportByUserIdAndStoryId(int userId, int storyId);

    @Query("select r from Report r where r.storyId is null and r.isHandled=?1 group by r.commentId order by count(r.commentId) desc ")
    Page<Report> findReportComment(boolean isHandled, Pageable pageable);

    @Query("select r.commentId from Report r where r.storyId is null and r.isHandled=?1")
    List<Integer> getCommentIds(boolean isHandled);

    @Query("select r.storyId from Report r where r.commentId is null and r.isHandled=?1")
    List<Integer> getStoryIds(boolean isHandled);

    @Query("select r from Report r where r.storyId is null and r.isHandled=?1 and r.commentId in ?2 group by r.commentId order by count(r.commentId) desc ")
    Page<Report> findReportCommentWithCommentIds(boolean isHandled,List<Integer> commentIds, Pageable pageable);
    //@Query("select count(r.commentId) from Report r where r.commentId in (:commentIds) and r.isHandled=(:isHandled) group by r.commentId order by count(r.commentId) desc")
    //List<Integer> getNumberOfReports(List<Integer> commentIds,boolean isHandled);

    @Query("select r from Report r where r.commentId = ?1 and r.isHandled=?2")
    Page<Report> getReportsByCommentId(int commentId, boolean isHandled, Pageable pageable);

    @Query("select r from Report r where  r.commentId in (:commentIds) " +
            "and r.isHandled=(:isHandled)")
    List<Report> findFullInformationReport(List<Integer> commentIds, boolean isHandled);

    @Query("select r from Report r where r.commentId is null and r.isHandled=?1 group by r.storyId order by count(r.storyId) desc ")
    Page<Report> findReportStory(boolean isHandled, Pageable pageable);

    @Query("select r from Report r where r.commentId is null and r.isHandled=?1 and r.storyId in ?2 group by r.storyId order by count(r.storyId) desc ")
    Page<Report> findReportCommentWithStoryIds(boolean isHandled,List<Integer> storyIdIds, Pageable pageable);

    @Query("select r from Report r where r.storyId = ?1 and r.isHandled=?2")
    Page<Report> getReportsByStoryId(int storyId, boolean isHandled, Pageable pageable);

    @Query("select r from Report r where  r.storyId in (:storyIds) and r.isHandled=(:isHandled)")
    List<Report> findFullInformationReportStory(List<Integer> storyIds, boolean isHandled);
}
