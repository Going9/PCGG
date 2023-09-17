package com.ssafy.pcgg.domain.share.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pcgg.domain.recommend.entity.ChassisEntity;
import com.ssafy.pcgg.domain.recommend.entity.CoolerEntity;
import com.ssafy.pcgg.domain.recommend.entity.CpuEntity;
import com.ssafy.pcgg.domain.recommend.entity.GpuEntity;
import com.ssafy.pcgg.domain.recommend.entity.MainboardEntity;
import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import com.ssafy.pcgg.domain.recommend.entity.RamEntity;
import com.ssafy.pcgg.domain.recommend.entity.SsdEntity;
import com.ssafy.pcgg.domain.recommend.repository.ChassisRepository;
import com.ssafy.pcgg.domain.recommend.repository.CoolerRepository;
import com.ssafy.pcgg.domain.recommend.repository.CpuRepository;
import com.ssafy.pcgg.domain.recommend.repository.GpuRepository;
import com.ssafy.pcgg.domain.recommend.repository.MainboardRepository;
import com.ssafy.pcgg.domain.recommend.repository.PowerRepository;
import com.ssafy.pcgg.domain.recommend.repository.QuoteRepository;
import com.ssafy.pcgg.domain.recommend.repository.RamRepository;
import com.ssafy.pcgg.domain.recommend.repository.SsdRepository;
import com.ssafy.pcgg.domain.share.dto.ShardAddQuoteRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareMarkRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareResponseDto;
import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareLike;
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

	private final ShareLikeRepository shareLikeRepository;

	@Transactional
	public Long writeShare(ShareAddRequestDto shareAddRequestDto) {
		//1. 견적 생성
		ShardAddQuoteRequestDto quoteRequestDto = shareAddRequestDto.getShardAddQuoteRequestDto();

		System.out.println("33");
		//1.1 견적Dto에서 받은 부품별 객체를 받아서
		CpuEntity cpuEntity = cpuRepository.findById(quoteRequestDto.getCpuId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 cpu가 존재하지 않습니다."));
		MainboardEntity mainboardEntity = mainboardRepository.findById(quoteRequestDto.getMainboardId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 mainboard가 존재하지 않습니다."));
		SsdEntity ssdEntity = ssdRepository.findById(quoteRequestDto.getSsdId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 ssd가 존재하지 않습니다."));
		RamEntity ramEntity = ramRepository.findById(quoteRequestDto.getRamId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 ram가 존재하지 않습니다."));
		GpuEntity gpuEntity = gpuRepository.findById(quoteRequestDto.getGpuId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 gpu가 존재하지 않습니다."));
		ChassisEntity chassisEntity = chassisRepository.findById(quoteRequestDto.getChassisId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 chassis가 존재하지 않습니다."));
		PowerEntity powerEntity = powerRepository.findById(quoteRequestDto.getPowerId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 power가 존재하지 않습니다."));
		CoolerEntity coolerEntity = coolerRepository.findById(quoteRequestDto.getCoolerId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 cooler가 존재하지 않습니다."));

		System.out.println("44");
		//1.2 견적엔티티(QuoteEntity)를 생성하여
		QuoteEntity quoteEntity = QuoteEntity.builder()
			.cpu(cpuEntity)
			.mainboard(mainboardEntity)
			.ssd(ssdEntity)
			.ram(ramEntity)
			.gpu(gpuEntity)
			.chassis(chassisEntity)
			.power(powerEntity)
			.cooler(coolerEntity)
			.build();

		QuoteEntity quote = quoteRepository.save(quoteEntity);

		UserEntity userEntity = userRepository.findById(shareAddRequestDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 사용자가 존재하지 않습니다."));

		//2. 공유마당 게시글 생성
		Share share = Share.builder()
			.user(userEntity)
			.quote(quote)
			.title(shareAddRequestDto.getTitle())
			.content(shareAddRequestDto.getContent())
			.summary(shareAddRequestDto.getSummary())
			.createdAt(LocalDateTime.now())
			.build();

		Share share1 = shareRepository.save(share);

		//3. 생성된 테이블의 id값을 리턴
		return share1.getId();
	}

	/**
	 * 공유마당 상세 조회
	 */
	public ShareResponseDto getShare(Long shareId){
		Share share =  shareRepository.findById(shareId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));

		ShareResponseDto shareResponseDto = ShareResponseDto.builder()
			.id(share.getId())
			.userId(share.getUser().getUserId())
			.quoteId(share.getQuote().getId())
			.title(share.getTitle())
			.content(share.getContent())
			.summary(share.getSummary())
			.createdAt(share.getCreatedAt())
			// .mark(0)
			.build();

		return shareResponseDto;
	}

	/**
	 * 공유마당 삭제
	 */
	public void deleteShare(Long shareId){
		Share share =  shareRepository.findById(shareId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));
		// TODO: 작성자와 삭제 요청자의 id의 일치 여부
		shareRepository.delete(share);
	}

	/**
	* 공유마당 목록 조회
	*/
	public Slice<ShareResponseDto> getAllShare(int pages){
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
		Slice<Share> shareResponseDtoList = shareRepository.findSliceBy(pageRequest);

		return shareResponseDtoList.map(this::convertToDto);
	}

	private ShareResponseDto convertToDto(Share share){
		ShareResponseDto shareResponseDto = ShareResponseDto.builder()
			.id(share.getId())
			.userId(share.getUser().getUserId())
			.quoteId(share.getQuote().getId())
			.title(share.getTitle())
			.content(share.getContent())
			.summary(share.getSummary())
			.createdAt(share.getCreatedAt())
			// .mark(0)
			.build();

		return shareResponseDto;
	}

	/**
	 * 공유마당 좋아요/싫어요
	 */
	@Transactional
	public void markLikes(Long articleId, ShareMarkRequestDto markRequestDto){
		UserEntity userEntity = userRepository.findById(markRequestDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 유저가 존재하지 않습니다."));
		Share share = shareRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));

		ShareLike shareLike = shareLikeRepository.findByShareAndUser(share, userEntity);

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
}
