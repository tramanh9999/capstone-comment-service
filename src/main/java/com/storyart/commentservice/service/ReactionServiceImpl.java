package com.storyart.commentservice.service;

import com.netflix.discovery.converters.Auto;
import com.storyart.commentservice.dto.comment.CommentHistoryResponseDTO;
import com.storyart.commentservice.dto.reaction.ReactionCommentDTO;
import com.storyart.commentservice.dto.reaction.ReactionHistoryResponseDTO;
import com.storyart.commentservice.model.Comment;
import com.storyart.commentservice.model.Reaction;
import com.storyart.commentservice.model.Story;
import com.storyart.commentservice.model.User;
import com.storyart.commentservice.repository.CommentRepository;
import com.storyart.commentservice.repository.ReactionRepository;
import com.storyart.commentservice.repository.StoryRepository;
import com.storyart.commentservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ReactionServiceImpl implements ReactionService {
    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoryRepository storyRepository;

    @Override
    public void react(ReactionCommentDTO reactionDTO) {
        Optional<Reaction> react = reactionRepository.findReactionByCommentIdAndUserId(reactionDTO.getCommentId(), reactionDTO.getUserId());
        Optional<Comment> comment = commentRepository.findById(reactionDTO.getCommentId());
        Optional<User> user = userRepository.findById(reactionDTO.getUserId());

        if(!reactionDTO.getType().equals("like") && !reactionDTO.getType().equals("dislike")){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Loại reaction chỉ được là 'like' hoặc 'dislike'");
        }

        if(!comment.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Bình luận không tồn tại.");
        }

        if(react.isPresent()){
            Reaction reaction = react.get();
            if(reaction.getType().equals(reactionDTO.getType())){
               //reaction.setActive(false);
               //reactionRepository.save(reaction);
                removeReaction(reactionDTO);
            }
            else {
                reaction.setType(reactionDTO.getType());
                reactionRepository.save(reaction);
            }
        } else {
            Reaction newReaction = new Reaction();
            newReaction.setCommentId(reactionDTO.getCommentId());
            newReaction.setUserId(reactionDTO.getUserId());
            newReaction.setType(reactionDTO.getType());
            newReaction.setActive(true);
            reactionRepository.save(newReaction);
        }
    }

    //@Override
    //public void like(ReactionCommentDTO reactionDTO) {
    //    Optional<Reaction> reaction = reactionRepository.findReactionByCommentIdAndUserId(reactionDTO.getCommentId(), reactionDTO.getUserId());
    //    Optional<Comment> comment = commentRepository.findById(reactionDTO.getCommentId());
    //    if(!comment.isPresent()){
    //        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Bình luận không tồn tại.");
    //    }
    //    if(reaction.isPresent()){
    //        Reaction updateReaction = reaction.get();
//
    //        updateReaction.setType("like");
    //        reactionRepository.save(updateReaction);
    //    }
    //    else {
    //        Reaction newReaction = new Reaction();
    //        newReaction.setCommentId(reactionDTO.getCommentId());
    //        newReaction.setUserId(reactionDTO.getUserId());
    //        newReaction.setType("like");
    //        reactionRepository.save(newReaction);
    //    }
    //}
    //@Override
    //public void dislike(ReactionCommentDTO reactionDTO) {
    //    Optional<Reaction> reaction = reactionRepository.findReactionByCommentIdAndUserId(reactionDTO.getCommentId(), reactionDTO.getUserId());
    //    Optional<Comment> comment = commentRepository.findById(reactionDTO.getCommentId());
//
    //    if(!comment.isPresent()){
    //        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Bình luận không tồn tại.");
    //    }
//
    //    if(reaction.isPresent()){
    //        Reaction updateReaction = reaction.get();
    //        updateReaction.setType("dislike");
////
    //        reactionRepository.save(updateReaction);
    //    }
    //    else {
    //        Reaction newReaction = new Reaction();
    //        newReaction.setComment(comment.get());
//  //          newReaction.setUserId(reactionDTO.getUserId());
    //        newReaction.setUser(null);
    //        newReaction.setType("dislike");
////
    //        reactionRepository.save(newReaction);
    //    }
//
    //}

    @Override
    public void removeReaction(ReactionCommentDTO reactionDTO) {
        //vi du. a dang dislike cmt 1, thi` nut dislike se~ sang', a bam vo, thi call cai nay`, delete reaction luon.
        //em hieu anh dang muon client se phai xac dinh la cai reaction nay thuoc loai nao de goi ham update, delete cua anh dung ko
        //dung r, co ban la v
        //con` neu nut like hoac. dislike user bam' k sang', cu' call thang~ like/dislike, dang sang' thi` remove
        //lam v thi cung tam on thoi
        //cai nao` ok thi` tam. giu~ di, minh` lam` xin. qua' cung~ k ai care haha
        //cung hen xui thôi,  ua` k lam` tao`lao qua' la` dc
        //ok, tam thhoi nhu v di
        Optional<Reaction> react = reactionRepository.findReactionByCommentIdAndUserId(reactionDTO.getCommentId(), reactionDTO.getUserId());
        Reaction reaction = react.get();
        if(react.isPresent()){
            //reaction.setActive(false);
            //reactionRepository.save(reaction);
            reactionRepository.delete(reaction);
        }


    }

    @Override
    public Page<ReactionHistoryResponseDTO> getReactionHistory(int userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Reaction> reactionPage = reactionRepository.getReactionsHistoryByUserId(userId, pageable);
        Page<ReactionHistoryResponseDTO> responsePage = reactionPage.map(new Function<Reaction, ReactionHistoryResponseDTO>() {
            @Override
            public ReactionHistoryResponseDTO apply(Reaction reaction) {
                ModelMapper mm = new ModelMapper();
                ReactionHistoryResponseDTO reactionHistoryResponseDTO = mm.map(reaction, ReactionHistoryResponseDTO.class);
                return reactionHistoryResponseDTO;
            }
        });

        List<Reaction> reactions = reactionPage.getContent();
        List<Integer> commentIds = new ArrayList<>();
        for (Reaction reaction: reactions) {
            commentIds.add(reaction.getCommentId());
        }

        List<Comment> comments = commentRepository.findAllByCommentIds(commentIds);

        List<Integer> userIds = new ArrayList<>();
        List<Integer> storyIds = new ArrayList<>();

        for (Comment comment: comments) {
            userIds.add(comment.getUserId());
            storyIds.add(comment.getStoryId());
        }

        List<User> users = userRepository.findAllById(userIds);
        List<Story> stories = storyRepository.findAllById(storyIds);

        List<ReactionHistoryResponseDTO> responseList = responsePage.getContent();
        //int index = 0;
        for (ReactionHistoryResponseDTO response : responseList) {
            //response.setStoryName(reactionPage.getContent().get(index).getComment().getStory().getTitle());
            //response.setCommentId(reactionPage.getContent().get(index).getComment().getId());
            //response.setCommentOwnerId(reactionPage.getContent().get(index).getComment().getId());
            //response.setCommentOwnerName(reactionPage.getContent().get(index).getComment().getUser().getUsername());

            for (Comment comment: comments) {
                if (response.getCommentId() == comment.getId()){
                    for (User user: users) {
                        
                        if (comment.getUserId() == user.getId()){
                            response.setCommentOwnerName(user.getName());
                            response.setCommentOwnerId(user.getId());
                            break;
                        }
                    }
                    for (Story story: stories) {
                        if (comment.getStoryId() == story.getId()){
                            response.setStoryName(story.getTitle());
                            response.setStoryId(story.getId());
                            break;
                        }
                    }

                }

            }
            //index++;
        }

        return responsePage;
    }
}
