package com.d205.foorrng.mark;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.mark.dto.MarkDto;
import com.d205.foorrng.operationInfo.OperationInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity
@Getter
@Builder
//@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_seq")
    private Long id;
    private Double latitude;        // 위도
    private Double longitude;       // 경도
    private String address;         // 지번 주소
//    private Boolean isOpen = false;         // 영업상태
    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private Boolean isOpen;         // 영업상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="foodtrucks_seq")
    @JsonIgnore
    private Foodtrucks foodtrucks;

    @OneToMany(mappedBy = "mark", cascade = CascadeType.REMOVE)
//    @JsonIgnore
    private List<OperationInfo> operationInfoList;

    public void update(MarkDto markDto) {
        this.address = markDto.getAddress();
        this.latitude = markDto.getLatitude();
        this.longitude = markDto.getLongitude();
    }

    public void changeIsOpen() {
        this.isOpen = !this.isOpen;
    }
}
