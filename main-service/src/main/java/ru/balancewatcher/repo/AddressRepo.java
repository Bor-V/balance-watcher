package ru.balancewatcher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balancewatcher.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
