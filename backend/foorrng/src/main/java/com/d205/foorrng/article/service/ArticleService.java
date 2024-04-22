package com.d205.foorrng.article.service;

import com.d205.foorrng.article.dto.request.ArticleReqDto;
import com.d205.foorrng.article.dto.request.ArticleUpdateReqDto;
import com.d205.foorrng.article.dto.response.ArticleListResDto;
import com.d205.foorrng.article.dto.response.ArticleResDto;
import com.d205.foorrng.article.entity.Article;
import com.d205.foorrng.common.model.BaseResponseBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    //상세 기능들에 대해서 ServiceClass에 기능 구현하기;

//    //조회 하나의 값을 반환
//    Optional<ArticleResDto> getArticle(Long id);

    //조회 리스트로 반환
//    List<ArticleListResDto> getArticleList();


    //게시글 저장
    ResponseEntity<BaseResponseBody> saveArticle(ArticleReqDto article, MultipartFile image);

    ResponseEntity<BaseResponseBody> updateArticle(ArticleUpdateReqDto article, MultipartFile image);

    // 내 게시글만 조회
    ResponseEntity<BaseResponseBody> getMyArticleList();


    //게시글 조회
    ResponseEntity<BaseResponseBody> searchArticle(Long articleId);


    //게시글 삭제
    ResponseEntity<BaseResponseBody> removeArticle(Long articleId);

    //게시글 리스트 반환
    ResponseEntity<BaseResponseBody> listArticle();

}
