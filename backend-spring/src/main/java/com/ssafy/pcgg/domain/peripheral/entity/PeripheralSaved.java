package com.ssafy.pcgg.domain.peripheral.entity;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peripheral_saved",
	uniqueConstraints = @UniqueConstraint(columnNames = {"type", "user_id", "peripheral_id"}))
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeripheralSaved {
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
}
