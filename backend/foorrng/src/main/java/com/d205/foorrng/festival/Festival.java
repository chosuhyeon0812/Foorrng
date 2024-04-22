package com.d205.foorrng.festival;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival {
    @Id
    @Column(name = "festival_id")
    private Long festivalId;
    private String country;
    private String period;
    private String festivalName;
    private String location;
    private String organization;
    private String agency;
    private String contact_number;

}
