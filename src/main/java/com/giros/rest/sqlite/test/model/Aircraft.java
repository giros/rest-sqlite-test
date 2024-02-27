package com.giros.rest.sqlite.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Getter
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NonNull
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "manufacturer")
    private String manufacturer;

    @NonNull
    @Column(name = "model")
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;
}
