package ru.tokenwoken.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tokenwoken.model.ValueData;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ValueDataRepo extends JpaRepository<ValueData, Long> {

    @Query(value = "SELECT v.receivedTime FROM ValueData v")
    List<LocalDateTime> getAllTimeStamps();

    @Query(value = "SELECT v FROM ValueData v ORDER BY v.receivedTime")
    List<ValueData> findAllOrderByReceived();
}
