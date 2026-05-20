package org.example.iocexam.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    private int number1;
    private int number2;

    Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    public CalculatorService() {}
    public CalculatorService(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public int add(int number1, int number2) {
        return number1 + number2;
    }
    public int subtract(int number1, int number2) {
        return number1 - number2;
    }

    public int multiply(int number1, int number2) {
        return number1 * number2;
    }
    public int divide(int number1, int number2) {
        if (number2 == 0) {
            logger.error("Cannot divide by zero");
        }
        return number1 / number2;
    }


}
