package com.demo.reserve.bank.asset.management.api.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an asset.
 */
@Entity
@Table(name = "t_asset")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetCode;
    private String assetName;
    private int value;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Asset other = (Asset) obj;
        return Objects.equals(assetCode, other.assetCode) &&
                Objects.equals(assetName, other.assetName) &&
                value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetCode, assetName, value);
    }
}
