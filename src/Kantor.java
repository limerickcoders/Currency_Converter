import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Niko Osa on 9/4/16.
 */
class Kantor {

    private final Map<String, Currency> stawki = new HashMap<>();
    private Scanner scanIn;
    private String currencyCodes = "";
    private String currentCurrencyCode;
    private String targetCurrencyCode;
    private String amount;
    private Double parsedAmount;


    void LadowanieKursowString() {

        String[] lines = Data.rates.split("\n");

        for (String line : lines) {
            String[] words = line.split(",");

            String name = words[0].concat("s");
            currencyCodes = currencyCodes + words[0].concat(" ").concat(words[1].concat("\n"));
            String code = words[1];
            Double rate = Double.parseDouble(words[2]);
            stawki.put(code, new Currency(code, name, rate));
        }

    }

    private Double wymiana() {
        if (stawki.get(currentCurrencyCode) != null && stawki.get(targetCurrencyCode) != null) {
            Double stwLkl = stawki.get(currentCurrencyCode).getRate();
            Double stwObc = stawki.get(targetCurrencyCode).getRate();

            return (parsedAmount * stwLkl) / stwObc;
        }
        return parsedAmount;
    }

    private Double wymiana(String current, String target, Double amount) {
        if (stawki.get(current) != null && stawki.get(target) != null) {
            Double stwLkl = stawki.get(current).getRate();
            Double stwObc = stawki.get(target).getRate();

            return (amount * stwLkl) / stwObc;
        }
        return amount;
    }

    void wynik() {
        DecimalFormat df = new DecimalFormat("0.00");
        if (stawki.get(currentCurrencyCode) != null && stawki.get(targetCurrencyCode) != null) {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println(amount + " " + getCurrByCode(currentCurrencyCode).getName()
                    + " equals " + df.format(wymiana()) + " "
                    + getCurrByCode(targetCurrencyCode).getName());
            System.out.println("-------------------------------------------------------------------------");

        }

    }

    void convertALL() {
        DecimalFormat df = new DecimalFormat("0.00");
        Set<String> keys = stawki.keySet();
        for (String currentCodeInLoop : keys) {
            if (stawki.get(currentCurrencyCode) != null && stawki.get(currentCodeInLoop) != null) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println(amount + " " + getCurrByCode(currentCurrencyCode).getName()
                        + " equals " + df.format(wymiana(currentCurrencyCode, currentCodeInLoop, parsedAmount)) + " "
                        + getCurrByCode(currentCodeInLoop).getName());
                System.out.println("-------------------------------------------------------------------------");
            }

        }

    }

    Currency getCurrByCode(String currCode) {
        if (stawki.get(currCode) != null) {
            return stawki.get(currCode);
        } else {
            return new Currency(currCode, "Not known", 1d);
        }
    }

    String getAllCurrCodes() {
        return currencyCodes;
    }

    boolean isAmountCorrect(String amount) {
        boolean parsable = true;
        try {
            double dtest = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;

    }

    void setCurrentCurrencyCode(String currentCurrencyCode) {
        this.currentCurrencyCode = currentCurrencyCode;
    }

    void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    void setAmount(String amount) {
        this.amount = amount;
        parseAmount(amount);
    }

    private void parseAmount(String string) {
        parsedAmount = Double.parseDouble(string);
    }

}
