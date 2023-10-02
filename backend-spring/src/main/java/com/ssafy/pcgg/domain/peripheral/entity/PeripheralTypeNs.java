package com.ssafy.pcgg.domain.peripheral.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peripheral_type_ns")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeripheralTypeNs {
	@Id
	@Column(length = 10)
	private String name;
}
