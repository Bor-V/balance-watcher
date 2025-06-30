package ru.balancewatcher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.balancewatcher.model.ValueData;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ValueDataRepo extends JpaRepository<ValueData, Long> {

    @Query(value = "SELECT v.receivedTime FROM ValueData v")
    List<LocalDateTime> getAllTimeStamps();

    @Query(value = "SELECT v.blockHash FROM ValueData v")
    List<String> getAllBlockHash();

    @Query(value = "SELECT v FROM ValueData v WHERE v.coinName = 'OCTA' ORDER BY v.receivedTime")
    List<ValueData> findAllOctaDataOrderByReceivedTime();

    @Query(value = "SELECT v FROM ValueData v WHERE v.coinName = 'ETC' ORDER BY v.receivedTime")
    List<ValueData> findAllEtcDataOrderByReceivedTime();

    @Query(value = "SELECT v FROM ValueData v WHERE v.coinName = 'KASPA' ORDER BY v.receivedTime")
    List<ValueData> findAllKaspaDataOrderByReceivedTime();
}
