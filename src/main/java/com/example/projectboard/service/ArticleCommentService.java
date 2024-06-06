package com.example.projectboard.service;

import com.example.projectboard.domain.ArticleComment;
import com.example.projectboard.dto.ArticleCommentDto;
import com.example.projectboard.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

  private final ArticleCommentRepository articleCommentRepository;

  @Transactional(readOnly = true)
  public List<ArticleCommentDto> searchArticleComments(Long articleId) {
    return List.of(null);
  }

  public void saveArticleComment(ArticleCommentDto articleCommentDto) {

  }

  public void updateArticleComment(ArticleCommentDto articleCommentDto) {

  }

  public void deleteArticleComment(Long articleCommentId) {

  }
}
