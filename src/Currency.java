/**
 * Created by Niko Osa on 9/4/16.
 */
class Currency {

    private final String code;
    private final String name;
    private final Double rate;

    Currency(String currencyCode, String currencyName, Double currencyRate) {

        this.code = currencyCode;
        this.name = currencyName;
        this.rate = currencyRate;

    }

    public String getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    Double getRate() {
        return rate;
    }
}
