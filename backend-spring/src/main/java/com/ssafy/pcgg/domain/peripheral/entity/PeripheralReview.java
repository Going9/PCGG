package com.ssafy.pcgg.domain.peripheral.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.pcgg.domain.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peripheral_review")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeripheralReview {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PeripheralTypeNs peripheralTypeNs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UserEntity user;

	@Column(name = "peripheral_id",nullable = false)
	private Long peripheralId;

	@Column(nullable = false)
	private Integer rating;

	@Column(nullable = false)
	private String review;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public void updateRating(Integer rating, String review){
		this.rating = rating;
		this.review = review;
	}

}
