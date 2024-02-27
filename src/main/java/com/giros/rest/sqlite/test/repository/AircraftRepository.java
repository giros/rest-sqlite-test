package com.giros.rest.sqlite.test.repository;

import com.giros.rest.sqlite.test.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, String> {

    public Aircraft findByCode(String code);

    @Query(value = "select ac from Aircraft ac where " +
        "(:code is null or ac.code = :code) and " +
        "(:manufacturer is null or ac.manufacturer = :manufacturer) and " +
        "(:model is null or ac.model = :model) and " +
        "(:description is null or ac.description like '%:description%') and " +
        "(:startDate is null or ac.startDate = :startDate)")
    public List<Aircraft> search(
        @Param("code") String code, @Param("manufacturer") String manufacturer, @Param("model") String model,
        @Param("description") String description, @Param("startDate") LocalDate startDate);

}
