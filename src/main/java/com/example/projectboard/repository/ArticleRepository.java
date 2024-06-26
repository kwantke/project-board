package com.example.projectboard.repository;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, //이걸 추가하면, Article에 모든 변수로 검색이 가능하도록 됨
        QuerydslBinderCustomizer<QArticle>
{

  //Containing 을 추가하면 like '%text%'(양쪽에 와일드카드로 %를 붙임) 로 조회한다.
  Page<Article> findByTitleContaining(String title, Pageable pageable);
  Page<Article> findByContentContaining(String content, Pageable pageable);
  Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
  Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
  Page<Article> findByHashtag(String hashtage, Pageable pageable);

  @Override
  default void customize(QuerydslBindings bindings, QArticle root) {
    bindings.excludeUnlistedProperties(true);
    bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
    //bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '{v}'
    bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%{v}%'
    bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.createdAt).first(DateTimeExpression::eq);
    bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
  }
}
