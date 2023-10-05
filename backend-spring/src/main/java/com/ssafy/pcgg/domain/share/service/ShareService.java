package com.ssafy.pcgg.domain.share.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import com.ssafy.pcgg.domain.recommend.entity.QuoteSaved;
import com.ssafy.pcgg.domain.part.repository.ChassisRepository;
import com.ssafy.pcgg.domain.part.repository.CoolerRepository;
import com.ssafy.pcgg.domain.part.repository.CpuRepository;
import com.ssafy.pcgg.domain.part.repository.GpuRepository;
import com.ssafy.pcgg.domain.part.repository.MainboardRepository;
import com.ssafy.pcgg.domain.part.repository.PowerRepository;
import com.ssafy.pcgg.domain.recommend.repository.QuoteRepository;
import com.ssafy.pcgg.domain.recommend.repository.QuoteSavedRepository;
import com.ssafy.pcgg.domain.part.repository.RamRepository;
import com.ssafy.pcgg.domain.part.repository.SsdRepository;
import com.ssafy.pcgg.domain.share.dto.AuthorMarkInfoDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddQuoteRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareDetailDto;
import com.ssafy.pcgg.domain.share.dto.ShareMarkRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareResponseDto;
import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareLike;
import com.ssafy.pcgg.domain.share.repository.ShareCommentRepository;
import com.ssafy.pcgg.domain.share.repository.ShareLikeRepository;
import com.ssafy.pcgg.domain.share.repository.ShareRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShareService {

	private final ShareRepository shareRepository;
	private final QuoteRepository quoteRepository;
	private final CpuRepository cpuRepository;
	private final MainboardRepository mainboardRepository;
	private final SsdRepository ssdRepository;
	private final RamRepository ramRepository;
	private final GpuRepository gpuRepository;
	private final ChassisRepository chassisRepository;
	private final PowerRepository powerRepository;
	private final CoolerRepository coolerRepository;
	private final UserRepository userRepository;

	private final QuoteSavedRepository quoteSavedRepository;
	private final ShareLikeRepository shareLikeRepository;
	private final ShareCommentRepository shareCommentRepository;

	@Transactional
	public Long addShare(UserIdDto userId, ShareAddRequestDto shareAddRequestDto) {

		UserEntity userEntity = userRepository.findById(userId.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 사용자가 존재하지 않습니다."));

		//1. 견적 생성
		ShareAddQuoteRequestDto quoteRequestDto = shareAddRequestDto.getShareAddQuoteRequestDto();

		//1.1 견적Dto에서 받은 부품별 객체를 받아서
		if(quoteRequestDto.getCpuId() != null && !cpuRepository.existsById(quoteRequestDto.getCpuId())){
			throw new IllegalArgumentException("해당 id에 일치하는 cpu가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getMainboardId() != null && !mainboardRepository.existsById(quoteRequestDto.getMainboardId())){
			throw new IllegalArgumentException("해당 id에 일치하는 mainboard가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getSsdId() != null && !ssdRepository.existsById(quoteRequestDto.getSsdId())){
			throw new IllegalArgumentException("해당 id에 일치하는 ssd가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getRamId() != null && !ramRepository.existsById(quoteRequestDto.getRamId())){
			throw new IllegalArgumentException("해당 id에 일치하는 ram이 존재하지 않습니다.");
		}
		if(quoteRequestDto.getGpuId() != null && !gpuRepository.existsById(quoteRequestDto.getGpuId())){
			throw new IllegalArgumentException("해당 id에 일치하는 gpu가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getChassisId() != null && !chassisRepository.existsById(quoteRequestDto.getChassisId())){
			throw new IllegalArgumentException("해당 id에 일치하는 chassis가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getPowerId() != null && !powerRepository.existsById(quoteRequestDto.getPowerId())){
			throw new IllegalArgumentException("해당 id에 일치하는 power가 존재하지 않습니다.");
		}
		if(quoteRequestDto.getCoolerId() != null && !coolerRepository.existsById(quoteRequestDto.getCoolerId())){
			throw new IllegalArgumentException("해당 id에 일치하는 cooler가 존재하지 않습니다.");
		}

		//1.2 견적엔티티(QuoteEntity)를 생성 및 저장
		QuoteEntity quote = quoteRepository.save(QuoteEntity.builder()
			.cpuId(quoteRequestDto.getCpuId())
			.mainboardId(quoteRequestDto.getMainboardId())
			.ssdId(quoteRequestDto.getSsdId())
			.ramId(quoteRequestDto.getRamId())
			.gpuId(quoteRequestDto.getGpuId())
			.chassisId(quoteRequestDto.getChassisId())
			.powerId(quoteRequestDto.getPowerId())
			.coolerId(quoteRequestDto.getCoolerId())
			.build());

		//2. 공유마당 게시글 생성
		Share share = Share.builder()
			.user(userEntity)
			.quote(quote)
			.title(shareAddRequestDto.getTitle())
			.content(shareAddRequestDto.getContent())
			.summary(shareAddRequestDto.getSummary())
			.createdAt(LocalDateTime.now())
			.build();

		Share newShare = shareRepository.save(share);

		//3. 생성된 공유마당 게시글의 id(pk)값을 리턴
		return newShare.getId();
	}

	/**
	 * 공유마당 상세 조회
	 */
	@Transactional(readOnly = true)
	public ShareDetailDto getShare(Long shareId){
		Share share =  shareRepository.findById(shareId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 공유마당 게시글이 존재하지 않습니다."));

		long likeCnt = shareLikeRepository.countLikesForShareWithId(shareId, 1);
		long disLikeCnt = shareLikeRepository.countLikesForShareWithId(shareId, -1);

		QuoteEntity quoteEntity = QuoteEntity.builder()
			.id(share.getQuote().getId())
			.cpu(share.getQuote().getCpu())
			.mainboard(share.getQuote().getMainboard())
			.ssd(share.getQuote().getSsd())
			.ram(share.getQuote().getRam())
			.gpu(share.getQuote().getGpu())
			.chassis(share.getQuote().getChassis())
			.power(share.getQuote().getPower())
			.cooler(share.getQuote().getCooler())
			.build();

		ShareDetailDto shareDetailDto = ShareDetailDto.builder()
			.id(share.getId())
			.userId(share.getUser().getUserId())
			.userNickname(share.getUser().getNickname())
			.quoteEntity(quoteEntity)
			.title(share.getTitle())
			.content(share.getContent())
			.summary(share.getSummary())
			.createdAt(share.getCreatedAt())
			.likeCnt(likeCnt)
			.dislikeCnt(disLikeCnt)
			.build();

		return shareDetailDto;
	}

	public List<ShareDetailDto> getTop5SharesWithMostLikeCnt() {
		List<Share> result = shareRepository.findTop5SharesWithMostLikes();
		List<ShareDetailDto> shareDetailDtos = new ArrayList<>();
		for(Share share : result){
			QuoteEntity quoteEntity = QuoteEntity.builder()
				.id(share.getQuote().getId())
				.cpu(share.getQuote().getCpu())
				.mainboard(share.getQuote().getMainboard())
				.ssd(share.getQuote().getSsd())
				.ram(share.getQuote().getRam())
				.gpu(share.getQuote().getGpu())
				.chassis(share.getQuote().getChassis())
				.power(share.getQuote().getPower())
				.cooler(share.getQuote().getCooler())
				.build();

			ShareDetailDto shareDetailDto = ShareDetailDto.builder()
				.id(share.getId())
				.userId(share.getUser().getUserId())
				.userNickname(share.getUser().getNickname())
				.quoteEntity(quoteEntity)
				.title(share.getTitle())
				.content(share.getContent())
				.summary(share.getSummary())
				.createdAt(share.getCreatedAt())
				.likeCnt(shareLikeRepository.countLikesForShareWithId(share.getId(), 1))
				.dislikeCnt(shareLikeRepository.countLikesForShareWithId(share.getId(), -1))
				.build();

			shareDetailDtos.add(shareDetailDto);
		}
		return shareDetailDtos;
	}

	/**
	 * 공유마당 삭제
	 */
	@Transactional
	public void deleteShare(UserIdDto userId, Long shareId){
		Share share =  shareRepository.findById(shareId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 공유마당 게시글이 존재하지 않습니다."));
		QuoteEntity quoteEntity = share.getQuote();

		if(!Objects.equals(share.getUser().getUserId(), userId.getUserId())) {
			throw new IllegalArgumentException("삭제 권한이 없는 사용자입니다. (작성자만 삭제가능)");
		}

		shareRepository.delete(share);
		quoteRepository.delete(quoteEntity);
	}

	/**
	 * 공유마당 목록 조회
	 */
	@Transactional(readOnly = true)
	public Slice<ShareResponseDto> getShares(String keyword, int pages){
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
		Slice<Share> shareResponseDtoList;

		if(keyword == null || keyword.isEmpty()){
			shareResponseDtoList = shareRepository.findSliceBy(pageRequest);
		} else if(keyword.length() < 2){
			throw new IllegalArgumentException("검색어의 길이는 최소 2글자 이상이어야 합니다.");
		} else{
			shareResponseDtoList = shareRepository.findSliceByTitleContaining(pageRequest, keyword);
		}

		return shareResponseDtoList.map(this::convertToDto);
	}

	@Transactional
	public ShareResponseDto convertToDto(Share share){
		ShareResponseDto shareResponseDto = ShareResponseDto.builder()
			.id(share.getId())
			.userId(share.getUser().getUserId())
			.userNickname(share.getUser().getNickname())
			.quoteId(share.getQuote().getId())
			.title(share.getTitle())
			.content(share.getContent())
			.summary(share.getSummary())
			.createdAt(share.getCreatedAt())
			.likeCnt(shareLikeRepository.countLikesForShareWithId(share.getId(), 1))
			.dislikeCnt(shareLikeRepository.countLikesForShareWithId(share.getId(), -1))
			.reviewCnt(shareCommentRepository.countReviewsForShareWithId(share.getId()))
			.build();

		return shareResponseDto;
	}

	/**
	 * 공유마당 좋아요/싫어요, 작성자 확인
	 */
	public AuthorMarkInfoDto getAuthorMarkInfo(UserIdDto userId, Long shareId){
		Integer mark = shareLikeRepository.findMarkByShareIdAndUserId(shareId, userId.getUserId());
		mark = (mark == null) ? 0 : mark;

		Share share = shareRepository.findById(shareId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 공유마당 게시글이 존재하지 않습니다."));
		boolean isAuthor = (share.getUser().getUserId() == userId.getUserId()) ? true : false;

		return AuthorMarkInfoDto.builder()
			.mark(mark)
			.isAuthor(isAuthor)
			.build();
	}

	/**
	 * 공유마당 좋아요/싫어요
	 */
	@Transactional
	public void markLikes(UserIdDto userId, Long articleId, ShareMarkRequestDto markRequestDto){
		UserEntity userEntity = userRepository.findById(userId.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 유저가 존재하지 않습니다."));
		Share share = shareRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 공유마당 게시글이 존재하지 않습니다."));

		ShareLike shareLike = shareLikeRepository.findByShareAndUser(share, userEntity);

		// TODO: mark 값 1, 0, -1만 허용하도록

		if(shareLike == null){
			shareLike = ShareLike.builder()
				.share(share)
				.user(userEntity)
				.mark(markRequestDto.getMark())
				.build();
			shareLikeRepository.save(shareLike);
		} else if(shareLike.getMark() == markRequestDto.getMark()) {
			shareLikeRepository.delete(shareLike);
		} else{
			shareLike.updateMark(markRequestDto.getMark());
			shareLikeRepository.save(shareLike);
		}
	}

	@Transactional
	public Long saveShare(UserIdDto userId, ShareAddQuoteRequestDto shareAddQuoteRequestDto){
		UserEntity userEntity = userRepository.findById(userId.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 유저가 존재하지 않습니다."));

		QuoteEntity quoteEntity = QuoteEntity.builder()
			.cpuId(shareAddQuoteRequestDto.getCpuId())
			.mainboardId(shareAddQuoteRequestDto.getMainboardId())
			.ssdId(shareAddQuoteRequestDto.getSsdId())
			.ramId(shareAddQuoteRequestDto.getRamId())
			.gpuId(shareAddQuoteRequestDto.getGpuId())
			.chassisId(shareAddQuoteRequestDto.getChassisId())
			.powerId(shareAddQuoteRequestDto.getPowerId())
			.coolerId(shareAddQuoteRequestDto.getCoolerId())
			.build();

		Long quoteId = quoteRepository.save(quoteEntity).getId();

		return quoteSavedRepository.save(QuoteSaved.builder()
			.userId(userId.getUserId())
			.quoteId(quoteId)
			.createdAt(LocalDateTime.now())
			.build()).getId();
	}

	@Transactional
	public String deleteShare(UserIdDto userIdDto, long quoteId) {
		QuoteSaved quoteSaved = quoteSavedRepository.findQuotesByUserIdAndQuoteId(userIdDto.getUserId(), quoteId);

		quoteSavedRepository.delete(quoteSaved);

		return "내 견적 삭제 완료";
	}

}
