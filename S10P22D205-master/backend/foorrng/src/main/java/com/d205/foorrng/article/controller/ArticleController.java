package com.d205.foorrng.article.controller;


import com.d205.foorrng.article.dto.request.ArticleReqDto;
import com.d205.foorrng.article.dto.request.ArticleUpdateReqDto;
import com.d205.foorrng.article.service.ArticleService;
import com.d205.foorrng.common.model.BaseResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "Article ", description = "관련 api ")
@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/article")
@Validated
public class ArticleController {

    private final ArticleService articleService;

    //201 : 생성
    //200 ok
    //204 : del

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공 /n/n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> updateArticle(
            @Valid @RequestPart("articleDto") ArticleUpdateReqDto articleDto,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage) {
        return articleService.updateArticle(articleDto, mainImage);
    }
    @GetMapping("/search/{article-id}")
    @Operation(summary = "게시글 반환", description = "게시글 하나를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "성공 /n/n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> searchArticle(@PathVariable("article-id") Long articleId) {
        return articleService.searchArticle(articleId);
    }
    @GetMapping(value = "/my/list/")
    @Operation(summary = "내 게시글 조회",description = "내 게시글만 볼 수 있습니다. ")
    @ApiResponse(responseCode = "200",description = "성공")
    public ResponseEntity<? extends BaseResponseBody> responseEntity(){
        return articleService.getMyArticleList();
    }

    @DeleteMapping("/delete/{article-id}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "성공 /n/n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> removeArticle(@PathVariable("article-id") Long articleId) {
        return articleService.removeArticle(articleId);
    }

    @GetMapping(value = "/list")
    @Operation(summary = "게시글 리스트반환", description = "게시글을 리스트로 반환합니다. ")
    @ApiResponse(responseCode = "200", description = "성공 /n/n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> articleList(){
        return articleService.listArticle();
    }

    @PostMapping(value = "/regist", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시글 등록", description = "게시글을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "성공 /n/n Success 반환")
    public ResponseEntity<? extends BaseResponseBody> articleSave(
            @Valid @RequestPart(value = "articleReqDto") ArticleReqDto articleReqDto,
            @RequestPart(value = "mainImage") MultipartFile mainImage) {
        return articleService.saveArticle(articleReqDto, mainImage);
    }

}
