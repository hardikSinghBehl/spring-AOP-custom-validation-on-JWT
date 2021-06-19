package com.hardik.crackerjack.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "master_country")
public class MasterCountry implements Serializable {

	private static final long serialVersionUID = 8150058396884155166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterCountry")
	private Set<User> user;

}
