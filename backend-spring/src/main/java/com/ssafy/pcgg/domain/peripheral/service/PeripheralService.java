package com.ssafy.pcgg.domain.peripheral.service;

import java.util.LongSummaryStatistics;
import java.util.Objects;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.dto.RatingRequestDto;
import com.ssafy.pcgg.domain.peripheral.dto.RatingResponseDto;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralRating;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralTypeNs;
import com.ssafy.pcgg.domain.peripheral.repository.EtcRepository;
import com.ssafy.pcgg.domain.peripheral.repository.KeyboardRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MonitorRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MouseRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PeripheralRatingRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PreipheralTypeNsRepository;
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
	private final PreipheralTypeNsRepository preipheralTypeNsRepository;
	private final PeripheralRatingRepository peripheralRatingRepository;

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
	public Slice<PeripheralResponseDto> peripheralList(String category, int pages) {
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
		}

		return peripheralResponseDtoSlice;
	}

	@Transactional
	public RatingResponseDto addComment(UserIdDto userIdDto, String category, RatingRequestDto ratingRequestDto){
		PeripheralTypeNs peripheralTypeNs = preipheralTypeNsRepository.findByName(category);
		UserEntity userEntity = userRepository.findById(userIdDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 일치하는 유저가 존재하지 않습니다."));

		PeripheralRating peripheralRating = PeripheralRating.builder()
			.peripheralTypeNs(peripheralTypeNs)
			.user(userEntity)
			.peripheralId(ratingRequestDto.getPeripheralId())
			.rating(ratingRequestDto.getRating())
			.comment(ratingRequestDto.getComment())
			.build();



		Long ratingId =  peripheralRatingRepository.save(peripheralRating).getId();

		// 평점 평균 계산
		String avgRating = calculateAverageRating(ratingRequestDto.getPeripheralId(), category);

		return RatingResponseDto.builder()
			.ratingId(ratingId)
			.avgRating(avgRating)
			.build();
	}

	@Transactional
	public void deleteComment(UserIdDto userIdDto, Long commentId){
		PeripheralRating peripheralRating = peripheralRatingRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 후기(평점)이 없습니다."));

		if(!Objects.equals(peripheralRating.getUser().getUserId(), userIdDto.getUserId())) {
			throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
		}

		peripheralRatingRepository.delete(peripheralRating);
	}

	@Transactional
	public RatingResponseDto updateComment(UserIdDto userIdDto, String category, Long commentId, RatingRequestDto ratingRequestDto){
		PeripheralRating peripheralRating = peripheralRatingRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 후기(평점)이 없습니다."));

		if(!Objects.equals(peripheralRating.getUser().getUserId(), userIdDto.getUserId())) {
			throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
		}

		peripheralRating.updateRating(ratingRequestDto.getRating(), ratingRequestDto.getComment());
		Long ratingId = peripheralRatingRepository.save(peripheralRating).getId();

		// 평점 평균 계산
		String avgRating = calculateAverageRating(ratingRequestDto.getPeripheralId(), category);

		return RatingResponseDto.builder()
			.ratingId(ratingId)
			.avgRating(avgRating)
			.build();
	}

	@Transactional
	public String calculateAverageRating(Long peripheralId, String type){
		Double value = peripheralRatingRepository.findAverageRatingByTypeNameAndPeripheralId(type, peripheralId);
		return String.format("%.1f", value);
	}

}
