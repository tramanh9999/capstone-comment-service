package com.storyart.commentservice.service;

import com.storyart.commentservice.dto.report.*;
import com.storyart.commentservice.model.Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportService {
    void reportComment(ReportCommentRequestDTO reportCommentRequestDTO);
    void reportStory(ReportStoryRequest request);
    Page<ReportCommentResponseDTO> getListReportComment(String searchString, boolean isHandled,int pageNo, int pageSize);
    Page<ReportByCommentIdResponse> getReportsByCommentId(int commentId, boolean isHandled, int pageNo, int pageSize);

    Page<StoryReportResponse> getListReportStory(String searchString,boolean isHandled,int pageNo, int pageSize);
    Page<ReportByStoryIdResponse> getReportsByStoryId(int storyId, boolean isHandled, int pageNo, int pageSize);
    //void handleReport(List<Integer> reportIds);
    void handleReport(HandleReportRequestDTO request);
    //minh co can lam tinh nang huy repỏt ko, chắc là k
    //k thay thằng nào làm vụ này :v

    //anh thu len youtube report 1 cai thu coi
    //chịu :v
    //đó h a thấy game hay gì cũng v,rp rồi thôi à
    //anh thu quay lai comment anh vua repỏt nay xe
    //    co nut remove ko
    //nãy report xong nó mất tiêu luôn, k cho mình thấy cmnt đó nữa
    //    oh
    //ko co luu lai lich su luon ta
    //ừa đúng r, k cần thiết đâu
    //bên admin handle r mới ban hay k ma`
    //ok anh
    //chac minh phai lam them cai an comment doi voi nguoi vua report =))
    //cũng đc, hoặc cứ tô sáng cái cờ lên, là xong :v
    //    Ok, em review toi day thoi
    //    anh nho dung xoa may comment nay di nha
    //de co gi biet note cho nao, sua phan nao
    //sau khi anh sua r anh push len, em check ok het thi anh ms xoa comment nha, ok e,
    //    ok anh, bye bye e, tyvm
}
