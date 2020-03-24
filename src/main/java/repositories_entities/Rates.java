package repositories_entities;


import javax.persistence.*;

/*
 TEST ENTITY FOR STORING DAILY EXCHANGE RATES. SHOULD BE REFACTORED.
 */
@Entity
public class Rates {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String rates;
    private String date;


    public Rates() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "id=" + id +
                ", rate='" + rates + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
