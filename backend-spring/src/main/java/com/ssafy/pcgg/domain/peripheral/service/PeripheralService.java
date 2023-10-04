package com.ssafy.pcgg.domain.peripheral.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.dto.ReviewDto;
import com.ssafy.pcgg.domain.peripheral.dto.ReviewListDto;
import com.ssafy.pcgg.domain.peripheral.dto.ReviewRequestDto;
import com.ssafy.pcgg.domain.peripheral.dto.RatingResponseDto;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralReview;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralSaved;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralTypeNs;
import com.ssafy.pcgg.domain.peripheral.repository.EtcRepository;
import com.ssafy.pcgg.domain.peripheral.repository.KeyboardRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MonitorRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MouseRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PeripheralReviewRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PeripheralSavedRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PeripheralTypeNsRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PrinterRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class PeripheralService {

	private final KeyboardRepository keyboardRepository;
	private final MonitorRepository monitorRepository;
	private final MouseRepository mouseRepository;
	private final PrinterRepository printerRepository;
	private final EtcRepository etcRepository;

	private final UserRepository userRepository;
	private final PeripheralTypeNsRepository peripheralTypeNsRepository;
	private final PeripheralReviewRepository peripheralReviewRepository;
	private final PeripheralSavedRepository peripheralSavedRepository;

	/**
	* 주변기기 - 키보드
	* 목록조회
	*/
	public Slice<PeripheralResponseDto> keyboardList(int pages) {
		PageRequest pageRequest = PageRequest.of(pages, 5, Sort.by(Sort.Direction.ASC, "id"));
		Slice<PeripheralResponseDto> keyboardResponseSlice =  keyboardRepository.findSliceBy(pageRequest);
		return keyboardResponseSlice;
	}

	/**
	 * 주변기기 - 키보드, 모니터, 마우스, 프린터, 기타
	 * 목록조회
	 */
	public Slice<PeripheralResponseDto> getPeripherals(String category, int pages) {
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.ASC, "id"));

		Slice<PeripheralResponseDto> peripheralResponseDtoSlice = null;

		if(category.equals("keyboard")){
			peripheralResponseDtoSlice =  keyboardRepository.findSliceBy(pageRequest);
		} else if(category.equals("monitor")){
			peripheralResponseDtoSlice =  monitorRepository.findSliceBy(pageRequest);
		} else if(category.equals("mouse")){
			peripheralResponseDtoSlice =  mouseRepository.findSliceBy(pageRequest);
		} else if(category.equals("printer")){
			peripheralResponseDtoSlice =  printerRepository.findSliceBy(pageRequest);
		} else if(category.equals("etc")){
			peripheralResponseDtoSlice =  etcRepository.findSliceBy(pageRequest);
		} else{
			throw new IllegalArgumentException("유효하지 않은 주변기기 카테고리입니다.");
		}

		return peripheralResponseDtoSlice;
	}

	@Transactional
	public RatingResponseDto addReview(UserIdDto userIdDto, String category, ReviewRequestDto reviewRequestDto){
		PeripheralTypeNs peripheralTypeNs = peripheralTypeNsRepository.findByName(category)
			.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
		UserEntity userEntity = userRepository.findById(userIdDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 유저가 존재하지 않습니다."));

		PeripheralReview peripheralReview = PeripheralReview.builder()
			.peripheralTypeNs(peripheralTypeNs)
			.user(userEntity)
			.peripheralId(reviewRequestDto.getPeripheralId())
			.rating(reviewRequestDto.getRating())
			.review(reviewRequestDto.getReview())
			.createdAt(LocalDateTime.now())
			.build();

		Long ratingId =  peripheralReviewRepository.save(peripheralReview).getId();

		// 평점 평균 계산
		String avgRating = calculateAverageRating(reviewRequestDto.getPeripheralId(), category);

		return RatingResponseDto.builder()
			.ratingId(ratingId)
			.avgRating(avgRating)
			.build();
	}

	@Transactional
	public void deleteReview(UserIdDto userIdDto, Long reviewId){
		PeripheralReview peripheralReview = peripheralReviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalArgumentException("해당 후기(평점)이 없습니다."));

		if(!Objects.equals(peripheralReview.getUser().getUserId(), userIdDto.getUserId())) {
			throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
		}

		peripheralReviewRepository.delete(peripheralReview);
	}

	@Transactional
	public RatingResponseDto updateReview(UserIdDto userIdDto, String category, Long reviewId, ReviewRequestDto reviewRequestDto){
		PeripheralReview peripheralReview = peripheralReviewRepository.findById(reviewId)
			.orElseThrow(() -> new IllegalArgumentException("해당 후기(평점)이 없습니다."));

		JpaRepository<?, Long> repository = null;
		String errorMessage = null;

		switch (category) {
			case "keyboard":
				repository = keyboardRepository;
				errorMessage = "해당 키보드 제품이 없습니다.";
				break;
			case "mouse":
				repository = mouseRepository;
				errorMessage = "해당 마우스 제품이 없습니다.";
				break;
			case "monitor":
				repository = monitorRepository;
				errorMessage = "해당 모니터 제품이 없습니다.";
				break;
			case "printer":
				repository = printerRepository;
				errorMessage = "해당 프린터 제품이 없습니다.";
				break;
			case "etc":
				repository = etcRepository;
				errorMessage = "해당 주변기기 제품 정보가 없습니다.";
				break;
			default:
				throw new IllegalArgumentException("유효하지 않은 카테고리입니다.");
		}

		if (repository != null && !repository.existsById(reviewRequestDto.getPeripheralId())) {
			throw new IllegalArgumentException(errorMessage);
		}

		if(!Objects.equals(peripheralReview.getUser().getUserId(), userIdDto.getUserId())) {
			throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
		}

		peripheralReview.updateRating(reviewRequestDto.getRating(), reviewRequestDto.getReview());
		Long ratingId = peripheralReviewRepository.save(peripheralReview).getId();

		// 평점 평균 계산
		String avgRating = calculateAverageRating(reviewRequestDto.getPeripheralId(), category);

		return RatingResponseDto.builder()
			.ratingId(ratingId)
			.avgRating(avgRating)
			.build();
	}

	@Transactional
	public ReviewListDto getReviews(int pages, String category, Long peripheralId){
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
		PeripheralTypeNs peripheralTypeNs = peripheralTypeNsRepository.findByName(category)
			.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
		Slice<PeripheralReview> peripheralReviews = peripheralReviewRepository.findSliceByTypeNameAndPeripheralId(peripheralTypeNs.getName(), peripheralId, pageRequest);

		List<Integer> ratingList = peripheralReviews.stream()
			.map(review -> review.getRating())
			.collect(Collectors.toList());

		double averageRating = ratingList.stream()
			.mapToInt(Integer::intValue)
			.average()
			.orElse(0.0); // 만약 리스트가 비어있으면 기본값을 설정

		return ReviewListDto.builder()
			.avgRating(String.format("%.1f", averageRating))
			.reviewDtos(peripheralReviews.map(this::convertToDto))
			.build();
	}

	private ReviewDto convertToDto(PeripheralReview peripheralReview) {
		return ReviewDto.builder()
			.reviewId(peripheralReview.getId())
			.userId(peripheralReview.getUser().getUserId())
			.userNickname(peripheralReview.getUser().getNickname())
			.rating(peripheralReview.getRating())
			.review(peripheralReview.getReview())
			.createdAt(peripheralReview.getCreatedAt())
			.build();
	}

	@Transactional
	public String calculateAverageRating(Long peripheralId, String type){
		Double value = peripheralReviewRepository.findAverageRatingByTypeNameAndPeripheralId(type, peripheralId);
		return String.format("%.1f", value);
	}

	@Transactional
	public Long savePeripheral(UserIdDto userIdDto, String category, Long peripheralId){
		PeripheralTypeNs peripheralTypeNs = peripheralTypeNsRepository.findByName(category)
			.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
		UserEntity userEntity = userRepository.findById(userIdDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 유저가 존재하지 않습니다."));

		// TODO: 해당 주변기기의 존재 유무 체크하기

		PeripheralSaved peripheralSaved = PeripheralSaved.builder()
			.peripheralTypeNs(peripheralTypeNs)
			.user(userEntity)
			.peripheralId(peripheralId)
			.build();

		return peripheralSavedRepository.save(peripheralSaved).getId();
	}

	@Transactional
	public void deleteMyPeripheral(UserIdDto userId, Long myperipheralId){
		PeripheralSaved peripheralSaved = peripheralSavedRepository.findById(myperipheralId)
			.orElseThrow(() -> new IllegalArgumentException("저장된 주변기기가 존재하지 않습니다."));
		if(!Objects.equals(userId.getUserId(), peripheralSaved.getUser().getUserId())){
			throw new IllegalArgumentException("자신이 저장한 주변기기만 삭제할 수 있습니다.");
		}
		peripheralSavedRepository.delete(peripheralSaved);
	}

}
