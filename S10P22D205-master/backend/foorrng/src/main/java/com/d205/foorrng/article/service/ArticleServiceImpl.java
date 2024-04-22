package com.d205.foorrng.article.service;

import com.d205.foorrng.article.dto.request.ArticleReqDto;
import com.d205.foorrng.article.dto.request.ArticleUpdateReqDto;
import com.d205.foorrng.article.dto.response.ArticleListResDto;
import com.d205.foorrng.article.dto.response.ArticleResDto;
import com.d205.foorrng.article.entity.Article;
import com.d205.foorrng.article.repository.ArticlePostRepository;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.common.service.S3Service;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.S3Image;
import com.d205.foorrng.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.tags.ArgumentTag;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static com.d205.foorrng.common.exception.ErrorCode.ARTICLE_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{

    private final S3Image imageSave; //S3 서비스 저장
    private final ArticlePostRepository articlePostRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    @Transactional
    public ResponseEntity<BaseResponseBody> updateArticle(ArticleUpdateReqDto articleDto, MultipartFile image) {
        try {
            Article article = articlePostRepository.findById(articleDto.getArticleId())
                    .orElseThrow(() -> new RuntimeException("Article not found with id " + articleDto.getArticleId()));
            String mainImgUrl = "";
            if (image == null) {
                //만약 null이면? 기존에 있는 걸로 쓴다.
                mainImgUrl = article.getMainImage();
            }else{
                //널이 아니면 저장한다.
                mainImgUrl = imageSave.saveImageS3(image,"updated"+ articleDto.getArticleId() + ".png", "/articleIMG");
            }
            article =Article.builder()
                    .id(article.getId())
                    .createdDatetime(article.getCreatedDatetime())
                    .user(article.getUser())
                    .address(articleDto.getAddress())
                    .phone(articleDto.getPhone())
                    .title(articleDto.getTitle())
                    .content(articleDto.getContent())
                    .latitude(articleDto.getLatitude())
                    .longitude(articleDto.getLongitude())
                    .email(articleDto.getEmail())
                    .kakaoID(articleDto.getKakaoId())
                    .organizer(articleDto.getOrganizer())
                    .startDate(articleDto.getStartDate())
                    .endDate(articleDto.getEndDate())
                    .mainImage(mainImgUrl)
                    .build();
            articlePostRepository.save(article);
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, "성공적으로 업데이트 됐습니다."));
        } catch (NumberFormatException e) {
            log.error("U",e);
            // 사용자 ID 변환 중 예외 발생
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseBody.of(1, "잘못된 사용자 ID"));
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("E",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponseBody.of(1, "서버 내부 오류: " + e.getMessage()));
        }
    }
    public static int getRandomNumber(){
        Random random = new Random();
        int randomIntInRange = random.nextInt(100000);
        return randomIntInRange;
    }
    public static String getMD5Hash(String input) throws NoSuchAlgorithmException{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    @Override
    public ResponseEntity<BaseResponseBody> saveArticle(ArticleReqDto article, MultipartFile mainImage) {
        try {
            logger.info("ASDF1");
            Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
            if (!currentUsername.isPresent()) {
                // 현재 사용자 이름을 가져오는 데 실패한 경우
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponseBody.of(1, "사용자 인증 실패"));
            }
            Long userId = Long.parseLong(currentUsername.get());
            logger.info(userId.toString()+"WERWERWER");

            int articleNumber = getRandomNumber();
            String articleImagePath = getMD5Hash(article.getTitle()+articleNumber);
            String mainImgUrl = "";

            if (mainImage != null && !mainImage.isEmpty()) {

                mainImgUrl = imageSave.saveImageS3(mainImage, articleImagePath + ".png", "/articleIMG");
            }
            Optional<User> userOptional = userRepository.findByUserUid(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBody.of(1, "사용자를 찾을 수 없음"));
            }
            logger.info("ASDF2");
            User user = userOptional.get();
            Article articleForSave = Article.builder()
                    .user(user)
                    .address(article.getAddress())
                    .phone(article.getPhone())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .latitude(article.getLatitude())
                    .longitude(article.getLongitude())
                    .email(article.getEmail())
                    .kakaoID(article.getKakaoId())
                    .organizer(article.getOrganizer())
                    .startDate(article.getStartDate())
                    .endDate(article.getEndDate())
                    .mainImage(mainImgUrl)
                    .build();
            logger.info("ASDF3");
            articlePostRepository.save(articleForSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, "성공적으로 저장"));
        } catch (NumberFormatException e) {
            log.error("U",e);
            // 사용자 ID 변환 중 예외 발생
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseBody.of(1, "잘못된 사용자 ID"));
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("E",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponseBody.of(1, "서버 내부 오류: " + e.getMessage()));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<BaseResponseBody> searchArticle(Long articleId) {

        //DB에서 생으로 데이터를 받는다.
        Optional<Article> articleOptional = articlePostRepository.findById(articleId);
        if (!articleOptional.isPresent()) {
            //존재하는지 체크를 해준다.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBody.of(1, "Article not found"));
        }
        //Optional을 풀어준다.
        Article article = articleOptional.get();

        logger.error(articleOptional.get().getAddress());
        logger.error(articleOptional.get().getKakaoID());
        logger.error(articleOptional.get().getEmail());
        ArticleResDto articleResDto = ArticleResDto.of(
                article.getId(),article.getUser().getId(),article.getTitle(), article.getContent(), article.getLatitude(), article.getLongitude(), article.getPhone(),
                article.getEmail(), article.getKakaoID(), article.getOrganizer(),
                article.getStartDate(), article.getEndDate(), article.getAddress(),article.getMainImage());


        return ResponseEntity.ok(BaseResponseBody.of(0, articleResDto));
    }
    @Transactional
    @Override
    public ResponseEntity<BaseResponseBody> removeArticle(Long articleId) {
        try {
            Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
            if (!currentUsername.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponseBody.of(1, "User authentication failed"));
            }
            Long userId = Long.parseLong(currentUsername.get());
            Optional<User> userOptional = userRepository.findByUserUid(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBody.of(1, "User not found"));
            }
            log.error("dsaf");
            Article article = articlePostRepository.findById(articleId)
                    .orElseThrow(() -> new Exceptions(ARTICLE_NOT_EXIST));
            articlePostRepository.delete(article);
            return ResponseEntity.ok(BaseResponseBody.of(0, "Article successfully deleted"));
        } catch (NumberFormatException e) {
            log.error("User ID format error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseBody.of(1, "Invalid user ID"));
        } catch (Exceptions e) {
            log.error("Article not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBody.of(1, e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponseBody.of(1, "Server error: " + e.getMessage()));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<BaseResponseBody> listArticle() {
        List<Article> articles = articlePostRepository.findAll();
        List<ArticleResDto> articleResDtoList = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            ArticleResDto articleResDto = ArticleResDto.of(
                    article.getId(),article.getUser().getId(),article.getTitle(), article.getContent(), article.getLatitude(), article.getLongitude(), article.getPhone(),
                    article.getEmail(), article.getKakaoID(), article.getOrganizer(),
                    article.getStartDate(), article.getEndDate(), article.getAddress(), article.getMainImage());
            articleResDtoList.add(articleResDto);
        }
        return ResponseEntity.ok(BaseResponseBody.of(0, articleResDtoList));
    }

    @Override
    public ResponseEntity<BaseResponseBody> getMyArticleList() {
//        List<Article> articles = articlePostRepository.findAll();

        List<ArticleResDto> articleResDtoList = new ArrayList<>();
        Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        Long userId = user.getId();
        if (!currentUsername.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponseBody.of(1, "User authentication failed"));
        }
//        Long userId = Long.parseLong(currentUsername.get());
        logger.info(userId.toString()+"ASDF");

        List<Article> articles = articlePostRepository.findAllByUserId(userId)
                .orElseThrow(() -> new RuntimeException("user not found with id "));

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            logger.info(article.getUser().getId().toString());
            logger.info(article.getUser().getUserUid().toString());
//            if (article.getUser().getId().equals(userId)){
            ArticleResDto articleResDto = ArticleResDto.of(
                    article.getId(),article.getUser().getId(),article.getTitle(),
                    article.getContent(), article.getLatitude(), article.getLongitude(),
                    article.getPhone(), article.getEmail(), article.getKakaoID(), article.getOrganizer(),
                    article.getStartDate(), article.getEndDate(), article.getAddress(), article.getMainImage());
            articleResDtoList.add(articleResDto);
//            }
        }
        return ResponseEntity.ok(BaseResponseBody.of(0, articleResDtoList));
    }

}
