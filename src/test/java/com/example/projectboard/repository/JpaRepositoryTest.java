package com.example.projectboard.repository;

import com.example.projectboard.config.JpaConfig;
import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

  private final ArticleRepository articleRepository;
  private final ArticleCommentRepository articleCommentRepository;
  @Autowired
  private UserAccountRepository userAccountRepository;

  public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                           @Autowired ArticleCommentRepository articleCommentRepository,
                           @Autowired UserAccountRepository userAccountRepository
  ) {
    this.articleRepository = articleRepository;
    this.articleCommentRepository = articleCommentRepository;
  }

  @DisplayName("select 테스트")
  @Test
  void givenTestData_whenSelecting_thenWorksFine() {
    //Given

    //When
    List<Article> articles = articleRepository.findAll();

    //Then
    assertThat(articles)
            .isNotNull()
            .hasSize(123);
  }

  @DisplayName("Insert 테스트")
  @Test
  void givenTestData_whenInserting_thenWorksFine() {
    //Given
    long previousCount = articleRepository.count();

    //When
    //Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));
    long afterCount = articleRepository.count();
    UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno","pw",null,null,null));
    //Then
    assertThat(afterCount).isEqualTo(previousCount + 1);
  }

  @DisplayName("update 테스트")
  @Test
  void givenTestData_whenUpdating_thenWorksFine() {
    //Given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updateHashtag = "#springboot";
    article.setHashtag(updateHashtag);

    //When
    Article updatedArticle = articleRepository.saveAndFlush(article);

    //Then
    //updatedArticle 객체에 hashtag 필드 값이 updateHashtag 필드 값과 같은지 검사
    assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
  }

  @DisplayName("delete 테스트")
  @Test
  void givenTestData_whenDeleting_thenWorksFine() {
    //Given
    Article article = articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count();
    long previousArticleCommentCount = articleCommentRepository.count();
    int deletedCommentsSize = article.getArticleComments().size();

    //When
    articleRepository.delete(article);

    //Then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
    assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
  }
}
