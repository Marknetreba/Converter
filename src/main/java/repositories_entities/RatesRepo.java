package repositories_entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 TEST REPOSITORY FOR POSSIBLE "Rates" TABLE IN H2.
 */
@Repository
public interface RatesRepo extends JpaRepository<Rates, Long> {

    Rates findTop3ByDate(String date);
}
