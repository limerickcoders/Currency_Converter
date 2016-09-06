import java.util.Objects;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Created by Niko Osa on 9/5/16.
 */
class Worker {

    private Scanner in = new Scanner(System.in);
    private Kantor kantor;

    Worker(Kantor kantor) {
        this.kantor = kantor;
    }

    private Kantor getKantor() {
        return kantor;
    }

    private void communicate(FILTER filter) {
        String text;
        switch (filter) {
            case BASE_CURRENCY_CODE:
                text = "\nPlease enter current currency code in (for example PLN)";
                communicate(text);
                break;
            case TARGET_CURRENCY_CODE:
                text = "\nPlease enter target currency code (for example EUR)";
                communicate(text);
                break;
            case CURRENCY_AMOUNT:
                text = "\nPlease enter  amount (for example 120.67 or 2450)\n";
                communicate(text);
                break;
            case BASE_CODE_INCORRECT:
                text = "\nCurrency code entered does not exist please see  "
                        + "currency codes available below";
                sleepz(1000);
                communicate(text);
                sleepz(3000);
                communicate(kantor.getAllCurrCodes());
                communicate("\nPlease enter current currency code (for example USD)");

                break;
            case TARGET_CODE_INCORRECT:
                text = "\nCurrency code entered does not exist in the database please see  "
                        + "currency codes currently available";
                communicate(text);
                sleepz(1000);
                communicate(kantor.getAllCurrCodes());
                communicate("\nPlease enter target currency code (for example RUB)");
                break;
            case CURRENCY_AMOUNT_INCORRECT:
                text = "\nAmount format is incorrect please enter number in the correct format( 128.23 or 220)";
                communicate(text);
                break;
            case CURRENCY_SELECTED:
                text = "\nYou have selected";
                communicate(text);
                break;
            case ANOTHER_CURRENCY_EXCHANGE:
                text = "\nWould you like to perform another currency exchange Y/N type A if you want to see all currency rates.";
                communicate(text);
                break;
            case REPEAT_ANOTHER_CURRENCY_EXCHANGE:
                text = "\nWould you like to perform another currency exchange Y/N";
                communicate(text);
                break;
            case SAY_GOODBYE:
                text = "\nBYE!!!";
                communicate(text);
                break;
            case INCORRECT_OPTION_SELECTED:
                text = "\nIncorrect option selected please type Y , N";
                communicate(text);
                break;
        }

    }

    private void communicate(String text) {
        System.out.println(text);
    }

    private String listen(FILTER filter) {

        String text = in.nextLine().toUpperCase();

        switch (filter) {
            case BASE_CURRENCY_CODE:
                if (!Objects.equals(getKantor().getCurrByCode(text).getName(), "Not known")) {
                    communicate(FILTER.CURRENCY_SELECTED);
                    communicate(getKantor().getCurrByCode(text).getName());
                    sleepz(1000);
                    return text;
                } else {
                    communicate(FILTER.BASE_CODE_INCORRECT);
                }
                return listen(FILTER.BASE_CURRENCY_CODE);

            case TARGET_CURRENCY_CODE:
                if (!Objects.equals(getKantor().getCurrByCode(text).getName(), "Not known")) {
                    communicate(FILTER.CURRENCY_SELECTED);
                    communicate(getKantor().getCurrByCode(text).getName());
                    sleepz(1000);
                    return text;
                } else {
                    communicate(FILTER.TARGET_CODE_INCORRECT);
                }
                return listen(FILTER.TARGET_CURRENCY_CODE);
            case CURRENCY_AMOUNT:
                if (kantor.isAmountCorrect(text)) {
                    return text;
                } else {
                    communicate(FILTER.CURRENCY_AMOUNT_INCORRECT);
                    return listen(FILTER.CURRENCY_AMOUNT);
                }
            case ANOTHER_CURRENCY_EXCHANGE:
                if ("Y".equals(text)) {
                    work();
                    return text;
                }
                if ("N".equals(text)) {
                    communicate(FILTER.SAY_GOODBYE);
                    return text;

                }
                if ("A".equals(text)) {
                    kantor.convertALL();
                    communicate(FILTER.REPEAT_ANOTHER_CURRENCY_EXCHANGE);
                    return listen(FILTER.REPEAT_ANOTHER_CURRENCY_EXCHANGE);

                } else {
                    communicate(FILTER.INCORRECT_OPTION_SELECTED);
                    return listen(FILTER.ANOTHER_CURRENCY_EXCHANGE);
                }
            case REPEAT_ANOTHER_CURRENCY_EXCHANGE:
                if ("Y".equals(text)) {
                    work();
                    return text;

                }
                if ("N".equals(text)) {
                    communicate(FILTER.SAY_GOODBYE);
                    return text;

                } else {
                    communicate(FILTER.INCORRECT_OPTION_SELECTED);
                    return listen(FILTER.REPEAT_ANOTHER_CURRENCY_EXCHANGE);
                }

        }

        return "error";
    }

    private void sleepz(int mls) {
        try {
            sleep(mls);
        } catch (InterruptedException ignored) {

        }
    }

    void work() {
        getKantor().LadowanieKursowString();
        communicate(FILTER.BASE_CURRENCY_CODE);
        getKantor().setCurrentCurrencyCode(listen(FILTER.BASE_CURRENCY_CODE));
        communicate(FILTER.TARGET_CURRENCY_CODE);
        getKantor().setTargetCurrencyCode(listen(FILTER.TARGET_CURRENCY_CODE));
        communicate(FILTER.CURRENCY_AMOUNT);
        getKantor().setAmount(listen(FILTER.CURRENCY_AMOUNT));
        getKantor().wynik();
        communicate(FILTER.ANOTHER_CURRENCY_EXCHANGE);
        listen(FILTER.ANOTHER_CURRENCY_EXCHANGE);
    }

}
