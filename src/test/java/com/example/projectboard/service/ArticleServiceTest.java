package com.example.projectboard.service;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.type.SearchType;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.ArticleUpdateDto;
import com.example.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

  @InjectMocks private ArticleService sut; //system under test
  @Mock private ArticleRepository articleRepository;

  @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
  @Test
  void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
    //Given
    //SearchParameters param = SearchParameters.of(SearchType.TITLE, "search keyword");
    //When
    Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

    //Then
    assertThat(articles).isNotNull();
  }

  @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
  @Test
  void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
    //Given

    //When
    ArticleDto article = sut.searchArticle(1L);

    //Then
    assertThat(article).isNotNull();
  }

  @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
  @Test
  void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
    //Given
    ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "uno", "title", "content", "hashtag");
    //여기서는 무슨 일이 일어날거다라는 표시를 해주는거다. Article객체로 들어오는 거는 다 save 해줄거다라는 명시다.
    //그리고 article save가 일어날거다 라는 명시를 해준다.
    given(articleRepository.save(any(Article.class))).willReturn(null);

    //When
    sut.saveArticle(dto);

    //Then
    //save 가 호출되었는지 확인한다.
    then(articleRepository).should().save(any(Article.class));
  }

  @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
  @Test
  void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdateArticle() {
    //Given
    //여기서는 무슨 일이 일어날거다라는 표시를 해주는거다. Article객체로 들어오는 거는 다 save 해줄거다라는 명시다.
    //그리고 article save가 일어날거다 라는 명시를 해준다.
    given(articleRepository.save(any(Article.class))).willReturn(null);

    //When
    sut.updateArticle(1L, ArticleUpdateDto.of( "title", "content", "#java"));

    //Then
    //save 가 호출되었는지 확인한다.
    then(articleRepository).should().save(any(Article.class));
  }

  @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
  @Test
  void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
    //Given
    //여기서는 무슨 일이 일어날거다라는 표시를 해주는거다. Article객체로 들어오는 거는 다 save 해줄거다라는 명시다.
    //그리고 article save가 일어날거다 라는 명시를 해준다.
    willDoNothing().given(articleRepository).delete(any(Article.class));

    //When
    sut.deleteArticle(1L);

    //Then
    //save 가 호출되었는지 확인한다.
    then(articleRepository).should().save(any(Article.class));
  }
}
