package com.sparta.plusweekassignment.domain.post.service;

import com.sparta.plusweekassignment.domain.post.dto.PostPageRequestDto;
import com.sparta.plusweekassignment.domain.post.dto.PostPageResponseDto;
import com.sparta.plusweekassignment.domain.post.dto.PostRequestDto;
import com.sparta.plusweekassignment.domain.post.dto.PostResponseDto;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.post.repository.PostRepository;
import com.sparta.plusweekassignment.domain.user.entity.User;
import com.sparta.plusweekassignment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto addPost(PostRequestDto postRequestDto, User user) {

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostResponseDto findById(long id) {
        Post post = findPostById(id);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> findAll() {
        List<Post> postlist = postRepository.findAll();
        return postlist.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(PostResponseDto::new)
                .toList();
    }

    public PostResponseDto updatePost(PostRequestDto postRequestDto, Long postId, User user) {
        Post post = findPostById(postId);

        validateUser(post, user);

        post.update(postRequestDto);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public void deletePost(Long postId, User user) {
        Post post = findPostById(postId);

        validateUser(post, user);

        postRepository.delete(post);
    }

    private Post findPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
    }

    private void validateUser(Post post, User user) {
        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("작성자만 할 수 있습니다.");
        }
    }

//    @Transactional
//    public Page<PostPageResponseDto> getPostPage(PostPageRequestDto requestDto) {
//        log.info(requestDto.toString());
//
//        Sort.Direction direction = Sort.Direction.DESC; //ASC 오름차순 , DESC 내림차순
//        //- 생성일자 기준 최신 - 좋아요 많은 순
//
//        // --- 정렬 방식 ---
//        //CREATE  or  LIKED
//        String sortBy = "created_at";
//        if (requestDto.getSortBy().equals("CREATE")) {
//            sortBy = "createdAt";
//        } else if (requestDto.getSortBy().equals("LIKED")) {
//            sortBy = "likeCount";
//        } else
//            throw new IllegalArgumentException("정렬은 CREATE 또는 LIKED 만 입력 가능합니다.");
//
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize(), sort);
//
//        Page<PostPageResponseDto> postList = null;
//
//        //---날짜 부분 ---
//        LocalDate lastDate = LocalDate.now();
//        LocalDate firstDate = LocalDate.parse("2000-01-01");
//        // null 이면 모든 날짜를 조회
//
//        if (requestDto.getLastDate() != null && requestDto.getFirstDate() != null) {
//            // 날짜 정보가 있으면 해당 날짜만 조회
//            try {
//                lastDate = LocalDate.parse(requestDto.getLastDate());
//                firstDate = LocalDate.parse(requestDto.getFirstDate());
//            } catch (Exception e) {
//                throw new IllegalArgumentException("날짜 포맷이 정상적이지 않습니다.");
//            }
//        }
//
//        postList = postRepository.findPostPages(firstDate.atStartOfDay().toString(), lastDate.atTime(LocalTime.MAX).toString(), pageable);
//
//        if (postList.getTotalElements() <= 0) {
//            log.error("페이지 없음");
//            throw new IllegalArgumentException("페이지가 존재하지 않습니다.");
//        }
//        if (postList.getTotalPages() < requestDto.getPage()) {
//            throw new IllegalArgumentException("유효한 페이지 번호가 아닙니다.");
//        }
//
//        return postList;
//    }

    //관리자 전용 게시글 수정 메서드
    public PostResponseDto adminUpdatePost(PostRequestDto postRequestDto, Long postId) {
        Post post = findPostById(postId);
        post.update(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //관리자 전용 게시글 삭제 메서드
    public void adminDeletePost(Long postId) {
        Post post = findPostById(postId);
        postRepository.delete(post);
    }
}
